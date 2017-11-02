package com.cdl.util;

import java.util.ResourceBundle;

public class ConfigUtil {
	
	public static String getByKey(String key){
		ResourceBundle rb = ResourceBundle.getBundle("setting");// 获取配置文件
        String typeOfNoConvert = rb.getString(key);
        
        return typeOfNoConvert;
	}
}