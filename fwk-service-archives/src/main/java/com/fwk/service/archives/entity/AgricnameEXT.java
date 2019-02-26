package com.fwk.service.archives.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TAagricname;
import com.fwk.common.util.Page;

/**
 * 农资名称的实体类
 * 
 * @author wangjunwen
 * @date 2018年1月20日 下午1:19:46
 *
 */
public class AgricnameEXT extends TAagricname {
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
	// 查询的关键字
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农资类别名称
	private String typeidname;
	// 规格单位名称
	private String specifunitname;

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

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

	public String getTypeidname() {
		return typeidname;
	}

	public void setTypeidname(String typeidname) {
		this.typeidname = typeidname;
	}

	public String getSpecifunitname() {
		return specifunitname;
	}

	public void setSpecifunitname(String specifunitname) {
		this.specifunitname = specifunitname;
	}

}
