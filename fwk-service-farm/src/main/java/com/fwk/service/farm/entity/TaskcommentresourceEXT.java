package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFtaskcommentresource;
import com.fwk.common.util.Page;

/**
 * 农事任务评论的资源实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月7日 下午2:51:17
 *
 */
public class TaskcommentresourceEXT extends TFtaskcommentresource {

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

	// 农事任务评论的ID列表
	private List<Integer> commentidlist;
	// 农事任务评论的资源的ID列表
	private List<Integer> resourceidlist;

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

	public List<Integer> getCommentidlist() {
		return commentidlist;
	}

	public void setCommentidlist(List<Integer> commentidlist) {
		this.commentidlist = commentidlist;
	}

	public List<Integer> getResourceidlist() {
		return resourceidlist;
	}

	public void setResourceidlist(List<Integer> resourceidlist) {
		this.resourceidlist = resourceidlist;
	}

}
