package com.cdl.hadoop.dc.job;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

/**
 * Berkeley DB:Berkeley DB (DB)是一个高性能的，嵌入数据库编程库(内存数据库)，和C语言，C++，Java，Perl，Python，PHP，Tcl以及其他很多语言都有绑定。
 * Berkeley DB可以保存任意类型的键/值对，而且可以为一个键保存多个数据。Berkeley DB可以支持数千的并发线程同时操作数据库
 * BDB JE学习(对java中内置对象的存储)  简称BDB
 * @author mc
 *
 */
public class JobDbEnv {
    private static final Log log = LogFactory.getLog(JobDbEnv.class);
    private Environment myEnv;
    
    private EntityStore jobs;//创建
    
    // Our constructor does nothing
    public JobDbEnv() {}

    // The setup() method opens the environment and store
    // for us.
    public void setup(File envHome, boolean readOnly) 
        throws DatabaseException {
    	System.out.println("执行   JobDbEnv   setup方法");
    	//EnvironmentConfig来对数据库进行管理的，每个EnvironmentConfig对象可以管理多个数据库
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();

        myEnvConfig.setReadOnly(readOnly);
        myEnvConfig.setTransactional(true);
        storeConfig.setReadOnly(readOnly);
        storeConfig.setTransactional(true);

        myEnvConfig.setAllowCreate(!readOnly);
        storeConfig.setAllowCreate(!readOnly);
        
        myEnvConfig.setLockTimeout(1*60*60*1000,TimeUnit.MILLISECONDS);
        
        myEnv = new Environment(envHome, myEnvConfig);
  
        jobs = new EntityStore(myEnv, "jobs", storeConfig);
    }

	public EntityStore getJobs()
	{
		return jobs;
	}

	// Close the store and environment
    public void close() {
        if (jobs != null) {
            try {
            	jobs.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (myEnv != null) {
            try {
                // Finally, close the store and environment.
                myEnv.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing MyDbEnv: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
    }
    
    public Environment getMyEnv()
	{
		return myEnv;
	}
    
	public void envLockStats()
    {
        StatsConfig sc = new StatsConfig();
        
        EnvironmentStats es = myEnv.getStats(sc);
        
        StringBuffer sb = new StringBuffer();
        sb.append("\nTotal locks currently in lock table: %d\n");
        sb.append("Total transactions waiting for locks: %d\n");
        sb.append("Total write locks currently held: %d\n");
        sb.append("Total number of lock requests to date: %d\n");
        sb.append("Total number of lock waits to date: %d\n");
        sb.append("Total lock owners in lock table: %d\n");
        
        log.info(es.toString());
    }
}
