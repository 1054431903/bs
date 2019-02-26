package com.fwk.service.user.controller;

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
import com.fwk.common.util.Page;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.entity.PermissionEXT;
import com.fwk.service.user.entity.RoleEXT;
import com.fwk.service.user.service.PermissionService;
import com.fwk.service.user.service.RoleService;

/**
 * 角色管理
 * */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 获取权限树形结构(二级加功能点)
	 * */
	@RequestMapping("/queryPermissionTree")
	private void queryFunctionModuleTree(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		requestMap.put("rootid", 0);
		List<PermissionEXT> permissionEXTs = permissionService.queryPermissionList(requestMap);
		if(ObjectUtil.isNotBlank(permissionEXTs) && permissionEXTs.size() > 0){
			buildChild(permissionEXTs);
		}
		resultWrap.setData(permissionEXTs);
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	public void buildChild(List<PermissionEXT> permissionEXTs){
		if(ObjectUtil.isNotBlank(permissionEXTs) && permissionEXTs.size() > 0){
			for (int i = 0; i < permissionEXTs.size(); i++) {
				PermissionEXT permissionEXT = permissionEXTs.get(i);
				//获取导航子集列表
				Map<String, Object> requestMap = new HashMap<String,Object>();
				requestMap.put("rootid", permissionEXT.getId());
				requestMap.put("typecode", "NAVIGATE");
				List<PermissionEXT> permissionEXTs2 = permissionService.queryPermissionList(requestMap);
				permissionEXT.setChildren(permissionEXTs2);
				//获取对应功能点列表
				Map<String, Object> requestMap1 = new HashMap<String,Object>();
				requestMap1.put("rootid", permissionEXT.getId());
				requestMap1.put("typecode", "FUNCTION");
				List<PermissionEXT> permissionEXTs3 = permissionService.queryPermissionList(requestMap1);
				permissionEXT.setFunctions(permissionEXTs3);
				if(permissionEXT.getTypecode().equals("NAVIGATE")){
					buildChild(permissionEXTs2);
				}
			}
		}
	}
	
	/**
	 * 查询角色列表
	 * */
	@RequestMapping("/queryRole")
	public void queryRole(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("loginorgid"))){
			if(ObjectUtil.isNotBlank(requestMap.get("searchval"))){
				requestMap.put("id", requestMap.get("searchval"));
				requestMap.put("rolename", requestMap.get("searchval"));
			}
			List<RoleEXT> roleEXTs = roleService.queryRole(requestMap);
			
			resultWrap.setData(roleEXTs);
			resultWrap.setPage(new Page().pageTotal(roleService.queryRoleCount(requestMap), request));
		}else{
			resultWrap.setParamIsnotEmpty("loginorgid");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 删除角色
	 * */
	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("id"))){
			requestMap.put("delstate", 0);
			int result = roleService.updateRole(requestMap);
			if(result >= 1){
				resultWrap.setDeleteSuccess();
			}else{
				resultWrap.setDeleteFailure();
			}
		}else{
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 获取功能块树形结构（不使用）
	 * */
//	@RequestMapping("/queryFunctionModuleTree")
//	private void queryFunctionModuleTree(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
//		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
//		//获取是所有功能块
//		List<FunctionModuleEXT> functionModuleEXTs = functionModuleService.queryFunctionModule(requestMap);
//		//获取所有功能点
//		List<FunctionPointEXT> functionPointEXTs = functionPointService.queryFunctionPoint(requestMap);
//		//将所有功能点赋值给功能块
//		for (int i = 0; i < functionModuleEXTs.size(); i++) {
//			FunctionModuleEXT functionModuleEXT = functionModuleEXTs.get(i);
//			functionModuleEXT.setChildren(functionPointEXTs);
//		}
//		resultWrap.setData(functionModuleEXTs);
//		HTMLUtil.sendWrap(response, resultWrap, request);
//	}
	
	/**
	 * 保存角色（包含新增及修改）
	 * */
	@RequestMapping("/saveRole")
	public void saveRole(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
//		if(ObjectUtil.isNotBlank(requestMap.get("rolename")) && ObjectUtil.isNotBlank(requestMap.get("permissions")) && ObjectUtil.isNotBlank(requestMap.get("orgid"))){
			if(ObjectUtil.isNotBlank(requestMap.get("rolename")) && ObjectUtil.isNotBlank(requestMap.get("permissions"))){
			int result = 0;
			Map<String,Object> checkRoleExistMap = new HashMap<String,Object>();
			checkRoleExistMap.put("rolename", requestMap.get("rolename"));
			if(ObjectUtil.isNotBlank(requestMap.get("id"))){
				checkRoleExistMap.put("noid", requestMap.get("id"));
			}
			List<RoleEXT> roleEXTs = roleService.queryRole(checkRoleExistMap);
			if(ObjectUtil.isNotBlank(roleEXTs) && roleEXTs.size() >= 1){//已经存在该角色
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-role-save-rolename-exist"));
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			if(ObjectUtil.isNotBlank(requestMap.get("id"))){//执行修改角色
				result = roleService.updateRole(requestMap);
			}else{//执行添加角色
				result = roleService.insertRole(requestMap);
			}
			//保存角色对应的权限
			saveRolePermission(requestMap.get("id"),requestMap.get("permissions"),resultWrap);
			if(result >= 1){
				resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-role-save-permission-success"));
			}else{
				resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-role-save-permission-failure"));
			}
		}else{
			resultWrap.setParamIsnotEmpty("{角色名称,角色权限}");
//			resultWrap.setParamIsnotEmpty("{角色名称,角色权限,所属组织}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 保存角色对应的所有权限
	 * */
	private boolean saveRolePermission(Object roleid,Object permissions,ResultWrap resultWrap){
		if(ObjectUtil.isNotBlank(roleid) && ObjectUtil.isNotBlank(permissions)){
			//删除角色的所有权限
			Map<String,Object> delRolepermission = new HashMap<String,Object>();
			delRolepermission.put("roleid", roleid);
			permissionService.deleteRolePermission(delRolepermission);
			//新增角色的所有权限
			String[] permissionss = permissions.toString().split(",");
			for (int i = 0; i < permissionss.length; i++) {
				Map<String,Object> saveRolePermission = new HashMap<String,Object>();
				saveRolePermission.put("roleid", roleid);
				saveRolePermission.put("permissionid", permissionss[i]);
				int result = permissionService.saveRolePermission(saveRolePermission);
				if(result<=0){
					resultWrap.error(LanguageUtil.getLanguageByKey(""));
					return false;
				}
			}
		}else{
			resultWrap.setParamIsnotEmpty("{roleid,permissions}");
		}
		return true;
	}
}
