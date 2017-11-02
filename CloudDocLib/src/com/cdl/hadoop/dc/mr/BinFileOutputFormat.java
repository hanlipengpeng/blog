package com.cdl.hadoop.dc.mr;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InvalidJobConfException;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 二进制输出目录格式
 *
 */
public class BinFileOutputFormat extends FileOutputFormat<Text, Text>
{
    /**
     * 检视输出目录
     */
    @Override
    public void checkOutputSpecs(JobContext job) throws IOException
    {
        Path outDir = getOutputPath(job);
        Path[] inDirs = FileInputFormat.getInputPaths(job);
        
        if (outDir == null) {
            throw new InvalidJobConfException("Output directory not set.");
          }
        
        // 本应用只能有一个输入目录
        if(inDirs == null || inDirs.length > 1)
        {
            throw new InvalidJobConfException("input directory not validate.");
        }
        
        FileSystem fs = FileSystem.get(job.getConfiguration());
        
        // 取输入目录或输入文件所在目录
        Path directory = null;
        if(fs.isDirectory(inDirs[0]))
        {
        	directory = inDirs[0];
        }
        else
        {
        	directory = inDirs[0].getParent();
        }
        
        // 输入目录和输出目录必须一致
        if(!outDir.toString().equals(directory.toString())
            && !getAbsolutePath(job.getConfiguration().get("fs.default.name"), outDir.toString()).equals(directory.toString()))
        {
            throw new InvalidJobConfException("Output directory not equals Input directory. input: " 
                + directory.toString() + ", output: " + getAbsolutePath(job.getConfiguration().get("fs.default.name"), outDir.toString()));
        }
    }
    
    /**
     * 通过根目录和相对路径，获取文件或文件夹的绝对路径
     * @param rootPath 根目录
     * @param path 相对路径
     * @return 绝对路径
     */
    protected String getAbsolutePath(String rootPath, String path)
    {
        if(path.startsWith(rootPath))
        {
            return path;
        }
        else if(path.startsWith("/"))
        {
            return path.replaceFirst("/", rootPath);
        }
        else
        {
            return rootPath + path;
        }
    }

    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext job) 
        throws IOException, InterruptedException
    {
        return new BinFileRecordWriter(job.getConfiguration());
    }
}
