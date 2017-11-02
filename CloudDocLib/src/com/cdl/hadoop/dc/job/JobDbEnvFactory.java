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
    		//������ִ��env�ĳ�ʼ������
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

