package com.fwk.service.common.support;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 支撑平台请求参数
 * 
 */
public class SupportRequestParam {

	/**
	 * 数据对象
	 */
	private JSONObject dataJson;
	
	/**
	 * 数据对象(List)
	 */
	private List<JSONObject> dataListJson;

	/**
	 * 查询过滤参数
	 */
	private JSONObject filterJson;

	/**
	 * 查询排序过滤参数
	 */
	private JSONObject sortJson;

	/**
	 * 分页过滤参数 limit:显示条数，skip：开始条数
	 */
	private JSONObject pageJson;
	
	/**
	 * 查询指定列参数
	 */
	private JSONObject projectionJson;

	public SupportRequestParam() {

	}

	public SupportRequestParam(JSONObject dataJson) {
		setDataJson(dataJson);
	}

	public JSONObject getDataJson() {
		return dataJson;
	}

	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}

	public List<JSONObject> getDataListJson() {
		return dataListJson;
	}

	public void setDataListJson(List<JSONObject> dataListJson) {
		this.dataListJson = dataListJson;
	}

	public JSONObject getFilterJson() {
		return filterJson;
	}

	public void setFilterJson(JSONObject filterJson) {
		this.filterJson = filterJson;
	}

	public JSONObject getSortJson() {
		return sortJson;
	}

	public void setSortJson(JSONObject sortJson) {
		this.sortJson = sortJson;
	}

	public JSONObject getPageJson() {
		return pageJson;
	}

	public void setPageJson(JSONObject pageJson) {
		this.pageJson = pageJson;
	}

	public JSONObject getProjectionJson() {
		return projectionJson;
	}

	public void setProjectionJson(JSONObject projectionJson) {
		this.projectionJson = projectionJson;
	}

}
