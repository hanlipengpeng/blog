package com.cdl.hadoop.dc.util;

public class YXSimpleLock {
	//volatile  最终变量
	private volatile boolean lockable = true;
	private String holder;
	synchronized public boolean obtainLock(){
		if(lockable){
			lockable = false;
			return true;
		}
		return lockable;
	}
	public void releaseLock(){
		lockable = true;
	}
	
	public String getHolder(){
		return holder;
	}
	
	public void setHolder(String holder){
		this.holder=holder;
	}
}
