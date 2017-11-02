package com.cdl.hadoop.dc.util;


import com.cdl.hadoop.dc.model.WorkUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sleepycat.je.Transaction;

/**
 * 主要是操作工作单元WorkUnit的持久化
 * @author root
 *
 */
public class YXJESupport {
	private static final Log log = LogFactory.getLog(YXJESupport.class);
	private DataAccessor da;
	private YxlibClientDbEnv env;
	
	//0:tocreate,1:toupdate,2:todelete,3:tocallback,4:tointelligenceredo,
	//5:successed,6:failed
	
	public void addWorkUnit2Create(WorkUnit wu){
		operate(1,0,wu);
	}
	
	public YXJESupport()
	{
		super();
	}

	public YXJESupport(YxlibClientDbEnv env,DataAccessor da)
	{
		super();
		this.da = da;
		this.env = env;
	}

	public DataAccessor getDa()
	{
		return da;
	}

	public void setDa(DataAccessor da)
	{
		this.da = da;
	}

	public YxlibClientDbEnv getEnv()
	{
		return env;
	}

	public void setEnv(YxlibClientDbEnv env)
	{
		this.env = env;
	}

	public void delWorkUnit2Create(WorkUnit wu){
		operate(2,0,wu);
	}
	
	public void addWorkUnit2Update(WorkUnit wu){
		operate(1,1,wu);
	}
	
	public void delWorkUnit2Update(WorkUnit wu){
		operate(2,1,wu);
	}
	
	public void addWorkUnit2Delete(WorkUnit wu){
		operate(1,2,wu);
	}
	
	public void delWorkUnit2Delete(WorkUnit wu){
		operate(2,2,wu);
	}
	
	public void addWorkUnit2Callback(WorkUnit wu){
		operate(1,3,wu);
	}
	
	public void delWorkUnit2Callback(WorkUnit wu){
		operate(2,3,wu);
	}
	
	public void addWorkUnit2Intelligenceredo(WorkUnit wu){
		operate(1,4,wu);
	}
	
	public void delWorkUnit2Intelligenceredo(WorkUnit wu){
		operate(2,4,wu);
	}
	
	public void addWorkUnit2Successed(WorkUnit wu){
		operate(1,5,wu);
	}
	
	public void delWorkUnit2Successed(WorkUnit wu){
		operate(2,5,wu);
	}
	
	public void addWorkUnit2Failed(WorkUnit wu){
		operate(1,6,wu);
	}
	
	public void delWorkUnit2Failed(WorkUnit wu){
		operate(2,6,wu);
	}
	
	private void operate(int opType,int storeIndex,WorkUnit wu){
		Transaction txn = env.getEnv().beginTransaction(null, null);
		try { 
			switch(opType){
			case 1:
				da.getIndex()[storeIndex].put(wu);
				break;
			case 2:
				da.getIndex()[storeIndex].delete(txn, wu.getFileMeta());
				break;
			default:
				break;
			}
            txn.commit(); 
        } catch (Exception e) { 
            if (txn != null) { 
                txn.abort(); 
                txn = null; 
            }
            log.error(" YXJESupport operate error",e);
        } 
	}
}

