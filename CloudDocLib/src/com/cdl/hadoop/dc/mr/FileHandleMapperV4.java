package com.cdl.hadoop.dc.mr;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.net.NetUtils;

import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.service.DCDeamonService;

/**
 * 下发文件转换类
 * @author root
 *
 */
public class FileHandleMapperV4 extends Mapper<Path, InputStream, Text, Text>{
	
	private static final Log log = LogFactory.getLog(FileHandleMapperV4.class);
	private Configuration conf;
	
	/**
	 * 设置任务元数据
	 * 设置会掉webservice地址
	 * 发送rpc（远方接口调用）请求，带哦用远程接口，；来触发数据节点上的进程来处理文件                         要求接受工作单元，请求不超时，死循环
	 */
	protected void map(Path key, InputStream value, Context context) throws java.io.IOException ,InterruptedException {
		
		File file = new File("/usr/han");
		file.mkdirs();
		
		conf = context.getConfiguration();
		String fileMeta = conf.get("yxlib.job.file.metadata");
		String wsAddress = conf.get("yxlib.job.webservice");
		String jobId = context.getJobID().toString();
		
		DCDeamonService ds =null;
		
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			InetSocketAddress socAddr = NetUtils.createSocketAddr(address,9529);
			ds = (DCDeamonService) RPC.waitForProxy(DCDeamonService.class, DCDeamonService.versionID, socAddr, conf);
			// 
			ds.receiveWorkUnit(fileMeta, wsAddress, jobId,1);
			log.info("fileHandleMapperv4执行到这里了---------------------------------");
			RPC.stopProxy(ds);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("接收单元异常", e);
		}
		
		
	}
}
