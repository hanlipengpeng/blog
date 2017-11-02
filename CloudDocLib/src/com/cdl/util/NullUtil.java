package com.cdl.util;

import org.springframework.util.StringUtils;

/**
 * /**
 * null转换对象
 *  增加方法isEmpty(str),isNotEmpty(str), 判断字符串是否为空 
 *  增加方法getString(src,default), 返回一个非空字符串.
 */
public class NullUtil {
	
	/**
	 * 转换字符串null为""
	 * @param s 需要转换的字符
	 * @return
	 */
	public static String convertNullToEmpty(String s){
		if(s==null || s.equals("null")){
			s = "";
		}
		return s;
	}
	
	/**
	 * 转换字符串null为""
	 * @param s 需要转换的字符
	 * @param comma 转换自定符号
	 * @param isEnd 是否是最后一个
	 * @return
	 */
	public static String nullToEmpty(String s, String comma,boolean isEnd){
		if(!isEnd) {
			if(s==null || s.equals("null")){
				s = ""+comma+"";
			} else {
				s = s+""+comma+"";
			}
		} else {
			if(s==null || s.equals("null")){
				s = "";
			} else {
				s = s+"";
			}
		}
		
		return s;
	}
	
	/**
	 * 判断字符串str是否为非空
	 * @author 龙宏海 2008-04-02
	 * @param str 被判断的字符串
	 * @return
	 */
	public static boolean isEmpty(String str){
		if( str == null || str.trim().equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串str是否为空
	 * @author 龙宏海 2008-04-02
	 * @param str 被判断的字符串
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str != null) && (!str.trim().equals(""))){
			return true;
		}
		return false;
	}
	
	/**
	 * 如果传入的第一个字符串为空或为""，那么返回其第二个参数的值。
	 * 
	 * @param src
	 * @param defValue
	 * @return
	 */
	public static String getString(String src,String defValue){
		if(isEmpty(src)){
			return defValue;
		}
		return src;
	}
	
	public static String getString(Integer src,String defValue){
		String tmp = null;
		if(src!=null){
			tmp = String.valueOf(src);
		}else{
			tmp = defValue;
		}
		return tmp;
	}
	
	public static String getString(Long src,String defValue){
		String tmp = null;
		if(src!=null){
			tmp = String.valueOf(src);
		}else{
			tmp = defValue;
		}
		return tmp;
	}
	
	public static Long getLong(String src,Long def){
		if(isEmpty(src)){
			return def;
		}
		Long l = null;
		try{
			l = Long.parseLong(src);
		}catch(Exception e){
			l = def;
		}
		return l;
	}
	
	public static Long getLong(String src,String def){
		if(isEmpty(src)){
			return Long.parseLong(def);
		}
		Long l = null;
		try{
			l = Long.parseLong(src);
		}catch(Exception e){
			l = Long.parseLong(def);
		}
		return l;
	}
	
	public static void main(String[] args){
		String s = "null,";
		String c = ",";
		String ids = "1,2,3,4,5";
		String id = "2";
		//System.out.println(NullUtil.nullToEmpty(s,c,false));
		String r = StringUtils.delete(ids,id+",");
		System.out.println(r);
	}

}
