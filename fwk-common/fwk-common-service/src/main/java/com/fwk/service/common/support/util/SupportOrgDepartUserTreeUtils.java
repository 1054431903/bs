package com.fwk.service.common.support.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.dao.TUdepartmentMapper;
import com.fwk.common.dao.TUorgMapper;
import com.fwk.common.dao.TUroleMapper;
import com.fwk.common.dao.TUusersMapper;
import com.fwk.common.entity.TUdepartment;
import com.fwk.common.entity.TUdepartmentExample;
import com.fwk.common.entity.TUorgExample;
import com.fwk.common.entity.TUorgWithBLOBs;
import com.fwk.common.entity.TUrole;
import com.fwk.common.entity.TUroleExample;
import com.fwk.common.entity.TUusers;
import com.fwk.common.entity.TUusersExample;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;

/**
 * MongoDB数据解析拼装
 * @author mhz
 *
 */
@Component
public class SupportOrgDepartUserTreeUtils {

	private final Logger logger = LoggerFactory.getLogger(SupportOrgDepartUserTreeUtils.class);
	
	@Autowired
	private TUorgMapper  orgMapper;
	
	@Autowired
	private TUdepartmentMapper  deptMapper;
	
	@Autowired
	private TUusersMapper  userMapper;
	
	@Autowired
	private TUroleMapper roleMapper;
	
	@Autowired
	private SupportService supportService;
	
