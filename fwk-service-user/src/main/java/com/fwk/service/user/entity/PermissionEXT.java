package com.fwk.service.user.entity;

import java.util.List;

import com.fwk.common.entity.TUpermission;

public class PermissionEXT extends TUpermission{
	
	private List<PermissionEXT> children;
	
	private List<PermissionEXT> functions;

	public List<PermissionEXT> getFunctions() {
		return functions;
	}

	public void setFunctions(List<PermissionEXT> functions) {
		this.functions = functions;
	}

	public List<PermissionEXT> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionEXT> children) {
		this.children = children;
	}

}
