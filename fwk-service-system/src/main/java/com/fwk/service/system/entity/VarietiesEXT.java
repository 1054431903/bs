package com.fwk.service.system.entity;

import com.fwk.common.entity.TSvarieties;
import com.fwk.common.util.Page;

public class VarietiesEXT extends TSvarieties {
	
	//品类名称
	private String categoryname;

	// 排序字段，默认以creattime字段
	private String ordercol;

	// 排序方式(升序或降序)，默认为desc
	private String desc;

	// 分页信息
	private Page page;

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
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
