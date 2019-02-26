package com.fwk.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * 编码器
 * */
public class EncoderUtil {

	/**
	 * 字符转换成MD5
	 * */
	public static String transStrToMd5(Logger logger,String str) {
		String newstr = "";
		try {
			//确定计算方法
			MessageDigest md5=MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			//加密后的字符串
			newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			ErrorUtil.writeLoggerError(logger, "EncodeUtil--transStrToMd5", e);
		} catch (UnsupportedEncodingException e) {
			ErrorUtil.writeLoggerError(logger, "EncodeUtil--transStrToMd5", e);
		}
        return newstr;
	}
	
}
