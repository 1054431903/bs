package com.fwk.service.common.entity;

import com.fwk.common.entity.TUorgWithBLOBs;

/**
 * 当前登录用户的组织信息
 * 
 * @author wangjunwen
 * @date 2018年3月20日 上午10:36:00
 *
 */
public class LoginUserOrgInfo extends TUorgWithBLOBs {

	// 法人编号
	private String businessEntity;

	public String getBusinessEntity() {
		return businessEntity;
	}

	public void setBusinessEntity(String businessEntity) {
		this.businessEntity = businessEntity;
	}

}
