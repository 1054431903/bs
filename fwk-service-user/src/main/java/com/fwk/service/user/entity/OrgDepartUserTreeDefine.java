package com.fwk.service.user.entity;

import java.util.List;

import com.fwk.common.entity.TUusers;


/**
 * 组织、部门、用户树形结构
 * */
public class OrgDepartUserTreeDefine extends TUusers{
	
	private Integer id;
	
	private String name;
	
	private String phoneurl;
	
	private String nickname;
	
	private String jobnumber;
	
	private String email;
	
	private String phone;
	
	private Integer count;
	
	private String sign;//标识：1：组织，2：部门，3：用户，4：角色
	
	private Boolean expanded; //节点是否展开
	
	private String title;
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	private Object children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneurl() {
		return phoneurl;
	}

	public void setPhoneurl(String phoneurl) {
		this.phoneurl = phoneurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Object getChildren() {
		return children;
	}

	public void setChildren(Object children) {
		this.children = children;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
