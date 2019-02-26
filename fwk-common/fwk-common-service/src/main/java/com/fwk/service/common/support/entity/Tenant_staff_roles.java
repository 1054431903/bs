package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 用户权限
 * */
public class Tenant_staff_roles {

	/**
	 * 用户
	 * */
	private String staff;

	/**
	 * 
	 * */
	private String tenant;

	/**
	 * 角色
	 * */
	private Array roles;

	/**
	 * 最近登录时间
	 * */
	private Number loginOn;


}
//{"title":"用户权限","properties":{"staff":{"title":"用户","type":"string","$id":"#tenant.staff"},"tenant":{"type":"string","$id":"#sys.tenant"},"roles":{"title":"角色","items":{"type":"string","$id":"#tenant.role"},"type":"array"},"loginOn":{"title":"最近登录时间","readOnly":true,"description":"登录本平台时间","format":"datetime","type":"number"}},"required":["staff","tenant","roles"],"type":"object"}
