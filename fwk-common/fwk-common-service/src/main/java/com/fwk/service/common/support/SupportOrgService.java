package com.fwk.service.common.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.rapidoid.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportOrgUtil;
import com.fwk.service.common.support.util.SupportUserUtil;
import com.fwk.service.common.support.util.SupportUtil;

/**
 * 支撑平台-组织和部门操作
 */

@Service
public class SupportOrgService {
	private final String START_OID = "{\"$oid\":";
	private final String START_NUMBER_LONG = "{\"$numberLong\":";
	private final int TENANT_TYPE_ORG = 1;
	private final int TENANT_TYPE_DEPT = 2;
	private final int REMOVED_DEFAULT_VALUE = 1;
	private final int STATE_DEFAULT_VALUE = 1;

	private static final Logger logger = LoggerFactory.getLogger(SupportOrgService.class);

	@Autowired
	private SupportUserUtil supportUserUtil;

	@Autowired
	private SupportService supportService;

	/********************************************************************************
	 **************** 部门管理.xlsx *****************
	 * ******************************************************************************
	 */
	/**
	 * 获取当前登录人的组织下的所有部门（树形结构）
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object queryDepartmentByLoginOrgId(Map<String, Object> requestMap, ResultWrap resultWrap) {
		return null;
	}

	/**
	 * 查询单个部门详情 DepartmentController queryDepartmentDetail
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Map<String, Object> queryDepartmentDetail(Map<String, Object> requestMap) {
		// TODO fwk的id 和 supportid(iss的id)的转换
		// 转换requestMap成支撑需要的json对象
		if(requestMap.containsKey("orgid")){// 去掉公共参数
			requestMap.remove("orgid");
		}
		List<Map<String, Object>> list = queryReturnList(requestMap, AllJsonRequestUrl.Sys_tenant, SupportOrgUtil.typeFromFwkToIssDept, SupportOrgUtil.typeFromIssToFwkDept, null);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存（新增）部门 DepartmentController saveDepartment
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public ResultWrap addDepartment(Map<String, Object> requestMap) {
		// TODO fwk的orgid和iss的parent的转换
		// requestMap.put("type", TENANT_TYPE_DEPT);
		requestMap.put("status", STATE_DEFAULT_VALUE);
		// 特殊处理orgId和parentid，都对应一个字段parent
		if (requestMap.get("parentid") != null) {
			requestMap.remove("orgid");
		}
		JSONObject dataJson = packOrgInfoToSupportSysTenant(requestMap, false, null, SupportOrgUtil.typeFromFwkToIssDept, null);
		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		return supportService.requestSupportPOST(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	/**
	 * 保存（新增） 组织 OrgController saveOrg
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public ResultWrap addOrg(Map<String, Object> requestMap) {
		// requestMap.put("type", TENANT_TYPE_DEPT);
		requestMap.put("status", STATE_DEFAULT_VALUE);
		JSONObject dataJson = packOrgInfoToSupportSysTenant(requestMap, false, null, SupportOrgUtil.typeFromFwkToIssOrg, null);
		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		return supportService.requestSupportPOST(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	/**
	 * 保存（修改）部门 OrgController updateOrg
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object updateOrg(Map<String, Object> requestMap) {
		JSONObject dataJson = packOrgInfoToSupportSysTenant(requestMap, true, "supportid", SupportOrgUtil.typeFromFwkToIssOrg, null);
		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		logger.info("支撑平台-组织修改----SupportOrgController----updateOrg:" + requestMap);
		return supportService.requestSupportPUT(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	public Object addUserToOrg(String tenantid, String id) {
		JSONObject dataJson = supportUserUtil.packUserAddTenantByUserId(tenantid, id);
		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		return supportService.requestSupportPUT(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}

	public Object queryOrgByName(String name) {
		JSONObject jb = new JSONObject();
		jb.put("name", name);
		jb.put("type", TENANT_TYPE_ORG);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(jb);
		return supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	/**
	 * 根据支撑的部门查询该部门下的人数
	 * 
	 * @param requestMap
	 * @return
	 */
	public Integer queryStaffCountByDeptSupportId(Map<String, Object> requestMap) {
		Integer total = 0;		
		// 设置过滤条件
		JSONObject inJO = new JSONObject();
		inJO.put("$oid", requestMap.get("deptSupportid"));
		JSONObject tenantsJO = new JSONObject();
		tenantsJO.put("tenants", inJO);
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(tenantsJO);
		
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			total = SupportUtil.supportGetTotal(result);
		}
		return total;

	}

	/**
	 * 保存（修改）部门 DepartmentController saveDepartment
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object updateDepartment(Map<String, Object> requestMap) {
		// 特殊处理orgId和parentid，都对应一个字段parent
		if (requestMap.get("parentid") != null) {
			requestMap.remove("orgid");
		}
		JSONObject dataJson = packOrgInfoToSupportSysTenant(requestMap, true, "supportid", SupportOrgUtil.typeFromFwkToIssDept, null);
		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		logger.info("支撑平台-部门修改----SupportOrgController----updateDepartment:" + requestMap);
		return supportService.requestSupportPUT(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	/**
	 * 删除部门
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object deleteDepartment(Map<String, Object> requestMap) {
		// requestMap.put("delstate", CommonConstantsUtil.IS_DELETE_TRUE);
		requestMap.put("_removed", REMOVED_DEFAULT_VALUE);
		JSONObject dataJson = packOrgInfoToSupportSysTenant(requestMap, true, "supportid", SupportOrgUtil.typeFromFwkToIssDept, null);

		SupportRequestParam supportRequestParam = new SupportRequestParam(dataJson);
		logger.info("支撑平台-删除部门----SupportOrgController----deleteDepartment:" + requestMap);
		return supportService.requestSupportPUT(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}

	/********************************************************************************
	 ***** 组织、部门、用户、权限（树）.xlsx ***********
	 * ******************************************************************************
	 */
	public Object OrgDepartUserTree(Map<String, Object> requestMap, ResultWrap resultWrap) {
		return null;
	}

	/********************************************************************************
	 ************* 组织管理.xlsx ***********************************
	 * ******************************************************************************
	 */

	/**
	 * 获取当前登录人的企业信息 OrgController queryOrgByOperateid
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryOrgByOperateid(Map<String, Object> requestMap) {// 请求中登录的组织ID就是支撑的组织id
		// return queryReturnList(requestMap, AllJsonRequestUrl.Sys_tenant,
		// SupportOrgUtil.typeFromFwkToIssOrg,
		// SupportOrgUtil.typeFromIssToFwkOrg, null);
		return queryOrg(requestMap);
	}

	/**
	 * 获取登录人的全部组织信息 OrgController queryOrgAllByOperateid
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryOrgAllByOperateid(Map<String, Object> requestMap) {
		// 1、 直接挂到组织下的人员 2、挂在部门下的人员
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject dataJson = addIdJson(requestMap, null, null);
		JSONArray objArray = callMongoRestIntf(dataJson, AllJsonRequestUrl.Tenant_staff);
		if (objArray != null) {
			JSONObject jsonObject = objArray.getJSONObject(0);
			Object tenantsObj = jsonObject.get("tenants");
			if (tenantsObj != null) {
				JSONArray tenantsArray = JSONArray.parseArray(tenantsObj.toString());
				for (int j = 0; j < tenantsArray.size(); j++) {
					JSONObject tenantObj = tenantsArray.getJSONObject(j);
					String id = tenantObj.getString("$oid");
					if (StringUtils.isBlank(id)) {
						continue;
					}
					// 根据ID去查询组织
					Map<String, Object> reqTenantMap = new HashMap<String, Object>();
					reqTenantMap.put("id", id);
					List<Map<String, Object>> tenantListT = queryOrg(reqTenantMap);
					if (!CollectionUtils.isEmpty(tenantListT)) {
						Map<String, Object> upOrgMap = upOrgBubble(tenantListT.get(0));
						if (upOrgMap != null) {
							list.add(upOrgMap);
						}
					}

				}
			}

		}
		return list;

	}

	/**
	 * 向上冒泡，找到最终的组织
	 * 
	 * @param tenantMap
	 * @return
	 */
	private Map<String, Object> upOrgBubble(Map<String, Object> tenantMap) {
		Integer sign = Integer.parseInt(String.valueOf(tenantMap.get("sign")));
		if (sign == TENANT_TYPE_ORG) {// 组织
			return tenantMap;
		} else {// 往上冒泡，直到找到组织
			Map<String, Object> reqTenantMap = new HashMap<String, Object>();
			Object parent = tenantMap.get("parent");
			reqTenantMap.put("id", parent);
			List<Map<String, Object>> tenantListT = queryOrg(reqTenantMap);
			if (!CollectionUtils.isEmpty(tenantListT)) {// 往上冒泡，直到找到组织
				Map<String, Object> tenantMapT = tenantListT.get(0);
				return upOrgBubble(tenantMapT);
			} else {
				return null;
			}
		}

	}

	/**
	 * 获取登录人的全部组织信息 OrgController queryOrgAllByOperateid
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Map<String, Object> queryDeptBelongOrg(Object supportDeptid) {
		if (ObjectUtil.isBlank(supportDeptid)) {
			return null;
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("id", supportDeptid);
		List<Map<String, Object>> tenantListT = queryOrg(requestMap);
		if (!CollectionUtils.isEmpty(tenantListT)) {// 往上冒泡，直到找到组织
			Map<String, Object> tenantMapT = tenantListT.get(0);
			return upOrgBubble(tenantMapT);
		}
		return null;
	}

	/**
	 * 保存企业信息
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object saveOrg(Map<String, Object> requestMap) {
		return null;
	}

	/**
	 * 组织所有人解散组织
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object deleteOrg(Map<String, Object> requestMap) {
		return null;
	}

	/**
	 * 移交组织
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public Object turnOrg(Map<String, Object> requestMap) {
		return null;
	}

	/***********************************************************************************/
	/******** 上述接口用到的api **************/
	/***********************************************************************************/

	/**
	 * 根据组织的id查询组织 [UserController]queryDepartmentByLoginOrgId
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryOrg(Map<String, Object> requestParamMap) {
		// TODO fwk的id(supportid) 和 iss的id的转换
		// 转换requestMap成支撑需要的json对象 ，转换成支撑的参数
		List<Map<String, Object>> orgList = queryReturnList(requestParamMap, AllJsonRequestUrl.Sys_tenant, SupportOrgUtil.typeFromFwkToIssOrg, SupportOrgUtil.typeFromIssToFwkOrg,
				null);
		Map<String, Object> requestStaffMap = new HashMap<>();
		for (Map<String, Object> orgMap : orgList) {// 遍历查询部门的负责人
			if (orgMap.containsKey("manager") && ObjectUtil.isNotBlank(orgMap.get("manager"))) {
				Object id = orgMap.get("manager");// 根据ID查询负责人
				requestStaffMap.put("id", id);
				JSONObject jsonObject = queryUserBySupportId(requestStaffMap);
				if (ObjectUtil.isNotBlank(jsonObject)) {
					if (ObjectUtil.isNotBlank(jsonObject.get("name"))) {
						orgMap.put("managerName", jsonObject.get("name"));
					}
					if (ObjectUtil.isNotBlank(jsonObject.get("mobile"))) {
						orgMap.put("managerMobile", jsonObject.get("mobile"));
					}
				} else {
					orgMap.put("managerName", "");
					orgMap.put("managerMobile", "");
				}

			} else {
				orgMap.put("managerName", "");
				orgMap.put("managerMobile", "");
			}

		}

		return orgList;
	}
	
	/**
	 * @Description: 根据组织ID列表查询组织，返回结果不包括负责人姓名及电话
	 *
	 * @param ids
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年8月15日 下午6:41:53
	 */
	public List<Map<String, Object>> queryOrgByIds(List<String> ids){
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("id", String.join(",", ids));
		return queryReturnList(queryMap, AllJsonRequestUrl.Sys_tenant, SupportOrgUtil.typeFromFwkToIssOrg, SupportOrgUtil.typeFromIssToFwkOrg,null);
	}
	
	/**
	 * 获取组织下的部门信息 [UserController]queryDepartmentByLoginOrgId
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryDepartment(Map<String, Object> requestParamMap) {
		// TODO fwk的orgid 和 iss的parent的转换
		// 转换requestMap成支撑需要的json对象
		// JSONObject queryJson = addColOidJson(requestParamMap, null,
		// "parentid", SupportOrgUtil.typeFromFwkToIssDept);
		return queryReturnList(requestParamMap, AllJsonRequestUrl.Sys_tenant, SupportOrgUtil.typeFromFwkToIssDept, SupportOrgUtil.typeFromIssToFwkDept, null);
	}

	/********************************************************************************/
	/******** 构造通用的方法 **************/
	/***********************************************************************************/
	/**
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryReturnList(Map<String, Object> requestMap, ResultWrap resultWrap, String queryType, String returnType, JSONObject queryJson) {
		// 转换requestMap成支撑需要的json对象
		JSONObject filterJson = packOrgInfoToSupportSysTenant(requestMap, false, null, queryType, queryJson);
		callMongoRestIntf(filterJson, resultWrap, AllJsonRequestUrl.Sys_tenant);
		JSONArray objArray = (JSONArray) resultWrap.getData();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < objArray.size(); j++) {
			JSONObject jsonObject = objArray.getJSONObject(j);
			// TODO
			Map<String, String> issValueMap = transJsonToIssOrgMap(jsonObject);
			Map<String, Object> fwkValueMap = transNeedOrgMap(issValueMap, returnType);
			if (fwkValueMap.size() > 0) {
				list.add(fwkValueMap);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public List<Map<String, Object>> queryReturnList(Map<String, Object> requestMap, String jsonSchemaUrl, String queryType, String returnType, JSONObject queryJson) {
		// 转换requestMap成支撑需要的json对象
		JSONObject filterJson = packOrgInfoToSupportSysTenant(requestMap, false, null, queryType, queryJson);
		JSONArray objArray = callMongoRestIntf(filterJson, jsonSchemaUrl);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (CollectionUtils.isEmpty(objArray)) {
			return list;
		}
		list = objArray.parallelStream().map(m -> {
			return transJsonToIssOrgMap((JSONObject)m);
		}).map(n -> {
			return transNeedOrgMap(n, returnType);
		}).collect(Collectors.toList());
		
//		if (objArray != null) {
//			for (int j = 0; j < objArray.size(); j++) {
//				JSONObject jsonObject = objArray.getJSONObject(j);
//				Map<String, String> issValueMap = transJsonToIssOrgMap(jsonObject);
//				Map<String, Object> fwkValueMap = transNeedOrgMap(issValueMap, returnType);
//				if (fwkValueMap.size() > 0) {
//					list.add(fwkValueMap);
//				}
//			}
//
//		}
		return list;

	}

	public void callMongoRestIntf(JSONObject filterJson, ResultWrap resultWrap, String jsonSchemaUrl) {
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		try {
			Object result = supportService.requestSupportGet(jsonSchemaUrl, supportRequestParam);
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

	public JSONArray callMongoRestIntf(JSONObject filterJson, String jsonSchemaUrl) {
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		try {
			Object result = supportService.requestSupportGet(jsonSchemaUrl, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				return resultJA;
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 通过json对象转换成值对象Map
	 */
	public Map<String, String> transJsonToIssOrgMap(JSONObject jsonObject) {
		Map<String, String> resultMap = new HashMap<>();
		if (ObjectUtil.isNotBlank(jsonObject)) {
			Map<String, Object> map = jsonObject;
			logger.info("=====================================");
			String valueStr = "";
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {// 有值，不为空
					valueStr = value.toString();
					if (valueStr.startsWith("{")) {// 以“{”开头
						JSONObject jsonObj = JSONObject.parseObject(valueStr);
						if (valueStr.startsWith(START_OID)) {
							valueStr = jsonObj.getString("$oid");
						} else if (valueStr.startsWith(START_NUMBER_LONG)) {
							valueStr = String.valueOf(jsonObj.getLongValue("$numberLong"));
						} else {// 特殊处理
							if (key.equals("registeredAddress")) {
								String addressCode = SupportUtil.supportGetAddressCode(jsonObj);
								String addressDetail = jsonObj.getString("street");
								logger.info("addressCode:" + addressCode + ",addressDetail=" + addressDetail);
								resultMap.put("addressCode", addressCode);
								resultMap.put("addressDetail", addressDetail);
							}
						}
					} else {// 不是以“{”开头
						valueStr = String.valueOf(value);
					}
					if (!valueStr.startsWith("{")) {// 以“{”开头,过滤掉还是对象的,因为对象的在上面已经特殊处理了
						resultMap.put(key, valueStr);
						// logger.info("key=" + key + ",value=" + valueStr);
					}

				}

			}
		}
		return resultMap;
	}

	/**
	 * 通过json对象转换成值对象Map
	 */
	public Map<String, Object> transNeedOrgMap(Map<String, String> map, String type) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> typeMapT = SupportOrgUtil.typeMap.get(type);
		for (Map.Entry<String, String> obj : map.entrySet()) {
			String key = obj.getKey();
			String value = obj.getValue();// createdOn:TRANS_TYPE_STR_DATE_TO_LONG
			// logger.info("============key=======start===============" + key);
			if (!CollectionUtils.isEmpty(typeMapT) && typeMapT.containsKey(key)) {
				String fwkKey = typeMapT.get(key);
				// resultMap.put(fwkKey, resultValue);
				processValueByTransType(resultMap, fwkKey, value);
			} else { // 特殊处理， parent addressCode addressDetail _id
				if ("addresscode".equals(key)) {// addresscode
					resultMap.put("addresscode", value);
				} else if ("addressDetail".equals(key)) {// addressdetail
					resultMap.put("addressdetail", value);
				} else if ("_id".equals(key)) {// supportid
					resultMap.put("supportid", value);
				} else {// 将原值放入
					resultMap.put(key, value);
				}

			}
			// logger.info("============key=======end===============");
		}
		return resultMap;
	}

	public void processValueByTransType(Map<String, Object> map, String keyValue, String transValue) {
		if (StringUtils.isBlank(transValue)) {
			return;
		}
		String[] keyAndTrans = keyValue.split(":");
		if (ArrayUtils.isEmpty(keyAndTrans) || keyAndTrans.length != 2) {
			return;
		} else {
			String key = keyAndTrans[0];
			String transType = keyAndTrans[1];
			try {
				if (SupportOrgUtil.TRANS_TYPE_NO_NEED.equals(transType)) {
					map.put(key, transValue);
				} else if (SupportOrgUtil.TRANS_TYPE_STR_DATE_TO_LONG.equals(transType)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					try {
						date = (Date) sdf.parse(transValue);
					} catch (ParseException e) {

					}
					map.put(key, date.getTime());
				} else if (SupportOrgUtil.TRANS_TYPE_STR_LONG_TO_STR_DATE.equals(transType)) {
					Date date = new Date(Long.parseLong(transValue));
					map.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
				} else if (SupportOrgUtil.TRANS_TYPE_STR_LONG_TO_STR_DATE_YYYY_MM_RR.equals(transType)) {
					Date date = new Date(Long.parseLong(transValue));
					map.put(key, new SimpleDateFormat("yyyy-MM-dd").format(date));
				} else if (SupportOrgUtil.TRANS_TYPE_STR_TO_INT.equals(transType)) {
					map.put(key, Integer.parseInt(transValue));
				} else if (SupportOrgUtil.TRANS_TYPE_STR_TO_DOUBLE.equals(transType)) {
					map.put(key, Double.parseDouble(transValue));
				} else {
					map.put(key, transValue);
				}
			} catch (Exception e) {
				map.put(key, transValue);;
			}

		}

	}

	/**
	 * 获取组织下的所有部门编号
	 * 
	 * @author xx
	 * @param tenantid:组织编号
	 * @resutn List<JSONObject>
	 */
	public List<JSONObject> getSupportTenantsId(String tenantid) {
		List<JSONObject> resultTenants = new ArrayList<JSONObject>();
		JSONObject tenantJO = new JSONObject();
		tenantJO.put("$oid", tenantid);
		resultTenants.add(tenantJO);
		if (ObjectUtil.isNotBlank(tenantid)) {
			JSONObject tenantJson = new JSONObject();
			tenantJson.put("_id", tenantJO);
			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(tenantJson);

			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
			if (ObjectUtil.isNotBlank(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				for (int i = 0; i < resultJA.size(); i++) {
					JSONObject jo = resultJA.getJSONObject(i);
					getSupportTenantByParentid(JSONObject.parseObject(jo.getString("_id")).getString("$oid"), resultTenants);
				}
			}
		}
		return resultTenants;
	}

	/**
	 * 通过父级查询下一个部门
	 * 
	 * @author xx
	 */
	public void getSupportTenantByParentid(String parentid, List<JSONObject> listJo) {
		JSONObject oidJo = new JSONObject();
		oidJo.put("$oid", parentid);
		JSONObject parentJO = new JSONObject();
		parentJO.put("parent", oidJo);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(parentJO);

		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		JSONArray resultJA = SupportUtil.supportGetItemData(result);

		if (ObjectUtil.isNotBlank(resultJA)) {
			for (int i = 0; i < resultJA.size(); i++) {
				JSONObject jo = resultJA.getJSONObject(i);
				JSONObject idjo = new JSONObject();
				idjo.put("$oid", JSONObject.parseObject(jo.getString("_id")).getString("$oid"));
				listJo.add(idjo);

				getSupportTenantByParentid(JSONObject.parseObject(jo.getString("_id")).getString("$oid"), listJo);
			}
		}
	}

	/**
	 * 通过支撑id获取支撑的组织/部门信息
	 * 
	 * @return JSONObject
	 */
	public JSONObject getSupportTenantById(String supportid) {
		JSONObject resultJO = new JSONObject();
		if (ObjectUtil.isNotBlank(supportid)) {
			JSONObject oidJO = new JSONObject();
			oidJO.put("$oid", supportid);
			JSONObject idJO = new JSONObject();
			idJO.put("_id", oidJO);

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(idJO);

			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				resultJO = SupportUtil.supportGetItemData(result).getJSONObject(0);
			}
		}
		return resultJO;
	}

	public JSONObject getSupportTenantByIdNew(String supportid) {
		JSONObject resultJO = new JSONObject();
		if (ObjectUtil.isNotBlank(supportid)) {
			JSONObject oidJO = new JSONObject();
			oidJO.put("$oid", supportid);
			JSONObject idJO = new JSONObject();
			idJO.put("_id", oidJO);

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(idJO);

			Object result = supportService.requestSupportGetNew(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				resultJO = SupportUtil.supportGetItemData(result).getJSONObject(0);
			}
		}
		return resultJO;
	}

	public static void main2(String[] args) throws Exception {
		SupportOrgService supportOrgController = new SupportOrgService();
		Map<String, Object> requestMap = new HashMap<String, Object>();

		// requestMap.put("supportid", "5a9f48cb46e0fb0001bb1bb4");
		// List<Map<String, Object>> list =
		// supportOrgController.queryOrg(requestMap);

		// requestMap.put("orgid", "5aa24b6546e0fb0001bb1bf8");
		// List<Map<String, Object>> list =
		// supportOrgController.queryDepartment(requestMap);

		// requestMap.put("supportid", "5a97549c46e0fb0001dd8319");
		// ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		// Map<String, String> map =
		// supportOrgController.queryDepartmentDetail(requestMap, resultWrap);
		// for (Map.Entry<String, String> entry : map.entrySet()) {
		// logger.info("key:" + entry.getKey() + ",value=" + entry.getValue());
		// }

		// requestMap.put("name", "测试报告组");
		// requestMap.put("orgid", "1");// 需要转换成iss的ID
		// requestMap.put("departno", "5aa24b6546e0fb0001bb1bf8");
		// requestMap.put("parentid", "1");
		// requestMap.put("chargeuserid", "1");
		// Object returnObj = supportOrgController.addDepartment(requestMap);
		// logger.info(returnObj.toString());

		// requestMap.put("supportid", "5aa24b6546e0fb0001bb1bf8");
		// requestMap.put("name", "11测试一部11");
		// Object returnObj = supportOrgController.updateDepartment(requestMap);
		// logger.info(returnObj.toString());

		// requestMap.put("supportid", "5aa5df5846e0fb0001bb1c28");
		// Object returnObj = supportOrgController.deleteDepartment(requestMap);
		// logger.info(returnObj.toString());

		// 查询登录人的组织信息
		// requestMap.put("id", "5a9f48cb46e0fb0001bb1bb4");
		// List<Map<String, Object>> list =
		// supportOrgController.queryOrgByOperateid(requestMap);

		// 获取登录人的全部组织信息
		// requestMap.put("userId", "5aa4cc2446e0fb0001bb1c17");
		// List<Map<String, Object>> list =
		// supportOrgController.queryOrgAllByOperateid(requestMap);
		// for (Map<String, Object> map : list) {
		// for (Map.Entry<String, Object> entry : map.entrySet()) {
		// logger.info("key:" + entry.getKey() + ",value=" + entry.getValue());
		// }
		// }

		// requestMap.put("id", "5aa7312846e0fb00013b6ed5");
		// JSONObject jsonObject =
		// supportOrgController.queryUserDeptBySupportId(requestMap);

		requestMap.put("id", "5aa7313646e0fb00013b6ed6");
		supportOrgController.queryDepartmentDetail(requestMap);

	}

	// ********************************************************************************/
	/**
	 * 封装组织机构基础数据（依照支撑平台表信息进行封装） requestMap:基础数据map对象
	 * isedit：是否是修改操作（修改操作需要另外一种json格式：
	 * 如：{filter:{"_id":{"$oid":"5a94f137b3cb1f000130f879"}},update:{$set:{password:"RgE9VuGQlGOk7dUxz7ku/bjtZ9irZ/nMZjeOtOEO9DE=$cXdlcnR5$100000111111",
	 * mobile: 1528812187811111}}}） objects ：不需要转换的属性
	 */
	private JSONObject packOrgInfoToSupportSysTenant(Map<String, Object> requestMap, boolean isEdit, String getValueField, String type, JSONObject resultJson) {
		if (resultJson == null) {
			resultJson = new JSONObject();
		}

		if (isEdit) {// 是修改操作
			addFilterAndIDJson(requestMap, resultJson, getValueField);
			JSONObject jo = new JSONObject();
			encryBaseJson(requestMap, jo, type);
			JSONObject setjo = new JSONObject();
			setjo.put("$set", jo);
			resultJson.put("update", setjo);
		} else {// 非修改操作
			encryBaseJson(requestMap, resultJson, type);
		}
		logger.info("resultJson:" + resultJson.toJSONString());
		return resultJson;
	}

	/**
	 * 封装基础属性
	 */
	private void encryBaseJson(Map<String, Object> requestMap, JSONObject requestJson, String type) {
		// 组织编号
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) { // 组织的id
			addIdJson(requestMap, requestJson, null);
		}
		// 组织编号
		if (ObjectUtil.isNotBlank(requestMap.get("parentid"))) { // 组织的id
			addColOidJson(requestMap, requestJson, "parentid", type);
		}
		Map<String, String> map = SupportOrgUtil.typeMap.get(type);
		for (Map.Entry<String, Object> entry : requestMap.entrySet()) { // 遍历请求参数
			String key = entry.getKey();
			if (!CollectionUtils.isEmpty(map) && map.containsKey(key)) {// 包含请求的字段，就要进行转义
				Object value = entry.getValue();
				String keyT = map.get(key); // createdOn:1
				String[] keyTArr = keyT.split(":");
				if ("id".equals(keyTArr[0])) {
					addIdJson(requestMap, requestJson, key);
				} else {
					requestJson.put(keyTArr[0], value);
				}

			}
		}
		encryRegisteredAddressJson(requestMap, requestJson);
	}

	/**
	 * 封装详细地址属性
	 */
	private void encryRegisteredAddressJson(Map<String, Object> requestMap, JSONObject requestJson) {
		if (ObjectUtil.isNotBlank(requestMap.get("addressdetail")) || ObjectUtil.isNotBlank(requestMap.get("addressProvince"))
				|| ObjectUtil.isNotBlank(requestMap.get("addressCounty")) || ObjectUtil.isNotBlank(requestMap.get("addressCity"))) {
			JSONObject json = new JSONObject();
			if (ObjectUtil.isNotBlank(requestMap.get("addressProvince"))) {
				JSONObject prjo = new JSONObject();
				prjo.put("$oid", requestMap.get("addressProvince"));
				json.put("province", prjo);
			}
			if (ObjectUtil.isNotBlank(requestMap.get("addressCity"))) {
				JSONObject ctjo = new JSONObject();
				ctjo.put("$oid", requestMap.get("addressCity"));
				json.put("city", ctjo);
			}
			if (ObjectUtil.isNotBlank(requestMap.get("addressCounty"))) {
				JSONObject cojo = new JSONObject();
				cojo.put("$oid", requestMap.get("addressCounty"));
				json.put("district", cojo);
			}
			if (ObjectUtil.isNotBlank(requestMap.get("addressdetail"))) {
				json.put("street", requestMap.get("addressdetail"));
			}
			requestJson.put("registeredAddress", json);
		}

	}

	/**
	 * 封装IDjson {"_id":{"$oid":"123"}}
	 */
	private JSONObject addIdJson(Map<String, Object> requestMap, JSONObject requestJson, String getValueField) {
		if (requestJson == null) {
			requestJson = new JSONObject();
		}
		if (ObjectUtil.isNotBlank(requestMap.get(getValueField))) {
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", requestMap.get(getValueField));
			requestJson.put("_id", oidjo);
			// requestMap.remove(getValueField);
		} else if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			String id = requestMap.get("id").toString();
			JSONObject oidjo = new JSONObject();
			//为了支持通过id批量查询，则判断ID是否为带逗号分隔的值
			if (id.contains(",")) {
				List<JSONObject> oidList = Arrays.asList(id.split(",")).stream().map(m -> {
					 return new JSONObject().fluentPut("$oid", m);
				 }).collect(Collectors.toList());
				oidjo.put("$in", oidList);
			}else {
				oidjo.put("$oid", requestMap.get("id"));
			}
			requestJson.put("_id", oidjo);
			// requestMap.remove("id");
		}
		return requestJson;
	}

	/**
	 * 封装字段的json {"字段":{"$oid":"123"}}
	 */
	private JSONObject addColOidJson(Map<String, Object> requestMap, JSONObject requestJson, String colName, String type) {
		if (requestJson == null) {
			requestJson = new JSONObject();
		}
		if (ObjectUtil.isNotBlank(requestMap.get(colName))) {
			JSONObject oidjo = new JSONObject();

			Map<String, String> map = SupportOrgUtil.typeMap.get(type);
			if (!CollectionUtils.isEmpty(map) && map.containsKey(colName)) {
				String colNameT = map.get(colName); // createdOn:1
				String[] colNameTArr = colNameT.split(":");
				oidjo.put("$oid", requestMap.get(colName));
				requestJson.put(colNameTArr[0], oidjo);
			} else {
				oidjo.put("$oid", requestMap.get(colName));
				requestJson.put(colName, oidjo);
			}
			requestMap.remove(colName);
		}
		return requestJson;
	}

	/**
	 * 封装filter： {"filter":{"_id":{"$oid":"123"}}}
	 */
	private JSONObject addFilter(JSONObject requestJson, JSONObject filterJson) {
		if (requestJson == null) {
			requestJson = new JSONObject();
		}
		requestJson.put("filter", filterJson);
		return requestJson;
	}

	/**
	 * 封装IDjson：filter
	 */
	private JSONObject addFilterAndIDJson(Map<String, Object> requestMap, JSONObject resultJson, String getValueField) {
		JSONObject idJsonObj = addIdJson(requestMap, null, getValueField);
		return addFilter(resultJson, idJsonObj);
	}

	/**
	 * 获取当前登录人的组织下的所有部门（树形结构） 提供给WJW调用
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public JSONObject queryUserDeptBySupportId(Map<String, Object> requestMap) {
		if (CollectionUtils.isEmpty(requestMap) || !requestMap.containsKey("id")) {
			return null;
		}
		JSONObject jsonObject = null;
		JSONObject dataJson = addIdJson(requestMap, null, null);
		JSONArray objArray = callMongoRestIntf(dataJson, AllJsonRequestUrl.Tenant_staff);
		if (objArray != null) {
			jsonObject = objArray.getJSONObject(0);
		}
		return jsonObject;
	}

	/**
	 * 根据支撑的ID查询用户的信息
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public JSONObject queryUserBySupportId(Map<String, Object> requestMap) {
		if (CollectionUtils.isEmpty(requestMap) || !requestMap.containsKey("id")) {
			return null;
		}
		JSONObject jsonObject = null;
		JSONObject dataJson = addIdJson(requestMap, null, null);
		JSONArray objArray = callMongoRestIntf(dataJson, AllJsonRequestUrl.Tenant_staff);
		if (objArray != null) {
			jsonObject = objArray.getJSONObject(0);
		}
		return jsonObject;
	}
	
	/**
	 * 根据支撑的ID列表查询用户的信息
	 * 
	 * @param requestMap
	 * @param resultWrap
	 * @return
	 */
	public JSONArray queryUserBySupportIds(Map<String, Object> requestMap) {
		if (CollectionUtils.isEmpty(requestMap) || !requestMap.containsKey("id")) {
			return null;
		}
		JSONObject dataJson = addIdJson(requestMap, null, null);
		return callMongoRestIntf(dataJson, AllJsonRequestUrl.Tenant_staff);
	}

	public String getSupportRoleNameById(String supportid) {
		String roleNames = "";
		
		JSONObject uoidJO = new JSONObject();
		uoidJO.put("$oid", supportid);
		JSONObject uidJO = new JSONObject();
		uidJO.put("staff", uoidJO);
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(uidJO);
		try {
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff_roles, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				if(resultJA != null) {
					for (int i = 0; i < resultJA.size(); i++) {
						JSONObject jo1 =  resultJA.getJSONObject(i);
						JSONArray ja1 = jo1.getJSONArray("roles");
						for (int j = 0; j < ja1.size(); j++) {
							JSONObject jo2 = ja1.getJSONObject(j);
							JSONObject roleJO = new JSONObject();
							roleJO.put("$oid", jo2.get("$oid"));
							JSONObject rJO = new JSONObject();
							rJO.put("_id", roleJO);
							
							SupportRequestParam supportRequestParam1 = new SupportRequestParam();
							supportRequestParam1.setFilterJson(rJO);
							
							Object result1 = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_role, supportRequestParam1);
							if (result1!=null) {
								roleNames += roleNames.equals("") ? "" : ",";
								roleNames += SupportUtil.supportGetItemData(result1).getJSONObject(0).getString("name");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleNames;
	}

	public String getSupportRoleIdById(String supportid) {
		String roleIDs = "";
		
		JSONObject uoidJO = new JSONObject();
		uoidJO.put("$oid", supportid);
		JSONObject uidJO = new JSONObject();
		uidJO.put("staff", uoidJO);
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(uidJO);
		try {
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff_roles, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray resultJA = SupportUtil.supportGetItemData(result);
				if(resultJA != null) {
					for (int i = 0; i < resultJA.size(); i++) {
						JSONObject jo1 =  resultJA.getJSONObject(i);
						JSONArray ja1 = jo1.getJSONArray("roles");
						for (int j = 0; j < ja1.size(); j++) {
							JSONObject jo2 = ja1.getJSONObject(j);
							if (jo2!=null) {
								roleIDs += roleIDs.equals("") ? "" : ",";
								roleIDs += jo2.get("$oid");
							}
						}
						
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleIDs;
	}

}
