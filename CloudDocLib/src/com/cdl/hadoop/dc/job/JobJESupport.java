package com.cdl.hadoop.dc.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.Transaction;

/**
 *
 */
public class JobJESupport {
	private static final Log log = LogFactory.getLog(JobJESupport.class);
    
	private JobDbEnv env = JobDbEnvFactory.instance();
	private JobDataAccessor da = new JobDataAccessor(env.getJobs());
	
	public void addJob(WKJob job){
		Transaction txn = env.getMyEnv().beginTransaction(null, null);
		try { 
			da.jobs.put(job);
            txn.commit(); 
        } catch (Exception e) { 
            if (txn != null) { 
                txn.abort(); 
                txn = null; 
            }
            log.error("JobJESupport  AddJOb error",e);
        } 
	}
	
	public void delJob(WKJob job){
		Transaction txn = env.getMyEnv().beginTransaction(null, null);
		try { 
			da.jobs.delete(job.getFileMetaData());
            txn.commit(); 
        } catch (Exception e) { 
            if (txn != null) { 
                txn.abort(); 
                txn = null; 
            }
            log.error("JobJESupport  delJob error",e);
        } 
	}

	public JobDbEnv getEnv()
	{
		return env;
	}

	public JobDataAccessor getDa()
	{
		return da;
	}
	
}
