package com.cdl.util;

import java.util.UUID;

public class UUIDCreate {

	public static String uuidStr(){
		String uuidstr = UUID.randomUUID().toString();
		StringBuffer sb = new StringBuffer(32);
		for(int i=0;i<uuidstr.length();i++){
			char c = uuidstr.charAt(i);
			if(c!='_'&&c!='-'){
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
