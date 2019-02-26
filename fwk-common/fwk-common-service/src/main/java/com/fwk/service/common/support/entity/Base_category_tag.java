package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 品类标签
 * */
public class Base_category_tag {

	/**
	 * 类型
	 * */
	private Integer level;

	/**
	 * 名称
	 * */
	private String name;

	/**
	 * 父级
	 * */
	private String parent;


}
//{"title":"品类标签","properties":{"level":{"title":"类型","enum":[1,2],"enumNames":["目录","TAG"],"type":"integer"},"name":{"title":"名称","type":"string"},"parent":{"title":"父级","type":"string","$id":"#base.category_tag"}},"required":["name","status"],"type":"object"}
