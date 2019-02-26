package com.fwk.service.common.support.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;

public class SupportFarmLandlUtil {

	/**
	 * 地块编码起始编号
	 */
	public static int FARM_LAND_CODE_START = 101;

	/**
	 * @Description: 封装支撑平台新增地块操作需要的数据
	 *
	 * @param requestMap
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午2:48:04
	 */
	public static JSONObject packAddDataToSupport(Map<String, Object> requestMap) {
		JSONObject addData = new JSONObject();
		packOtherFilterIdJson(requestMap, addData);
		packBaseJson(requestMap, addData);
		return addData;
	}
	
	public static List<JSONObject> packAddDataToSupport(List<Map<String, Object>> requestDatas) {
		List<JSONObject> addDatas = new ArrayList<JSONObject>();
		for (Map<String, Object> map : requestDatas) {
			JSONObject addData = new JSONObject();
			packOtherFilterIdJson(map, addData);
			packBaseJson(map, addData);
			addDatas.add(addData);
		}
		return addDatas;
	}

	/**
	 * @Description: 封装支撑平台删除地块操作需要的数据
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月9日 下午4:13:31
	 */
	public static JSONObject packDelDataToSupport(Map<String, Object> requestMap) {
		JSONObject delData = new JSONObject();
		packEditFilterIdJson(requestMap, delData);
		return delData;
	}
	
	/**
	 * @Description: 封装支撑平台删除地块坐标需要的数据
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年5月2日 下午4:21:22
	 */
	public static JSONObject packDelCoordsDataToSupport(Map<String, Object> requestMap) {
		JSONObject editData = new JSONObject();
		packEditFilterIdJson(requestMap, editData);
		JSONObject baseJson = new JSONObject();
		baseJson.put("lng", "");
		baseJson.put("lat", "");
		baseJson.put("gis", "");
		JSONObject setJson = new JSONObject();
		setJson.put("$set", baseJson);
		editData.put("update", setJson);
		return editData;
	}

	/**
	 * @Description: 封装支撑平台编辑地块操作需要的数据
	 *
	 * @param requestMap
	 * @return {"filter":{"_id":{"$oid":"xx"}},"update":{"$set":{"name":"xx"}}}
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月9日 下午4:03:12
	 */
	public static JSONObject packEditDataToSupport(Map<String, Object> requestMap) {
		JSONObject editData = new JSONObject();
		packEditFilterIdJson(requestMap, editData);
		JSONObject baseJson = new JSONObject();
		packBaseJson(requestMap, baseJson);
		JSONObject setJson = new JSONObject();
		setJson.put("$set", baseJson);
		editData.put("update", setJson);
		return editData;
	}

	/**
	 * @Description: 封装支撑平台地块查询的过滤条件
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月8日 下午5:22:39
	 */
	public static JSONObject packQueryFilterToSupport(Map<String, Object> requestMap) {
		JSONObject filterJson = new JSONObject();
		packOtherFilterIdJson(requestMap, filterJson);
		packBaseJson(requestMap, filterJson, true);
		return filterJson;
	}

	/**
	 * @Description: 封装支撑平台地块查询的过滤条件(过滤条件为id数组)
	 *
	 * @param ids
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 上午10:36:42
	 */
	public static JSONObject packQueryFilterToSupportByIds(List<String> ids) {
		JSONObject filterJson = new JSONObject();
		packOtherFilterIdsJson(ids, filterJson);
		return filterJson;
	}

	/**
	 * @Description: 封装分页信息
	 *
	 * @param requestMap
	 * @param requestJson
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月9日 下午3:59:14
	 */
	public static JSONObject packPageJson(Map<String, Object> requestMap) {
		JSONObject pageJson = new JSONObject();
		if (ObjectUtil.isNotBlank(requestMap.get("currentPage")) && ObjectUtil.isNotBlank(requestMap.get("pageSize"))) {
			int skip = ((int) requestMap.get("currentPage") - 1) * (int) requestMap.get("pageSize");
			int limit = (int) requestMap.get("pageSize");
			pageJson.fluentPut("skip", skip).fluentPut("limit", limit);
		}
		if (pageJson.isEmpty()) {
			return null;
		}
		return pageJson;
	}

