package com.fwk.service.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.SupportOrgDepartUserService;
import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.service.DepartmentService;
import com.fwk.service.user.service.OrgService;
import com.fwk.service.user.service.RoleService;
import com.fwk.service.user.service.UserService;

/**
 * 公共控制类
 * */
@Controller
@RequestMapping("/pub")
public class PublicController {
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private Boolean isshowuser = false;//是否显示用户树
	
	private Boolean isshowrole = false;//是否显示角色树
	
	@Autowired
	private SupportOrgDepartUserService supportOrgDepartUserService;
		
	@RequestMapping("/OrgDepartUserTree")
	public void queryOrgDepartUserTree(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		if(ObjectUtil.isNotBlank(requestMap.get("orgid"))){
			resultWrap.setData(supportOrgDepartUserService.queryOrgDepartUserTree(requestMap));
		}else{
			resultWrap.setParamIsnotEmpty("{orgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	/**
	 * 异步加载组织部门用户tree
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOrgDepartUserTreeAsy")
	public void queryOrgDepartUserTreeAsy(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		if(ObjectUtil.isNotBlank(requestMap.get("orgid")) || ObjectUtil.isNotBlank(requestMap.get("pId"))){
			resultWrap.setData(supportOrgDepartUserService.queryOrgDepartUserTreeAsy(requestMap));
		}else{
			resultWrap.setParamIsnotEmpty("{orgid}或者{pId}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * APP端用户搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryUsersByKeyWord")
	public void queryUsersByKeyWord(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		if(ObjectUtil.isNotBlank(requestMap.get("orgid")) || ObjectUtil.isNotBlank(requestMap.get("keyWord"))){
			resultWrap.setData(supportOrgDepartUserService.queryUsersByKeyWord(requestMap));
		}else{
			resultWrap.setParamIsnotEmpty("{orgid}或者{keyWord}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 统计部门用户数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/countDeptUsers")
	public void countDeptUsers(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		if(ObjectUtil.isNotBlank(requestMap.get("deptId"))){
			resultWrap.setData(supportOrgDepartUserService.countDeptUsers((String)requestMap.get("deptId")));
		}else{
			resultWrap.setParamIsnotEmpty("{orgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/*@RequestMapping("/OrgDepartUserTree")
	public void OrgDepartUserTree(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("orgid"))){
			if(ObjectUtil.isNotBlank(requestMap.get("showtype"))){
				if(requestMap.get("showtype").equals("user")){
					isshowuser = true;
					isshowrole = false;
				}else if(requestMap.get("showtype").equals("role")){
					isshowrole = true;
					isshowuser = false;
				}else{
					isshowuser = false;
					isshowrole = false;
				}
			}else{
				isshowuser = false;
				isshowrole = false;
			}
			//查询企业信息放至树结构实体
			Map<String,Object> orgMap = new HashMap<String,Object>();
			orgMap.put("id", requestMap.get("orgid"));
			List<OrgDepartUserTreeDefine> orgList = orgService.getOrgToTree(orgMap); 
			//获取组织的部门信息
			if(ObjectUtil.isNotBlank(orgList) && orgList.size() > 0){
				for (int i = 0; i < orgList.size(); i++) {//组织
					OrgDepartUserTreeDefine treeDefine = orgList.get(i);
					Map<String,Object> departmentMap = new HashMap<String,Object>();
					departmentMap.put("orgid", treeDefine.getId());
					List<OrgDepartUserTreeDefine> departList = departmentService.getDepartmentTree(departmentMap);
						
					//获取部门的子集部门
					getDepartment(departList);//获取部门
					
					Map<String,Object> userMap = new HashMap<String,Object>();
					userMap.put("orgid", requestMap.get("orgid"));
					List<OrgDepartUserTreeDefine> userTreeDefines = userService.getUserTree(userMap);
					treeDefine.setCount(userTreeDefines.size());
					//获取组织的用户列表
					if(isshowuser){//获取用户
						if(ObjectUtil.isNotBlank(userTreeDefines) && userTreeDefines.size() > 0){
							treeDefine.setCount(userTreeDefines.size());
							for (int j = 0; j < userTreeDefines.size(); j++) {
								departList.add(userTreeDefines.get(j));
							}
						}
					}
					//获取组织的角色列表
					if(isshowrole){
						Map<String,Object> roleTreeMap = new HashMap<String,Object>();
						roleTreeMap.put("orgid", requestMap.get("orgid"));
						List<OrgDepartUserTreeDefine> roleDefineTree = roleService.queryRoleTree(roleTreeMap);
						for (int j = 0; j < roleDefineTree.size(); j++) {
							departList.add(roleDefineTree.get(j));
						}
					}
					
					treeDefine.setChildren(departList);
				}
			}
			resultWrap.setData(orgList);
		}else{
			resultWrap.setParamIsnotEmpty("{orgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	//通过部门的父级查询子集
	private void getDepartment(List<OrgDepartUserTreeDefine> orgList){
		if(ObjectUtil.isNotBlank(orgList) && orgList.size() > 0){
			for (int i = 0; i < orgList.size(); i++) {
				OrgDepartUserTreeDefine orgDepartUserTreeDefine = orgList.get(i);
				Map<String,Object> departmentMap = new HashMap<String,Object>();
				departmentMap.put("parentid", orgDepartUserTreeDefine.getId());
				List<OrgDepartUserTreeDefine> departUserTreeDefines = departmentService.getDepartmentTree(departmentMap);
				getDepartment(departUserTreeDefines);
				
				Map<String,Object> userMap = new HashMap<String,Object>();
//				userMap.put("departmentid", orgDepartUserTreeDefine.getId());
				
				if(ObjectUtil.isNotBlank(orgDepartUserTreeDefine.getId())){
					departmentIds = new ArrayList<Integer>();
					getDepartmentIds(orgDepartUserTreeDefine.getId());
					userMap.put("departmentids", departmentIds);
				}
				
				List<OrgDepartUserTreeDefine> userTreeDefines = userService.getUserTree(userMap);
				orgDepartUserTreeDefine.setCount(userTreeDefines.size());
				//获取部门的用户列表
				if(isshowuser){
					if(ObjectUtil.isNotBlank(userTreeDefines) && userTreeDefines.size() > 0){
						for (int j = 0; j < userTreeDefines.size(); j++) {
							departUserTreeDefines.add(userTreeDefines.get(j));
						}
					}
				}
				orgDepartUserTreeDefine.setChildren(departUserTreeDefines);
			}
		}
	}*/
	
	/**
	 * 获取该部门下的部门id
	 * @param departmentId:部门编号
	 * */
	private List<Integer> departmentIds = new ArrayList<Integer>();
	private void getDepartmentIds(Integer departmentId){
		departmentIds.add(departmentId);
		Map<String,Object> queryDepartmentMap = new HashMap<String,Object>();
		queryDepartmentMap.put("parentid", departmentId);
		List<DepartmentEXT> departmentEXTs = departmentService.queryDepartment(queryDepartmentMap);
		for (int i = 0; i < departmentEXTs.size(); i++) {
//			departmentIds+= departmentEXTs.get(i).getId()+",";
			departmentIds.add(departmentEXTs.get(i).getId());
		}
	}
}
