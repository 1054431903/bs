package com.fwk.service.user.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.entity.OrgEXT;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.entity.UserOrgDepartEXT;
import com.fwk.service.user.service.DepartmentService;
import com.fwk.service.user.service.OrgService;
import com.fwk.service.user.service.UserService;

/**
 * 用户与支撑关联数据操作
 * */
@Service
public class UserSupportBO {
	
	@Autowired
	private SupportUserService supportUserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private SupportOrgService supportOrgService;
	
	@Autowired
	private SupportService supportService;
	
	/**
	 * 通过本地单个用户查询支撑用户（并同步）
	 * */
	public UserEXT setSupportInfoByUser(UserEXT userEXT,ResultWrap resultWrap,String orgSupportid){
		if(ObjectUtil.isNotBlank(userEXT)){
			//支撑关联id不为空，获取支撑数据
			if(ObjectUtil.isNotBlank(userEXT.getSupportid())){
				Map<String,Object> requestMap = new HashMap<String,Object>();
				requestMap.put("supportid", userEXT.getSupportid());
				supportUserService.queryUserInfoToSupport(requestMap, resultWrap);
				if(ObjectUtil.isNotBlank(resultWrap) && ObjectUtil.isNotBlank(resultWrap.getData())){
					JSONObject resultJO = JSONArray.parseArray(resultWrap.getData().toString()).getJSONObject(0);
					if(ObjectUtil.isNotBlank(resultJO.get("updatedOn"))) {
						JSONObject jo = JSONObject.parseObject(resultJO.getString("updatedOn"));
						userEXT.setOperateredittime(jo.get("$numberLong")+"");
					}
					userEXT = ConformSupportToUser(userEXT,resultJO);
					if(ObjectUtil.isNotBlank(resultJO.get("tenants"))){
						List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
						if(ObjectUtil.isNotBlank(orgSupportid)){
							jsonObjects = supportOrgService.getSupportTenantsId(orgSupportid);//获取组织下的所有部门编号
						}
						
						JSONArray tenantsJA = JSONArray.parseArray(resultJO.getString("tenants"));
						for (int i = 0; i < tenantsJA.size(); i++) {
							String tenantID = tenantsJA.getJSONObject(i).getString("$oid");
							for (int j = 0; j < jsonObjects.size(); j++) {//设置departmentid/departmentname
								if(ObjectUtil.isNotBlank(tenantID) && tenantID.equals(jsonObjects.get(j).getString("$oid"))){
									userEXT.setDepartmentid(tenantID);
									JSONObject resultTenantJO = supportOrgService.getSupportTenantById(tenantID);
									userEXT.setDepartmentid(tenantID);
									userEXT.setDepartmentname(resultTenantJO.getString("name"));
								}
							}
							JSONObject tenantOidJO = new JSONObject();
							tenantOidJO.put("$oid", tenantID);
							JSONObject tenantIdJO = new JSONObject();
							tenantIdJO.put("_id", tenantOidJO);
							
							SupportRequestParam supportRequestParam = new SupportRequestParam();
							supportRequestParam.setFilterJson(tenantIdJO);
							Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
							if(ObjectUtil.isNotBlank(result)){
								JSONArray resultJA = SupportUtil.supportGetItemData(result);
								for (int j = 0; j < resultJA.size(); j++) {
									Integer type = JSONObject.parseObject(resultJA.getJSONObject(j).toString()).getInteger("type");
									
									String supportid = JSONObject.parseObject(JSONObject.parseObject(resultJA.getJSONObject(j).toString()).getString("_id")).getString("$oid");
									if(type == 1){//更新组织信息
										Map<String,Object> departMap = new HashMap<String,Object>();
										departMap.put("supportid", supportid);
										List<DepartmentEXT> departmentEXTs = departmentService.queryDepartment(departMap);
										if(departmentEXTs.size() <= 0){//添加数据至部门
											departmentService.saveDepartment(departMap);
										}
									}else if(type == 2){//更新部门信息
										Map<String,Object> orgMap = new HashMap<String,Object>();
										orgMap.put("supportid", supportid);
										List<OrgEXT> orgEXTs = orgService.queryOrg(orgMap);
										if(orgEXTs.size() <= 0){//添加数据至部门
											orgService.saveOrg(orgMap);
										}
									}
								}
							}
						}
					}
				}else{
					resultWrap.setSupportNoInfoActFailure();
				}
			}else{//关联支撑id为空，执行同步数据至支撑
				JSONObject supportJO = new JSONObject();
				ConformUserToSupport(userEXT, supportJO);
				SupportRequestParam supportRequestParam = new SupportRequestParam();
				supportRequestParam.setDataJson(supportJO);
				ResultWrap resultWrap2 = supportService.requestSupportPOST(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
				if(ObjectUtil.isNotBlank(resultWrap2) && ObjectUtil.isNotBlank(resultWrap2.getData())){
					JSONObject jo = JSONObject.parseObject(resultWrap2.getData().toString());
					userEXT.setSupportid(JSONObject.parseObject(jo.getString("_id")).getString("$oid"));
					userService.updateUserSupportid(userEXT);
				}
			}
		}
		return userEXT;
	}
	
	/**
	 * 通过支撑单个用户查询本地用户（并同步）
	 * */
	
	/**
	 * 通过本地用户列表查询支撑用户（并同步）
	 * */
	
	/**
	 * 通过支撑用户列表查询本地用户（并同步）
	 * */
	
	
	/**
	 * 整合支撑数据至用户对象
	 * { "items" : [{ "_id" : { "$oid" : "5aa72f2946e0fb00013b6ed2" }, "status" : 1, 
	 * "mobile" : "15288121878", "name" : "谢谢", "gender" : 2, "nickname" : "xx", 
	 * "avatar" : "http://wt-yunchuang.oss-cn-beijing.aliyuncs.com/staff-avatar-1520905948542.jpg", 
	 * "address" : { "province" : { "$oid" : "530000000000530000000000" }, "city" : { "$oid" : "530100000000530100000000" }, 
	 * "district" : { "$oid" : "530112000000530112000000" }, "street" : "街道搬事处" }, 
	 * "createdOn" : { "$numberLong" : "1520906025548" }, "updatedOn" : { "$numberLong" : "1520906025548" }, 
	 * "tenants" : [{ "$oid" : "5aa72ea046e0fb00013b6ecf" }] }], "total" : { "$numberLong" : "1" } }
	 * */
	public UserEXT ConformSupportToUser(UserEXT userEXT,JSONObject jsonObject){
		if(ObjectUtil.isNotBlank(jsonObject.get("_id"))) userEXT.setSupportid(JSONObject.parseObject(jsonObject.getString("_id")).getString("$oid"));
		if(ObjectUtil.isNotBlank(userEXT.getSupportid())){
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("supportid", userEXT.getSupportid());
			List<UserEXT> userEXTs = userService.queryUser(userMap);
			if(ObjectUtil.isNotBlank(userEXTs) && userEXTs.size()==1){
				userEXT = userEXTs.get(0);
			}
		}
		if(ObjectUtil.isNotBlank(jsonObject.get("updatedOn"))) {
			JSONObject jo = JSONObject.parseObject(jsonObject.getString("updatedOn"));
			userEXT.setOperateredittime(jo.get("$numberLong")+"");
		}
		if(jsonObject.get("mobile") != null) {
			userEXT.setAccount(jsonObject.getString("mobile"));
			userEXT.setPhone(jsonObject.getString("mobile"));
		}
		if(jsonObject.get("gender") != null) {
			userEXT.setSex(jsonObject.getString("gender"));
		}
		if(jsonObject.get("name") != null)  userEXT.setName(jsonObject.getString("name"));
		if(jsonObject.get("password") != null) userEXT.setPassword(jsonObject.getString("password"));
		if(jsonObject.get("status") != null) userEXT.setStatus(jsonObject.getString("status"));
		if(ObjectUtil.isNotBlank(jsonObject.get("birthday"))) userEXT.setBirthday(JSONObject.parseObject(jsonObject.getString("birthday")).getDate("$numberLong"));
		if(jsonObject.get("nickname") != null) userEXT.setNickname(jsonObject.getString("nickname"));
		if(jsonObject.get("avatar") != null) userEXT.setPhotoURL(jsonObject.getString("avatar"));
		if(jsonObject.get("address") != null) {
			JSONObject addressJO = JSONObject.parseObject(jsonObject.getString("address"));
			userEXT.setAddresscode(SupportUtil.supportGetAddressCode(addressJO));
			userEXT.setAddressdetail(SupportUtil.supportGetAddressDetail(addressJO));
		}
		if(ObjectUtil.isNotBlank(jsonObject.get("loginOn"))) userEXT.setLastlogin(jsonObject.getDate("loginOn"));
		if(ObjectUtil.isNotBlank(jsonObject.get("createdOn"))) userEXT.setCreattime(JSONObject.parseObject(jsonObject.getString("createdOn")).getDate("$numberLong"));
		
		return userEXT;
	}
	
	/**
	 * 整合用户对象至支撑数据
	 * */
	public void ConformUserToSupport(UserEXT userEXT,JSONObject jsonObject){
		if(ObjectUtil.isNotBlank(userEXT.getPhone())) jsonObject.put("mobile", userEXT.getPhone());
		if(ObjectUtil.isNotBlank(userEXT.getSex())) jsonObject.put("gender", userEXT.getSex());
		if(ObjectUtil.isNotBlank(userEXT.getName())) jsonObject.put("name", userEXT.getName());
		if(ObjectUtil.isNotBlank(userEXT.getPassword())) jsonObject.put("password", userEXT.getPassword());
		if(ObjectUtil.isNotBlank(userEXT.getStatus())) jsonObject.put("status", userEXT.getStatus());
		if(ObjectUtil.isNotBlank(userEXT.getBirthday())) jsonObject.put("birthday", userEXT.getBirthday());
		if(ObjectUtil.isNotBlank(userEXT.getNickname())) jsonObject.put("nickname", userEXT.getNickname());
		if(ObjectUtil.isNotBlank(userEXT.getPhotoURL())) jsonObject.put("avatar", userEXT.getPhotoURL());
		if(ObjectUtil.isNotBlank(userEXT.getAddresscode())) jsonObject.put("address", userEXT.getAddresscode());
		if(ObjectUtil.isNotBlank(userEXT.getLastlogin())) jsonObject.put("loginOn", userEXT.getLastlogin());
		
		//获取组织/部门关系
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("userid", userEXT.getId());
		List<UserOrgDepartEXT> userOrgDepartEXTs = userService.queryUserOrgDepart(userMap);
		JSONArray tenantsJA = new JSONArray();
		for (int i = 0; i < userOrgDepartEXTs.size(); i++) {
			JSONObject tenantsidJO = new JSONObject();
			UserOrgDepartEXT userOrgDepartEXT = userOrgDepartEXTs.get(i);
			//获取组织或部门的supportid
			if(ObjectUtil.isNotBlank(userOrgDepartEXT.getDepartmentid())){//部门编号不为空
				//获取部门的supportid
				Map<String,Object> departMap = new HashMap<String,Object>();
				departMap.put("id", userOrgDepartEXT.getDepartmentid());
				DepartmentEXT departmentEXT = departmentService.queryDepartmentDetail(departMap);
				if(ObjectUtil.isNotBlank(departmentEXT) && ObjectUtil.isNotBlank(departmentEXT.getSupportid())){
					tenantsidJO.put("$oid", departmentEXT.getSupportid());
					tenantsJA.add(tenantsidJO);
				}
			}else if(ObjectUtil.isNotBlank(userOrgDepartEXT.getOrgid())){//组织编号不为空
				//获取组织的supportid
				Map<String,Object> orgMap = new HashMap<String,Object>();
				orgMap.put("id", userOrgDepartEXT.getOrgid());
				List<OrgEXT> orgEXTs = orgService.queryOrg(orgMap);
				if(ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() >= 1 && ObjectUtil.isNotBlank(orgEXTs.get(0).getSupportid())){
					tenantsidJO.put("$oid", orgEXTs.get(0).getSupportid());
					tenantsJA.add(tenantsidJO);
				}
			}
		}
		jsonObject.put("tenants", tenantsJA);
	}
}
