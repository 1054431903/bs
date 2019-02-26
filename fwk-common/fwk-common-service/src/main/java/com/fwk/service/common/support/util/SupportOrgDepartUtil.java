package com.fwk.service.common.support.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;

/**
 * 支撑组织/部门帮助类
 * */
@Component
public class SupportOrgDepartUtil {
	
	@Autowired
	private SupportService supportService;
	
	/**
	 * 通过部门/组织 id 递归获取组织编号
	 * */
	public String supportGetTenantParent(String id){
		String orgsupportid = "";
		JSONObject oidjo = new JSONObject();
		oidjo.put("$oid", id);
		JSONObject idjo = new JSONObject();
		idjo.put("_id", oidjo);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(idjo);
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		JSONArray ja = SupportUtil.supportGetItemData(result);
		if(ObjectUtil.isNotBlank(ja) && ja.size() >= 1){
			JSONObject resultJO = JSONObject.parseObject(ja.get(0).toString());
			if(ObjectUtil.isNotBlank(resultJO.get("type"))){
				Integer type = Integer.parseInt(resultJO.get("type").toString());
				if(type == 0 || type == 1){
					orgsupportid = JSONObject.parseObject(resultJO.getString("_id")).getString("$oid");
				}else{
					orgsupportid = supportGetTenantParent(JSONObject.parseObject(resultJO.getString("parent")).getString("$oid"));
				}
			}
		}else{
			orgsupportid = id;
		}
		return orgsupportid;
	}
	
	/**
	 * @Description: 批量获取当前传入的组织列表的父级组织
	 *
	 * @param tenants
	 * @return
	 * @return：返回结果：如果当前列表中没有部门，则原样返回，反之则查询其父级组织放入当前列表，但排除了部门
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年8月16日 下午2:48:08
	 */
	public List<String> batchGetParentTenants(List<String> tenants) {
		List<String> resultList = tenants;
		List<JSONObject> oids = tenants.stream().map(m -> {
			return new JSONObject().fluentPut("$oid", m);
		}).collect(Collectors.toList());
		JSONObject param = new JSONObject().fluentPut("_id", new JSONObject().fluentPut("$in", oids));
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(param);
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		JSONArray resultArray = SupportUtil.supportGetItemData(result);
		if (CollectionUtils.isEmpty(resultArray)) {
			return resultList;
		}
		//部门的上级组织ID列表
		List<String> departParent = Collections.synchronizedList(new ArrayList<>());
		//过滤出是部门的ID
		List<String> departList = resultArray.stream().filter(f -> ((JSONObject)f).getInteger("type") == 2).map(m -> {
			departParent.add(((JSONObject)m).getJSONObject("parent").getString("$oid"));
			return ((JSONObject)m).getJSONObject("_id").getString("$oid");
		}).collect(Collectors.toList());
		//如果部门列表为空，则直接把传入的列表返回，否则需要再继续找部门的低级组织
		if (CollectionUtils.isEmpty(departList)) {
			return resultList;
		}
		resultList.removeAll(departList);
		List<String> tmpList = batchGetParentTenants(departParent);
		resultList.addAll(tmpList);
		return resultList;
	}
	

}
