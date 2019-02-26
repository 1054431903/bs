package com.fwk.service.common.support.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支撑组织机构对象
 */
public class SupportOrgUtil {
	public static String typeFromFwkToIssOrg = "1";
	public static String typeFromFwkToIssDept = "2";
//	public static String typeFromFwkToIssStaff = "3";

	public static String typeFromIssToFwkOrg = "4";
	public static String typeFromIssToFwkDept = "5";
//	public static String typeFromIssToFwkStaff = "6";

	public static String TRANS_TYPE_NO_NEED = "0"; // 不需要转义
	public static String TRANS_TYPE_STR_DATE_TO_LONG = "1";// 字符串转化为long,日期类型的 农事的日期字符串转化为支撑的long
	public static String TRANS_TYPE_STR_LONG_TO_STR_DATE = "2";//
	public static String TRANS_TYPE_STR_LONG_TO_STR_DATE_YYYY_MM_RR = "5";//只要保存年月日
	public static String TRANS_TYPE_STR_TO_INT = "3";// 字符串类型转化为int类型
	public static String TRANS_TYPE_STR_TO_DOUBLE = "4";// 字符串类型转化为int类型

	public static Map<String, Map<String, String>> typeMap = new HashMap<String, Map<String, String>>();
	public static Map<String, String> fromFwkToIssOrgColMap = new HashMap<String, String>();
	public static Map<String, String> fromFwkToIssDeptColMap = new HashMap<String, String>();
	public static Map<String, String> fromFwkToIssStaffColMap = new HashMap<String, String>();

	public static Map<String, String> fromIssToFwkOrgColMap = new HashMap<String, String>();
	public static Map<String, String> fromIssToFwkDeptColMap = new HashMap<String, String>();
	public static Map<String, String> fromIssToFwkStaffColMap = new HashMap<String, String>();

