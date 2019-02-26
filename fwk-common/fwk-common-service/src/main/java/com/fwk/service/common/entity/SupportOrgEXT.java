package com.fwk.service.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.entity.TUorgWithBLOBs;
import com.fwk.common.util.ObjectUtil;

/**
 * 支撑平台的组织信息扩展 类
 * 
 * @author wangjunwen
 * @date 2018年3月29日 上午10:59:02
 *
 */
public class SupportOrgEXT extends TUorgWithBLOBs {

	// {
	// "businessLicense":"",
	// "businessScope":"斯蒂芬斯蒂芬",
	// "remark":"使得发达省份",
	// "businessEntity":"11231",
	// "updatedOn":{
	// "$numberLong":"1521781270081"
	// },
	// "type":1,
	// "createdOn":{
	// "$numberLong":"1521781270081"
	// },
	// "registeredAddress":{
	// "province":{
	// "$oid":"130000000000130000000000"
	// },
	// "city":{
	// "$oid":"130300000000130300000000"
	// },
	// "street":"斯蒂芬斯蒂芬",
	// "district":{
	// "$oid":"130303000000130303000000"
	// }
	// },
	// "staffSize":"scale-above",
	// "registeredCapital":1232131,
	// "foundingTime":{
	// "$numberLong":"1520870400000"
	// },
	// "name":"测试",
	// "logo":"",
	// "_id":{
	// "$oid":"5ab48a1646e0fb00013b7032"
	// },
	// "businessType":"nature-institution"
	// }

	private String logourl;

	private String businessLicense;

	private String businessScope;

	private String remark;

	private String businessEntity;

	private String updatedOn;

	private Integer type;

	private String createdOn;

	private String registeredAddress;

	private String staffSize;

	private Integer registeredCapital;

	private String foundingTime;

	private String supportname;

	private String supportlogo;

	private String _id;

	private String businessType;

	private String addressscodedetail;

	// 管理员
	private JSONObject manager;
	// 管理员的ID
	private String manageruserid;

	// 当前登录的用户是否是管理员或者是法人
	private boolean currentlonginuserisadmin = false;

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusinessEntity() {
		return businessEntity;
	}

	public void setBusinessEntity(String businessEntity) {
		this.businessEntity = businessEntity;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getStaffSize() {
		return staffSize;
	}

	public void setStaffSize(String staffSize) {
		this.staffSize = staffSize;
	}

	public Integer getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Integer registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getFoundingTime() {
		return foundingTime;
	}

	public void setFoundingTime(String foundingTime) {
		this.foundingTime = foundingTime;
	}

	public String getSupportname() {
		return supportname;
	}

	public void setSupportname(String supportname) {
		this.supportname = supportname;
	}

	public String getSupportlogo() {
		return supportlogo;
	}

	public void setSupportlogo(String supportlogo) {
		this.supportlogo = supportlogo;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {

		if (ObjectUtil.isNotBlank(_id)) {
			try {
				JSONObject jsonObject = (JSONObject) JSONObject.parse(_id);
				this.setSupportid(jsonObject.getString("$oid"));
			} catch (Exception e) {
			}
		}

		this._id = _id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getAddressscodedetail() {
		return addressscodedetail;
	}

	public void setAddressscodedetail(String addressscodedetail) {
		this.addressscodedetail = addressscodedetail;
	}

	public JSONObject getManager() {
		return manager;
	}

	public void setManager(JSONObject manager) {

		if (null != manager) {
			this.setManageruserid(manager.getString("$oid"));
		}

		this.manager = manager;
	}

	public String getManageruserid() {
		return manageruserid;
	}

	public void setManageruserid(String manageruserid) {
		this.manageruserid = manageruserid;
	}

	public boolean isCurrentlonginuserisadmin() {
		return currentlonginuserisadmin;
	}

	public void setCurrentlonginuserisadmin(boolean currentlonginuserisadmin) {
		this.currentlonginuserisadmin = currentlonginuserisadmin;
	}

}
