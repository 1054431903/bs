package com.fwk.common.util.language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class LanguageUtil {
	
	//读取配置文件（key）
	public static String getLanguageByKey(String key){
		Properties properties = new Properties();
		try {
			InputStream inputStream = LanguageUtil.class.getResourceAsStream("/application-language.properties");
			properties.load(inputStream);
			inputStream.close();
			return properties.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "国际化文件（application-language.properties）读取失败！";
		} catch (IOException e) {
			e.printStackTrace();
			return "国际化文件（application-language.properties）加载失败！";
		}
	}
	
	//获取内容并传参
	public static String getLanguageByKeyAndParam(String key,Object ... arguments){
		return MessageFormat.format(getLanguageByKey(key), arguments);
	}
	
	//根据配置文件获取对应方法的中文名称
	public static String getActNameByMethod(String methodName){
		Properties properties = new Properties();
		try {
			InputStream inputStream = LanguageUtil.class.getResourceAsStream("/application-log-action-name.properties");
			properties.load(inputStream);
			inputStream.close();
			return properties.getProperty(methodName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "国际化文件（方法对应名称:application-log-action-name.properties）读取失败！";
		} catch (IOException e) {
			e.printStackTrace();
			return "国际化文件（方法对应名称:application-log-action-name.properties）加载失败！";
		}
	}
	
	//获取日志为业务类的方法名称
	public static String checkMethodByLoggerType(String methodName){
		Properties properties = new Properties();
		try {
			InputStream inputStream = LanguageUtil.class.getResourceAsStream("/application-log-type-execute.properties");
			properties.load(inputStream);
			inputStream.close();
			return properties.getProperty(methodName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "国际化文件（日志为业务类的方法名:application-log-type-execute.properties）读取失败！";
		} catch (IOException e) {
			e.printStackTrace();
			return "国际化文件（日志为业务类的方法名:application-log-type-execute.properties）加载失败！";
		}
	}
	
	//获取需要添加关注度的数据
	public static String getArchiveAttent(String methodName){
		Properties properties = new Properties();
		try {
			InputStream inputStream = LanguageUtil.class.getResourceAsStream("/application-archive-attent-action-name.properties");
			properties.load(inputStream);
			inputStream.close();
			return properties.getProperty(methodName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "国际化文件（产品关注度的方法名:application-archive-attent-action-name.properties）读取失败！";
		} catch (IOException e) {
			e.printStackTrace();
			return "国际化文件（产品关注度的方法名:application-archive-attent-action-name.properties）加载失败！";
		}
	}

}
