package com.cdl.hadoop.dc.util;

import com.cdl.hadoop.dc.model.XNG;

import org.apache.hadoop.conf.Configuration;

public class LibConfiguration
{
    private Configuration config;
    
    public LibConfiguration(Configuration config)
    {
        this.config = config;
    }
    
    public String getWorkDir()
    {
        return getDaemonBaseDir()+"work/";
    }
    
    public String getDBDir()
    {
        return getDaemonBaseDir()+"db/";
    }
    
    public String getJDKHOME()
    {
        String jdkhome = config.get("yxlib.dcdaemon.jdkhome","/usr/tools/jdk1.7.0_79");
        if(!jdkhome.endsWith("/"))
        {
            jdkhome = jdkhome+"/";
        }
        return jdkhome;
    }
    
    private String getDaemonBaseDir()
    {
        String baseDir = config.get("yxlib.dcdaemon.basedir",XNG.YXLIB_DAEMON_BASEDIR);
        
        if(!baseDir.endsWith("/"))
        {
            baseDir = baseDir+"/";
        }
        
        return baseDir;
    }
    
    public String getIgnoreFileType() {
    	String ignoreFileType = config.get("yxlib.dcdeamon.ignore.filetype", XNG.YXLIB_DCDEAMON_IGNORE_FILETYPE);
    	return ignoreFileType;
    }
}

