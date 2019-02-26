package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFrecordfarmtype;
import com.fwk.common.util.Page;

/**
 * 农事类别
 * 
 * @author wangjunwen
 * @date 2018年2月9日 下午4:07:04
 *
 */
public class RecordfarmtypeEXT extends TFrecordfarmtype {

	// 分页查询对象
	private Page page;
	// 排序的SQL语句
	private String orderBySQL;
	// 开始时间
	private String createdatestart;
	// 截至时间
	private String createdateend;
	// 业务主键列表
	private List<Integer> idlist;
	// 查询的关键字（记录编号、协议文件编号、协议执行状态、合作农户）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农事记录的ID列表
	private List<Integer> recordidlist;
	// 农事类别名称
	private String farmtypename;
	// 农事类别的ID列表
	private List<Integer> farmtypelist;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getOrderBySQL() {
		return orderBySQL;
	}

	public void setOrderBySQL(String orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

	public String getCreatedatestart() {
		return createdatestart;
	}

	public void setCreatedatestart(String createdatestart) {
		this.createdatestart = createdatestart;
	}

	public String getCreatedateend() {
		return createdateend;
	}

	public void setCreatedateend(String createdateend) {
		this.createdateend = createdateend;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Integer> getRecordidlist() {
		return recordidlist;
	}

	public void setRecordidlist(List<Integer> recordidlist) {
		this.recordidlist = recordidlist;
	}

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

	public String getFarmtypename() {
		return farmtypename;
	}

	public void setFarmtypename(String farmtypename) {
		this.farmtypename = farmtypename;
	}

	public List<Integer> getFarmtypelist() {
		return farmtypelist;
	}

	public void setFarmtypelist(List<Integer> farmtypelist) {
		this.farmtypelist = farmtypelist;
	}

}
