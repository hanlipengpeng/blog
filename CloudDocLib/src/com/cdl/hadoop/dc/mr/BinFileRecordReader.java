package com.cdl.hadoop.dc.mr;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * 二进制文件记录阅读器
 * @author root
 *
 */
public class BinFileRecordReader extends RecordReader<Path,InputStream>{

	private Path path = null;
	private InputStream is = null;
	private boolean hasNextKey = true;//是不是还有下一个key
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		if(is!=null){
			is.close();
		}
	}

	@Override
	public Path getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return path;
	}

	@Override
	public InputStream getCurrentValue() throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return is;
	}

	/**
	 * 获取当前进度
	 */
	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if(is == null || path == null){
			return 0;
		}
		return 1;
	}

	@Override
	public void initialize(InputSplit inputSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileSplit split = (FileSplit)inputSplit;
		Configuration conf = context.getConfiguration();
		path = split.getPath();
		FileSystem fs = path.getFileSystem(conf);
		is = fs.open(split.getPath());
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		boolean temHasNextKey = hasNextKey;
		if(temHasNextKey){
			hasNextKey = !hasNextKey; //置成false
		}
		return temHasNextKey;
	}

}
