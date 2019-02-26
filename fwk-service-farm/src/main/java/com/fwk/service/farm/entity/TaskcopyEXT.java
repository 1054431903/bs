package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFtaskcopy;
import com.fwk.common.util.Page;

/**
 * 农事任务抄送人的实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月7日 下午2:56:24
 *
 */
public class TaskcopyEXT extends TFtaskcopy {

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
	// 抄送人的姓名
	private String username;
	// 支撑平台的用户ID
	private String usersupportid;
	// 参与人的姓名
	private String usernickname;
	// 参与人的头像
	private String userphotourl;

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

}
