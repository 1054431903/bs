package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 角色
 * */
public class Tenant_role {

	/**
	 * 
	 * */
	private String tenant;

	/**
	 * 权限
	 * */
	private Array authorities;

	/**
	 * 
	 * */

	/**
	 * 备注
	 * */
	private String remark;

	/**
	 * 名称
	 * */
	private String name;

	/**
	 * ID
	 * */
	private String code;


}
//{"title":"角色","properties":{"authorities":{"title":"权限","items":{"type":"string","$id":"#sys.authority"},"description":"权限","type":"array"},"tenant":{"description":"所属租户","type":"string","$id":"#sys.tenant"},"remark":{"title":"备注","type":"string"},"status":{"$ref":"@"},"name":{"title":"名称","type":"string"},"code":{"title":"ID","type":"string"}},"required":["name","status","authorities"],"type":"object"}
