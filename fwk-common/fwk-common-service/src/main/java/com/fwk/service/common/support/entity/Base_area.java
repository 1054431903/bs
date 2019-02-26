package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 区域
 * */
public class Base_area {

	/**
	 * 级别
	 * */
	private Integer level;

	/**
	 * 行政区域名称
	 * */
	private String name;

	/**
	 * 父级
	 * */
	private String parent;

	/**
	 * 国别，省别，市别代码
	 * */
	private String code;


}
//{"title":"区域","properties":{"level":{"title":"级别","enum":[1,2,3,4,5,6],"enumNames":["国家","省","市","县","乡镇","村"],"type":"integer"},"name":{"title":"行政区域名称","type":"string"},"parent":{"title":"父级","type":"string","$id":"#base.area"},"code":{"title":"国别，省别，市别代码","type":"string"}},"required":["name","code"],"type":"object"}
