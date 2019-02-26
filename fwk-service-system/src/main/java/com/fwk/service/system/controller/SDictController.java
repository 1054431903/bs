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
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.system.entity.SDictEXT;
import com.fwk.service.system.service.SDictService;

@Controller
@RequestMapping("/dict")
public class SDictController {

	@Autowired
	private SDictService dictService;

	@Autowired
	private SupportUserService supportUserService;

	/**
	 * 查询系统字典
	 */
	@RequestMapping("/queryTSdict")
	public void queryTSdict(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<SDictEXT> tSdictTypeEXTs = null;
		try {
			tSdictTypeEXTs = dictService.queryTSdict(requestMap);
			for (int i = 0; i < tSdictTypeEXTs.size(); i++) {
				SDictEXT sDictEXT = tSdictTypeEXTs.get(i);
				JSONObject resultJO = supportUserService.queryUserDetailBySupportid(sDictEXT.getUserid());
				if(ObjectUtil.isNotBlank(resultJO) && resultJO.keySet().size() >= 1){
					if(ObjectUtil.isNotBlank(resultJO.getString("name"))) {
						sDictEXT.setUserName(resultJO.getString("name"));
					}
				}
			}
			resultWrap.setPage(new Page().pageTotal(dictService.queryTSdictCount(requestMap), request));
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		resultWrap.setData(tSdictTypeEXTs);
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 添加系统字典
	 */
	@RequestMapping("/saveTSdict")
	public void saveTSdict(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("name")) && ObjectUtil.isNotBlank(requestMap.get("parentcode"))
				&& ObjectUtil.isNotBlank(requestMap.get("parentid")) && ObjectUtil.isNotBlank("operateid")) {
			int result = 0;
			try {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("name", requestMap.get("name"));
				if (dictService.queryTSdictNotExist(queryMap) >= 1) {// 验证code是否已经存在
					resultWrap.setDateRepit();
				} else {
					result = dictService.saveTSdict(requestMap);// 正常添加数据
					if (result <= 0) {
						resultWrap.setSaveFailure();
					} else {
						resultWrap.setSaveSuccess();
					}
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		} else {
			resultWrap.setParamIsnotEmpty("{name,parentcode,parentid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 修改系统字典
	 */
	@RequestMapping("/updateTSdict")
	public void updateTSdict(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("name")) && ObjectUtil.isNotBlank(requestMap.get("id"))) {
			int result = 0;
			try {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("name", requestMap.get("name"));
				queryMap.put("noid", requestMap.get("id"));
				if (dictService.queryTSdictNotExist(queryMap) >= 1) {// 验证code是否已经存在
					resultWrap.setDateRepit();
				} else {
					result = dictService.updateTSdict(requestMap);// 正常修改数据
					if (result <= 0) {
						resultWrap.setUpdateFailure();
					} else {
						resultWrap.setUpdateSuccess();
					}
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		} else {
			resultWrap.setParamIsnotEmpty("{id,name}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 删除系统字典
	 */
	@RequestMapping("/deleteTSdict")
	public void deleteTSdict(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			int result = 0;
			try {
				requestMap.put("delstate", CommonConstantsUtil.IS_DELETE_TRUE);
				result = dictService.updateTSdict(requestMap);// 正常修改数据
				if (result <= 0) {
					resultWrap.setDeleteFailure();
				} else {
					resultWrap.setDeleteSuccess();
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
			}
		} else {
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
}
