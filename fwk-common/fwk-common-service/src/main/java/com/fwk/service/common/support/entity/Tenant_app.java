package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 平台
 * */
public class Tenant_app {

	/**
	 * 
	 * */
	private String id;

	/**
	 * 
	 * */
	private Array grant_type;

	/**
	 * 
	 * */

	/**
	 * 组织
	 * */
	private Array tenants;

	/**
	 * 
	 * */
	private Array redirect_uri;

	/**
	 * 
	 * */
	private String remark;

	/**
	 * 平台名称
	 * */
	private String name;

	/**
	 * 
	 * */
	private Array roles;

	/**
	 * 
	 * */
	private String secret;

	/**
	 * 类型
	 * */
	private Integer type;


}
//{"title":"平台","properties":{"id":{"type":"string"},"grant_type":{"items":{"description":"1: Authorization Code 2: Password, 3: Client credentials, 4: Implicit","enum":[1,2,3,4],"type":"integer"},"type":"array"},"remark":{"type":"string"},"redirect_uri":{"items":{"type":"string"},"type":"array"},"tenants":{"title":"组织","items":{"type":"string","$id":"#sys.tenant"},"type":"array"},"status":{"$ref":"@"},"roles":{"items":{"properties":{"tenant":{"type":"string","$id":"#sys.tenant"},"roles":{"items":{"type":"string","$id":"#tenant.role"},"type":"array"}},"type":"object"},"description":"client_credentials 授权时对应的角色","type":"array"},"name":{"title":"平台名称","type":"string"},"secret":{"type":"string"},"type":{"title":"类型","enum":[1,2],"enumNames":["内部平台","开放平台"],"type":"integer"}},"required":["id","name","secret","status","redirect_uri","grant_type","roles"],"type":"object"}
