package com.fwk.common.util;

/**
 * 
 * @author wangjunwen
 * @date 2017年9月14日 上午9:56:09
 *
 */
public class CustomStringUtils {

	/**
	 * 去掉字符串左端的0
	 * 
	 * @param src
	 *            待处理的字符串
	 * @return
	 */
	public static String removeLeftEndsZero(String src) {
		if ((null == src)) {
			return "";
		} else {
			src = (src.trim().replaceFirst("[0]+", ""));
			return src;
		}
	}

	/**
	 * 去掉字符串右端的0
	 * 
	 * @param src
	 *            待处理的字符串
	 * @return
	 */
	public static String removeRightEndsZero(String src) {
		if ((null == src)) {
			return "";
		} else {
			src = (src.trim());

			StringBuffer buffer = new StringBuffer(src);

			src = (buffer.reverse().toString().replaceFirst("[0]+", ""));

			buffer = new StringBuffer(src);

			return (buffer.reverse().toString());
		}
	}

	/**
	 * 去掉字符串两端的0
	 * 
	 * @param src
	 *            待处理的字符串
	 * @return
	 */
	public static String removeBothEndsZero(String src) {
		if ((null == src)) {
			return "";
		} else {
			src = (src.trim().replaceFirst("[0]+", ""));

			StringBuffer buffer = new StringBuffer(src);

			src = (buffer.reverse().toString().replaceFirst("[0]+", ""));

			buffer = new StringBuffer(src);

			return (buffer.reverse().toString());
		}

	}
}
