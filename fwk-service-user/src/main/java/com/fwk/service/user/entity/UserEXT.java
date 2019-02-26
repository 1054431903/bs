package com.fwk.service.user.entity;

import com.fwk.common.entity.TUusers;

public class UserEXT extends TUusers{
	
	private String account = "-";//用户名，来源于支撑平台数据
	
	private String rolename = "";//角色名称，以，分割
	
	private String roleid = "";//角色编号，以，分割
	
	private String photoURL = "";//图片地址
	
	private String departmentid = "";//所属部门编号
	
	private String departmentname = "";//所属部门名称
	
	private String orgName = "";//组织名称
	
	private String sexName = "";
	
	private String type = "2";
	
	private String operateredittime;
	
	public String getOperateredittime() {
		return operateredittime;
	}

	public void setOperateredittime(String operateredittime) {
		this.operateredittime = operateredittime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	private boolean isdepartmentbelong;//是否是部门所属人
	
	@Override
	public void setSex(String sex) {
		super.setSex(sex);
		if("2".equals(sex)){
			setSexName("女");
		}
		if("1".equals(sex)){
			setSexName("男");
		}
	}
	
	public boolean isIsdepartmentbelong() {
		return isdepartmentbelong;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public void setIsdepartmentbelong(boolean isdepartmentbelong) {
		this.isdepartmentbelong = isdepartmentbelong;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
}
