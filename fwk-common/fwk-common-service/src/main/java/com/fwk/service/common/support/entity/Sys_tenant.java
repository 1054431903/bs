package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 组织
 * */
public class Sys_tenant {

	/**
	 * 添加时间
	 * */
	private Number createdOn;

	/**
	 * 负责人
	 * */
	private String manager;

	/**
	 * 
	 * */

	/**
	 * 名称
	 * */
	private String name;

	/**
	 * 上级
	 * */
	private String parent;

	/**
	 * 类型
	 * */
	private Integer type;


}
//{"title":"组织","dependencies":{"type":{"oneOf":[{"properties":{"staffSize":{"title":"人员规模(人)","enum":[1,2,3],"enumNames":["100人以内","100-500人","500人以上"],"type":"integer"},"logo":{"title":"Logo","format":"img","type":"string"},"businessScope":{"title":"业务范围","type":"string"},"foundingTime":{"title":"成立时间","format":"date","type":"number"},"remark":{"title":"简介","type":"string"},"registeredAddress":{"$ref":"@"},"businessType":{"title":"组织性质","enum":[1,2,3,4],"enumNames":["事业单位","公司","合作社","个人"],"type":"integer"},"type":{"enum":[1]},"registeredCapital":{"title":"注册资金(万元)","type":"number"},"businessEntity":{"title":"法人","type":"string"}}},{"properties":{"code":{"title":"部门编号","type":"string"},"director":{"title":"部门主管","type":"string","$id":"#tenant.staff"},"type":{"enum":[2]}}}]}},"properties":{"createdOn":{"title":"添加时间","format":"datetime","type":"number"},"manager":{"title":"负责人","readOnly":true,"type":"string","$id":"#tenant.staff"},"status":{"$ref":"@"},"name":{"title":"名称","type":"string"},"parent":{"title":"上级","type":"string","$id":"#sys.tenant"},"type":{"title":"类型","enum":[1,2],"enumNames":["组织","部门"],"type":"integer"}},"required":["type","name","status"],"type":"object"}
