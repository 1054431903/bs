package com.fwk.service.common.support.util;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;

/**
 * 支撑平台帮助类
 * */
public class SupportUtil {
	
	/**
	 * 判断支撑返回的Object内的items是否有数据
	 * */
	public static boolean supportResultItemIsNotEmpty(Object result){
		if(ObjectUtil.isNotBlank(result)){
			JSONObject resultJson = JSONObject.parseObject(result.toString());
			if(ObjectUtil.isNotBlank(resultJson.get("items"))){
				JSONArray ja = JSONArray.parseArray(resultJson.get("items")+"");
				if(ja.size() >= 1){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 获取支撑返回的Object内的items数据
	 * return JSONArray对象
	 * */
	public static JSONArray supportGetItemData(Object result){
		JSONArray ja = new JSONArray();
		if(ObjectUtil.isNotBlank(result)){
			JSONObject resultJson = JSONObject.parseObject(result.toString());
			if(ObjectUtil.isNotBlank(resultJson.get("items"))){
				ja = JSONArray.parseArray(resultJson.get("items")+"");
			}
		}
		return ja;
	}
	
	/**
	 * 获取支撑返回的Object内的total总数
	 * return JSONArray对象
	 * */
	public static Integer supportGetTotal(Object result){
		Integer total = 0;
		if(ObjectUtil.isNotBlank(result)){
			JSONObject resultJson = JSONObject.parseObject(result.toString());
			if(ObjectUtil.isNotBlank(resultJson.get("total"))){
				Object objectTotal = resultJson.get("total");
				if (!objectTotal.getClass().getName().equals("com.alibaba.fastjson.JSONObject")) {
					return Integer.parseInt(objectTotal.toString());
				}
				JSONObject jo = JSONObject.parseObject(objectTotal.toString());
				if(ObjectUtil.isNotBlank(jo.getBigInteger("$numberLong"))){
					total = Integer.parseInt(jo.getString("$numberLong"));
				}
			}
		}
		return total;
	}
	
	/**
	 * 获取最底级行政区划
	 * */
	public static String supportGetAddressCode(JSONObject jsonObject){
		String result = "";
		if(ObjectUtil.isNotBlank(jsonObject)){
			if(ObjectUtil.isNotBlank(jsonObject.get("village"))){
				result = JSONObject.parseObject(jsonObject.getString("village")).getString("$oid");
			}else if(ObjectUtil.isNotBlank(jsonObject.get("town"))){
				result = JSONObject.parseObject(jsonObject.getString("town")).getString("$oid");
			}else if(ObjectUtil.isNotBlank(jsonObject.get("district"))){
				result = JSONObject.parseObject(jsonObject.getString("district")).getString("$oid");
			}else if(ObjectUtil.isNotBlank(jsonObject.get("city"))){
				result = JSONObject.parseObject(jsonObject.getString("city")).getString("$oid");
			}else if(ObjectUtil.isNotBlank(jsonObject.get("province"))){
				result = JSONObject.parseObject(jsonObject.getString("province")).getString("$oid");
			}
		}
		return result;
	}
	
	/**
	 * 获取最底级行政区划
	 * */
	public static String supportGetAddressCode(JSONArray jsonArray){
		String result = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(ObjectUtil.isNotBlank(jsonObject)){
				if(ObjectUtil.isNotBlank(jsonObject.get("village"))){
					result = JSONObject.parseObject(jsonObject.getString("village")).getString("$oid");
				}else if(ObjectUtil.isNotBlank(jsonObject.get("town"))){
					result = JSONObject.parseObject(jsonObject.getString("town")).getString("$oid");
				}else if(ObjectUtil.isNotBlank(jsonObject.get("district"))){
					result = JSONObject.parseObject(jsonObject.getString("district")).getString("$oid");
				}else if(ObjectUtil.isNotBlank(jsonObject.get("city"))){
					result = JSONObject.parseObject(jsonObject.getString("city")).getString("$oid");
				}else if(ObjectUtil.isNotBlank(jsonObject.get("province"))){
					result = JSONObject.parseObject(jsonObject.getString("province")).getString("$oid");
				}
			}
		}
		return result;
	}
	
	public static String supportGetAddressDetail(JSONArray jsonArray){
		String result = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(ObjectUtil.isNotBlank(jsonObject)){
				if(ObjectUtil.isNotBlank(jsonObject.get("street"))){
					result = jsonObject.getString("street");
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取最底级行政区划
	 * 获取json对象内的street属性
	 * */
	public static String supportGetAddressDetail(JSONObject jsonObject){
		String result = "";
		if(ObjectUtil.isNotBlank(jsonObject)){
			if(ObjectUtil.isNotBlank(jsonObject.get("street"))){
				result = jsonObject.getString("street");
			}
		}
		return result;
	}
	
	/**
	 * 获取json对象内的id值
	 * */
	public static String supportGetJsonOid(JSONObject jsonObject){
		String id = "";
		if(ObjectUtil.isNotBlank(jsonObject)){
			if(ObjectUtil.isNotBlank(jsonObject.get("_id"))){
				JSONObject jo = JSONObject.parseObject(jsonObject.getString("_id"));
				if(ObjectUtil.isNotBlank(jo)){
					id = jo.getString("$oid");
				}
			}
		}
		return id;
	}
	
	/**
	 * 将id转换成json对象
	 * */
	public static JSONObject supportIdTo_id(String supportid){
		JSONObject oidJson = new JSONObject();
		oidJson.put("$oid", supportid);
		JSONObject idJson = new JSONObject();
		idJson.put("_id", oidJson);
		return idJson;
	}

	public static JSONObject supportSetPage(Map<String,Object> resultWrap) {
		JSONObject pageJO = new JSONObject();
		if(ObjectUtil.isNotBlank(resultWrap.get("currentPage")) && ObjectUtil.isNotBlank(resultWrap.get("pageSize"))){
			Integer currentPage = Integer.parseInt(resultWrap.get("currentPage").toString());//当前页
			Integer pageSize = Integer.parseInt(resultWrap.get("pageSize").toString());//每页显示条数
			pageJO.put("limit", pageSize);
			pageJO.put("skip", (currentPage-1)*pageSize);
		}
		return pageJO;
	}
	
	public static String getSupportUpdateDate(Object obj) {
		String returnStr = "";
		if(ObjectUtil.isBlank(obj)){
			return returnStr;
		}			
		if (obj instanceof String) {
			String temp = obj.toString();
			JSONObject jsonObj = JSONObject.parseObject(temp);			
			Long  updatedOn = jsonObj.getJSONObject("updatedOn").getLong("$numberLong");			
//			Date date = new Date(updatedOn);
//			returnStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			returnStr = updatedOn.toString();
		}
		return returnStr;
	}
}
