package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFtaskpartics;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;

/**
 * 农事任务参与人实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月7日 下午3:01:50
 *
 */
public class TaskparticsEXT extends TFtaskpartics {

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
	// 查询的关键字（记录编号、农户名称）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农事任务的ID列表
	private List<Integer> taskidlist;
	// 参与人的姓名
	private String username;
	// 参与人的参与状态的名称
	private String taskparticsstatusname;
	// 参与人的任务状态的名称
	private String taskstatusname;
	// 用户头像的URL
	private String photourl;
	// 支撑平台的用户ID
	private String usersupportid;
	// 参与人的姓名
	private String usernickname;
	// 参与人的头像
	private String userphotourl;
	// 是否保存到农事记录
	private Boolean issavetorecord;
	// 农事记录列表
	private List<Integer> recordeidlist;

	public void setRecordeid(String recordeid) {

		@SuppressWarnings("unchecked")
		List<Integer> recordIdList = ObjectUtil.convertToNumberList(recordeid, Integer.class);

		this.setRecordeidlist(recordIdList);

		super.setRecordeid(recordeid);
	}

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

	public List<Integer> getTaskidlist() {
		return taskidlist;
	}

	public void setTaskidlist(List<Integer> taskidlist) {
		this.taskidlist = taskidlist;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTaskparticsstatusname() {
		return taskparticsstatusname;
	}

	public void setTaskparticsstatusname(String taskparticsstatusname) {
		this.taskparticsstatusname = taskparticsstatusname;
	}

	public String getTaskstatusname() {
		return taskstatusname;
	}

	public void setTaskstatusname(String taskstatusname) {
		this.taskstatusname = taskstatusname;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getUsersupportid() {
		return usersupportid;
	}

	public void setUsersupportid(String usersupportid) {
		this.usersupportid = usersupportid;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	public String getUserphotourl() {
		return userphotourl;
	}

	public void setUserphotourl(String userphotourl) {
		this.userphotourl = userphotourl;
	}

	public Boolean getIssavetorecord() {
		return issavetorecord;
	}

	public void setIssavetorecord(Boolean issavetorecord) {
		this.issavetorecord = issavetorecord;
	}

	public List<Integer> getRecordeidlist() {
		return recordeidlist;
	}

	public void setRecordeidlist(List<Integer> recordeidlist) {
		this.recordeidlist = recordeidlist;
	}

}
