package com.cdl.hadoop.dc.mr;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class BinFileInputFormat extends FileInputFormat<Path,InputStream>{

	@Override
	public RecordReader<Path, InputStream> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return new BinFileRecordReader();
	}

	protected boolean isSplitable(JobContext context,Path filename) {
		return false;
	}
}
