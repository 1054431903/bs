package com.fwk.service.common.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.service.CUserService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportUserPasswordUtil;
import com.fwk.service.common.support.util.SupportUserUtil;
import com.fwk.service.common.support.util.SupportUtil;

/**
 * 支撑平台-用户操作
 */
@Service
public class SupportUserService {

	@Autowired
	private SupportUserUtil supportUserUtil;

	@Autowired
	private SupportService supportService;
	
	@Autowired
	private CUserService userService;

	/**
	 * 密码加密
	 */
	public Object encryPassword(Object password) {
		return SupportUserPasswordUtil.passwordHash(password.toString().toCharArray());
	}

	/**
	 * 用户登录
	 */
	public void supportUserLogin(Map<String, Object> requestMap, ResultWrap resultWrap) {
		String password = requestMap.get("password") + "";
		// 转换requestMap成支撑需要的json对象
		JSONObject filterJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, false, "password");
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			JSONArray resultJA = SupportUtil.supportGetItemData(result);
			if (resultJA.size() >= 1) {
				//支撑密码
				if(ObjectUtil.isNotBlank(requestMap.get("supportmm"))) {
					if (requestMap.get("supportmm").equals(((JSONObject) resultJA.get(0)).get("password").toString())) {
						resultWrap.setData(resultJA);
					}else {
						resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
					}
				//用户输入的密码
				}else if (SupportUserPasswordUtil.passwordMatches(password,
						((JSONObject) resultJA.get(0)).get("password").toString())) {
					resultWrap.setData(resultJA);
				} else {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
				}
			} else {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
			}
		} else {
			resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
		}
	}

	/**
	 * 查询用户
	 */
	public void queryUserInfoToSupport(Map<String, Object> requestMap, ResultWrap resultWrap) {
		System.out.println("支撑平台-用户查询----SupportUserController----queryUserInfoToSupport:" + requestMap);
		// 转换requestMap成支撑需要的json对象
		JSONObject filterJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, false);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		try {
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				if (resultJA.size() >= 1) {
					resultWrap.setData(resultJA);
				}
			}
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
		}
	}

	public void queryUserInfoToSupportNew(Map<String, Object> requestMap, ResultWrap resultWrap) {
		System.out.println("支撑平台-用户查询----SupportUserController----queryUserInfoToSupport:" + requestMap);
		// 转换requestMap成支撑需要的json对象
		JSONObject filterJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, false);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		try {
			Object result = supportService.requestSupportGetNew(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				if (resultJA.size() >= 1) {
					resultWrap.setData(resultJA);
				}
			}
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
		}
	}

	/**
	 * 添加用户
	 */
	public ResultWrap insertUserInfoToSupport(Map<String, Object> requestMap) {
		System.out.println("支撑平台-用户添加----SupportUserController----insertUserInfoToSupport:" + requestMap);
		if (ObjectUtil.isNotBlank(requestMap.get("password"))) {
			requestMap.put("password", encryPassword(requestMap.get("password")));
		}
		requestMap.put("status", 1);
		JSONObject dataJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, false);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		return supportService.requestSupportPOST(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}

	/**
	 * 修改用户
	 */
	public Object editUserInfoToSupport(Map<String, Object> requestMap) {
		if (ObjectUtil.isNotBlank(requestMap.get("password"))) {
			requestMap.put("password", encryPassword(requestMap.get("password")));
		}
		JSONObject dataJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, true);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		System.out.println("支撑平台-用户修改----SupportUserController----editUserInfoToSupport:" + requestMap);
		return supportService.requestSupportPUT(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}

	/**
	 * 删除用户
	 */
	public Object deleteUserInfoToSupport(Map<String, Object> requestMap) {
		requestMap.put("_removed", 1);
		JSONObject dataJson = supportUserUtil.packUserInfoToSupportTenantStaff(requestMap, true);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		System.out.println("支撑平台-用户删除----SupportUserController----deleteUserInfoToSupport:" + requestMap);
		return supportService.requestSupportDELETE(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}

	/**
	 * 将用户内的 组织/部门 信息移出 tenantid:组织编号（非部门编号） supportid：用户编号
	 */
	public boolean deleteTenantsBySupportid(String tenantid, String supportid) {
		if (ObjectUtil.isBlank(tenantid) || ObjectUtil.isBlank(supportid)) {
			return false;
		}
		// 获取用户的关联组织/部门信息
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("supportid", supportid);
		ResultWrap resultWrap = new ResultWrap();
		queryUserInfoToSupport(requestMap, resultWrap);
		boolean isresult = false;
		if (ObjectUtil.isNotBlank(resultWrap) && resultWrap.getFlag() == 1
				&& ObjectUtil.isNotBlank(resultWrap.getData())) {
			JSONArray ja = JSONArray.parseArray(resultWrap.getData().toString());
			if (ObjectUtil.isNotBlank(ja) && ja.size() == 1) {
				JSONObject jo = ja.getJSONObject(0);
				if (ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(jo.get("tenants"))) {
					JSONArray tenantsja = jo.getJSONArray("tenants");
					for (int i = 0; i < tenantsja.size(); i++) {
						// 判断当前组织/部门id是否等于tenantid
						String orgid = checkTenantidIsBelongTenantid(tenantsja.getJSONObject(i).getString("$oid"));
						if (ObjectUtil.isNotBlank(orgid) && tenantid.equals(orgid)) {
							// 获取删除组织的json
							JSONObject dataJson = supportUserUtil.packUserDeleteTenantByUserId(
									tenantsja.getJSONObject(i).getString("$oid"), supportid);
							SupportRequestParam supportRequestParam = new SupportRequestParam();
							supportRequestParam.setDataJson(dataJson);
							Object result = supportService.requestSupportPUT(AllJsonRequestUrl.Tenant_staff,
									supportRequestParam);
							System.out.println(result);
							if (ObjectUtil.isNotBlank(isresult)) {
								isresult = true;
								break;
							}
						}
					}
				}
			}
		}
		return isresult;
	}

	/**
	 * 通过 组织/部门 id 获取组织id departid：组织或部门id
	 */
	public String checkTenantidIsBelongTenantid(String departid) {
		if (ObjectUtil.isNotBlank(departid)) {
			JSONObject oidJO = new JSONObject();
			oidJO.put("$oid", departid);
			JSONObject idJO = new JSONObject();
			idJO.put("_id", oidJO);

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(idJO);

			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
			JSONArray resultJA = SupportUtil.supportGetItemData(result);
			if (ObjectUtil.isNotBlank(resultJA) && resultJA.size() >= 1) {
				JSONObject jo = resultJA.getJSONObject(0);
				if (ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(jo.getInteger("type"))) {
					if (jo.getInteger("type") == 2) {// 部门,继续查询上一级
						return checkTenantidIsBelongTenantid(jo.getJSONObject("parent").getString("$oid"));
					} else {// 组织，直接返回id
						return jo.getJSONObject("_id").getString("$oid");
					}
				}
			}
			return "";
		} else {
			return "";
		}
	}

	/**
	 * 监测当前用户是不是该组织的法人 tenantid:组织id supportid:用户id
	 */
	public boolean isTenantManagerBySupportid(String tenantid, String supportid) {
		JSONObject toidJO = new JSONObject();
		toidJO.put("$oid", tenantid);
		JSONObject tidJO = new JSONObject();
		tidJO.put("_id", toidJO);
		JSONObject filterJO = new JSONObject();

		JSONObject tmoidJO = new JSONObject();
		tmoidJO.put("$oid", supportid);
		filterJO.put("manager", tmoidJO);
		filterJO.put("_id", toidJO);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJO);

		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		return SupportUtil.supportResultItemIsNotEmpty(result);
	}

	/**
	 * 获取用户的名称
	 */
	public String getDepartmentNameByUserId(String userSupportid, List<JSONObject> listJo) {
		String departmentName = "";
		JSONObject uoidJO = new JSONObject();
		uoidJO.put("$oid", userSupportid);
		JSONObject uidJO = new JSONObject();
		uidJO.put("_id", uoidJO);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(uidJO);

		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		if (ObjectUtil.isNotBlank(result)) {
			JSONArray resultJA = SupportUtil.supportGetItemData(result);
			if (ObjectUtil.isNotBlank(resultJA)) {
				for (int i = 0; i < resultJA.size(); i++) {
					JSONObject jo = resultJA.getJSONObject(i);
					if (ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(jo.get("tenants"))) {
						JSONArray ja = JSONArray.parseArray(jo.getString("tenants"));
						for (int j = 0; j < ja.size(); j++) {
							if (listJo.contains(ja.getJSONObject(j))) {
								String departmentid = ja.getJSONObject(j).getString("$oid");

								JSONObject doidJO = new JSONObject();
								doidJO.put("$oid", departmentid);
								JSONObject didJO = new JSONObject();
								didJO.put("_id", doidJO);

								SupportRequestParam supportRequestParam1 = new SupportRequestParam();
								supportRequestParam1.setFilterJson(didJO);

								Object resultd = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant,
										supportRequestParam1);
								if (ObjectUtil.isNotBlank(resultd)
										&& SupportUtil.supportResultItemIsNotEmpty(resultd)) {
									departmentName = JSONObject
											.parseObject(SupportUtil.supportGetItemData(resultd).get(0).toString())
											.getString("name");
								}
								break;
							}
						}
					}
				}
			}
		}
		return departmentName;
	}

	// 根据企业id获取登录的企业名称
	public String getOrgName(String loginorgid) {
		String orgName = "";
		if (ObjectUtil.isNotBlank(loginorgid)) {
			JSONObject doidJO = new JSONObject();
			doidJO.put("$oid", loginorgid);
			JSONObject didJO = new JSONObject();
			didJO.put("_id", doidJO);

			SupportRequestParam supportRequestParam1 = new SupportRequestParam();
			supportRequestParam1.setFilterJson(didJO);

			Object resultd = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam1);
			if (ObjectUtil.isNotBlank(resultd) && SupportUtil.supportResultItemIsNotEmpty(resultd)) {
				orgName = JSONObject.parseObject(SupportUtil.supportGetItemData(resultd).get(0).toString())
						.getString("name");
			}
		}
		return orgName;
	}

	public JSONObject queryUserDetailBySupportid(String userid) {
		JSONObject jsonObject = new JSONObject();
		if (ObjectUtil.isNotBlank(userid)) {
			JSONObject toidJO = new JSONObject();
			toidJO.put("$oid", userid);
			JSONObject tidJO = new JSONObject();
			tidJO.put("_id", toidJO);

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(tidJO);

//			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			Object result = requestGet(AllJsonRequestUrl.Tenant_staff,supportRequestParam,1,10);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				if (resultJA.size() >= 1) {
					jsonObject = resultJA.getJSONObject(0);
				}
			}
		}
		return jsonObject;
	}
	
	private Object requestGet(String table,SupportRequestParam supportRequestParam,int curCount,int totalCount) {
		if(curCount <= totalCount) {
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			try {
				JSONObject.parseObject(result+"");
				return result;
			} catch (Exception e) {
				System.out.println("数据错误重新发起请求："+e.getMessage());
				return requestGet(table,supportRequestParam,curCount+1,totalCount);
			}
		}
		return null;
	}

	/**
	 * 根据支撑平台的用户的ID列表查询出支撑平台上的用户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午12:44:37
	 *
	 * @param userSupportidList
	 *            支撑平台的用户的ID列表
	 * @return
	 */
	public JSONArray queryUserDetailBySupportidList(List<String> userSupportidList) {

		if ((null == userSupportidList) || (userSupportidList.isEmpty())) {
			return null;
		}

		// 请求示例：http://47.104.162.6:9000/api/mongo/iov/tenant.staff?filter={"_removed":null,"_id":{$in:[{"$oid":"5abb5a2e46e0fb00013b70b7"},{"$oid":"5abc7f2b46e0fb00013b70bf"}]}}
		final JSONArray parammJSONArray = new JSONArray();
		JSONObject paramJSONObject = null;
		for (int i = 0; i < userSupportidList.size(); i++) {
			if (ObjectUtil.isBlank(userSupportidList.get(i))) {
				continue;
			}

			paramJSONObject = new JSONObject();
			paramJSONObject.put("$oid", userSupportidList.get(i));
			parammJSONArray.add(i, paramJSONObject);
		}

		if (0 == parammJSONArray.size()) {
			return null;
		}

		JSONObject toidJSONObject = new JSONObject();
		toidJSONObject.put("$in", parammJSONArray);
		JSONObject tidJSONObject = new JSONObject();
		tidJSONObject.put("_id", toidJSONObject);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(tidJSONObject);

		JSONArray resultJSONArray = null;
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			resultJSONArray = SupportUtil.supportGetItemData(result);
		}

		return resultJSONArray;
	}
	
	/**
	 * 获取当前登录人的所有下级部门的用户
	 * @param loginer:登录者supportid
	 * 		  loginorgid:登录者的组织supportid
	 * */
	public List<Integer> getDepartUsersByLoginerIdBak(String loginer,String loginorgid){
		List<JSONObject> listJo = new ArrayList<JSONObject>();
		List<Integer> userStr = new ArrayList<Integer>();
		if(ObjectUtil.isNotBlank(loginer) && ObjectUtil.isNotBlank(loginorgid)) {
			//获取当前用户所在的部门/组织
			JSONObject userJO = queryUserDetailBySupportid(loginer);
			if(ObjectUtil.isNotBlank(userJO) && userJO.keySet().size() > 0 && ObjectUtil.isNotBlank(userJO.get("tenants"))) {
				String departid = "";
				JSONArray userTenantJA = JSONArray.parseArray(userJO.get("tenants")+"");
				//循环判断获取用户所属当前组织的部门id
				for (int i = 0; i < userTenantJA.size(); i++) {
					JSONObject jo = userTenantJA.getJSONObject(i);
					if(loginorgid.equals(checkTenantidIsBelongTenantid(jo.getString("$oid")))) {
						departid = jo.getString("$oid");
						continue;
					}
				}
				//获取当前部门下的所有部门
				getUserByDepardid(departid,userStr);
				getSupportTenantByParentid(departid,listJo,userStr);
			}
		}
		return userStr;
	}
	
	/**
	 * @Description: 获取当前登录人的所有下级部门的用户
	 *
	 * @param loginer  登录者supportid
	 * @param loginorgid  登录者的组织supportid
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年8月6日 上午9:47:38
	 */
	public List<Integer> getDepartUsersByLoginerId(String loginer,String loginorgid){
		List<JSONObject> listJo = new ArrayList<JSONObject>();
		List<Integer> userStr = new ArrayList<Integer>();
		if (ObjectUtil.isBlank(loginer) || ObjectUtil.isBlank(loginorgid)) {
			return userStr;
		}
		//获取当前用户所在的部门/组织
		JSONObject userJO = queryUserDetailBySupportid(loginer);
		if (ObjectUtil.isBlank(userJO) || userJO.size() == 0 || ObjectUtil.isBlank(userJO.get("tenants"))) {
			return userStr;
		}
		JSONArray userTenantJA = userJO.getJSONArray("tenants");
		//所属组织的Id
		List<String> tenantIds = userTenantJA.stream().map(m -> {
			return ((JSONObject)m).getString("$oid");
		}).collect(Collectors.toList());
		//判断当前登录的orgid是否包含在其所属组织列表中
		if (!tenantIds.contains(loginorgid)) {
			JSONObject param = new JSONObject();
			param.put("_id", new JSONObject().fluentPut("$in", userTenantJA));
			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(param);
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
			JSONArray tmpArray = SupportUtil.supportGetItemData(result);
			if (tmpArray.isEmpty()) {
				return userStr;
			}
			//查询部门对应的企业id
			List<String> tmpTenantId = tmpArray.stream().filter(f -> ((JSONObject)f).getInteger("type") == 2).map(m -> {
				return checkTenantidIsBelongTenantid(((JSONObject)m).getJSONObject("parent").getString("$oid"));
			}).collect(Collectors.toList());
			//合并所有所属企业id
			tenantIds.addAll(tmpTenantId);
			if (!tenantIds.contains(loginorgid)) {
				return userStr;
			}
		}
		//获取当前部门的所有用户
		getUserByDepardid(loginorgid,userStr);
		//获取当前组织/部门下的所有部门及用户
		getSupportTenantByParentid(loginorgid,listJo,userStr);
		return userStr;
	}
	
	/**
	 * 通过父级查询下一个部门
	 * @author xx
	 */
	public void getSupportTenantByParentid(String parentid, List<JSONObject> listJo, List<Integer> userStr) {
		if (ObjectUtil.isBlank(parentid)) {
			return;
		}
		// 获取部门的下级部门
		JSONObject oidJo = new JSONObject();
		oidJo.put("$oid", parentid);
		JSONObject parentJO = new JSONObject();
		parentJO.put("parent", oidJo);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(parentJO);
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		JSONArray resultJA = SupportUtil.supportGetItemData(result);
		// 赋值给 userStr、listJo
		if (CollectionUtils.isNotEmpty(resultJA)) {
			resultJA.stream().map(m -> {
				return (JSONObject) m;
			}).forEach(f -> {
				listJo.add(f.getJSONObject("_id"));
				// 获取该部门的所有用户
				getUserByDepardid(f.getJSONObject("_id").getString("$oid"), userStr);
				// 获取该部门下的所有部门
				getSupportTenantByParentid(f.getJSONObject("_id").getString("$oid"), listJo, userStr);
			});
		}
	}
	
	/**
	 * 获取部门对应的用户
	 * */
	private void getUserByDepardid(String departSupportId,List<Integer> userStr) {
		if(ObjectUtil.isNotBlank(departSupportId)) {
			JSONObject tidJo = new JSONObject();
			tidJo.put("$oid", departSupportId);
			JSONObject tenantJO = new JSONObject();
			tenantJO.put("tenants", tidJo);
			SupportRequestParam supportRequestParam1 = new SupportRequestParam();
			supportRequestParam1.setFilterJson(tenantJO);
			Object result1 = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam1);
			JSONArray resultJA1 = SupportUtil.supportGetItemData(result1);
			if (CollectionUtils.isEmpty(resultJA1)) {
				return;
			}
			List<String> supportIds = resultJA1.stream()
					.filter(f -> ObjectUtil.isNotBlank(f) && ((JSONObject) f).keySet().size() > 0).map(m -> {
						return ((JSONObject) m).getJSONObject("_id").getString("$oid");
					}).collect(Collectors.toList());
			userStr.addAll(userService.getUserIdBySupportIds(supportIds));
		}
	}
	
	/**
	 * 通过上级部门id获取所有下级部门
	 * @param departid
	 * @return string(以,分割)
	 * */
	public void getAllUnderDepartIdByUpId(String parentid,List<String> departStr) {
//		departStr.append(departStr.toString().equals("") ? "'"+parentid+"'" : "");
		if(departStr.size() <= 0)  departStr.add(parentid);
		//获取部门的下级部门
		JSONObject oidJo = new JSONObject();
		oidJo.put("$oid", parentid);
		JSONObject parentJO = new JSONObject();
		parentJO.put("parent", oidJo);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(parentJO);
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		JSONArray resultJA = SupportUtil.supportGetItemData(result);
		//赋值给 departStr
		if (ObjectUtil.isNotBlank(resultJA)) {
			for (int i = 0; i < resultJA.size(); i++) {
				JSONObject jo = resultJA.getJSONObject(i);
				departStr.add(JSONObject.parseObject(jo.getString("_id")).getString("$oid"));
//				departStr.append(!departStr.toString().equals("") ? "," : "")
//				.append("'"+JSONObject.parseObject(jo.getString("_id")).getString("$oid")+"'");
				//获取该部门下的所有部门
				getAllUnderDepartIdByUpId(JSONObject.parseObject(jo.getString("_id")).getString("$oid"), departStr);
			}
		}
	}

	//获取角色名称
	public String getUserRoleNameBySupport(String supportid) {
		String roleName = "";
		try {
			if(ObjectUtil.isNotBlank(supportid)) {
				JSONObject oidJo = new JSONObject();
				oidJo.put("$oid", supportid);
				JSONObject parentJO = new JSONObject();
				parentJO.put("staff", oidJo);
				
				SupportRequestParam supportRequestParam = new SupportRequestParam();
				supportRequestParam.setFilterJson(parentJO);
				
				Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff_roles, supportRequestParam,true);
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				
				if (ObjectUtil.isNotBlank(resultJA)) {
					for (int i = 0; i < resultJA.size(); i++) {
						JSONObject jo = resultJA.getJSONObject(i);
						if(ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(jo.get("roles")) && jo.getJSONArray("roles").size() >0) {
							JSONArray rolesJA = jo.getJSONArray("roles");
							for (int j = 0; j < rolesJA.size(); j++) {
								JSONObject roleJO = rolesJA.getJSONObject(j);
								if(ObjectUtil.isNotBlank(roleJO) && ObjectUtil.isNotBlank(roleJO.getString("$oid"))) {
									JSONObject roleJo = new JSONObject();
									roleJo.put("$oid", roleJO.getString("$oid"));
									JSONObject roleidJO = new JSONObject();
									roleidJO.put("_id", roleJo);
									
									SupportRequestParam supportRequestParam1 = new SupportRequestParam();
									supportRequestParam1.setFilterJson(roleidJO);
									
									Object result1 = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_role, supportRequestParam1,true);
									JSONArray resultJA1 = SupportUtil.supportGetItemData(result1);
									
									for (int k = 0; k < resultJA1.size(); k++) {
										if(ObjectUtil.isNotBlank(roleName)) roleName += ",";
										roleName += resultJA1.getJSONObject(k).getString("name");
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			roleName = "";
		}
		return roleName;
	}
	
	//获取角色id
	public String getUserRoleIdBySupport(String supportid) {
		String roleid = "";
		try {
			if(ObjectUtil.isNotBlank(supportid)) {
				JSONObject oidJo = new JSONObject();
				oidJo.put("$oid", supportid);
				JSONObject parentJO = new JSONObject();
				parentJO.put("staff", oidJo);
				
				SupportRequestParam supportRequestParam = new SupportRequestParam();
				supportRequestParam.setFilterJson(parentJO);
				
				Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff_roles, supportRequestParam,true);
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				
				if (ObjectUtil.isNotBlank(resultJA)) {
					for (int i = 0; i < resultJA.size(); i++) {
						JSONObject jo = resultJA.getJSONObject(i);
						if(ObjectUtil.isNotBlank(jo) && ObjectUtil.isNotBlank(jo.get("roles")) && jo.getJSONArray("roles").size() >0) {
							JSONArray rolesJA = jo.getJSONArray("roles");
							for (int j = 0; j < rolesJA.size(); j++) {
								JSONObject roleJO = rolesJA.getJSONObject(j);
								if(ObjectUtil.isNotBlank(roleJO) && ObjectUtil.isNotBlank(roleJO.getString("$oid"))) {
									JSONObject roleJo = new JSONObject();
									roleJo.put("$oid", roleJO.getString("$oid"));
									JSONObject roleidJO = new JSONObject();
									roleidJO.put("_id", roleJo);
									
									SupportRequestParam supportRequestParam1 = new SupportRequestParam();
									supportRequestParam1.setFilterJson(roleidJO);
									
									Object result1 = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_role, supportRequestParam1,true);
									JSONArray resultJA1 = SupportUtil.supportGetItemData(result1);
									
									for (int k = 0; k < resultJA1.size(); k++) {
										if(ObjectUtil.isNotBlank(roleid)) roleid += ",";
										roleid += resultJA1.getJSONObject(k).getJSONObject("_id").getString("$oid");
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			roleid = "";
		}
		return roleid;
	}
}
