package com.fwk.service.common.entity;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 品类对象
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月5日 下午4:48:54
 */
public class CategoryEXT {
	
	//品类编号
	private String id;
	
	//品类名称
	private String name;
	
	//品类状态(0：无效，1：有效)
	private int status;
	
	//品类级别
	private int level;
	
	//品类标签(标签编号列表)
	private List<String> tags;
	
	//添加时间
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
