package com.cdl.hadoop.dc.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * ɾ��Ŀ¼������
 *
 */
public class YXFileUtil
{

    /**
     * ɾ��Ŀ¼
     * @param dir ��ɾ��Ŀ¼
     * @param suffix ɾ���ļ���׺��
     * @param recursive �Ƿ�ݹ�ɾ��
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
     * ɾ��Ŀ¼
     * @param dir ��ɾ��Ŀ¼
     * @param suffix ɾ���ļ���׺��
     * @param recursive �Ƿ�ݹ�ɾ��
     */
    public static void deleteDir(String dir,String[] suffix,boolean recursive)
    {
        File deleteDir = new File(dir);
        deleteDir(deleteDir,suffix,recursive);
    }
    
    /**
     * ɾ��������ʱ�ļ�
     * @param libconf
     */
    public static void deleteIndexTempFiles(LibConfiguration libconf)
    {
        deleteDir(libconf.getWorkDir(), new String[]{"tmp"}, false);
    }
}
