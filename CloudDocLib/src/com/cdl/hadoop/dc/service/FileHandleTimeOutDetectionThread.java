package com.cdl.hadoop.dc.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件处理超时计数类
 * 目前记录的超时处理阶段：pdf2swf
 * @author liwei
 *
 */
public class FileHandleTimeOutDetectionThread implements Runnable
{
    private static final Log log = LogFactory.getLog(FileHandleTimeOutDetectionThread.class);
    private long file2PdfStartTimestamp;
    private long file2PdfTimeOut;
    private Process process;
    
    private volatile boolean exit = false;
    
    public FileHandleTimeOutDetectionThread(Process process,long start,long timeout)
    {
        this.file2PdfStartTimestamp = start;
        this.file2PdfTimeOut = timeout;
        this.process = process;
    }
    
    public boolean isFile2PdfTimeOut()
    {
        return System.currentTimeMillis()-file2PdfStartTimestamp>file2PdfTimeOut;
    }
    
    public void exit()
    {
        this.exit = true;
    }
    
    public void run()
    {
        while(!exit)
        {
            if(isFile2PdfTimeOut())
            {
                try
                {
                    process.destroy();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }else{
            	try {
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
            }
        }
    }

}

