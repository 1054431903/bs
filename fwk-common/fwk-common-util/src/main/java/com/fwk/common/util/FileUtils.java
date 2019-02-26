package com.fwk.common.util;

import java.io.File;

/**
 * 文件处理工具类
 * 
 * @author wangjunwen
 * @date 2018年4月3日 上午11:17:43
 *
 */
public class FileUtils {

	/** 空白的字符串 */
	private static final String BLANK_STRING = "";

	/**
	 * 获取文件的扩展名
	 * 
	 * <pre>
	 * 	例如：File file = new File("/var/local/setting.xml");
	 * 		返回：.xml
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 上午11:09:24
	 *
	 * @param file
	 *            文件对象
	 * @return 如果有扩展名，则返回扩展名，否则返回空字符串
	 */
	public static String getFileExtentionName(File file) {

		if (null == file) {
			return BLANK_STRING;
		}

		return getFileExtentionName(file.getAbsolutePath());
	}

	/**
	 * 根据文件的路径获取文件的扩展名
	 * 
	 * <pre>
	 * 	例如：/var/local/setting.xml
	 * 		返回：.xml
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 上午11:09:24
	 *
	 * @param filePath
	 *            文件的路径
	 * @return 如果有扩展名，则返回扩展名，否则返回空字符串
	 */
	public static String getFileExtentionName(String filePath) {

		if ((null == filePath) || (filePath = filePath.trim()).isEmpty()) {
			return BLANK_STRING;
		}

		if (filePath.lastIndexOf(".") > 0) {
			return (filePath.substring(filePath.lastIndexOf(".")));
		}

		return BLANK_STRING;
	}

}
