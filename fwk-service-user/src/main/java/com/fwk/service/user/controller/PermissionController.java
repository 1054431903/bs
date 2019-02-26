package com.fwk.service.user.controller;

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
import com.fwk.service.user.entity.PermissionEXT;
import com.fwk.service.user.service.PermissionService;
import com.fwk.service.user.service.UserService;

/**
 * 权限管理
 * */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 查询全部权限(带有层级结构)
	 * */
	@RequestMapping("/queryAllPermissionLevel")
	public void queryAllPermission(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 查询全部权限(不带有层级结构，平级展示)
	 * */
	@RequestMapping("/queryAllPermissionFlat")
	public void queryAllPermissionFlat(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 查询用户所拥有的权限
	 * */
	public void queryPermissionByUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get(""))){
			
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 获取所有的权限列表
	 * */
	private List<PermissionEXT> getPermissionList(Map<String, Object> requestMap){
		return permissionService.queryPermissionList(requestMap);
	}
}
