package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * 地块
 * */
public class Tenant_farmland {

	/**
	 * 添加时间
	 * */
	private Number createdOn;

	/**
	 * 品类
	 * */
	private Array category;

	/**
	 * 组织
	 * */
	private String tenant;

	/**
	 * 地址
	 * */
	private Object address;

	/**
	 * 
	 * */

	/**
	 * 名称
	 * */
	private String name;

	/**
	 * 海拔
	 * */
	private Number alti;

	/**
	 * GIS地图
	 * */
	private Array gis;

	/**
	 * 经度
	 * */
	private Number lng;

	/**
	 * 编号
	 * */
	private String code;

	/**
	 * 地块面积（亩）
	 * */
	private Number acreage;

	/**
	 * 纬度
	 * */
	private Number lat;


}
//{"title":"地块","description":"地块","properties":{"createdOn":{"title":"添加时间","format":"datetime","type":"number"},"category":{"title":"品类","items":{"type":"string","$id":"$base.category"},"type":"array"},"tenant":{"title":"组织","type":"string","$id":"#sys.tenant"},"status":{"$ref":"@"},"address":{"title":"地址","properties":{"village":{"title":"村","type":"string","$id":"#base.area"},"street":{"title":"详细地址","type":"string"},"province":{"title":"省","type":"string","$id":"#base.area"},"town":{"title":"乡镇","type":"string","$id":"#base.area"},"district":{"title":"区","type":"string","$id":"#base.area"},"city":{"title":"市","type":"string","$id":"#base.area"}},"format":"address","required":["province","city","district"],"type":"object"},"name":{"title":"名称","type":"string"},"alti":{"title":"海拔","format":"long","type":"number"},"lng":{"title":"经度","format":"long","type":"number"},"gis":{"title":"GIS地图","items":{"properties":{"lng":{"title":"经度","format":"long","type":"number"},"lat":{"title":"纬度","format":"long","type":"number"}},"type":"object"},"type":"array"},"code":{"title":"编号","type":"string"},"acreage":{"title":"地块面积（亩）","format":"long","type":"number"},"lat":{"title":"纬度","format":"long","type":"number"}},"required":["name","acreage","status"],"type":"object"}
