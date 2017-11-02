package com.cdl.hadoop.dc.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * 写二进制记录
 *
 */
public class BinFileRecordWriter extends RecordWriter<Text, Text>
{
    private FileSystem fs = null;
    private Configuration conf = null;
    
    public BinFileRecordWriter(Configuration conf)
    {
        this.conf = conf;
    }
    
    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException
    {
        //fs.close();
    }

    @Override
    public void write(Text key, Text value) throws IOException, InterruptedException
    {
        getFs(conf).copyFromLocalFile(new Path(key.toString()), new Path(value.toString()));
    }
    
    private FileSystem getFs(Configuration conf) throws IOException
    {
        if(fs == null)
        {
            fs = FileSystem.get(conf);
        }
        return fs;
    }
}

