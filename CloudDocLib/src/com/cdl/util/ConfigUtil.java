package com.cdl.util;

import java.util.ResourceBundle;

public class ConfigUtil {
	
	public static String getByKey(String key){
		ResourceBundle rb = ResourceBundle.getBundle("setting");// ��ȡ�����ļ�
        String typeOfNoConvert = rb.getString(key);
        
        return typeOfNoConvert;
	}
}