	/**
	 * @Description: 封装排序字段信息 默认用[createdOn]字段，且为降序排序
	 *
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 下午5:31:42
	 */
	public static JSONObject packSortJson() {
		return new JSONObject().fluentPut("createdOn", -1);
	}
	
	/**
	 * @Description: 封装指定查询字段信息，默认查询全部
	 *
	 * @param projectionMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年4月13日 上午11:41:39
	 */
	public static JSONObject packProjectionJson(Map<String, Object> projectionMap) {
		JSONObject projectionJson = new JSONObject();
		if (projectionMap == null || projectionMap.isEmpty()) {
			return null;
		}
		for (String key : projectionMap.keySet()) {
			projectionJson.fluentPut(key, projectionMap.get(key));
		}
		return projectionJson;
	}

	/**
	 * @Description: 封装编辑操作时的ID过虑条件
	 *
	 * @param requestMap
	 * @param requestJson
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午2:51:54
	 */
	private static void packEditFilterIdJson(Map<String, Object> requestMap, JSONObject requestJson) {
		Object _id = "";
		if (ObjectUtil.isNotBlank(requestMap.get("supportid"))) {
			_id = requestMap.get("supportid");
		} else if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			_id = requestMap.get("id");
		}
		if (ObjectUtil.isNotBlank(_id)) {
			JSONObject filterjo = new JSONObject();
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", _id);
			filterjo.put("_id", oidjo);
			requestJson.put("filter", filterjo);
		}
	}

	/**
	 * @Description: 封装除编辑操作以外的ID过虑条件
	 *
	 * @param requestMap
	 * @param requestJson
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月8日 下午1:34:36
	 */
	private static void packOtherFilterIdJson(Map<String, Object> requestMap, JSONObject requestJson) {
		Object _id = "";
		if (ObjectUtil.isNotBlank(requestMap.get("supportid"))) {
			_id = requestMap.get("supportid");
		} else if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			_id = requestMap.get("id");
		}
		if (ObjectUtil.isNotBlank(_id)) {
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", _id);
			requestJson.put("_id", oidjo);
		}
	}

	/**
	 * @Description: 封装除编辑操作以外的ID过虑条件,过滤条件为id数组
	 *               如：{"_id":{"$in":[{"$oid":"xxx"},{"$oid":"xxx"}]}}
	 * @param ids
	 * @param requestJson
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 上午10:35:24
	 */
	private static void packOtherFilterIdsJson(List<String> ids, JSONObject requestJson) {
		List<JSONObject> oidList = ids.stream().map(id -> {
			return new JSONObject().fluentPut("$oid", id);
		}).collect(Collectors.toList());
		requestJson.put("_id", new JSONObject().fluentPut("$in", oidList));
	}

	/**
	 * @Description: 封装基础属性
	 *
	 * @param requestMap
	 * @param requestJson
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月9日 下午3:43:46
	 */
	private static void packBaseJson(Map<String, Object> requestMap, JSONObject requestJson) {
		packBaseJson(requestMap, requestJson, false);
	}

	/**
	 * @Description: 封装基础属性
	 *
	 * @param requestMap
	 * @param requestJson(返回值)
	 * @param isQuery(查询时为true)
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月7日 下午2:47:11
	 */
	private static void packBaseJson(Map<String, Object> requestMap, JSONObject requestJson, boolean isQuery) {
		// 地块编号
		if (ObjectUtil.isNotBlank(requestMap.get("parcelno"))) {
			if (isQuery) {
				JSONObject regex = new JSONObject();
				regex.put("$regex", requestMap.get("parcelno"));
				requestJson.put("code", regex);
			} else {
				requestJson.put("code", requestMap.get("parcelno"));
			}
		}
		// 地块名称
		if (ObjectUtil.isNotBlank(requestMap.get("name"))) {
			if (isQuery) {
				JSONObject regex = new JSONObject();
				regex.put("$regex", requestMap.get("name"));
				requestJson.put("name", regex);
			} else {
				requestJson.put("name", requestMap.get("name"));
			}
		}
		// 组织机构
		if (ObjectUtil.isNotBlank(requestMap.get("orgid"))) {
			JSONObject jo = new JSONObject();
			jo.put("$oid", requestMap.get("orgid"));
			requestJson.put("tenant", jo);
		}
		// 面积
		if (ObjectUtil.isNotBlank(requestMap.get("area"))) {
			requestJson.put("acreage", requestMap.get("area"));
		}
		// 海拔
		if (ObjectUtil.isNotBlank(requestMap.get("elevation"))) {
			requestJson.put("alti", requestMap.get("elevation"));
		}
		// 地址(省、市、县(区)、镇(乡)、村及详细地址)
		if (ObjectUtil.isNotBlank(requestMap.get("address"))) {
			if (isQuery) {
				JSONObject address = (JSONObject) requestMap.get("address");
				if (ObjectUtil.isNotBlank(address.get("province"))) {
					JSONObject province = new JSONObject();
					province.put("$oid", address.get("province"));
					requestJson.put("address.province", province);
				}
				if (ObjectUtil.isNotBlank(address.get("city"))) {
					JSONObject city = new JSONObject();
					city.put("$oid", address.get("city"));
					requestJson.put("address.city", city);
				}
				if (ObjectUtil.isNotBlank(address.get("district"))) {
					JSONObject district = new JSONObject();
					district.put("$oid", address.get("district"));
					requestJson.put("address.district", district);
				}
				if (ObjectUtil.isNotBlank(address.get("town"))) {
					JSONObject town = new JSONObject();
					town.put("$oid", address.get("town"));
					requestJson.put("address.town", town);
				}
				if (ObjectUtil.isNotBlank(address.get("village"))) {
					JSONObject village = new JSONObject();
					village.put("$oid", address.get("village"));
					requestJson.put("address.village", village);
				}
			} else {
				JSONObject address = (JSONObject) requestMap.get("address");
				JSONObject jo = new JSONObject();
				if (ObjectUtil.isNotBlank(address.get("province"))) {
					JSONObject province = new JSONObject();
					province.put("$oid", address.get("province"));
					jo.put("province", province);
				} else {
					jo.put("province", null);
				}
				if (ObjectUtil.isNotBlank(address.get("city"))) {
					JSONObject city = new JSONObject();
					city.put("$oid", address.get("city"));
					jo.put("city", city);
				} else {
					jo.put("city", null);
				}
				if (ObjectUtil.isNotBlank(address.get("district"))) {
					JSONObject district = new JSONObject();
					district.put("$oid", address.get("district"));
					jo.put("district", district);
				} else {
					jo.put("district", null);
				}
				if (ObjectUtil.isNotBlank(address.get("town"))) {
					JSONObject town = new JSONObject();
					town.put("$oid", address.get("town"));
					jo.put("town", town);
				} else {
					jo.put("town", null);
				}
				if (ObjectUtil.isNotBlank(address.get("village"))) {
					JSONObject village = new JSONObject();
					village.put("$oid", address.get("village"));
					jo.put("village", village);
				} else {
					jo.put("village", null);
				}
				if (!jo.isEmpty()) {
					requestJson.put("address", jo);
				}
			}
		}
		// 详细地址
		if (ObjectUtil.isNotBlank(requestMap.get("addressdetail"))) {
			if (isQuery) {
				JSONObject regex = new JSONObject();
				regex.put("$regex", requestMap.get("addressdetail"));
				requestJson.put("address.street", regex);
			} else {
				if (ObjectUtil.isNotBlank(requestJson.get("address"))) {
					JSONObject address = requestJson.getJSONObject("address");
					address.put("street", requestMap.get("addressdetail"));
					requestJson.put("address", address);
				} else {
					JSONObject street = new JSONObject();
					street.put("street", requestMap.get("addressdetail"));
					requestJson.put("address", street);
				}
			}

		}
		// 状态
		if (ObjectUtil.isNotBlank(requestMap.get("status"))) {
			requestJson.put("status", requestMap.get("status"));
		}
		// 经度
		if (ObjectUtil.isNotBlank(requestMap.get("longitude"))) {
			requestJson.put("lng", requestMap.get("longitude"));
		}
		// 纬度
		if (ObjectUtil.isNotBlank(requestMap.get("latitude"))) {
			requestJson.put("lat", requestMap.get("latitude"));
		}
		// 品类列表(List<String>类型)
		if (ObjectUtil.isNotBlank(requestMap.get("categoryid"))) {
			List<String> categoryIds = Arrays.asList(requestMap.get("categoryid").toString().split(","));
			List<JSONObject> categoryList = new ArrayList<>();
			for (String id : categoryIds) {
				JSONObject oid = new JSONObject();
				oid.put("$oid", id);
				categoryList.add(oid);
			}
			if (isQuery) {
				requestJson.put("category", new JSONObject().fluentPut("$in", categoryList));
			} else {
				requestJson.put("category", categoryList);
			}
		}
		// 删除状态
		if (ObjectUtil.isNotBlank(requestMap.get("_removed"))) {
			requestJson.put("_removed", requestMap.get("_removed"));
		}
		// 地块区域坐标
		if (ObjectUtil.isNotBlank(requestMap.get("coords"))) {
			JSONArray coords = (JSONArray) requestMap.get("coords");
			List<JSONObject> gis = coords.stream().map(coord -> {
				JSONObject coordJson = (JSONObject) JSON.toJSON(coord);
				JSONObject point = new JSONObject();
				point.put("lng", coordJson.get("longitude"));
				point.put("lat", coordJson.get("latitude"));
				return point;
			}).collect(Collectors.toList());
			requestJson.put("gis", gis);
		}
	}

	/**
	 * @Description: 把支持平台查询地块的结果集转换农事系统地块的实体map
	 *
	 * @param jsonObject
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月12日 下午11:11:39
	 */
	public static Map<String, Object> supportResultToMap(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<>();
		if (ObjectUtil.isNotBlank(jsonObject) || !jsonObject.isEmpty()) {
			// 支撑平台ID
			if (ObjectUtil.isNotBlank(jsonObject.getJSONObject("_id"))) {
				resultMap.put("supportid", jsonObject.getJSONObject("_id").get("$oid"));
			}
			if (ObjectUtil.isNotBlank(jsonObject.get("code"))) {
				resultMap.put("parcelno", jsonObject.get("code"));
			}
			// 状态
			if (ObjectUtil.isNotBlank(jsonObject.get("status"))) {
				resultMap.put("status", jsonObject.get("status"));
			}
			// 地块名称
			if (ObjectUtil.isNotBlank(jsonObject.get("name"))) {
				resultMap.put("name", jsonObject.get("name"));
			}
			// 面积
			if (ObjectUtil.isNotBlank(jsonObject.get("acreage"))) {
				resultMap.put("area", jsonObject.get("acreage"));
			}
			// 经度
			if (ObjectUtil.isNotBlank(jsonObject.get("lng"))) {
				resultMap.put("longitude", jsonObject.get("lng"));
			}
			// 纬度
			if (ObjectUtil.isNotBlank(jsonObject.get("lat"))) {
				resultMap.put("latitude", jsonObject.get("lat"));
			}
			// 组织
			if (ObjectUtil.isNotBlank(jsonObject.getJSONObject("tenant"))) {
				resultMap.put("orgid", jsonObject.getJSONObject("tenant").get("$oid"));
			}
			// 海拔
			if (ObjectUtil.isNotBlank(jsonObject.get("alti"))) {
				resultMap.put("elevation", jsonObject.get("alti"));
			}
			// 品类列表(逗号分隔的字符串)
			if (ObjectUtil.isNotBlank(jsonObject.getJSONArray("category"))) {
				JSONArray category = jsonObject.getJSONArray("category");
				List<String> categoryList = new ArrayList<>();
				for (Object object : category) {
					categoryList.add(((JSONObject) object).getString("$oid"));
				}
				resultMap.put("categoryid", StringUtils.join(categoryList, ","));
			}
			// 地址
			if (ObjectUtil.isNotBlank(jsonObject.get("address"))) {
				JSONObject address = jsonObject.getJSONObject("address");
				// 省
				if (ObjectUtil.isNotBlank(address.get("province"))) {
					resultMap.put("addresscode", address.getJSONObject("province").get("$oid"));
					resultMap.put("provincecode", address.getJSONObject("province").get("$oid"));
				}
				// 市
				if (ObjectUtil.isNotBlank(address.get("city"))) {
					resultMap.put("addresscode", address.getJSONObject("city").get("$oid"));
					resultMap.put("citycode", address.getJSONObject("city").get("$oid"));
				}
				// 区县
				if (ObjectUtil.isNotBlank(address.get("district"))) {
					resultMap.put("addresscode", address.getJSONObject("district").get("$oid"));
					resultMap.put("countycode", address.getJSONObject("district").get("$oid"));
				}
				// 乡镇
				if (ObjectUtil.isNotBlank(address.get("town"))) {
					resultMap.put("addresscode", address.getJSONObject("town").get("$oid"));
					resultMap.put("towncode", address.getJSONObject("town").get("$oid"));
				}
				// 村
				if (ObjectUtil.isNotBlank(address.get("village"))) {
					resultMap.put("addresscode", address.getJSONObject("village").get("$oid"));
					resultMap.put("villagecode", address.getJSONObject("village").get("$oid"));
				}
				// 详细地址
				if (ObjectUtil.isNotBlank(address.get("street"))) {
					resultMap.put("addressdetail", address.get("street"));
				}
			}
			// 创建时间
			if (ObjectUtil.isNotBlank(jsonObject.get("createdOn"))) {
				resultMap.put("creattime", jsonObject.getJSONObject("createdOn").getLong("$numberLong"));
			}
			// 更新时间
			if (ObjectUtil.isNotBlank(jsonObject.get("updatedOn"))) {
				resultMap.put("edittime", jsonObject.getJSONObject("updatedOn").getLong("$numberLong"));
			}
			// 地块区域坐标
			if (ObjectUtil.isNotBlank(jsonObject.get("gis"))) {
				JSONArray gis = jsonObject.getJSONArray("gis");
				List<JSONObject> coords = gis.stream().map(coord -> {
					JSONObject coordJson = (JSONObject) JSON.toJSON(coord);
					JSONObject point = new JSONObject();
					point.put("longitude", coordJson.get("lng"));
					point.put("latitude", coordJson.get("lat"));
					return point;
				}).collect(Collectors.toList());
				resultMap.put("coords", coords);
			}
		}
		return resultMap;
	}

	/**
	 * 获取支撑平台添加或修改地块后返回的Long类型的时间
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static Date getSupportResultOfCreateOrUpdate(JSONObject jsonObject) {
		if (jsonObject.containsKey("createdOn")) {
			return new Date(jsonObject.getJSONObject("createdOn").getLong("$numberLong"));
		}
		if (jsonObject.containsKey("updatedOn")) {
			return new Date(jsonObject.getJSONObject("updatedOn").getLong("$numberLong"));
		}
		return null;
	}
}
