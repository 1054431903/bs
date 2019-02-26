package com.fwk.service.user.entity;

import java.util.List;


/**
 * 查询用户自定义实体
 * */
public class OrgDepartUserDefine {
	
	private String id;
	
	private String name;
	
	private String type = "1";
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private List<UserEXT> children;

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

	public List<UserEXT> getChildren() {
		return children;
	}

	public void setChildren(List<UserEXT> children) {
		this.children = children;
	}

	

}
