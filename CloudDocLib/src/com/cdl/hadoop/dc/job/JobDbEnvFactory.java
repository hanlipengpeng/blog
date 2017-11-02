package com.cdl.hadoop.dc.job;

import java.io.File;

/**
 * @author
 *
 */
public class JobDbEnvFactory {

	private static JobDbEnv env;
	
    public synchronized static JobDbEnv instance(){
    	if(env == null){
    		env = new JobDbEnv();
    		System.out.println(System.getProperty("java.io.tmpdir"));
    		//在这里执行env的初始化方法
    		env.setup(new File(System.getProperty("java.io.tmpdir")), false);
    	}
    	return env;
    }
    
    public synchronized static void close(){
    	if(env != null){
    		env.close();
    	}
    }

}

