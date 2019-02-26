package com.fwk.service.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.system.entity.SDictTypeEXT;
import com.fwk.service.system.service.SDictTypeService;

@Controller
@RequestMapping("/tsdictType")
public class SDictTypeController {

	@Autowired
	private SDictTypeService dictTypeService;
	
	/**
	 * 查询系统字典
	 * */
	@RequestMapping("/queryTSdictType")
	public void queryTSdictType(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<SDictTypeEXT> tSdictTypeEXTs = null;
		try {
			tSdictTypeEXTs = dictTypeService.queryTSdictType(requestMap);
			resultWrap.setPage(new Page().pageTotal(dictTypeService.queryTSdictTypeCount(requestMap),request));
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		resultWrap.setData(tSdictTypeEXTs);
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 添加系统字典
	 * */
	@RequestMapping("/saveTSdictType")
	public void saveTSdictType(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("name")) && ObjectUtil.isNotBlank(requestMap.get("code"))){
			int result = 0;
			try {
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("code", requestMap.get("code"));
				if(dictTypeService.queryTSdictTypeNotExist(queryMap) >= 1){//验证code是否已经存在
					resultWrap.setDateRepit();
				}else{
					result = dictTypeService.saveTSdictType(requestMap);//正常添加数据
					if(result <= 0){
						resultWrap.setSaveFailure();
					}else{
						resultWrap.setSaveSuccess();
					}
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		}else{
			resultWrap.setParamIsnotEmpty("{name,code}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 修改系统字典
	 * */
	@RequestMapping("/updateTSdictType")
	public void updateTSdictType(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("name")) && ObjectUtil.isNotBlank(requestMap.get("code")) && ObjectUtil.isNotBlank(requestMap.get("id"))){
			int result = 0;
			try {
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("code", requestMap.get("code"));
				queryMap.put("noid", requestMap.get("id"));
				if(dictTypeService.queryTSdictTypeNotExist(queryMap) >= 1){//验证code是否已经存在
					resultWrap.setDateRepit();
				}else{
					result = dictTypeService.updateTSdictType(requestMap);//正常修改数据
					if(result <= 0){
						resultWrap.setUpdateFailure();
					}else{
						resultWrap.setUpdateSuccess();
					}
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		}else{
			resultWrap.setParamIsnotEmpty("{id,name,code}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 删除系统字典
	 * */
	@RequestMapping("/deleteTSdictType")
	public void deleteTSdictType(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("id"))){
			int result = 0;
			try {
				requestMap.put("delstate", CommonConstantsUtil.IS_DELETE_TRUE);
				result = dictTypeService.updateTSdictType(requestMap);//正常修改数据
				if(result <= 0){
					resultWrap.setDeleteFailure();
				}else{
					resultWrap.setDeleteSuccess();
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		}else{
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
}
