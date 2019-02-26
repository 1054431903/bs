package com.fwk.common.util;

import org.slf4j.Logger;

//日志异常管理
public class ErrorUtil {

	/**
	 * logger:写入对应的日志 name:方法名称/对应路径：例：queryUsers e:异常信息
	 */
	public static void writeLoggerError(Logger logger, String name, Exception e) {
		// 把异常堆栈信息写入到日志
		logger.error("error--------" + name + ":" + e.getMessage(), e);
	}

}
