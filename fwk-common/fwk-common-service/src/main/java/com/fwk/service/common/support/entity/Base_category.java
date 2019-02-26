package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 品类
 * */
public class Base_category {

	/**
	 * 标签
	 * */
	private Array tags;

	/**
	 * 添加时间
	 * */
	private Number createdOn;

	/**
	 * 
	 * */

	/**
	 * 名称
	 * */
	private String name;


}
//{"title":"品类","description":"品类","properties":{"createdOn":{"title":"添加时间","format":"datetime","type":"number"},"tags":{"title":"标签","items":{"type":"string","$id":"#base.category_tag"},"type":"array"},"status":{"$ref":"@"},"name":{"title":"名称","type":"string"}},"required":["name","status"],"type":"object"}
