package com.fwk.service.common.entity;

import java.util.List;

import com.fwk.common.entity.TUusers;

/**
 * 当前登录的用户信息
 * 
 * @author wangjunwen
 * @date 2018年3月8日 下午4:14:59
 *
 */
public class LoginUserInfo extends TUusers {

	// 用户所属的部门ID（来源于支撑平台）
	private List<String> departmentsupportidlist;
	// 当前登录用户所在组织的部门信息
	private List<LoginUserDepartmentInfo> loginuserdepartmentinfolist;
	// 当前用户登录到该组织下
	private LoginUserOrgInfo loginuserorginfo;
	// 当前用户拥有的所有组织
	private List<SupportOrgEXT> ownerorglist;
	// 当前用户拥有的所有部门
	private List<SupportOrgEXT> ownerdepartmentlist;
	
	//xx:可查询用户列表
	private List<Integer> userIds;

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public List<String> getDepartmentsupportidlist() {
		return departmentsupportidlist;
	}

	public void setDepartmentsupportidlist(List<String> departmentsupportidlist) {
		this.departmentsupportidlist = departmentsupportidlist;
	}

	public List<LoginUserDepartmentInfo> getLoginuserdepartmentinfolist() {
		return loginuserdepartmentinfolist;
	}

	public void setLoginuserdepartmentinfolist(List<LoginUserDepartmentInfo> loginuserdepartmentinfolist) {
		this.loginuserdepartmentinfolist = loginuserdepartmentinfolist;
	}

	public LoginUserOrgInfo getLoginuserorginfo() {
		return loginuserorginfo;
	}

	public void setLoginuserorginfo(LoginUserOrgInfo loginuserorginfo) {
		this.loginuserorginfo = loginuserorginfo;
	}

	public List<SupportOrgEXT> getOwnerorglist() {
		return ownerorglist;
	}

	public void setOwnerorglist(List<SupportOrgEXT> ownerorglist) {
		this.ownerorglist = ownerorglist;
	}

	public List<SupportOrgEXT> getOwnerdepartmentlist() {
		return ownerdepartmentlist;
	}

	public void setOwnerdepartmentlist(List<SupportOrgEXT> ownerdepartmentlist) {
		this.ownerdepartmentlist = ownerdepartmentlist;
	}

}
