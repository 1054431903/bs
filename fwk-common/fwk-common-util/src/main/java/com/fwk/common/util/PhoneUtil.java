package com.fwk.common.util;

public class PhoneUtil {
	
	//通过user-agent字符串获取手机型号（mozilla/5.0 (linux; u; android 7.1.1; zh-cn; mi 6 build/nmf26x) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/53.0.2785.146 mobile safari/537.36 xiaomi/miuibrowser/9.1.3）
	public static String getPhoneModelByUA(String strUA){
		String result = "";
		try {
			if(ObjectUtil.isNotBlank(strUA)){
				result = strUA.substring(strUA.indexOf("(")+1, strUA.indexOf(")"));
				if(ObjectUtil.isNotBlank(result) && result.contains("build")){
					result = result.substring(0, result.lastIndexOf("build"));
					if(result.contains(";")){
						result = result.substring(result.lastIndexOf(";")+1, result.length());
						if(ObjectUtil.isNotBlank(result)){
							result = result.trim();
						}
					}
				}else if(result.contains("iphone")){
					result = "iphone";
				}else if(result.contains("iPod")){
					result = "iPod";
				}else if(result.contains("iPad")){
					result = "iPad";
				}else{
					result = "未知";
				}
			}
		} catch (Exception e) {
			result = "未知 ";
		}
		return result;
	}

}
