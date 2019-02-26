package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 用户
 * */
public class Tenant_staff {

	/**
	 * 出生日期
	 * */
	private Number birthday;

	/**
	 * 组织
	 * */
	private Array tenants;

	/**
	 * 
	 * */

	/**
	 * 昵称
	 * */
	private String nickname;

	/**
	 * 
	 * */

	/**
	 * 姓名
	 * */
	private String name;

	/**
	 * 性别
	 * */
	private Integer gender;

	/**
	 * 最近登录时间
	 * */
	private Number loginOn;

	/**
	 * 头像
	 * */
	private String avatar;

	/**
	 * 密码
	 * */
	private String password;

	/**
	 * 手机号
	 * */
	private String mobile;


}
//{"title":"用户","properties":{"birthday":{"title":"出生日期","format":"date","type":"number"},"address":{"$ref":"@"},"nickname":{"title":"昵称","type":"string"},"status":{"$ref":"@"},"tenants":{"title":"组织","items":{"type":"string","$id":"#sys.tenant"},"type":"array"},"name":{"title":"姓名","type":"string"},"gender":{"title":"性别","enum":[1,2],"enumNames":["男","女"],"type":"integer"},"loginOn":{"title":"最近登录时间","readOnly":true,"description":"统一鉴权登录时间","format":"datetime","type":"number"},"avatar":{"title":"头像","format":"img","type":"string"},"password":{"title":"密码","minLength":5,"format":"password","type":"string"},"mobile":{"title":"手机号","pattern":"^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$","minLength":11,"type":"string"}},"required":["mobile","password","status"],"type":"object"}
