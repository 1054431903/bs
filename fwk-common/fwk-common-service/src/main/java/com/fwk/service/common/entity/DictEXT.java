package com.fwk.service.common.entity;

import com.fwk.common.entity.TCdict;
import com.fwk.common.util.Page;

/**
 * 数据字典实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年1月20日 上午9:50:48
 *
 */
public class DictEXT extends TCdict {

	// 分页对象
	private Page page;
	// 排序的SQL（不包含“ ORDER BY ”）
	private String orderBySQL;
	// 开始时间
	private String createdatestart;
	// 截至时间
	private String createdateend;

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

}
