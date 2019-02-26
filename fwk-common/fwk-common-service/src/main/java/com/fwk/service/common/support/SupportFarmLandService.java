package com.fwk.service.common.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rapidoid.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportFarmLandlUtil;
import com.fwk.service.common.support.util.SupportUtil;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 支撑平台地块管理接口
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月7日 下午3:04:15
 */
@Service
public class SupportFarmLandService {

	@Autowired
	private SupportService supportService;
	
	/**
	 * @Description: 支撑平台添加地块信息
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午3:11:56
	 */
	public ResultWrap addFarmLandInfoToSupport(Map<String, Object> requestMap) {
		requestMap.put("status", 1);
		System.out.println("支撑平台-地块添加----SupportFarmLandController----addFarmLandInfoToSupport:" + requestMap);
		JSONObject dataJson = SupportFarmLandlUtil.packAddDataToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		return supportService.postRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}
	
	/**
	 * @Description: 支撑平台添加地块信息(批量添加)
	 *
	 * @param requestDatas
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年5月11日 下午2:12:08
	 */
	public ResultWrap batchAddFarmlandInfoToSupport(List<Map<String, Object>> requestDatas) {
		List<JSONObject> dataListJson = SupportFarmLandlUtil.packAddDataToSupport(requestDatas);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataListJson(dataListJson);
		return supportService.postRequestWithListParam(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}

	/**
	 * @Description: 支撑平台删除地块
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午4:14:16
	 */
	public ResultWrap deleteFarmLandInfoToSupport(Map<String, Object> requestMap) {
		requestMap.put("_removed", 1);
		System.out.println("支撑平台-删除地块----SupportFarmLandController----deleteFarmLandInfoToSupport:" + requestMap);
		JSONObject dataJson = SupportFarmLandlUtil.packDelDataToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		return supportService.deleteRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}
	
	/**
	 * @Description: 支撑平台删除地块坐标
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年5月2日 下午4:20:59
	 */
	public ResultWrap deleteFarmLandCoordsToSupport(Map<String, Object> requestMap) {
		System.out.println("支撑平台-删除地块坐标----SupportFarmLandController----deleteFarmLandCoordsToSupport:" + requestMap);
		JSONObject dataJson = SupportFarmLandlUtil.packDelCoordsDataToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		return supportService.putRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}

	/**
	 * @Description: 支撑平台修改地块信息
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午4:19:56
	 */
	public ResultWrap updateFarmLandInfoToSupport(Map<String, Object> requestMap) {
		System.out.println("支撑平台-修改地块----SupportFarmLandController----updateFarmLandInfoToSupport:" + requestMap);
		JSONObject dataJson = SupportFarmLandlUtil.packEditDataToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(dataJson);
		return supportService.putRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}

	/**
	 * @Description: 支撑平台查询地块信息
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午4:59:18
	 */
	public ResultWrap queryFarmLandInfoToSupport(Map<String, Object> requestMap) {
		System.out.println("支撑平台-查询地块----SupportFarmLandController----queryFarmLandInfoToSupport:" + requestMap);
		JSONObject filterJson = SupportFarmLandlUtil.packQueryFilterToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		JSONObject pageJson = SupportFarmLandlUtil.packPageJson(requestMap);
		supportRequestParam.setPageJson(pageJson);
		JSONObject sortJson = SupportFarmLandlUtil.packSortJson();
		supportRequestParam.setSortJson(sortJson);
		return supportService.getRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}
	
	/**
	 *  @Description: 支撑平台查询地块信息,指定查询列
	 *  
	 * @param requestMap
	 * @param resultColMap
	 * @return
	 */
	public ResultWrap queryFarmLandInfoToSupport(Map<String, Object> requestMap, Map<String, Object> resultColMap) {
		System.out.println("支撑平台-查询地块----SupportFarmLandController----queryFarmLandInfoToSupport:" + requestMap);
		JSONObject filterJson = SupportFarmLandlUtil.packQueryFilterToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		JSONObject pageJson = SupportFarmLandlUtil.packPageJson(requestMap);
		supportRequestParam.setPageJson(pageJson);
		JSONObject sortJson = SupportFarmLandlUtil.packSortJson();
		supportRequestParam.setSortJson(sortJson);
		JSONObject projectionJson = SupportFarmLandlUtil.packProjectionJson(resultColMap);
		supportRequestParam.setProjectionJson(projectionJson);
		return supportService.getRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}
	
	/**
	 * @Description: 支撑平台查询地块数量
	 *
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年7月26日 下午3:11:21
	 */
	public Object queryFarmlandCountToSupport(Map<String, Object> requestMap) {
		JSONObject filterJson = SupportFarmLandlUtil.packQueryFilterToSupport(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		return supportService.requestSupportGetNew(AllJsonRequestUrl.Tenant_farmland + "/count", supportRequestParam);
	}
	
	/**
	 * @Description: 支撑平台查询地块信息(根据id列表查询)(分批查询)
	 *
	 * @param ids
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 上午10:39:38
	 */
	public ResultWrap queryFarmLandInfoToSupportByIdsAndBatch(List<String> ids, String orgId){
		System.out.println("支撑平台-根据id查询地块----SupportFarmLandController----queryFarmLandInfoToSupportByIds:" + ids);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<Object> resultList = new ArrayList<Object>();
		int dataLimit = 1000;
		List<List<String>> list = HTMLUtil.splitList(ids, dataLimit);
		int size = list.size();
		int total = 0;
		for (int i = 0; i < size; i++) {
			//封装请求参数
			SupportRequestParam supportRequestParam = new SupportRequestParam();
			//设置请求过虑参数
			JSONObject filterJson = SupportFarmLandlUtil.packQueryFilterToSupportByIds(list.get(i));
			if (ObjectUtil.isNotBlank(orgId)) {
				filterJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
			}
			supportRequestParam.setFilterJson(filterJson);
			//设置排序参数
			JSONObject sortJson = SupportFarmLandlUtil.packSortJson();
			supportRequestParam.setSortJson(sortJson);
			ResultWrap tmpResultWrap = supportService.getRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
			if (tmpResultWrap.getFlag() != ConstantUtils.RESULTWRAP_FLAG_OK) {
				tmpResultWrap.setMessage("支撑平台根据supportid查询地块信息出错," + tmpResultWrap.getMessage());
				return tmpResultWrap;
			}
			Object data = tmpResultWrap.getData();
			JSONArray tmpArray = SupportUtil.supportGetItemData(data);
			total += SupportUtil.supportGetTotal(data);
			resultList.addAll(tmpArray);
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("total", total);
		resultJson.put("items", resultList);
		resultWrap.setData(resultJson);
		return resultWrap;
	}
	
	/**
	 * @Description: 支撑平台查询地块信息(根据id列表查询)
	 *
	 * @param ids
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 上午10:39:38
	 */
	public ResultWrap queryFarmLandInfoToSupportByIds(List<String> ids, String orgId) {
		System.out.println("支撑平台-根据id查询地块----SupportFarmLandController----queryFarmLandInfoToSupportByIds:" + ids);
		JSONObject filterJson = SupportFarmLandlUtil.packQueryFilterToSupportByIds(ids);
		if (ObjectUtil.isNotBlank(orgId)) {
			filterJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
		}
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		JSONObject sortJson = SupportFarmLandlUtil.packSortJson();
		supportRequestParam.setSortJson(sortJson);
		// 解决支撑平台不带分页参数时只返回500条记录的问题
		if (ids.size() > 500) {
			Object result = supportService.requestSupportGetNew(AllJsonRequestUrl.Tenant_farmland + "/count",
					supportRequestParam);
			int count = Integer.parseInt(result.toString().trim());
			supportRequestParam.setPageJson(new JSONObject().fluentPut("limit", count).fluentPut("skip", 0));
		}
		return supportService.getRequest(AllJsonRequestUrl.Tenant_farmland, supportRequestParam);
	}

	/**
	 * @Description: 根据地址编码生成地块编码
	 *
	 * @param addressCode(区县级的地址码)
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @throws Exception 
	 * @date: 2018年3月9日 下午5:24:35
	 */
	public String generateFarmLandCodeByAddressCode(String addressCode) throws Exception {
		String code = null;
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("parcelno", addressCode);
		ResultWrap result = queryFarmLandInfoToSupport(filterMap);
		if (result.getFlag() != ConstantUtils.RESULTWRAP_FLAG_OK) {
			throw new Exception(result.getMessage());
		}
		if (SupportUtil.supportResultItemIsNotEmpty(result.getData())) {
			System.out.println("result.getData():" + result.getData());
			JSONArray items = SupportUtil.supportGetItemData(result.getData());
			List<Integer> numbers = new ArrayList<>();
			for (Object item : items) {
				code = ((JSONObject) item).getString("code");
				if (ObjectUtil.isNotBlank(code)) {
					numbers.add(Integer.parseInt(code.split("-")[1]));
				}
			}
			if (numbers.isEmpty()) {
				code = String.format("%s-%s", addressCode, SupportFarmLandlUtil.FARM_LAND_CODE_START);
			}else {
				code = String.format("%s-%s", addressCode, Collections.max(numbers) + 1);
			}
		} else {
			code = String.format("%s-%s", addressCode, SupportFarmLandlUtil.FARM_LAND_CODE_START);
		}
		return code;
	}
	
}
