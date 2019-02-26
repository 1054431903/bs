package com.fwk.common.util;

import javax.servlet.http.HttpServletRequest;

public class BrowKernelUtil {
	
	//获取浏览器端内核版本信息
	public static String getBrowKernel(HttpServletRequest request){
		String resultBrowKernel = "";
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		if(ObjectUtil.isNotBlank(userAgent)){
			if(userAgent.contains("chrome")){
				if(userAgent.contains("safari") && userAgent.indexOf("chrome") < userAgent.indexOf("safari")){
					resultBrowKernel = userAgent.substring(userAgent.indexOf("chrome")+7, userAgent.indexOf("safari")).trim();
				}else{
					resultBrowKernel = userAgent.substring(userAgent.indexOf("chrome")+7, userAgent.length()-1).trim();
				}
			}
		}
		return resultBrowKernel;
	}

}
