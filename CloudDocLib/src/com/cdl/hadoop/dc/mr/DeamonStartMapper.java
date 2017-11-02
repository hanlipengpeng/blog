package com.cdl.hadoop.dc.mr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Shell.ExitCodeException;
import org.apache.hadoop.util.Shell.ShellCommandExecutor;

import com.cdl.hadoop.dc.job.LibJob;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.service.DCDeamonThread;
import com.cdl.hadoop.dc.util.LibConfiguration;

/**
 * 文件转换启动守护进程类
 * 
 * 检查是否已有守护进程开启 如果没有，新建一个守护进程
 * 
 */
public class DeamonStartMapper extends Mapper<Path, InputStream, Text, Text> {
	private static final Log log = LogFactory.getLog(DeamonStartMapper.class);
	
	/**
	 * 原有进程pid号
	 */
	private static int pid;
	private Configuration conf;
	private LibConfiguration libconf;

	/**
	 * 功能：根据配置信息启动、停止或重启动守护进程
	 */
	@Override
	protected void map(Path key, InputStream value, Context context)throws IOException, InterruptedException {
		File file =new File("/usr/hanlipengpengpeng");
		file.mkdirs();
		conf = context.getConfiguration();
		libconf = new LibConfiguration(conf);
		int operType = conf.getInt("yxlib.dcdaemon.operation", XNG.YXLIB_DEAMON_RESTART);
		if(deamonIsLiving())
		{
            killDeamon();
		}
		switch(operType)
		{
		case XNG.YXLIB_DEAMON_START:
			 log.info("执行启动命令["+operType+"]");
			 //这里一定要注意，没有BREAK，相当于没有停止
        case XNG.YXLIB_DEAMON_RESTART:
   		     log.info("执行启动命令["+operType+"]");
            startDeamon();
            break;
		default:
		    break;
		}
}

	/**
	 * 检测守护进程是否活着,主要实现的流程是检测进程pid文件是否存在。这里一定要注意更换DCDeamonThread
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	//TODO 这里应该是检测对应服务程序的响应情况
	private boolean deamonIsLiving() throws InterruptedException, IOException {
		pid = readJVMPid();
		log.info("old pid is : " + pid);
		try{
            String jpsCmd = libconf.getJDKHOME()+"bin/jps";
            StringBuffer jpsBuffer = new StringBuffer();
            Process process = Runtime.getRuntime().exec(jpsCmd);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line = null;
            while((line = input.readLine()) != null)
            {
                jpsBuffer.append(line);
            }
            return jpsBuffer.indexOf("DCDeamonThread")!=-1;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
	}

	/**
	 * 调用命令中止守护进程
	 * @throws IOException
	 */
	private void killDeamon() throws IOException {
	    log.info("killing deamon...");
		try {
			ShellCommandExecutor shell = new ShellCommandExecutor(new String[] {"bash", "-c",FileUtil.makeShellPath(new File("/bin/kill")) + " " + pid });
			shell.execute();
		} catch (ExitCodeException e) {
			// 忽略异常
			e.printStackTrace();
		}
	}

