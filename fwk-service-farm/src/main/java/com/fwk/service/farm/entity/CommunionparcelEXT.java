package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFcommunionparcel;
import com.fwk.common.util.Page;

/**
 * 农事交流的地块实体类扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月1日 上午9:28:54
 *
 */
public class CommunionparcelEXT extends TFcommunionparcel {
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

	// 地块名称
	private String parcelidname;
	// 地块所处的行政区划编号
	private String addresscode;
	// 地块显示的信息
	private String parcelshowinfo;

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

	public String getParcelidname() {
		return parcelidname;
	}

	public void setParcelidname(String parcelidname) {
		this.parcelidname = parcelidname;
	}

	public String getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}

	public String getParcelshowinfo() {
		return parcelshowinfo;
	}

	public void setParcelshowinfo(String parcelshowinfo) {
		this.parcelshowinfo = parcelshowinfo;
	}

}
