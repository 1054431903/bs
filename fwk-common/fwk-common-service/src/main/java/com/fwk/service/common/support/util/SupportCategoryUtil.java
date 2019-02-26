package com.fwk.service.common.support.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.entity.CategoryEXT;

public class SupportCategoryUtil {

	/**
	 * @Description: 封装支撑平台的过滤条件jsonObject
	 *
	 * @param requestMap
	 * @return
	 * @return：返回过滤条件的jsonObject
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月6日 上午10:31:28
	 */
	public static JSONObject packageFilterJson(Map<String, Object> requestMap) {
		JSONObject filterJson = new JSONObject();
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			JSONObject oidJson = new JSONObject();
			oidJson.put("$oid", requestMap.get("id"));
			filterJson.put("_id", oidJson);
		}
		if (ObjectUtil.isNotBlank(requestMap.get("name"))) {
			JSONObject regJson = new JSONObject();
			regJson.put("$regex", requestMap.get("name"));
			filterJson.put("name", regJson);
		}
		return filterJson;
	}

	/**
	 * @Description: 封装支撑平台按品类编号列表查询的过虑条件
	 *
	 * @param categoryIdList
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月13日 下午2:07:54
	 */
	public static JSONObject packIdListFilterJson(List<String> categoryIdList){
		JSONObject filterJson = new JSONObject();
		List<JSONObject> inList = new ArrayList<>();
		for (String categoryId : categoryIdList) {
			inList.add(new JSONObject().fluentPut("$oid", categoryId));
		}
		filterJson.put("_id", new JSONObject().fluentPut("$in", inList));
		return filterJson;
	}
	
	/**
	 * @Description: 封装排序字段信息
	 * 	默认用[createdOn]字段，且为降序排序
	 *
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 下午5:31:42
	 */
	public static JSONObject packSortJson(){
		return new JSONObject().fluentPut("createdOn", -1);
	}
	
	/**
	 * @Description: 把支撑请求回来的品类结果映射为品类实体对象
	 *
	 * @param jsonObject
	 * @param catetoryEXT
	 * @return：返回品类实体对象 
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月6日 上午10:32:19
	 */
	public static void transJSONObjectToCategoryEXT(JSONObject jsonObject, CategoryEXT catetoryEXT) {
		//映射ID
		if (ObjectUtil.isNotBlank(jsonObject.get("_id"))) {
			JSONObject jsonObjectId = (JSONObject) jsonObject.get("_id");
			if (ObjectUtil.isNotBlank(jsonObjectId.get("$oid"))) {
				catetoryEXT.setId(jsonObjectId.getString("$oid"));
			}
		}
		//映射Level
		if (ObjectUtil.isNotBlank(jsonObject.get("level"))){
			catetoryEXT.setLevel(jsonObject.getIntValue("level"));
		}
		//映射name
		if (ObjectUtil.isNotBlank(jsonObject.get("name"))) {
			catetoryEXT.setName(jsonObject.getString("name"));
		}
		//映射status
		if (ObjectUtil.isNotBlank(jsonObject.get("status"))) {
			catetoryEXT.setStatus(jsonObject.getIntValue("status"));
		}
		//映射tags
		if (ObjectUtil.isNotBlank(jsonObject.get("tags")) && jsonObject.getJSONArray("tags").size() > 0) {
			List<String> tags = new ArrayList<>();
			JSONArray tagsArray = jsonObject.getJSONArray("tags");
			for (Object object : tagsArray) {
				tags.add(((JSONObject)object).getString("$oid"));
			}
			catetoryEXT.setTags(tags);
		}
		//映射createdOn
		if (ObjectUtil.isNotBlank(jsonObject.get("createdOn"))) {
			JSONObject obj = jsonObject.getJSONObject("createdOn");
			if (ObjectUtil.isNotBlank(obj.get("$numberLong"))) {
				catetoryEXT.setCreatedOn(new Date(obj.getLongValue("$numberLong")));
			}
		}
	}

}
