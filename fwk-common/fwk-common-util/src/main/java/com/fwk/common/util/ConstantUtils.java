package com.fwk.common.util;

import com.fwk.common.util.constants.EDict;

/**
 * 自定义常量类
 * 
 * @author Orcanil
 *
 */
public class ConstantUtils extends DictionaryUtils {

	/**
	 * 响应体内容类型：application/json;charset=utf-8
	 */
	public static final String RESPONSE_CONTENT_TYPE = "application/json;charset=utf-8";

	/**
	 * 是否标志：1 - 是
	 */
	public static final Integer YES_OR_NO_YES = 1;

	/**
	 * 是否标志：0 - 否
	 */
	public static final Integer YES_OR_NO_NO = 0;

	/**
	 * 返回状态：1 - 成功
	 */
	public static final Integer RESULTWRAP_FLAG_OK = 1;

	/**
	 * 返回状态：0 - 失败
	 */
	public static final Integer RESULTWRAP_FLAG_ERROR = 0;

	/**
	 * 返回状态：2 - 失败-拒绝再次尝试
	 */
	public static final Integer RESULTWRAP_FLAG_ERROR_NOT_TRY = 2;

	/**
	 * 返回状态：3 - 失败-Token校验不通过
	 */
	public static final Integer RESULTWRAP_FLAG_ERROR_TOKEN = 3;

	/**
	 * 返回状态：4 - 失败-没有权限
	 */
	public static final Integer RESULTWRAP_FLAG_ERROR_NO_PERMISSION = 4;
	
	/**
	 * 返回状态：5 - 失败-需重新登录
	 * */
	public static final Integer RESULTWRAP_FLAG_LOGIN_AGAIN = 5;
	
	/**
	 * 返回状态：6 - 失败-需重新登录
	 * */
	public static final Integer RESULTWRAP_FLAG_PARAM_ERROR_LOGIN_AGAIN = 6;
	
	/**
	 * 返回状态码 statecode：1 - 负责人不能退出企业
	 * */
	public static final Integer RESULTWRAP_STATECODE_OUT_ENTERPRISE = 1;

	/**
	 * 用户令牌：Access-Token
	 */
	public static final String ACCESS_TOKEN = "Access-Token";

	/**
	 * 游客机构：999999
	 */
	public static final Integer ORG_TOURIST = 999999;
	
	/** 系统使用地区 */
//	public static final String SYSTEM_USE_BY_AREA = EDict.SYSTEM_USE_BY_AREA_CHINA.getDict().getDictName();

}
