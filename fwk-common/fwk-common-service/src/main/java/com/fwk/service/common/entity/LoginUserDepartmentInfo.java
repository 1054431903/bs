package com.fwk.service.common.entity;

import com.fwk.common.entity.TUdepartment;

/**
 * 登录用户所在的部门
 * 
 * @author wangjunwen
 * @date 2018年3月20日 上午10:10:25
 *
 */
public class LoginUserDepartmentInfo extends TUdepartment {
	// 服务人编号
	private String manager;

	// 数据的创建人（数据权限）
	private String userpermisionscreateuserid;
	// 农事参与人
	private String recordpartuserids;

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getUserpermisionscreateuserid() {
		return userpermisionscreateuserid;
	}

	public void setUserpermisionscreateuserid(String userpermisionscreateuserid) {
		this.userpermisionscreateuserid = userpermisionscreateuserid;
	}

	public String getRecordpartuserids() {
		return recordpartuserids;
	}

	public void setRecordpartuserids(String recordpartuserids) {
		this.recordpartuserids = recordpartuserids;
	}

}
