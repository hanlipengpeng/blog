package com.cdl.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 利用反射原理加载bean
 * @author root
 *
 */
public class SpringServiceUtil implements BeanFactoryAware{
	private static BeanFactory beanFactory = null;
	private static SpringServiceUtil servlocator =null;
	public void setBeanFactory(BeanFactory factory) throws BeansException{
		beanFactory = factory;
	}
	
	public static BeanFactory getBeanFactory(){
		return beanFactory;
	}
	
	public static SpringServiceUtil getInstance(){
		if(servlocator ==null){
			servlocator = (SpringServiceUtil)beanFactory.getBean("SpringServiceUtil");
		}
		return servlocator;
	}
	
	//根据提供的bean名称得到相应的服务类
	public static Object getService(String servName){
		return getInstance().getBeanFactory().getBean(servName);
	}
	
	//根据提供的bean名称带到指定类型的服务类
	public static Object getService(String servName,Class clazz){
		return getInstance().getBeanFactory().getBean(servName,clazz);
	}
	
}
