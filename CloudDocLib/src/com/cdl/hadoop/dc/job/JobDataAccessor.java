package com.cdl.hadoop.dc.job;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

/**
 * berkeley DB的简称
 * bdb访问辅助类
 *
 */
public class JobDataAccessor {
	public PrimaryIndex<String,WKJob> jobs;
    public JobDataAccessor(EntityStore store)throws DatabaseException {        
    	jobs = store.getPrimaryIndex(String.class, WKJob.class);
    }
    
}
