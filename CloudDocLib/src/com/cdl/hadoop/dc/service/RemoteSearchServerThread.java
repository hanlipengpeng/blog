package com.cdl.hadoop.dc.service;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdl.hadoop.dc.util.YXRAMDirectory;
import com.cdl.hadoop.dc.util.YXRemoteSearchable;

/**
 * 分布式查询服务类
 * @author root
 *
 */
public class RemoteSearchServerThread implements Runnable{

	private static final Log log = LogFactory.getLog(RemoteSearchServerThread.class);
	private YXRemoteSearchable impl; //查询实现类
	public void setImpl(YXRemoteSearchable impl) {
		this.impl = impl;
	}
	private int port;//rmi绑定的端口
	private YXRAMDirectory ramd; //查询内存索引缓存
	
	public RemoteSearchServerThread(YXRAMDirectory ramd,YXRemoteSearchable impl,int port){
		super();
		this.ramd = ramd;
		this.impl = impl;
		this.port = port;
	}
	
	/**
	 * 查询服务类主方法
	 * 1.如果无索引或者索引目录为空，线程睡眠
	 * 2.如果有索引则绑定RMI服务
	 */
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(ramd!=null&&ramd.listAll().length>0&&impl!=null){
				break;
			}else{
				try {
					Thread.sleep(60000);//睡眠一分钟
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		
		try {
			//注册RMI端口
			LocateRegistry.createRegistry(port);
			String ip = InetAddress.getLocalHost().getHostAddress();
			//绑定地址
			String bindAddress = "//"+ip+":"+port+"/Searchable";
			Naming.rebind(bindAddress, impl);
			log.info("启动RMI server,绑定地址:"+bindAddress);
		} catch (Exception e) {
			log.error("启动RMI失败");
		}
	}

}