	static {
		/***************************************************************************************************
		 * 组织 t_u_org 新增、修改、翻译成iss的字段 typeFromFwkToIssOrg = "1"
		 ***************************************************************************************************/
		fromFwkToIssOrgColMap.put("creattime", new StringBuilder("createdOn").append(":").append(TRANS_TYPE_STR_DATE_TO_LONG).toString());// 创建时间
		fromFwkToIssOrgColMap.put("delstate", new StringBuilder("status").append(":").append(TRANS_TYPE_NO_NEED).toString());// 删除状态（1：可用，0：不可用）
		fromFwkToIssOrgColMap.put("name", new StringBuilder("name").append(":").append(TRANS_TYPE_NO_NEED).toString());// 组织名称
		fromFwkToIssOrgColMap.put("nature", new StringBuilder("businessType").append(":").append(TRANS_TYPE_NO_NEED).toString());// 组织性质（外键表：t_c_dict：dictvalue）
		fromFwkToIssOrgColMap.put("legal", new StringBuilder("businessEntity").append(":").append(TRANS_TYPE_NO_NEED).toString());// 组织法人
		fromFwkToIssOrgColMap.put("eastablishtime",
				new StringBuilder("foundingTime").append(":").append(TRANS_TYPE_STR_DATE_TO_LONG).toString());// 成立时间
		fromFwkToIssOrgColMap.put("logo", new StringBuilder("logo").append(":").append(TRANS_TYPE_NO_NEED).toString());// logo（外键表：t_c_resource_history：id）
		fromFwkToIssOrgColMap.put("regisfund", new StringBuilder("registeredCapital").append(":").append(TRANS_TYPE_NO_NEED).toString());// 注册资金
		fromFwkToIssOrgColMap.put("scale", new StringBuilder("staffSize").append(":").append(TRANS_TYPE_NO_NEED).toString());// 人员规模（外键表：t_c_dict：dictvalue）
		//fromFwkToIssOrgColMap.put("addresscode", new StringBuilder("").append(":").append(TRANS_TYPE_NO_NEED).toString());// 地址行政区划（外键表：t_c_position：code）
//		fromFwkToIssOrgColMap.put("addressdetail",
//				new StringBuilder("registeredAddress").append(":").append(TRANS_TYPE_NO_NEED).toString());// 详细地址
		
		fromFwkToIssOrgColMap.put("introduce", new StringBuilder("remark").append(":").append(TRANS_TYPE_NO_NEED).toString());// 简介
		fromFwkToIssOrgColMap.put("scope", new StringBuilder("businessScope").append(":").append(TRANS_TYPE_NO_NEED).toString());// 业务范围
		// fromFwkToIssOrgColMap.put("belonguserid", "");//组织所属人（外键表：t_u_users：id）
		 fromFwkToIssOrgColMap.put("permit",new StringBuilder("businessLicense").append(":").append(TRANS_TYPE_NO_NEED).toString());//营业执照（外键表：t_c_resource_history：id）
		 
		fromFwkToIssOrgColMap.put("edittime", new StringBuilder("updatedOn").append(":").append(TRANS_TYPE_STR_DATE_TO_LONG).toString());//更新时间
		//fromFwkToIssOrgColMap.put("supportid", new StringBuilder("id").append(":").append(TRANS_TYPE_NO_NEED).toString());// 支撑平台关联id
		fromFwkToIssOrgColMap.put("_removed", new StringBuilder("_removed").append(":").append(TRANS_TYPE_NO_NEED).toString());// 支撑平台关联id
		fromFwkToIssOrgColMap.put("type", new StringBuilder("type").append(":").append(TRANS_TYPE_NO_NEED).toString());
		fromFwkToIssOrgColMap.put("parentid", new StringBuilder("parent").append(":").append(TRANS_TYPE_NO_NEED).toString());// 上一级部门编号（外键表：t_u_org_department：id）
		/**********************************************************************************************************
		 * typeFromFwkToIssOrg = "2" ****** 部门t_u_department 新增、修改、翻译成iss的字段
		 ***********************************************************************************************************/
		fromFwkToIssDeptColMap.put("creattime", new StringBuilder("createdOn").append(":").append(TRANS_TYPE_STR_DATE_TO_LONG).toString());// 创建时间
		fromFwkToIssDeptColMap.put("delstate", new StringBuilder("status").append(":").append(TRANS_TYPE_NO_NEED).toString());// 删除状态（1：可用，0：不可用）
		// fromFwkToIssDeptColMap.put("orgid", "");//所属组织编号（外键表：t_u_org：id）
		fromFwkToIssDeptColMap.put("parentid", new StringBuilder("parent").append(":").append(TRANS_TYPE_NO_NEED).toString());// 上一级部门编号（外键表：t_u_org_department：id）
		fromFwkToIssDeptColMap.put("name", new StringBuilder("name").append(":").append(TRANS_TYPE_NO_NEED).toString());// 部门名称
		fromFwkToIssDeptColMap.put("departno", new StringBuilder("code").append(":").append(TRANS_TYPE_NO_NEED).toString());// 部门编号
		fromFwkToIssDeptColMap.put("chargeuserid", new StringBuilder("director").append(":").append(TRANS_TYPE_NO_NEED).toString());// 部门主管用户编号（外键表：t_u_users：id）
		fromFwkToIssDeptColMap.put("edittime", new StringBuilder("updatedOn").append(":").append(TRANS_TYPE_STR_DATE_TO_LONG).toString());//更新时间
         // 不能添加supportid，否则会报错，可以直接用id
		// fromFwkToIssDeptColMap.put("supportid", new StringBuilder("id").append(":").append(TRANS_TYPE_NO_NEED).toString());// 支撑平台关联id
		fromFwkToIssDeptColMap.put("orgid", new StringBuilder("parent").append(":").append(TRANS_TYPE_NO_NEED).toString());// 支撑的组织和部门是放在一个表里的
		fromFwkToIssDeptColMap.put("type", new StringBuilder("type").append(":").append(TRANS_TYPE_NO_NEED).toString());   //  类型;1是组织，2是部门
		fromFwkToIssDeptColMap.put("_removed", new StringBuilder("_removed").append(":").append(TRANS_TYPE_NO_NEED).toString());// 支撑平台关联id
		/****************************************************************************************************************
		 * typeFromFwkToIssOrg = "4" 组织 查询：翻译成fwk的字段
		 ****************************************************************************************************************/
		// fromIssToFwkOrgColMap.put("parent", "");//上级
		fromIssToFwkOrgColMap.put("name", new StringBuilder("name").append(":").append(TRANS_TYPE_NO_NEED).toString());// 名称
		fromIssToFwkOrgColMap.put("status", new StringBuilder("delstate").append(":").append(TRANS_TYPE_STR_TO_INT).toString());// 状态;有效、无效
		// fromIssToFwkOrgColMap.put("manager", new StringBuilder("orgChargePerson").append(":").append(TRANS_TYPE_NO_NEED).toString());// 负责人		
		fromIssToFwkOrgColMap.put("createdOn",
				new StringBuilder("creattime").append(":").append(TRANS_TYPE_STR_LONG_TO_STR_DATE).toString());// 添加时间
		fromIssToFwkOrgColMap.put("businessEntity", new StringBuilder("legal").append(":").append(TRANS_TYPE_NO_NEED).toString());// 法人
		fromIssToFwkOrgColMap.put("foundingTime",
				new StringBuilder("eastablishtime").append(":").append(TRANS_TYPE_STR_LONG_TO_STR_DATE_YYYY_MM_RR).toString());// 成立时间
		fromIssToFwkOrgColMap.put("businessType", new StringBuilder("nature").append(":").append(TRANS_TYPE_NO_NEED).toString());// 组织性质;
																																	// 1,事业单位
																																	// 2,公司
		// 3,合作社 4个人
		fromIssToFwkOrgColMap.put("logo", new StringBuilder("logourl").append(":").append(TRANS_TYPE_NO_NEED).toString());// 组织的logo
		fromIssToFwkOrgColMap.put("registeredCapital", new StringBuilder("regisfund").append(":").append(TRANS_TYPE_STR_TO_DOUBLE).toString());// 注册资金(万元)
		fromIssToFwkOrgColMap.put("staffSize", new StringBuilder("scale").append(":").append(TRANS_TYPE_NO_NEED).toString());// 人员规模(人)；
																																	// 1,100人以内
		// 2,100-500人
		// 3，500人以上
		fromIssToFwkOrgColMap.put("businessLicense", new StringBuilder("permiturl").append(":").append(TRANS_TYPE_NO_NEED).toString());// 注册资金(万元)
		fromIssToFwkOrgColMap.put("registeredAddress",
				new StringBuilder("addressdetail").append(":").append(TRANS_TYPE_NO_NEED).toString());//
		fromIssToFwkOrgColMap.put("remark", new StringBuilder("introduce").append(":").append(TRANS_TYPE_NO_NEED).toString());// 简介
		fromIssToFwkOrgColMap.put("businessScope", new StringBuilder("scope").append(":").append(TRANS_TYPE_NO_NEED).toString());// 业务范围
		// fromIssToFwkOrgColMap.put("code", "");//部门编号
		// fromIssToFwkOrgColMap.put("director", "");//部门主管
		fromIssToFwkOrgColMap.put("_id", new StringBuilder("supportid").append(":").append(TRANS_TYPE_NO_NEED).toString());// 主键
		fromIssToFwkOrgColMap.put("type", new StringBuilder("sign").append(":").append(TRANS_TYPE_STR_TO_INT).toString());//  类型;1是组织，2是部门
		fromIssToFwkOrgColMap.put("updatedOn",
				new StringBuilder("edittime").append(":").append(TRANS_TYPE_STR_LONG_TO_STR_DATE).toString());// 更新时间
		/****************************************************************************************************************
		 * typeFromFwkToIssOrg = "5" 部门 查询：翻译成fwk的字段 ***************************************************************
		 *****************************************************************************************************************/
		fromIssToFwkDeptColMap.put("parent", new StringBuilder("parentid").append(":").append(TRANS_TYPE_NO_NEED).toString());// 上级
		fromIssToFwkDeptColMap.put("name", new StringBuilder("name").append(":").append(TRANS_TYPE_NO_NEED).toString());// 名称
		fromIssToFwkDeptColMap.put("status", new StringBuilder("delstate").append(":").append(TRANS_TYPE_STR_TO_INT).toString());// 状态;有效、无效
		fromIssToFwkDeptColMap.put("manager", new StringBuilder("").append(":").append(TRANS_TYPE_NO_NEED).toString());// 负责人
		fromIssToFwkDeptColMap.put("createdOn",
				new StringBuilder("creattime").append(":").append(TRANS_TYPE_STR_LONG_TO_STR_DATE).toString());// 添加时间
		
		fromIssToFwkDeptColMap.put("code", new StringBuilder("departno").append(":").append(TRANS_TYPE_NO_NEED).toString());// 部门编号
		fromIssToFwkDeptColMap.put("director", new StringBuilder("chargeuserid").append(":").append(TRANS_TYPE_NO_NEED).toString());// 部门主管
		fromIssToFwkDeptColMap.put("type", new StringBuilder("sign").append(":").append(TRANS_TYPE_STR_TO_INT).toString());// 类型;1是组织，2是部门
		fromIssToFwkDeptColMap.put("updatedOn",
				new StringBuilder("edittime").append(":").append(TRANS_TYPE_STR_LONG_TO_STR_DATE).toString());// 更新时间
		/******************************************************************************************************************************/
		typeMap.put(typeFromFwkToIssOrg, fromFwkToIssOrgColMap);
		typeMap.put(typeFromFwkToIssDept, fromFwkToIssDeptColMap);
		typeMap.put(typeFromIssToFwkOrg, fromIssToFwkOrgColMap);
		typeMap.put(typeFromIssToFwkDept, fromIssToFwkDeptColMap);

	}

}
