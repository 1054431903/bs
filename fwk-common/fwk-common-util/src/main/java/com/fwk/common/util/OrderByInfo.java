package com.fwk.common.util;

import com.fwk.common.util.constants.EOrderBySQL;

/**
 * 用于排序的字段信息
 * 
 * @author wangjunwen
 * @date 2018年2月27日 下午2:23:00
 *
 */
public class OrderByInfo {
	// 需要排序的字段
	private String paremetername;
	// 排序的方式
	private String orderbytype;
	// 排序方式对象（默认升序排序）
	private EOrderBySQL eorderbysql = EOrderBySQL.ASC;

	public String getParemetername() {
		return paremetername;
	}

	public void setParemetername(String paremetername) {
		this.paremetername = paremetername;
	}

	public String getOrderbytype() {
		return orderbytype;
	}

	public void setOrderbytype(String orderbytype) {
		this.orderbytype = orderbytype;

		/** 在内部设置排序方式 */
		this.setEorderbysql(EOrderBySQL.getDefaultByNameIfNull(this.orderbytype));
	}

	public EOrderBySQL getEorderbysql() {
		return eorderbysql;
	}

	public void setEorderbysql(EOrderBySQL eorderbysql) {
		this.eorderbysql = eorderbysql;
	}

}
