package com.fwk.common.util.request;

import java.util.HashMap;
import java.util.Map;

//拦截对应下载文件权限的名称（方法名）
public class InterceptFileLoad {

	public static Map<String,Object> interceptFileLoadMethodMap = new HashMap<String,Object>();

	static {
		// key:方法名，value:对应权限表内solecode字段值
		interceptFileLoadMethodMap.put("system-fileLoad", "ACCOUNT_MANAGE_EXPORT");
		interceptFileLoadMethodMap.put("product-exportTraceArchivesForReportStatistics", "STATIC_REPORT_EXPORT");// 导出首页报表统计
		interceptFileLoadMethodMap.put("enterprise-exportEnterprise", "ENTERPRISE_LIST_RECYCLE_EXPORT");// 导出企业列表
		interceptFileLoadMethodMap.put("product-exportAllProduction", "ARCHIVES_LIST_EXPORT");// 导出档案列表
	}
}
