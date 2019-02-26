package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFrecorduserpermisions;
import com.fwk.common.util.Page;

/**
 * 农事记录的数据权限实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月8日 下午2:25:35
 *
 */
public class RecorduserpermisionsEXT extends TFrecorduserpermisions {

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

	// 农事记录的ID列表
	private List<Integer> recordidlist;
	// 创建人的用户ID列表
	private List<Integer> createuseridlist;
	// 所属组织的ID列表
	private List<Integer> createuserorgidlist;
	// 所属部门的ID列表
	private List<Integer> createuserdepartmentidlist;

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

	public List<Integer> getRecordidlist() {
		return recordidlist;
	}

	public void setRecordidlist(List<Integer> recordidlist) {
		this.recordidlist = recordidlist;
	}

	public List<Integer> getCreateuseridlist() {
		return createuseridlist;
	}

	public void setCreateuseridlist(List<Integer> createuseridlist) {
		this.createuseridlist = createuseridlist;
	}

	public List<Integer> getCreateuserorgidlist() {
		return createuserorgidlist;
	}

	public void setCreateuserorgidlist(List<Integer> createuserorgidlist) {
		this.createuserorgidlist = createuserorgidlist;
	}

	public List<Integer> getCreateuserdepartmentidlist() {
		return createuserdepartmentidlist;
	}

	public void setCreateuserdepartmentidlist(List<Integer> createuserdepartmentidlist) {
		this.createuserdepartmentidlist = createuserdepartmentidlist;
	}

}
