
package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFtaskcomment;
import com.fwk.common.util.Page;

/**
 * 农事任务评论的实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月7日 下午2:47:05
 *
 */
public class TaskcommentEXT extends TFtaskcomment {

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
	// 评论人的姓名
	private String useridname;
	// 头像的URL地址
	private String headportraiturl;
	// 农事记录的ID（当参与人完成农事任务并保存为农事记录之后就会有该字段）
	private Integer recordeid;
	// 评论人的昵称
	private String useridnickname;

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

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public String getHeadportraiturl() {
		return headportraiturl;
	}

	public void setHeadportraiturl(String headportraiturl) {
		this.headportraiturl = headportraiturl;
	}

	public Integer getRecordeid() {
		return recordeid;
	}

	public void setRecordeid(Integer recordeid) {
		this.recordeid = recordeid;
	}

	public String getUseridnickname() {
		return useridnickname;
	}

	public void setUseridnickname(String useridnickname) {
		this.useridnickname = useridnickname;
	}

}
