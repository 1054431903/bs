package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFrecordpartuser;
import com.fwk.common.util.Page;

/**
 * 农事记录的参与人实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年1月30日 上午11:10:41
 *
 */
public class RecordpartuserEXT extends TFrecordpartuser {
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

	// 参与人的姓名
	private String username;
	// 记录的ID
	private List<Integer> recordidlist;
	// 支撑平台的用户ID
	private String usersupportid;
	private String usernickname;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Integer> getRecordidlist() {
		return recordidlist;
	}

	public void setRecordidlist(List<Integer> recordidlist) {
		if ((null != recordidlist) && (recordidlist.size() > 0)) {
			// 如果以农事记录的ID列表进行查询，那么查询的排序方式为：以农事记录的ID降序排序、以参与用户表的ID升序排序（为了与添加时候选择的参与人顺序保持一致）
			this.setOrderBySQL(" recordid DESC, id ASC ");
		}

		this.recordidlist = recordidlist;
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
