package com.fwk.service.user.entity;

import com.fwk.common.entity.TUrole;

public class RoleEXT extends TUrole{
	
	private String permissionnames;
	
	private String permissionids;

	public String getPermissionids() {
		return permissionids;
	}

	public void setPermissionids(String permissionids) {
		this.permissionids = permissionids;
	}

	public String getPermissionnames() {
		return permissionnames;
	}

	public void setPermissionnames(String permissionnames) {
		this.permissionnames = permissionnames;
	}

	

}
