package com.fwk.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequestUtil {

	private static Object parseRequestJson(String json) {
		return JSON.parse(json);
	}

	public static Map<String, Object> setPageInfo(Map<String, Object> requestParamMap) {
		if (null != requestParamMap) {

			Object currentPage = requestParamMap.get("currentPage");

			if (null == currentPage) {
				requestParamMap.put("currentPage", 1);
			} else {
				try {
					requestParamMap.put("currentPage", Integer.parseInt(currentPage.toString()));
				} catch (Exception e) {
					requestParamMap.put("currentPage", 1);
				}
			}

			Object pageSize = requestParamMap.get("pageSize");

			if (null == pageSize) {
				requestParamMap.put("pageSize", DataTablePage.PAGESIZE);
			} else {
				try {
					requestParamMap.put("pageSize", Integer.parseInt(pageSize.toString()));
				} catch (Exception e) {
					requestParamMap.put("pageSize", DataTablePage.PAGESIZE);
				}
			}
		}
		return requestParamMap;
	}

	/**
	 * 将request json [{},{}] 组织成Map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> getParamMap(String json) {
		Object requestParam = parseRequestJson(json);
		// 解析Map传参
		if (requestParam instanceof Map) {
			Map requestParamMap = (Map) requestParam;
			return setPageInfo(requestParamMap);
		}

		// 解析List<Map> 传参格式 [{"name":"xxxx","value":"valxxx"},{}]
		List<Map<String, Object>> listMap = null;
		if (requestParam instanceof List) {
			List objList = (List) requestParam;
			if (objList.size() > 0) {
				if (objList.get(0) instanceof Map) {
					listMap = (List<Map<String, Object>>) requestParam;
				}
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null != listMap && listMap.size() > 0) {
			for (Map<String, Object> map : listMap) {
				String paramName = (String) map.get("name");
				Object paramVal = map.get("value");
				if (paramVal != null && (paramVal instanceof String)) {
					paramVal = ((String) paramVal).trim();
				}
				Object paramMapVal = paramMap.get(paramName);
				if (paramMapVal == null) {
					paramMap.put(paramName, paramVal);
				} else {
					if (paramMapVal instanceof ArrayList) {
						((ArrayList) (paramMapVal)).add(paramVal);
					} else {
						ArrayList valList = new ArrayList();
						valList.add(paramMapVal);
						valList.add(paramVal);
						paramMap.put(paramName, valList);
					}
				}
			}
		}

		return setPageInfo(paramMap);
	}

	/**
	 * 获取客户端的原始请求参数
	 * 
	 * <pre>
	 * 将原始请求的 request json [{},{}] 组织成Map
	 * </pre>
	 * 
	 * @param json
	 *            字符串
	 * @return
	 */
	public static Map<String, Object> getOriginalRequestParamMap(String json) {
		Object requestParam = parseRequestJson(json);

		// 解析Map传参
		if (requestParam instanceof Map) {
			Map requestParamMap = (Map) requestParam;
			return requestParamMap;
		}

		// 解析List<Map> 传参格式 [{"name":"xxxx","value":"valxxx"},{}]
		List<Map<String, Object>> listMap = null;

		if (requestParam instanceof List) {
			List objList = (List) requestParam;
			if (objList.size() > 0) {
				if (objList.get(0) instanceof Map) {
					listMap = (List<Map<String, Object>>) requestParam;
				}
			}
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (null != listMap && listMap.size() > 0) {
			for (Map<String, Object> map : listMap) {
				String paramName = (String) map.get("name");
				Object paramVal = map.get("value");
				if (paramVal != null && (paramVal instanceof String)) {
					paramVal = ((String) paramVal).trim();
				}
				Object paramMapVal = paramMap.get(paramName);
				if (paramMapVal == null) {
					paramMap.put(paramName, paramVal);
				} else {
					if (paramMapVal instanceof ArrayList) {
						((ArrayList) (paramMapVal)).add(paramVal);
					} else {
						ArrayList valList = new ArrayList();
						valList.add(paramMapVal);
						valList.add(paramVal);
						paramMap.put(paramName, valList);
					}
				}
			}
		}

		return paramMap;
	}

	/**
	 * 获取DataTable插件传递过来的分页参数
	 * 
	 * @param requestParam
	 * @return
	 */
	public static DataTablePage getDataTablePage(Map<String, Object> requestParam) {
		DataTablePage pageBean = new DataTablePage();
		Object pageSize = requestParam.get("pageSize");
		Object currentPage = requestParam.get("currentPage");

		if (null != pageSize) {
			pageBean.setPageSize((Integer) pageSize);
		}
		if (currentPage != null) {
			try {
				pageBean.setCurrentPage((Integer) currentPage);
			} catch (Exception e) {
				pageBean.setCurrentPage(1);
			}
		}
		return pageBean;
	}

	/**
	 * 
	 * @param requestParam
	 *            前台获取的对象要更新的字段信息
	 * @param retrieveObj
	 *            根据ID从数据库取到的对象
	 * @return 返回对象的最终要更新至的状态
	 */
	public static Map<String, Object> getUpdateEntityByRequestParam(Map<String, Object> requestParam,
			Object retrieveObj) {
		Map<String, Object> newObjMap = new HashMap<String, Object>();
		if (retrieveObj != null && requestParam != null) {
			Class objCls = retrieveObj.getClass();
			Method[] methods = objCls.getMethods();
			if (methods != null && methods.length > 0) {
				for (Method method : methods) {
					String methodName = method.getName();
					if (methodName.startsWith("get") && (methodName.indexOf("Class") == -1)) {
						Integer length = method.getParameterTypes().length;
						if (length == 0) {
							String propertyName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
							Object requestVal = requestParam.get(propertyName);
							try {
								newObjMap.put(propertyName,
										requestVal != null ? requestVal : method.invoke(retrieveObj));
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			Object newObj = JSON.parseObject(JSON.toJSONStringWithDateFormat(newObjMap,
					DateTimeUtil.DEFAULT_DATETIME_FORMAT, SerializerFeature.WriteDateUseDateFormat), objCls);
			return JSON.parseObject(JSON.toJSONStringWithDateFormat(newObj, DateTimeUtil.DEFAULT_DATETIME_FORMAT,
					SerializerFeature.WriteDateUseDateFormat), Map.class);
		}
		return newObjMap;
	}

	public static Map<String, Object> getValidSqlParamMap(Map<String, Object> requestParam, Class entityClass) {
		Object validObj = JSON.parseObject(JSON.toJSONStringWithDateFormat(requestParam,
				DateTimeUtil.DEFAULT_DATETIME_FORMAT, SerializerFeature.WriteDateUseDateFormat), entityClass);
		return JSON.parseObject(JSON.toJSONStringWithDateFormat(validObj, DateTimeUtil.DEFAULT_DATETIME_FORMAT,
				SerializerFeature.WriteDateUseDateFormat), Map.class);
	}

}
