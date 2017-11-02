package com.cdl.hadoop.dc.job;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.cdl.util.PubFun;
import com.cdl.hadoop.dc.job.JobSubmiter;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.mr.BinFileInputFormat;
import com.cdl.hadoop.dc.mr.BinFileOutputFormat;
import com.cdl.hadoop.dc.mr.DeamonStartMapper;
import com.cdl.hadoop.dc.mr.FileHandleMapperV4;


/**
 * @author han
 * 
 */
public class InternalSubmiter {
	private static final Log log = LogFactory.getLog(InternalSubmiter.class);
	/**
	 * 提交任务类
	 */
	public void submitJob(WKJob wkj)
	{
		String fileMetaData = wkj.getFileMetaData();
		String input = wkj.getInput();
		int jobType = wkj.getJobType();
		int indexType = wkj.getIndexType();
		String extra = wkj.getExtra();
		//打jar包
		File jarFile = prepareJar4FileConverterJob(wkj);
		if (jarFile == null) {
			log.error("打包任务jar包失败,任务直接返回.");
			return;
		}
		
		//job任务的参数设置
		Job job = null;
		try {
			job = new Job();
			addThirdLib2Job((JobConf) job.getConfiguration());
		} catch (IOException e) {
			log.error("job设置第3方依赖包异常！", e);
			return;
		}

		((JobConf) job.getConfiguration()).set("hadoop.job.history.user.location", "none");
		((JobConf) job.getConfiguration()).set("mapred.reduce.tasks", "0");
		((JobConf) job.getConfiguration()).setJar(jarFile.toString());
		((JobConf) job.getConfiguration()).set("yxlib.job.file.metadata",
				fileMetaData);

		try {
			Path inputPath = new Path(input);
			FileInputFormat.addInputPath(job, new Path(input));
			FileOutputFormat.setOutputPath(job, inputPath.getParent());
		} catch (IOException e) {
			log.error("job设置输入输出目录异常！", e);
			return;
		}

		// 文件转换，索引初次建立
		if (jobType == XNG.YXLIB_JOB_TYPE_FILE_CONVERT) {
			// FIXME liwei@此处由于将主机名解析为对应节点IP，如果提交任务的本机没有正确的主机名与IP地址映射
			// 此处可能出现未知的IP地址
			String ips = buildInputHosts(job, input);
			if (StringUtils.isNotBlank(ips)) {
				((JobConf) job.getConfiguration()).set("job.desired.ip", ips);
			}

			job.setInputFormatClass(BinFileInputFormat.class);
			job.setMapperClass(FileHandleMapperV4.class);
			job.setOutputFormatClass(BinFileOutputFormat.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
		}
		// 索引更新或删除
		else if (jobType == XNG.YXLIB_JOB_TYPE_INDEX_BUILD) {
			((JobConf) job.getConfiguration()).set("job.desired.ip", extra);

			job.setInputFormatClass(BinFileInputFormat.class);
//			job.setMapperClass(FileIndexMapper.class);
			job.setOutputFormatClass(BinFileOutputFormat.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			if (indexType == XNG.YXLIB_JOB_INDEX_TYPE_DELETE) {
				((JobConf) job.getConfiguration())
						.setInt("yxlib.job.index.type",
								XNG.YXLIB_JOB_INDEX_TYPE_DELETE);
			} else if (indexType == XNG.YXLIB_JOB_INDEX_TYPE_UPDATE) {
				((JobConf) job.getConfiguration())
						.setInt("yxlib.job.index.type",
								XNG.YXLIB_JOB_INDEX_TYPE_UPDATE);
			} else {
				log.info("索引类型不能识别，0：索引更新，1：索引删除");
				return;
			}
		}
		// 守护进程管理
		else if (jobType == XNG.YXLIB_JOB_TYPE_START_DEAMON) {
			// 此处借用indexType变量
			((JobConf) job.getConfiguration()).setInt("yxlib.dcdaemon.operation", indexType);
			((JobConf) job.getConfiguration()).set("job.desired.ip", extra);
			job.setInputFormatClass(BinFileInputFormat.class);
			job.setMapperClass(DeamonStartMapper.class);
			job.setOutputFormatClass(BinFileOutputFormat.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
		} else {
			log.info("提交任务类型不能识别，目前支持，1：文件转换，2：索引建立，4：管理守护进程");
			return;
		}

		try {
			System.out.println("执行到这里1111");
			job.submit();         
			System.out.println("任务提交完成");
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 打包job jar文件
	 * 
	 */
	private File prepareJar4FileConverterJob(WKJob wkj)
	{
		int jobType = wkj.getJobType();
		int indexType = wkj.getIndexType();

		File jarFile = null;
		String jobName = "unknown";

		switch (jobType) {
		case XNG.YXLIB_JOB_TYPE_FILE_CONVERT:
			jobName = "fileconverter";
			break;
		case XNG.YXLIB_JOB_TYPE_INDEX_BUILD:
			if (indexType == XNG.YXLIB_JOB_INDEX_TYPE_DELETE)
				jobName = "indexrebuilder-delete";
			else if (indexType == XNG.YXLIB_JOB_INDEX_TYPE_UPDATE)
				jobName = "indexrebuilder-update";
			break;
		case XNG.YXLIB_JOB_TYPE_START_DEAMON:
			jobName = "deamon";
			break;
		default:
			break;
		}

		try {
			synchronized ("hadoop-job-jar-build") {
				jarFile = new File(getAppLocalPath() + File.separator
						+ XNG.JOB_NAME + jobName + ".jar");
				if (!jarFile.exists())
					jarFile = LibJob.createTempJar(getAppLocalPath(),
							XNG.JOB_NAME + jobName);
			}
		} catch (IOException e) {
			// 输入错误,在调用者处作异常处理
			e.printStackTrace();
		}

		return jarFile;

	}

	/**
	 * 添加第三方依赖到运行的任务中来
	 *  任务运行配置文件
	 */
	private void addThirdLib2Job(JobConf conf)
	{

		// TODO liwei@对任务运行的环境，此处注意设置，保持提交与运行时路径分隔符的一致，否则会报class not found错误
		System.setProperty("path.separator", ":");

		String paths = conf.get("yxlib.job.third.libs");
		String path[] = null;

		if (paths != null && !paths.equals("")) {
			path = paths.split(",");
		} else {
			path = XNG.DEPENDENCY_LIB;
		}

		for (int j = 0; j < path.length; j++) {
			try {
				FileSystem fs = FileSystem.get(conf);
				String p = path[j].trim();
				FileStatus[] fstatus = fs.listStatus(new Path(p));
				if (fstatus != null) {
					for (FileStatus parser : fstatus) {
						// TODO liwei@此处用相对路径
						DistributedCache.addFileToClassPath(new Path(p), conf);
					}
				}
			} catch (IOException e) {
				log.error("",e);
			}
		}
	}

	/**
	 * 指定运行主机 此处指定任务所运行在的task node上，原则如下： 1：找到此任务处理文件的文件块所在主机
	 * 2：将此主机添加到任务允许工作主机上来 如果此主机已在配置文件中指定，以配置文件为主
	 * 
	 * @param job
	 *            任务
	 * @return 可运行此主机的列表，如：192.168.1.170,192.168.1.205
	 */
	private String buildInputHosts(Job job, String input)
	{

		String desiredIp = job.getConfiguration().get("job.desired.ip");
		if (desiredIp != null) {
			return desiredIp;
		}

		if (StringUtils.isBlank(input)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			Path path = new Path(input);
			FileSystem fs = path.getFileSystem(job.getConfiguration());
			FileStatus fstatus = fs.getFileStatus(path);
			long length = fstatus.getLen();
			BlockLocation[] blkLocations = fs.getFileBlockLocations(fstatus, 0,
					length);
			for (BlockLocation location : blkLocations) {
				String h[] = location.getHosts();
				String ip = null;
				for (int j = 0; j < h.length; j++) {
					try {
						ip = null;
						ip = InetAddress.getByName(h[j]).getHostAddress();
					} catch (UnknownHostException e) {
						log.info("主机名：" + h[j] + " 无法解析为对应IP地址.", e);
					}

					sb.append(ip);
					sb.append(",");
				}
			}
		} catch (IOException e1) {
			log.warn("输入文件 "+input+" 在HDFS上未找到存储块.",e1);
		}

		return sb.length()==0?null:sb.substring(0, sb.length() - 1);
	}

	/**
	 * 得到当前任务提交类的路径
	 * 
	 * @return 应用部署路径
	 */
	public static String getAppLocalPath()
	{
		String s = JobSubmiter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		s = PubFun.unescape(s);
		File file = new File(s);
		s = file.getParent();
		System.out.println("当前应用部署路径"+s);
		return s;
	}
}

