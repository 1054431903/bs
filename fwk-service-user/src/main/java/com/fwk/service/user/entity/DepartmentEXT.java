package com.fwk.service.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.fwk.common.entity.TUdepartment;

public class DepartmentEXT extends TUdepartment{
	
	private String sign="2";
	
	private List<DepartmentEXT> children;
	
	private List<UserEXT> userChildren;//用户集合
	
	private Integer userCount;//用户数量

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public List<UserEXT> getUserChildren() {
		return userChildren;
	}

	public void setUserChildren(List<UserEXT> userChildren) {
		this.userChildren = userChildren;
	}

	public List<DepartmentEXT> getChildren() {
		return children;
	}

	public void setChildren(List<DepartmentEXT> children) {
		this.children = children;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	

}
