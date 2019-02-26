package com.fwk.service.system.entity;

import java.util.List;

import com.fwk.common.entity.TSinformation;
import com.fwk.common.util.Page;

public class InformationEXT extends TSinformation{
	
	// 业务主键列表
	private List<Integer> idlist;
	
	//资源图片编号
	private Integer resourceid;
	
	//资源图片路径
	private String resourcepath;
	
	//发布人头像编号
	private Integer photoid;
	
	//发布人头像路径
	private String photopath;
	
	//发布人所属机构名称
	private String puborgname;
	
	//排序字段，默认以creattime字段
	private String ordercol;
	
	//排序方式(升序或降序)，默认为desc 
	private String desc;
	
	//分布信息
	private Page page;
	
	//信息发布人姓名
	private String pubusername;
	
	//搜索关键字(包括标题、发布人姓名等)
	private String keywords;
	
	//品类名称
	private String categoryname;

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public Integer getResourceid() {
		return resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}

	public String getResourcepath() {
		return resourcepath;
	}

	public void setResourcepath(String resourcepath) {
		this.resourcepath = resourcepath;
	}

	public Integer getPhotoid() {
		return photoid;
	}

	public void setPhotoid(Integer photoid) {
		this.photoid = photoid;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getPuborgname() {
		return puborgname;
	}

	public void setPuborgname(String puborgname) {
		this.puborgname = puborgname;
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

	public String getPubusername() {
		return pubusername;
	}

	public void setPubusername(String pubusername) {
		this.pubusername = pubusername;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
