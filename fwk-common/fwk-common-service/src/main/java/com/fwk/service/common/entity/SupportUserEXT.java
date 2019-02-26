package com.fwk.service.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.entity.TUusers;
import com.fwk.common.util.ObjectUtil;

/**
 * 支撑平台的用户实体扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月30日 下午4:29:14
 *
 */
public class SupportUserEXT extends TUusers {
	// {
	// "birthday":{
	// "$numberLong":"1520899200000"
	// },
	// "tenants":[
	// {
	// "$oid":"5abda9c146e0fb00013b70db"
	// }
	// ],
	// "address":{
	// "province":{
	// "$oid":"530000000000530000000000"
	// },
	// "city":{
	// "$oid":"530100000000530100000000"
	// },
	// "street":"昆明市呈贡区银河科技园",
	// "district":{
	// "$oid":"530114000000530114000000"
	// }
	// },
	// "gender":2,
	// "mobile":"18182914406",
	// "updatedOn":{
	// "$numberLong":"1522378894307"
	// },
	// "avatar":"http://sg-iss.oss-cn-qingdao.aliyuncs.com/staff-avatar-1522378991858.jpg",
	// "createdOn":{
	// "$numberLong":"1522378894307"
	// },
	// "password":"wugKd5x1626quiGlwQYau6xfbq2X74U40aGTkQEf+gU=$uN6TkSXJQF8KLJfs8leTUf/pveA=$100000",
	// "name":"merry",
	// "nickname":"123",
	// "_id":{
	// "$oid":"5abda88e46e0fb00013b70d8"
	// },
	// "status":1
	// }

	// 支撑平台的用户头像地址
	private String avatar;
	// 农事系统内部的用户头像地址
	private String photourl;
	// 支撑平台上的用户的ID列表
	private List<String> supportidlist;
	// 农事系统的用户ID列表
	private List<Integer> idlist;
	// 是否已经拷贝过属性值
	private Boolean iscopyvalue;
	// 该用户所属的组织列表
	private JSONArray tenants;
	private List<String> tenantlist;
	private String _id;
	// 手机号码
	private String mobile;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public List<String> getSupportidlist() {
		return supportidlist;
	}

	public void setSupportidlist(List<String> supportidlist) {
		this.supportidlist = supportidlist;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public Boolean getIscopyvalue() {
		return iscopyvalue;
	}

	public void setIscopyvalue(Boolean iscopyvalue) {
		this.iscopyvalue = iscopyvalue;
	}

	public JSONArray getTenants() {
		return tenants;
	}

	public void setTenants(JSONArray tenants) {

		if ((null != tenants) && (tenants.size() > 0)) {
			List<String> tenantlist = new ArrayList<String>();

			for (int i = 0; i < tenants.size(); i++) {
				tenantlist.add(((JSONObject) tenants.get(i)).getString("$oid"));
			}

			this.setTenantlist(tenantlist);
		}

		this.tenants = tenants;
	}

	public List<String> getTenantlist() {
		return tenantlist;
	}

	public void setTenantlist(List<String> tenantlist) {
		this.tenantlist = tenantlist;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
