package com.cdl.hadoop.dc.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * 删除目录工具类
 *
 */
public class YXFileUtil
{

    /**
     * 删除目录
     * @param dir 待删除目录
     * @param suffix 删除文件后缀名
     * @param recursive 是否递归删除
     */
    public static void deleteDir(File dir,String[] suffix,boolean recursive)
    {
        Collection tmps = FileUtils.listFiles(dir, suffix, false);
        Iterator it = tmps.iterator();
        while(it.hasNext())
        {
            try
            {
                FileUtils.forceDelete((File)it.next());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 删除目录
     * @param dir 待删除目录
     * @param suffix 删除文件后缀名
     * @param recursive 是否递归删除
     */
    public static void deleteDir(String dir,String[] suffix,boolean recursive)
    {
        File deleteDir = new File(dir);
        deleteDir(deleteDir,suffix,recursive);
    }
    
    /**
     * 删除索引临时文件
     * @param libconf
     */
    public static void deleteIndexTempFiles(LibConfiguration libconf)
    {
        deleteDir(libconf.getWorkDir(), new String[]{"tmp"}, false);
    }
}
