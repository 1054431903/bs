package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFcommunioncomment;
import com.fwk.common.util.Page;

/**
 * 农事交流的评论的
 * 
 * @author wangjunwen
 * @date 2018年3月1日 上午9:52:18
 *
 */
public class CommunioncommentEXT extends TFcommunioncomment {

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

	// 农事交流的ID列表
	private List<Integer> communionidlist;
	// 增加或者减少的操作数（主要用于增加或者减少评论的数量）
	private Integer increaseordecreaseoperand;
	// 评论人的姓名
	private String commentuseridname;
	// 评论人的昵称
	private String commentuseridnickname;
	// 评论人的头像的URL
	private String photourl;
	// 是否可编辑
	private Boolean editable;
	// 支撑平台的用户ID
	private String usersupportid;

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

	public List<Integer> getCommunionidlist() {
		return communionidlist;
	}

	public void setCommunionidlist(List<Integer> communionidlist) {
		this.communionidlist = communionidlist;
	}

	public Integer getIncreaseordecreaseoperand() {
		return increaseordecreaseoperand;
	}

	public void setIncreaseordecreaseoperand(Integer increaseordecreaseoperand) {
		this.increaseordecreaseoperand = increaseordecreaseoperand;
	}

	public String getCommentuseridname() {
		return commentuseridname;
	}

	public void setCommentuseridname(String commentuseridname) {
		this.commentuseridname = commentuseridname;
	}

	public String getCommentuseridnickname() {
		return commentuseridnickname;
	}

	public void setCommentuseridnickname(String commentuseridnickname) {
		this.commentuseridnickname = commentuseridnickname;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getUsersupportid() {
		return usersupportid;
	}

	public void setUsersupportid(String usersupportid) {
		this.usersupportid = usersupportid;
	}

}
