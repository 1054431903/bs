package com.fwk.service.system.entity;

import java.util.List;

import com.fwk.common.entity.TSinformationcontent;
import com.fwk.common.util.Page;

public class InformationContentEXT extends TSinformationcontent {

	// 业务主键列表
	private List<Integer> idlist;

	// 排序字段，默认以sort字段
	private String ordercol;

	// 排序方式(升序或降序)，默认为asc
	private String desc;

	//分页信息
	private Page page;
	
	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public String getOrdercol() {
		return ordercol;
	}

	public void setOrdercol(String ordercol) {
		this.ordercol = ordercol;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
