package test.mr;

import com.cdl.hadoop.dc.service.DCDeamonThread;
import com.cdl.hadoop.dc.service.FileHandleService;

public class T1 {
	public static void main(String[] args) {
		System.out.println(DCDeamonThread.class.getName());
		FileHandleService f = new FileHandleService();
		System.out.println(f.toString());
	}

}
