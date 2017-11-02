package com.cdl.hadoop.dc.service;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.net.SocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.net.NetUtils;

import com.cdl.action.XngAction;
import com.cdl.hadoop.dc.job.JobSubmiter;
import com.cdl.hadoop.dc.model.DeamonStatus;
import com.cdl.hadoop.dc.model.TaskStatus;
import com.cdl.hadoop.dc.model.XNG;

public class DCDeamonMangeServiceImpl implements DCDeamonMangeService{
	
	private JobSubmiter jobSubmiter;
	
	private static final Log log = LogFactory.getLog(XngAction.class);
	private String loger;
	
	@Override
	public List<DeamonStatus> listDeamons() throws Exception {
		DeamonStatus ds = null;
		String ip = null;
		LinkedList<DeamonStatus> deamons = new LinkedList<DeamonStatus>();
		//获取主机列表
		Iterator liveIter = XNG.liveHost.keySet().iterator();
		Iterator deadIter = XNG.deadHost.keySet().iterator();
		while(liveIter.hasNext()){
			ip = (String)liveIter.next();
			ds = deamonStatus(ip);
			deamons.add(ds);
		}
		while(deadIter.hasNext()){
			ip = (String)deadIter.next();
			ds = deamonStatus(ip);
			deamons.add(ds);
		}
		return deamons;
	}
	
	public DeamonStatus deamonStatus(String host){
		final int PORT = 9529;
		DeamonStatus ds = new DeamonStatus();
		DCDeamonService idcds =null;
		InetSocketAddress socAddr = NetUtils.createSocketAddr(host,PORT);
		Configuration conf = new Configuration();
		
		try {
			//1:判断端口是不是开启
			if(portOpened(host,PORT)){
				idcds = (DCDeamonService)RPC.waitForProxy(DCDeamonService.class, DCDeamonService.versionID, socAddr, conf);
				String status = idcds.reportProgress().toString();
				RPC.stopProxy(idcds);
				String[] stats = status.split(",");
				ds.setTotal(Integer.parseInt(stats[0]));
				ds.setSuccessQueueSize(Integer.parseInt(stats[1]));
				ds.setWorkQueueSize(Integer.parseInt(stats[2]));
				ds.setIndexRebuildQueueSize(Integer.parseInt(stats[3]));
				ds.setIndexDeleteQueueSize(Integer.parseInt(stats[4]));
				ds.setFailureQueueSize(Integer.parseInt(stats[5]));
				ds.setRunningQueueSize(Integer.parseInt(stats[6]));
				ds.setHostName(host);
				ds.setIp(host);
				ds.setStatus("alive");
			}else{
				log.info("host" +host+"on port"+PORT+"is closed!!!!");
				ds = new DeamonStatus(host, host, 0, 0, 0, 0, 0, 0, 0, "dead");
			}
		} catch (Exception e) {
			ds = new DeamonStatus(host, host, 0, 0, 0, 0, 0, 0, 0, "dead");
		}
		
		return ds;
	}
	
	private boolean portOpened(String host,int port){
		boolean result = false;
		Socket socket = null;
		try {
			socket = SocketFactory.getDefault().createSocket();
			socket.setTcpNoDelay(false);
			NetUtils.connect(socket, NetUtils.createSocketAddr(host,port), 20000);
			result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean startDeamon(String host) throws Exception {
		jobSubmiter.submit("", "/libs/upload/upload.txt", XNG.YXLIB_JOB_TYPE_START_DEAMON, XNG.YXLIB_DEAMON_START, host);
		if(!XNG.liveHost.containsKey(host)){
			XNG.liveHost.put(host, host);
		}
		if(XNG.deadHost.containsKey(host)){
			XNG.deadHost.remove(host);
		}
		return true;
	}

	@Override
	public boolean stopDeamon(String host) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean restartDeamon(String host) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String reportLog(String host) throws Exception {
		DCDeamonService idcds =null;
		InetSocketAddress socAddr = NetUtils.createSocketAddr(host,9529);
		Configuration conf = new Configuration();
		try {
			idcds = (DCDeamonService)RPC.waitForProxy(DCDeamonService.class, DCDeamonService.versionID, socAddr, conf);
			Text t = idcds.reportLog(XNG.YXLIB_DEAMON_DEFAULT_LOG_SIZE);
			loger = t.toString();
			RPC.stopProxy(idcds);
		} catch (Exception e) {
			log.error("DCDeaminMangeService汇报日志失败", e);
		}
		return loger;
	}

	@Override
	public List<TaskStatus> reportWorkUnitQueue(String host, int type)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void killCurrentWorkUnit(String host) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setJobSubmiter(JobSubmiter jobSubmiter) {
		this.jobSubmiter = jobSubmiter;
	}
	

}
