package com.fwk.service.common.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TPparcel;
import com.fwk.common.util.Page;

/**
 * 地块实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年1月31日 下午4:41:26
 *
 */
public class ParcelEXT extends TPparcel {
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
	// 部门编号（来源于支撑平台）
	private List<String> departmentidlist;

	// 用户的ID
	private Integer userid;

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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public List<String> getDepartmentidlist() {
		return departmentidlist;
	}

	public void setDepartmentidlist(List<String> departmentidlist) {
		this.departmentidlist = departmentidlist;
	}

}