	/**
	 * 查询组织信息
	 * @param orgId
	 * @return
	 */
	public Object queryOrg(Map<String, Object> requestMap){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		
		if (ObjectUtil.isNotBlank(requestMap.get("orgid"))) {
			
			JSONObject condit = new JSONObject();
			condit.put("$oid", requestMap.get("orgid"));
			filter.put("_id", condit);
			// filter.put("status", 1);
		}
		supportRequestParam.setFilterJson(filter);
		logger.debug("queryOrg filter:" + filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}
	
	/**
	 * 查询所有组织和部门
	 * @return
	 */
	public Object queryOrgAndDept(){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}
	
	/**
	 * 查询所有用户
	 * @param deptId
	 * @return
	 */
	public Object countOrgUsersCount(){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();		
		// filter.put("status", 1);
		logger.debug("countOrgUsersCount filter:" + filter);
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}
	/**
	 * 查询子部门
	 * @param parentId
	 * @return
	 */
	public Object queryDepts(String orgId){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		JSONObject condit = new JSONObject();
		condit.put("$oid", orgId);
		filter.put("parent", condit);
		// filter.put("status", 1);
		supportRequestParam.setFilterJson(filter);
		logger.debug("queryDepts filter:" + filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
	}
	/**
	 * 查询部门用户
	 * @param deptId
	 * @return
	 */
	public Object queryUsers(String deptId){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		JSONObject tenant = new JSONObject();
		tenant.put("$oid", deptId);
		JSONArray tenants = new JSONArray();
		tenants.add(tenant);
		JSONObject in = new JSONObject();
		in.put("$in", tenants);
		filter.put("tenants", in);
		// filter.put("status", 1);
		logger.debug("queryUsers filter:" + filter);
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}
	
	/**
	 * 根据关键字查询用户 
	 * @param deptIds
	 * @param keyWord
	 * @return
	 */
	public Object queryUsersByKeyWord(List<String> deptIds, String keyWord){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		JSONArray tenants = new JSONArray();
		for(String deptId : deptIds){
			JSONObject tenant = new JSONObject();
			tenant.put("$oid", deptId);
			tenants.add(tenant);
		}
		JSONObject in = new JSONObject();
		in.put("$in", tenants);
		filter.put("tenants", in);
		if(ObjectUtil.isNotBlank(keyWord)){
			
			JSONArray orList = new JSONArray();
			
			JSONObject name = new JSONObject();
			JSONObject _name = new JSONObject();
			_name.put("$regex", keyWord);
			name.put("name", _name);
			orList.add(name);
			
			JSONObject nickName = new JSONObject();
			JSONObject _nickName = new JSONObject();
			_nickName.put("$regex", keyWord);
			nickName.put("nickname", _nickName);
			orList.add(nickName);
			
			JSONObject mobile = new JSONObject();
			JSONObject _mobile = new JSONObject();
			_mobile.put("$regex",keyWord);
			mobile.put("mobile", _mobile);
			orList.add(mobile);
			
			filter.put("$or", orList);
		}
		// filter.put("status", 1);
		logger.debug("queryUsersByKeyWord filter:" + filter);
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}
	
	/**
	 * 统计部门人数
	 * @param deptIds
	 * @return
	 */
	public Object countDeptUsers(List<String> deptIds){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();	
		JSONArray tenants = new JSONArray();
		for(String deptId : deptIds){
			JSONObject tenant = new JSONObject();
			tenant.put("$oid", deptId);
			tenants.add(tenant);
		}
		
		JSONObject in = new JSONObject();
		in.put("$in", tenants);
		filter.put("tenants", in);
		// filter.put("status", 1);
		logger.debug("countDeptUsers filter:" + filter);
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}
	
	/**
	 * 查询角色
	 * @param requestMap
	 * @return
	 */
	public Object queryRolesSupport(String orgId){
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		JSONObject filter = new JSONObject();
		JSONObject condit = new JSONObject();
		condit.put("$oid", orgId);
		filter.put("parent", condit);
		// filter.put("status", 1);
		supportRequestParam.setFilterJson(filter);
		return supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
	}	
	/**
	 * 根据支撑数据标识查询组织信息
	 * @param supportid
	 * @return
	 */
	public TUorgWithBLOBs queryOrg(String supportid){
		
		TUorgExample contion = new TUorgExample();
		contion.createCriteria().andSupportidEqualTo(supportid)
		.andDelstateEqualTo(1);
		
		List<TUorgWithBLOBs> listOrg = orgMapper.selectByExampleWithBLOBs(contion);
		if(CollectionUtils.isEmpty(listOrg)){
			return null;
		}
		return listOrg.get(0);
	}
	
	/**
	 * 同步支撑组织数据
	 * @param org
	 * @return
	 */
	public Integer saveOrg(TUorgWithBLOBs org){
		return orgMapper.insertSelective(org);
	}
	
	/**
	 * 根据支撑数据标识查询部门信息
	 * @param supportid
	 * @return
	 */
	public TUdepartment queryDept(String supportid){
		
		TUdepartmentExample contion = new TUdepartmentExample();
		contion.createCriteria().andSupportidEqualTo(supportid)
		.andDelstateEqualTo(1);
		
		List<TUdepartment> listDept = deptMapper.selectByExample(contion);
		if(CollectionUtils.isEmpty(listDept)){
			return null;
		}
		return listDept.get(0);
	}
	/**
	 * 同步支撑部门数据
	 * @param dept
	 * @return
	 */
	public Integer saveDept(TUdepartment dept){
		return deptMapper.insertSelective(dept);
	}
	
	/**
	 * 根据支撑数据标识查询用户信息
	 * @param supportid
	 * @return
	 */
	public TUusers queryUser(String supportid){
		
		TUusersExample contion = new TUusersExample();
		contion.createCriteria().andSupportidEqualTo(supportid)
		.andDelstateEqualTo(1);
		
		List<TUusers> listDept = userMapper.selectByExample(contion);
		if(CollectionUtils.isEmpty(listDept)){
			return null;
		}
		return listDept.get(0);
	}
	
	/**
	 * 同步支撑用户数据
	 * @param dept
	 * @return
	 */
	public Integer saveUser(TUusers user){
		return userMapper.insertSelective(user);
	}
	/**
	 * 查询组织的角色列表
	 * @param orgId
	 * @return
	 */
	public List<TUrole> queryRoles(String orgId){
		
		TUorgWithBLOBs org = this.queryOrg(orgId);
		if(org == null){
			return null;
		}
		
		TUroleExample contion = new TUroleExample();
		contion.createCriteria().andOrgidEqualTo(ObjectUtil.toString(org.getId()));		
		List<TUrole> listRole = roleMapper.selectByExample(contion);
		
		return listRole;
	}
	
	public static void main(String[] args) {
		Integer  aa =null ;
		String  a = String.valueOf(aa);
		System.out.println(a);
		
	}
}
