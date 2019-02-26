package com.fwk.service.archives.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TAmedicineinto;
import com.fwk.common.util.Page;

/**
 * 农资投入的实体类
 * 
 * @author wangjunwen
 * @date 2018年1月19日 上午9:35:10
 *
 */
public class MedicineintoEXT extends TAmedicineinto {

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
	// 查询的关键字（记录编号、地块编号、地块名称、农资名称、投入执行人）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农资名称
	private String name;
	// 农资类别名称
	private String typename;
	// 投入执行人姓名
	private String executeuseridname;
	// 地块名称
	private String parcelidname;
	// 地块编号
	private String parcelidcode;
	// 规格单位名称
	private String specificunitname;
	// 数量单位名称
	private String numunitname;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getExecuteuseridname() {
		return executeuseridname;
	}

	public void setExecuteuseridname(String executeuseridname) {
		this.executeuseridname = executeuseridname;
	}

	public String getParcelidname() {
		return parcelidname;
	}

	public void setParcelidname(String parcelidname) {
		this.parcelidname = parcelidname;
	}

	public String getParcelidcode() {
		return parcelidcode;
	}

	public void setParcelidcode(String parcelidcode) {
		this.parcelidcode = parcelidcode;
	}

	public String getSpecificunitname() {
		return specificunitname;
	}

	public void setSpecificunitname(String specificunitname) {
		this.specificunitname = specificunitname;
	}

	public String getNumunitname() {
		return numunitname;
	}

	public void setNumunitname(String numunitname) {
		this.numunitname = numunitname;
	}

}