	/**
	 * 启动守护进程
	 * 1：清除原有工作目录
	 * 2：建立新工作目录
	 * 3：拷贝本地库到工作目录
	 * 4：解压类到工作目录
	 * 5：构建命令行启动参数
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void startDeamon() throws InterruptedException, IOException {
		
	    FileSystem fs = FileSystem.get(conf);
	    String workDirs = libconf.getWorkDir();
	    System.out.println("workDirs"+workDirs);
	    File workDir = new File(workDirs);
	    
        if(workDir.exists())
        {
            try{
                FileUtils.cleanDirectory(workDir);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        else
        {
            if(workDir.mkdir())
            {
                if (!workDir.isDirectory()) {
                    log.fatal("Mkdirs failed to create " + workDir.toString());
                  }
            }
        }
		
		fs.copyToLocalFile(false, new Path("/libs/jod/libjmupdf32.so"), new Path(workDirs+"libjmupdf32.so"));
		fs.copyToLocalFile(false, new Path("/libs/jod/libjmupdf64.so"), new Path(workDirs+"libjmupdf64.so"));
		
		LibJob.unJar(new File(conf.get("mapred.jar")),workDir);
		
		String cmd = buildClassPath(workDir);
		
		System.out.println(cmd);
		log.info("cmd:\n"+cmd);
		
		ProcessBuilder shb = new ProcessBuilder(new String[] { "bash","-c", "chmod +x "+workDirs+"*.sh" });
		Process shp = shb.start();
		
//		FileOutputStream out=new FileOutputStream(new File("/usr/tools/log/logDeamonStartMapper.log"));
//		out.write(cmd.getBytes());
//		out.flush();
//		out.close();
		
		try {
			shp.waitFor();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}finally{
			shp.destroy();
		}
		
		ProcessBuilder builder = new ProcessBuilder(new String[] { "bash","-c", cmd.toString() });
		Process p = builder.start();
		//p.destroy();
	}

	/**
	 * 构建classpath
	 * 1：系统本身类路径
	 * 2：第3方依赖类路径
	 * 3：工作目录
	 * @param workDir
	 * @return
	 * @throws IOException
	 */
	private String buildClassPath(File workDir) throws IOException {
		URI[] archives = DistributedCache.getCacheArchives(conf);

		String sep = System.getProperty("path.separator");
		StringBuffer classPath = new StringBuffer();
		classPath.append(System.getProperty("java.class.path"));
		classPath.append(sep);

		Path[] archiveClasspaths = DistributedCache.getArchiveClassPaths(conf);
		if (archiveClasspaths != null && archives != null) {
			Path[] localArchives = DistributedCache.getLocalCacheArchives(conf);
			if (localArchives != null) {
				for (int i = 0; i < archives.length; i++) {
					for (int j = 0; j < archiveClasspaths.length; j++) {
						if (archives[i].getPath().equals(
								archiveClasspaths[j].toString())) {
							classPath.append(sep);
							classPath.append(localArchives[i].toString());
							log.info(localArchives[i].toString());//***********
							System.out.println(localArchives[i].toString());//*********
						}
					}
				}
			}
		}

		classPath.append(sep);
		classPath.append(workDir);

		Vector<String> vargs = new Vector<String>(8);
		File jvm = new File(new File(System.getProperty("java.home"), "bin"), "java");

		vargs.add(jvm.toString());
		int HEAP_MIN = conf.getInt("yxlib.dcdeamon.memory.min", 256);
		int HEAP_MAX = conf.getInt("yxlib.dcdeamon.memory.max", 512);
		vargs.add("-Xms"+HEAP_MIN+"M");
		vargs.add("-Xmx"+HEAP_MAX+"M");

		String libraryPath = System.getProperty("java.library.path");
		if (libraryPath == null) {
			libraryPath = workDir.getAbsolutePath();
		} else {
			libraryPath += sep + workDir;
		}
		vargs.add("-Djava.library.path=" + libraryPath);
		vargs.add("-Djava.io.tmpdir=" + workDir.toString());
		
		//liwei@20110307 增加jmx管控端口
		vargs.add("-Dcom.sun.management.jmxremote.port=9531");
		vargs.add("-Dcom.sun.management.jmxremote.ssl=false");
		vargs.add("-Dcom.sun.management.jmxremote.authenticate=false");

		vargs.add("-classpath");
		vargs.add(classPath.toString());

		vargs.add(DCDeamonThread.class.getName()); // main of Child

		Map<String, String> env = new HashMap<String, String>();
		StringBuffer ldLibraryPath = new StringBuffer();
		ldLibraryPath.append(workDir.toString());
		String oldLdLibraryPath = null;
		oldLdLibraryPath = System.getenv("LD_LIBRARY_PATH");
		if (oldLdLibraryPath != null) {
			ldLibraryPath.append(sep);
			ldLibraryPath.append(oldLdLibraryPath);
		}
		env.put("LD_LIBRARY_PATH", ldLibraryPath.toString());

		StringBuffer cmd = new StringBuffer();
		cmd.append("echo $$ > ");
		cmd.append(workDir.toString());
		cmd.append("/pid;");
		cmd.append(" exec ");
		for (int j = 0; j < vargs.size(); j++) {
			cmd.append("'");
			cmd.append(vargs.get(j));
			cmd.append("' ");
		}
		cmd.append(" < /dev/null  1>> ");
		cmd.append(workDir.toString());
		cmd.append("/stdout.log");
		cmd.append(" 2>> ");
		cmd.append(workDir.toString());
		cmd.append("/stderr.log");

		log.info("执行DeamonStartMapper批处理命令:{--"+cmd+"--}");
		return cmd.toString();
	}

	/**
	 * 读取pid文件
	 * @return
	 */
	private int readJVMPid() {
		String line = "0"; // 用来保存第一行读取的内容
		File pid = new File(libconf.getWorkDir()+"pid");
		if (pid.exists() && pid.canRead()) {
			InputStream is = null;
			try {
				is = new FileInputStream(pid);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(line)) {
			return Integer.valueOf(line.trim());
		}

		return 0;
	}
}
