package com.fwk.common.util.constants;

/**
 * 通用的常量工具类
 * 
 * @author wangjunwen
 * @date 2018年1月19日 上午10:07:08
 *
 */
public class CommonConstantsUtil {

	/** 已删除状态（在回收站，可恢复） */
	public static final int IS_DELETE_TRUE = 0;

	/** 未删除状态 */
	public static final int IS_DELETE_FALSE = 1;

	/** 已删除状态（从回收站删除，不可恢复） */
	public static final int IS_DELETE_FROM_TRASH = 2;

	/** 资源的默认排序号 */
	public static final int DEFAULT_RESOURCE_ORDER_NO = 0;

	/**
	 * 已发布状态
	 */
	public static final int IS_PUB_TRUE = 1;

	/**
	 * 未发布状态
	 */
	public static final int IS_PUB_FALSE = 0;

	/** 是否：是 */
	public static final int YES_OR_NO_YES = 1;

	/** 是否：否 */
	public static final int YES_OR_NO_NO = 0;
	
	/** 组织 **/
	public static final int sign_org = 1;

	/** 部门 **/
	public static final int sign_dept = 2;
	
	public static final int TENANT_TYPE_ORG = 1;
	public static final int TENANT_TYPE_DEPT = 2;

}
