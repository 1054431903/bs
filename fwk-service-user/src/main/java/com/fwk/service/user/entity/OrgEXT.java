package com.fwk.service.user.entity;

import java.util.List;

import com.fwk.common.entity.TUorgWithBLOBs;

public class OrgEXT extends TUorgWithBLOBs{
	
	private String sign="1";
	
	private String logourl = "";
	
	private String permiturl = "";
	
	private String naturename = "";
	
	private String scalename = "";
	
	private List<UserEXT> userChildren;//用户集合
	
	private Integer userCount;//用户数量
	
	public List<UserEXT> getUserChildren() {
		return userChildren;
	}

	public void setUserChildren(List<UserEXT> userChildren) {
		this.userChildren = userChildren;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	private List<DepartmentEXT> children;
	
	public List<DepartmentEXT> getChildren() {
		return children;
	}

	public void setChildren(List<DepartmentEXT> children) {
		this.children = children;
	}

	public String getNaturename() {
		return naturename;
	}

	public void setNaturename(String naturename) {
		this.naturename = naturename;
	}

	public String getScalename() {
		return scalename;
	}

	public void setScalename(String scalename) {
		this.scalename = scalename;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getPermiturl() {
		return permiturl;
	}

	public void setPermiturl(String permiturl) {
		this.permiturl = permiturl;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
