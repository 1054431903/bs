package com.fwk.service.archives.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TAmedicinesend;
import com.fwk.common.util.Page;

/**
 * 农资发放的实体类
 * 
 * @author wangjunwen
 * @date 2018年1月19日 上午9:35:10
 *
 */
public class MedicinesendEXT extends TAmedicinesend {

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
	// 查询的关键字（记录编号、农资名称、领取人）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农资名称
	private String name;
	// 农资类别名称
	private String typename;
	// 领取人的姓名
	private String drowusername;
	// 领取地点
	private String drowaddress;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getDrowusername() {
		return drowusername;
	}

	public void setDrowusername(String drowusername) {
		this.drowusername = drowusername;
	}

	public String getDrowaddress() {
		return drowaddress;
	}

	public void setDrowaddress(String drowaddress) {
		this.drowaddress = drowaddress;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

}
