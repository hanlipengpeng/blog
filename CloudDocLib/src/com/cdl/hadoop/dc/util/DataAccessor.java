package com.cdl.hadoop.dc.util;

import com.cdl.hadoop.dc.model.WorkUnit;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

/**
 * bdb访问辅助类
 *
 */
public class DataAccessor {
	// 待文档转换的任务单元
    public PrimaryIndex<String,WorkUnit> tocreate;
    // 待索引更新的任务单元
    public PrimaryIndex<String,WorkUnit> toupdate;
    // 待索引删除的任务单元
    public PrimaryIndex<String,WorkUnit> todelete;
    public PrimaryIndex<String,WorkUnit> tocallback;
    public PrimaryIndex<String,WorkUnit> tointelligenceredo;

    // 已成功执行的任务
    public PrimaryIndex<String,WorkUnit> successed;
    // 已失败的任务处理单元
    public PrimaryIndex<String,WorkUnit> failed;
    
    private PrimaryIndex<String,WorkUnit>[] indexs;
    
    public DataAccessor(EntityStore[] store)throws DatabaseException {
        
    	tocreate = store[0].getPrimaryIndex(String.class, WorkUnit.class);
        
    	toupdate = store[1].getPrimaryIndex(String.class, WorkUnit.class);
        
    	todelete = store[2].getPrimaryIndex(String.class, WorkUnit.class);
        
    	tocallback = store[3].getPrimaryIndex(String.class, WorkUnit.class);
        
    	tointelligenceredo = store[4].getPrimaryIndex(String.class, WorkUnit.class);
    	
    	successed = store[5].getPrimaryIndex(String.class, WorkUnit.class);
    	
    	failed = store[6].getPrimaryIndex(String.class, WorkUnit.class);
    }

    
    public PrimaryIndex<String,WorkUnit>[] getIndex(){
    	if(indexs == null){
    		indexs = new PrimaryIndex[]{tocreate,toupdate,todelete,tocallback,tointelligenceredo,successed,failed};
    	}
    	return indexs;
    }
}
