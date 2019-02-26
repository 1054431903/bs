package com.fwk.service.archives.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TApeasantprotocol;
import com.fwk.common.util.Page;
import com.fwk.service.common.entity.ResourcehistoryEXT;

/**
 * 农户合作协议的实体类
 * 
 * @author wangjunwen
 * @date 2018年1月28日 下午1:45:05
 *
 */
public class PeasantprotocolEXT extends TApeasantprotocol {

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

	// 农户合作协议的附件
	private List<ResourcehistoryEXT> resourcehistorylist;
	// 协议合作状态名称
	private String actstatename;
	// 实际的协议合作状态
	private String realactstate;
	// 合作农户
	private String peasantidname;
	// 农户合作协议的附件
	private List<PeasantprotocolresourceEXT> peasantprotocolresourceEXTList;
	// 支撑平台的组织ID
	private String supportorgid;
	// 联系电话
	private String peasantidlinkphone;

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

	public List<ResourcehistoryEXT> getResourcehistorylist() {
		return resourcehistorylist;
	}

	public void setResourcehistorylist(List<ResourcehistoryEXT> resourcehistorylist) {
		this.resourcehistorylist = resourcehistorylist;
	}

	public String getActstatename() {
		return actstatename;
	}

	public void setActstatename(String actstatename) {
		this.actstatename = actstatename;
	}

	public String getRealactstate() {
		return realactstate;
	}

	public void setRealactstate(String realactstate) {
		this.realactstate = realactstate;
	}

	public String getPeasantidname() {
		return peasantidname;
	}

	public void setPeasantidname(String peasantidname) {
		this.peasantidname = peasantidname;
	}

	public List<PeasantprotocolresourceEXT> getPeasantprotocolresourceEXTList() {
		return peasantprotocolresourceEXTList;
	}

	public void setPeasantprotocolresourceEXTList(List<PeasantprotocolresourceEXT> peasantprotocolresourceEXTList) {
		this.peasantprotocolresourceEXTList = peasantprotocolresourceEXTList;
	}

	public String getSupportorgid() {
		return supportorgid;
	}

	public void setSupportorgid(String supportorgid) {
		this.supportorgid = supportorgid;
	}

	public String getPeasantidlinkphone() {
		return peasantidlinkphone;
	}

	public void setPeasantidlinkphone(String peasantidlinkphone) {
		this.peasantidlinkphone = peasantidlinkphone;
	}

}
