package com.fwk.service.common.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.entity.TUdepartment;
import com.fwk.common.entity.TUorgWithBLOBs;
import com.fwk.common.entity.TUrole;
import com.fwk.common.entity.TUusers;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.support.util.SupportOrgDepartUserTreeUtils;
import com.fwk.service.common.support.util.SupportUtil;
@Service
public class SupportOrgDepartUserService {

	private final Logger logger = LoggerFactory.getLogger(SupportOrgDepartUserService.class);
	
	@Autowired
	private SupportOrgDepartUserTreeUtils supportOrgDepartUserTreeUtils;
	
	/**
	 * 是否查询组织或者部门下用户信息
	 */
	private Boolean queryUser = false;
	
	/**
	 * 是否查询组织下的角色信息
	 */
	private Boolean queryRole = false;
	
	/**
	 * 组织、部门、用户统计
	 */
	private Boolean queryCount = false;
		
	public JSONArray queryOrgDepartUserTree(Map<String, Object> requestMap){
		
		if(ObjectUtil.isNotBlank(requestMap.get("showtype"))){
			if(requestMap.get("showtype").equals("user")){
				this.queryUser= true;
			}else if(requestMap.get("showtype").equals("role")){
				this.queryRole = true;
			}else if(requestMap.get("showtype").equals("countUser")){
				this.queryCount = true;
			}
		}		
		JSONArray orgList = null;
		Object resultWrapOrg = supportOrgDepartUserTreeUtils.queryOrg(requestMap);		
		JSONArray orgSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			orgSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}			
		orgList = this.converOrgData(orgSupportList);
		// 重置条件
		this.queryUser= false;
		this.queryRole = false;
		this.queryCount = false;
		logger.debug("orgList:" + JSON.toJSONString(orgList));
		return orgList;
	}
	
	/**
	 * 异步查询组织、部门、用户tree
	 * @param requestMap
	 * @return
	 */
	public JSONArray queryOrgDepartUserTreeAsy(Map<String, Object> requestMap){

		
		JSONArray orgList = null;
		JSONArray userList = null;
		Object resultWrapOrg = null;
		
		if(ObjectUtil.isNotBlank(requestMap.get("orgid"))){
			resultWrapOrg = supportOrgDepartUserTreeUtils.queryOrg(requestMap);
		}else if(ObjectUtil.isNotBlank(requestMap.get("pId"))){
			String pId = (String)requestMap.get("pId");
			resultWrapOrg = supportOrgDepartUserTreeUtils.queryDepts(pId);
			userList = this.converUserData(pId, null, null);
		}
		
		JSONArray orgSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			orgSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}
		
		orgList = this.converOrgDataAsy(orgSupportList);
		if(!CollectionUtils.isEmpty(userList)){
			orgList.addAll(userList);
		}
		logger.debug("orgListAsy:" + JSON.toJSONString(orgList));
		return orgList;
	}
	
	/**
	 * 根据关键字查询用户
	 * @param requestMap
	 * @return
	 */
	public JSONArray queryUsersByKeyWord(Map<String, Object> requestMap){

		
		JSONArray userList = null;
		
		String pId = (String)requestMap.get("orgid");
		String keyWord = (String)requestMap.get("keyWord");
		if(ObjectUtil.isNotBlank(pId) && ObjectUtil.isNotBlank(keyWord)){
			List<String> deptIds = new ArrayList<String>();
			deptIds.add(pId);
			deptIds.addAll(this.querydeptIdsByParentId(pId,deptIds));
			userList = this.converUserData(null, keyWord, deptIds);
		}
		
		return userList;
	}
	
	/**
	 * 异步
	 * @param orgSupportList
	 * @return
	 */
	public JSONArray converOrgDataAsy(JSONArray orgSupportList){
		JSONArray orgList = new JSONArray();
		for(Object org: orgSupportList){
			JSONObject newOrg = this.convertData((JSONObject)org);
			if(newOrg.getIntValue("sign") == 1){//组织
				String supportId = newOrg.getString("id");
				TUorgWithBLOBs orgLocal = supportOrgDepartUserTreeUtils.queryOrg(supportId);
				
				if(orgLocal == null){
					orgLocal = new TUorgWithBLOBs();
					orgLocal.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveOrg(orgLocal);
					orgLocal = supportOrgDepartUserTreeUtils.queryOrg(supportId);
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", orgLocal.getId());
				}else{
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", orgLocal.getId());
				}
			}else if(newOrg.getIntValue("sign") == 2){
				String supportId = newOrg.getString("id");
				TUdepartment dept = supportOrgDepartUserTreeUtils.queryDept(supportId);
				if(dept == null){
					dept = new TUdepartment();
					dept.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveDept(dept);
					dept = supportOrgDepartUserTreeUtils.queryDept(supportId);
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", dept.getId());
				}else{
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", dept.getId());
				}
			}
			if(ObjectUtil.isNotBlank(newOrg.getString("parentId"))){
				newOrg.remove("parentId");
			}
			orgList.add(newOrg);
		}
		return orgList;
	}
	
	/**
	 * 解析转换组织信息
	 * @param orgSupportList
	 * @return
	 */
	public JSONArray converOrgData(JSONArray orgSupportList){
				
		JSONArray orgList = new JSONArray();
		for(Object org: orgSupportList){
			JSONObject newOrg = this.convertData((JSONObject)org);
			JSONArray deptList = null;
			JSONArray userList = null;
			JSONArray roleList = null;
			
			deptList = this.converDeptData(newOrg.getString("id"));
			
			if(this.queryUser){
				userList = this.converUserData(newOrg.getString("id"), null, null);
			}
			
			if(this.queryRole && (newOrg.getInteger("sign") == 1)){
				roleList = this.queryRoles(newOrg.getString("id"));
			}
			
			if(!CollectionUtils.isEmpty(deptList)){
				
				if(!CollectionUtils.isEmpty(userList)){
					deptList.addAll(userList);
				}
				
				if(!CollectionUtils.isEmpty(roleList)){
					deptList.addAll(roleList);
				}
			}else{
				if(!CollectionUtils.isEmpty(userList)){
					deptList = userList;
				}
				
				if(!CollectionUtils.isEmpty(deptList)){
					if(!CollectionUtils.isEmpty(roleList)){
						deptList.addAll(roleList);
					}
				}else{
					if(!CollectionUtils.isEmpty(roleList)){
						deptList = roleList;
					}
				}
			}
			
			newOrg.put("children", deptList);
			
			if(ObjectUtil.isBlank(newOrg.getString("parentId"))){
				newOrg.put("expanded", true);
			}else{
				newOrg.put("expanded", false);
			}
			
			if(this.queryCount){
				newOrg.put("count", this.countDeptUsers(newOrg.getString("id")));
			}
			
			logger.debug(newOrg.toJSONString());
			if(newOrg.getIntValue("sign") == 1){//组织
				String supportId = newOrg.getString("id");
				TUorgWithBLOBs orgLocal = supportOrgDepartUserTreeUtils.queryOrg(supportId);
				
				if(orgLocal == null){
					orgLocal = new TUorgWithBLOBs();
					orgLocal.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveOrg(orgLocal);
					orgLocal = supportOrgDepartUserTreeUtils.queryOrg(supportId);
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", orgLocal.getId());
				}else{
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", orgLocal.getId());
				}
			}else if(newOrg.getIntValue("sign") == 2){
				String supportId = newOrg.getString("id");
				TUdepartment dept = supportOrgDepartUserTreeUtils.queryDept(supportId);
				if(dept == null){
					dept = new TUdepartment();
					dept.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveDept(dept);
					dept = supportOrgDepartUserTreeUtils.queryDept(supportId);
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", dept.getId());
				}else{
					newOrg.put("supportid", newOrg.getString("id"));
					newOrg.put("id", dept.getId());
				}
			}
			if(ObjectUtil.isNotBlank(newOrg.getString("parentId"))){
				newOrg.remove("parentId");
			}
			orgList.add(newOrg);
		}
		return orgList;
	}
	
	/**
	 * 部门数据转换
	 * @param parentId
	 * @return
	 */
	public JSONArray converDeptData(String parentId){
		
		Object resultWrapOrg = supportOrgDepartUserTreeUtils.queryDepts(parentId);
		
		JSONArray deptSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			deptSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}
		
		if(CollectionUtils.isEmpty(deptSupportList)){
			return null;
		}
		
		return this.converOrgData(deptSupportList);
	}
	
	/**
	 * 用户数据转换
	 * @param deptId
	 * @return
	 */
	public JSONArray converUserData(String deptId, String keyWord, List<String> deptIds){
		
		Object resultWrapUser = null;
		
		if(ObjectUtil.isNotBlank(keyWord)){
			resultWrapUser = supportOrgDepartUserTreeUtils.queryUsersByKeyWord(deptIds, keyWord);
		}else if(ObjectUtil.isNotBlank(deptId)){
			resultWrapUser = supportOrgDepartUserTreeUtils.queryUsers(deptId);
		}
		
		JSONArray userSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapUser)){
			userSupportList = SupportUtil.supportGetItemData(resultWrapUser);
		}
		
		if(CollectionUtils.isEmpty(userSupportList)){
			return null;
		}
		
		JSONArray userList = new JSONArray();
		for(Object userSupport: userSupportList){
			JSONObject newUser = this.convertData((JSONObject)userSupport);
			newUser.put("sign", 3);
			TUusers local = supportOrgDepartUserTreeUtils.queryUser(newUser.getString("id"));
			if(ObjectUtil.isNotBlank(local)){
				newUser.put("supportid", newUser.getString("id"));
				newUser.put("id", local.getId());
				newUser.put("email", local.getEmail());
				newUser.put("jobnumber", local.getJobnumber());
			}else{
				local = new TUusers();
				local.setSupportid(newUser.getString("id"));
				supportOrgDepartUserTreeUtils.saveUser(local);
				local = supportOrgDepartUserTreeUtils.queryUser(newUser.getString("id"));
				
				newUser.put("supportid", newUser.getString("id"));
				newUser.put("id", local.getId());
				newUser.put("email", local.getEmail());
				newUser.put("jobnumber", local.getJobnumber());
			}
			
			userList.add(newUser);
		}
		
		return userList;
	}
	/**
	 * 
	 * @param orgId
	 * @return
	 */
	public Integer countOrgUsers(){
		
		Integer count = 0;
		Object resultWrap = supportOrgDepartUserTreeUtils.countOrgUsersCount();
		if(ObjectUtil.isNotBlank(resultWrap)){
			count = SupportUtil.supportGetTotal(resultWrap);
		}
		return count;
	}
	/**
	 * 递归子部门
	 * @param parentId
	 * @return
	 */
	public List<String> querydeptIdsByParentId(String parentId, List<String> deptIds){
		
		Object resultWrapOrg = supportOrgDepartUserTreeUtils.queryDepts(parentId);
		JSONArray deptSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			deptSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}
		for(Object obj : deptSupportList){
			JSONObject json = (JSONObject)obj;
			if(ObjectUtil.isNotBlank(json.getString("_id"))){
				JSONObject _id = (JSONObject)json.get("_id");
				String id = _id.getString("$oid");
				deptIds.add(id);
				this.querydeptIdsByParentId(id,deptIds);
				
			}
		}
		
		return deptIds;
	}
	
	/**
	 * 统计部门用户数
	 * @param deptId
	 * @return
	 */
	public Integer countDeptUsers(String deptId){
		Integer count = 0;
		List<String> deptIds = new ArrayList<String>();
		deptIds.add(deptId);
		deptIds.addAll(this.querydeptIdsByParentId(deptId,deptIds));
		logger.debug("deptIds:" + deptIds.toString());
		
		Object resultWrap = supportOrgDepartUserTreeUtils.countDeptUsers(deptIds);
		
		if(ObjectUtil.isNotBlank(resultWrap)){
			count = SupportUtil.supportGetTotal(resultWrap);
		}
		return count;
	}
	
	/**
	 * 查询组织角色列表
	 * @param orgId
	 * @return
	 */
	public JSONArray queryRoles(String orgId){
		
		List<TUrole> list1 = supportOrgDepartUserTreeUtils.queryRoles(orgId);
		JSONArray list2 = new JSONArray();
		if(CollectionUtils.isEmpty(list1)){
			return null;
		}
		for(TUrole role : list1){
			JSONObject json = new JSONObject();
			json.put("id", role.getId());
			json.put("name", role.getRolename());
			json.put("sign", 4);
			json.put("title", role.getRolename());
			list2.add(json);
		}
		return list2;
	}
	
	/**
	 * 基础数据转换
	 * @param data
	 * @return
	 */
	public JSONObject convertData(JSONObject data){
		
		JSONObject newData = new JSONObject();
		
		if(ObjectUtil.isNotBlank(data.getString("_id"))){
			JSONObject _id = (JSONObject)data.get("_id");
			newData.put("id", _id.getString("$oid"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("name"))){
			newData.put("name", data.getString("name"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("avatar"))){
			newData.put("phoneurl", data.getString("avatar"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("nickname"))){
			newData.put("nickname", data.getString("nickname"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("mobile"))){
			newData.put("phone", data.getString("mobile"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("type"))){
			newData.put("sign", data.getString("type"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("name"))){
			newData.put("title", data.getString("name"));
		}
		
		if(ObjectUtil.isNotBlank(data.getString("parent"))){
			JSONObject _id = (JSONObject)data.get("parent");
			newData.put("parentId", _id.getString("$oid"));
		}
		
		return newData;
	}
	
	/**
	 * 数据同步
	 * @param requestMap
	 */
	public void supportDS(Map<String, Object> requestMap){
		this.supportDsOrgs(requestMap);
	}
	/**
	 * 组织部门数据同步
	 * @param requestMap
	 */
	public void supportDsOrgs(Map<String, Object> requestMap){
				
		Object resultWrapOrg = supportOrgDepartUserTreeUtils.queryOrg(requestMap);
		JSONObject orgData = null;
		JSONArray orgSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			orgSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}
		if(CollectionUtils.isEmpty(orgSupportList)){
			return;
		}
		
		for(Object orgSupport: orgSupportList){
			JSONObject jsonObj = this.convertData((JSONObject)orgSupport);
			if(jsonObj.getInteger("sign") == 1){//组织
				JSONObject _id = (JSONObject)jsonObj.get("_id");
				String supportId = _id.getString("$oid");
				TUorgWithBLOBs org = supportOrgDepartUserTreeUtils.queryOrg(supportId);
				if(org == null){
					org = new TUorgWithBLOBs();
					org.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveOrg(org);
				}
				this.supportDsUsers(supportId);
			}else if(jsonObj.getInteger("sign") == 2){
				JSONObject _id = (JSONObject)jsonObj.get("_id");
				String supportId = _id.getString("$oid");
				TUdepartment dept = supportOrgDepartUserTreeUtils.queryDept(supportId);
				if(dept == null){
					dept = new TUdepartment();
					dept.setSupportid(supportId);
					supportOrgDepartUserTreeUtils.saveDept(dept);
				}
				this.supportDsUsers(supportId);
			}
		}
		
	}
	
	/**
	 * 用户数据同步
	 */
	public void supportDsUsers(String deptId){
		
		Object resultWrapOrg = supportOrgDepartUserTreeUtils.queryUsers(deptId);
		JSONArray userSupportList = null;
		if(ObjectUtil.isNotBlank(resultWrapOrg)){
			userSupportList = SupportUtil.supportGetItemData(resultWrapOrg);
		}
		
		if(CollectionUtils.isEmpty(userSupportList)){
			return;
		}
		
		for(Object userSupport: userSupportList){
			JSONObject json = this.convertData((JSONObject)userSupport);
			JSONObject _id = (JSONObject)json.get("_id");
			String supportId = _id.getString("$oid");
			TUusers user = supportOrgDepartUserTreeUtils.queryUser(supportId);
			if(user == null){
				user = new TUusers();
				user.setSupportid(supportId);
				supportOrgDepartUserTreeUtils.saveUser(user);
			}
		}
	}
}
