package com.cdl.hadoop.dc.util;

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

public class YxlibClientDbEnv {
    private static final Log log = LogFactory.getLog(YxlibClientDbEnv.class);
    private Environment myEnv;
    
    private EntityStore tocreate;//����
    private EntityStore toupdate;//����
    private EntityStore todelete;//ɾ��
    private EntityStore tocallback;//�ص�
    private EntityStore tointelligenceredo;//��������
    
    private EntityStore successed;//�ѳɹ�
    private EntityStore failed;//ʧ��
    
    private EntityStore[] stores;

    // Our constructor does nothing
    public YxlibClientDbEnv() {}

    // The setup() method opens the environment and store
    // for us.
    public void setup(File envHome, boolean readOnly) 
        throws DatabaseException {
		System.out.println("-----------ִ��YxlibClientDbEnv--setup-------------");

    	
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();

        myEnvConfig.setReadOnly(readOnly);
        myEnvConfig.setTransactional(true);
        storeConfig.setReadOnly(readOnly);
        storeConfig.setTransactional(true);

        // If the environment is opened for write, then we want to be 
        // able to create the environment and entity store if 
        // they do not exist.
        myEnvConfig.setAllowCreate(!readOnly);
        storeConfig.setAllowCreate(!readOnly);
        
        //���ó�ʱʱ�� - 1Сʱ,�˴����75����
        myEnvConfig.setLockTimeout(1*60*60*1000,TimeUnit.MILLISECONDS);
        
        myEnv = new Environment(envHome, myEnvConfig);
        
        tocreate = new EntityStore(myEnv, "tocreate", storeConfig);
        toupdate = new EntityStore(myEnv, "toupdate", storeConfig);
        todelete = new EntityStore(myEnv, "todelete", storeConfig);
        tocallback = new EntityStore(myEnv, "tocallback", storeConfig);
        tointelligenceredo = new EntityStore(myEnv, "tointelligenceredo", storeConfig);
        
        successed = new EntityStore(myEnv, "successed", storeConfig);
        failed = new EntityStore(myEnv, "failed", storeConfig);
    }

    /**
     * ��ȡBDB�洢��Ԫ
     * @return
     */
    public EntityStore[] getStore(){
		System.out.println("-----------ִ��YxlibClientDbEnv--��ȡBDB�洢��Ԫ-------------");

    	if(stores == null){
    		stores = new EntityStore[]{tocreate,toupdate,todelete,tocallback,tointelligenceredo
        			,successed,failed};
    	}
    	return stores;
    }

    public EntityStore getTocreate()
	{
		return tocreate;
	}

	public EntityStore getToupdate()
	{
		return toupdate;
	}

	public EntityStore getTodelete()
	{
		return todelete;
	}

	public EntityStore getTocallback()
	{
		return tocallback;
	}

	public EntityStore getTointelligenceredo()
	{
		return tointelligenceredo;
	}

	// Return a handle to the environment
    public Environment getEnv() {
        return myEnv;
    }

    public EntityStore getSuccessed()
	{
		return successed;
	}

	public EntityStore getFailed()
	{
		return failed;
	}

	// Close the store and environment
    public void close() {
		System.out.println("-----------ִ��YxlibClientDbEnv--close-------------");
    	
        if (tocreate != null) {
            try {
            	tocreate.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (toupdate != null) {
            try {
            	toupdate.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (todelete != null) {
            try {
            	todelete.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (tocallback != null) {
            try {
            	tocallback.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (tointelligenceredo != null) {
            try {
            	tointelligenceredo.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (successed != null) {
            try {
            	successed.close();
            } catch(DatabaseException dbe) {
                System.err.println("Error closing store: " + 
                                    dbe.toString());
               System.exit(-1);
            }
        }
        
        if (failed != null) {
            try {
            	failed.close();
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
    
    public void envLockStats()
    {
		System.out.println("-----------ִ��YxlibClientDbEnv--envLockStats-------------");

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

