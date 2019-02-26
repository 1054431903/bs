package com.fwk.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 页面传输及获取
 * 
 * @author xx
 */
public class HTMLUtil {

	private static String requestParam = "request-param";

	/** 公共请求参数：操作人编号（userid） */
	public static final String common_request_param_operateid = "operateid";

	/** 公共请求参数：上次调用接口的时间 */
	public static final String common_request_param_lastservicetime = "lastservicetime";

	/** 公共请求参数：当前用户是在哪个组织下登录的 */
	public static final String common_request_param_loginorgid = "loginorgid";

	// 获取前台传输的值
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getPageParams(HttpServletRequest request) {
		try {
			BaseAccess baseAccess = new BaseAccess();
			Map<String, Object> requestMap = new HashMap<String, Object>();
			Object requestMapObj = request.getAttribute(requestParam);
			if (requestMapObj == null) {// 第一次请求，request没有存页面传输参数，从新获取request页面参数
				requestMapObj = baseAccess.getParamMap(request);
				request.setAttribute(requestParam, requestMapObj);
			}
			requestMap = (Map<String, Object>) requestMapObj;
			return requestMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}

	// 返回值给前端
	public static void sendWrap(HttpServletResponse response, ResultWrap resultWrap, HttpServletRequest request) {
		try {
			request.setAttribute("returnContentToHtml", resultWrap);
			BaseAccess baseAccess = new BaseAccess();
			baseAccess.sendJson(response, resultWrap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 返回值给前端(自定义返回值的日期格式)
	public static void sendWrap(HttpServletResponse response, ResultWrap resultWrap, HttpServletRequest request, String customDateFormat) {
		try {
			request.setAttribute("returnContentToHtml", resultWrap);
			BaseAccess baseAccess = new BaseAccess();
			baseAccess.sendJson(response, resultWrap, customDateFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取默认返回值内容
	public static ResultWrap getDefaultWrap() {
		ResultWrap resultWrap = new ResultWrap();
		resultWrap.setActSuccess();
		resultWrap.setPage(resultWrap.getPage());
		return resultWrap;
	}

	// 初始化对象ResultWrap
	public static ResultWrap getResultWrap() {
		BaseAccess baseAccess = new BaseAccess();
		return baseAccess.getResultWrap();
	}

	// 初始化对象ResultWrap
	public static ResultWrap getInitResultWrapError() {
		BaseAccess baseAccess = new BaseAccess();
		return baseAccess.getInitResultWrap();
	}

	/**
	 * map对象转成对应的实体类
	 * 
	 * <pre>
	 * 	备注：返回类型变更为支持泛型
	 * </pre>
	 * 
	 * @author xx
	 * @author wangjunwen
	 * @date 2017-07-21 15:02:04(modify)
	 *
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T getMapToEntity(Class<T> clazz, Map<String, Object> map) {
		T obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			obj = clazz.newInstance();
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					try {
						Object value = map.get(propertyName);

						if ((null != value)) {

							Object[] args = new Object[1];
							Class<?> tCla = descriptor.getPropertyType();

							if (tCla.equals(String.class)) {// String

								value = value + "";

							} else if ((Integer.class.equals(tCla)) || (int.class.equals(tCla))) {// int

								value = Integer.parseInt(value + "");

							} else if ((Double.class.equals(tCla)) || ((double.class.equals(tCla)))) {// double

								value = Double.parseDouble(value + "");

							} else if ((Long.class.equals(tCla)) || (long.class.equals(tCla))) {// long

								value = Long.parseLong(value + "");

							} else if (BigDecimal.class.equals(tCla)) {// BigDecimal

								value = new BigDecimal(value + "");

							} else if (Date.class.equals(tCla)) {// Date

								if (value instanceof String) {
									value = DateTimeUtil.parse(value.toString());
								} else if (value.toString().matches("\\d+")) {
									value = new Date(Long.valueOf(value.toString()));
								}

							} else if ((Boolean.class.equals(tCla)) || (boolean.class.equals(tCla))) {// boolean

								if (value.toString().equals("1") || value.toString().equals("true")) {
									value = true;
								} else {
									value = false;
								}

							} else {
								// 对字段类型是数组的处理
								value = getArrayValue(descriptor, tCla, obj, value);
							}
							args[0] = value;
							descriptor.getWriteMethod().invoke(obj, args);
						}
					} catch (Exception e) {
						// e.printStackTrace();
						continue;
					}
				}
			}

			return obj;

		} catch (Exception e) {
			// e.printStackTrace();
		}

		return obj;

	}

	/**
	 * @Description: 将一个 JavaBean 对象转化为一个  Map 
	 *
	 * @param bean 要转化的JavaBean 对象 
	 * @return 转化出来的  Map 对象 
	 * @throws IntrospectionException 如果分析类属性失败 
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败 
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyq
	 * @date: 2018年5月11日 上午9:41:44
	 */
	public static Map<String, Object> getEntityToMap(Object bean)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (ObjectUtil.isBlank(bean )) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String propertyName  = property.getName();
			// 过滤class属性
			if (!propertyName .equals("class")) {
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				Object value = getter.invoke(bean, new Object[0]);
				if (value != null) {
					resultMap.put(propertyName , value);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 对字段类型是数组的处理
	 * 
	 * @author wangjunwen
	 * @date 2018年2月2日 下午1:32:18
	 *
	 * @param propertyDescriptor
	 *            字段的详细信息
	 * @param propertyType
	 *            字段的属性类型
	 * @param beanObject
	 *            要赋值的对象
	 * @param propertyValue
	 *            未经过处理的原始值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getArrayValue(final PropertyDescriptor propertyDescriptor, final Class<?> propertyType,
			final Object beanObject, final Object propertyValue) throws Exception {

		if ((null == propertyValue)) {
			return null;
		}

		JSONArray jsonArray = JSONArray.parseArray(propertyValue.toString().trim());

		if (0 == jsonArray.size()) {
			return null;
		}

		String[] values = propertyValue.toString().replaceAll("\\[|\\]", "").split(",");

		if (values.length < 1) {
			return null;
		} else {
			final List<String> finalValueList = new ArrayList<String>();

			for (String value : values) {
				value = (value.trim());// 去掉字符串两端的空格

				if ("null".equals(value)) {
					continue;
				} else {
					finalValueList.add(value);
				}
			}

			if (finalValueList.isEmpty()) {
				return null;
			} else {
				values = finalValueList.toArray(new String[finalValueList.size()]);
			}
		}

		if (String[].class.equals(propertyType)) {// String 数组

			return values;

		} else if ((Integer[].class.equals(propertyType)) || (int[].class.equals(propertyType))) {// Integer或者int数组

			if ((Integer[].class.equals(propertyType))) {// Integer 数组

				Integer[] valuesInteger = new Integer[values.length];
				for (int i = 0; i < values.length; i++) {
					valuesInteger[i] = Integer.parseInt(values[i]);
				}

				return valuesInteger;

			} else {// int 数组

				int[] valuesInt = new int[values.length];
				for (int i = 0; i < values.length; i++) {
					valuesInt[i] = Integer.parseInt(values[i]);
				}

				return valuesInt;
			}

		} else if ((Long[].class.equals(propertyType)) || (long[].class.equals(propertyType))) {// Long或者long数组

			if ((Long[].class.equals(propertyType))) {// Long 数组

				Long[] valuesLong = new Long[values.length];

				for (int i = 0; i < values.length; i++) {
					valuesLong[i] = Long.parseLong(values[i]);
				}

				return valuesLong;

			} else {// long 数组

				long[] valueslong = new long[values.length];

				for (int i = 0; i < values.length; i++) {
					valueslong[i] = Long.parseLong(values[i]);
				}

				return valueslong;
			}

		} else if ((Double[].class.equals(propertyType)) || (double[].class.equals(propertyType))) {// Double或者double数组

			if ((Double[].class.equals(propertyType))) {// Double 数组

				Double[] valuesDouble = new Double[values.length];

				for (int i = 0; i < values.length; i++) {
					valuesDouble[i] = Double.parseDouble(values[i]);
				}

				return valuesDouble;

			} else {// double 数组

				double[] valuesdouble = new double[values.length];

				for (int i = 0; i < values.length; i++) {
					valuesdouble[i] = Double.parseDouble(values[i]);
				}

				return valuesdouble;
			}

		} else if (java.util.Date[].class.equals(propertyType)) {// Date 数组
			Date[] valuesDate = new Date[values.length];

			for (int i = 0; i < values.length; i++) {
				valuesDate[i] = (DateTimeUtil.parse(values[i]));
			}

			return valuesDate;

		} else if (BigDecimal[].class.equals(propertyType)) {// BigDecimal 数组
			BigDecimal[] valuesBigDecimal = new BigDecimal[values.length];

			for (int i = 0; i < values.length; i++) {
				valuesBigDecimal[i] = (new BigDecimal(values[i]));
			}

			return valuesBigDecimal;

		} else if ((java.util.List.class.equals(propertyType)) || (java.util.ArrayList.class.equals(propertyType))) {// List

			// 获取 getter 方法返回值的类型
			Type genericReturnType = propertyDescriptor.getReadMethod().getGenericReturnType();

			if (("java.util.List<java.lang.String>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.lang.String>".equals(genericReturnType.getTypeName()))) {// List<Integer>

				List<String> stringList = new ArrayList<String>();

				for (int i = 0; i < values.length; i++) {
					stringList.add(values[i]);
				}

				return stringList;

			} else if (("java.util.List<java.lang.Integer>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.lang.Integer>".equals(genericReturnType.getTypeName()))) {// List<Integer>

				List<Integer> integerList = new ArrayList<Integer>();

				for (int i = 0; i < values.length; i++) {
					integerList.add(Integer.parseInt(values[i]));
				}

				return integerList;

			} else if (("java.util.List<java.lang.Long>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.lang.Long>".equals(genericReturnType.getTypeName()))) {// List<Long>

				List<Long> longList = new ArrayList<Long>();

				for (int i = 0; i < values.length; i++) {
					longList.add(Long.parseLong(values[i]));
				}

				return longList;

			} else if (("java.util.List<java.lang.Double>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.lang.Double>".equals(genericReturnType.getTypeName()))) {// List<Double>

				List<Double> doubleList = new ArrayList<Double>();

				for (int i = 0; i < values.length; i++) {
					doubleList.add(Double.parseDouble(values[i]));
				}

				return doubleList;

			} else if (("java.util.List<java.util.Date>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.util.Date>".equals(genericReturnType.getTypeName()))) {// List<Date>

				List<Date> dateList = new ArrayList<Date>();

				for (int i = 0; i < values.length; i++) {
					dateList.add(DateTimeUtil.parse(values[i]));
				}

				return dateList;

			} else if (("java.util.List<java.math.BigDecimal>".equals(genericReturnType.getTypeName()))
					|| ("java.util.ArrayList<java.math.BigDecimal>".equals(genericReturnType.getTypeName()))) {// List<BigDecimal>

				List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();

				for (int i = 0; i < values.length; i++) {
					bigDecimalList.add(new BigDecimal(values[i]));
				}

				return bigDecimalList;

			} else {// 自定义的类

				String calssName = null;// 自定义的类名

				int indexOfSubString = -1;

				final String returnTypeName = genericReturnType.getTypeName();
				final int returnTypeNameLength = genericReturnType.getTypeName().length();

				if (-1 != (indexOfSubString = returnTypeName.indexOf("java.util.List<"))) {

					indexOfSubString = "java.util.List<".length();

					calssName = (returnTypeName.substring(indexOfSubString, (returnTypeNameLength - 1)));

				} else if (-1 != (indexOfSubString = returnTypeName.indexOf("java.util.ArrayList<"))) {

					indexOfSubString = "java.util.ArrayList<".length();

					calssName = (returnTypeName.substring(indexOfSubString, (returnTypeNameLength - 1)));

				}

				if (null != calssName) {
					List beanList = new ArrayList();

					JSONObject jsonObject = null;
					for (int i = 0; i < jsonArray.size(); i++) {
						jsonObject = jsonArray.getJSONObject(i);

						if (!(jsonObject.keySet().isEmpty())) {
							beanList.add(getMapToEntity(Class.forName(calssName), jsonObject));
						}

					}

					return beanList;
				}

			}
		}

		return null;

	}

	/**
	 * 更新对象的值
	 * 
	 * @author wangjunwen
	 * @date 2017年8月30日 下午4:48:37
	 *
	 * @param src
	 *            从该对象中取值
	 * @param target
	 *            更新到该对象中
	 * @param paramMap
	 *            客户端传过来的参数
	 * @return 如果目标对象的属性值被更新，那么返回 true，否则返回 false
	 * @throws Exception
	 */
	public static boolean updateObject(final Object src, final Object target, final Map<String, Object> paramMap)
			throws Exception {
		if ((null == src) || (null == target) || (null == paramMap)) {
			return false;
		}

		Set<String> fieldNameSet = paramMap.keySet();
		return ObjectUtil.updateObject(src, target, fieldNameSet);
	}

	/**
	 * 重新设置不符合标准的日期时间的范围
	 * 
	 * <pre>
	 * 	如果不提供参数名称，那么默认起始时间的参数为“createdatestart”，结束时间的参数为“createdateend”。
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2017年9月19日 下午4:35:52
	 *
	 * @param paramMap
	 *            客户端传过来的参数
	 * @param paramDateRange
	 *            日期范围的参数名称，中间以竖线“|”分隔。
	 * 
	 *            <pre>
	 *            例如： 
	 *            	1、起始时间和结束时间都要重新设置，那么日期范围的参数名称的设置方法为： 起始日期的参数名称|截至日期的参数名称
	 *            	2、只设置起始日期，那么日期范围的参数名称的设置方法为： 起始日期的参数名称|空字符串
	 *            	3、只设置截至日期，那么日期范围的参数名称的设置方法为： 空字符串|截至日期的参数名称
	 *            </pre>
	 * 
	 *            <pre>
	 *            <code>
	 *            	Map<String, Object> paramMap = new HashMap<String, Object>();
	
					System.out.println("========== 使用默认的参数名称重置日期 ===========");
					paramMap.put("createdatestart", "2017-09-21");
					paramMap.put("createdateend", "2017-09-21");
			
					HTMLUtil.resetDateRange(paramMap);
			
					System.out.println(paramMap);
			
					System.out.println("========= 重置起始时间和截至时间 ============");
					paramMap.put("dateStart", "2016-08-16");
					paramMap.put("dateEnd", "2016-08-25");
			
					HTMLUtil.resetDateRange(paramMap, "dateStart|dateEnd");
			
					System.out.println(paramMap);
			
					System.out.println("========= 只重置截至时间 ============");
					paramMap.put("dateStart", "2016-08-16");
					paramMap.put("dateEnd", "2016-08-25");
			
					HTMLUtil.resetDateRange(paramMap, "|dateEnd");
			
					System.out.println(paramMap);
			
					System.out.println("======== 只重置起始时间 =============");
					paramMap.put("dateStart", "2016-08-16");
					paramMap.put("dateEnd", "2016-08-25");
			
					HTMLUtil.resetDateRange(paramMap, "dateStart|");
			
					System.out.println(paramMap);
			
					System.out.println("======== 重置多个日期范围 =============");
					paramMap.put("dateStart1", "2016-05-13");
					paramMap.put("dateEnd1", "2016-05-13");
			
					paramMap.put("dateStart2", "2015-02-15");
					paramMap.put("dateEnd2", "2015-02-15");
			
					paramMap.put("dateStart3", "2014-03-18");
					paramMap.put("dateEnd3", "2014-03-19");
			
					HTMLUtil.resetDateRange(paramMap, "dateStart1|dateEnd1", "dateStart2|dateEnd2", "dateStart3|dateEnd3");
			
					System.out.println(paramMap);
	 *            </code>
	 *            </pre>
	 */
	public static void resetDateRange(Map<String, Object> paramMap, String... paramDateRange) {
		if ((null == paramMap) || (paramMap.isEmpty())) {
			return;
		}

		if ((null == paramDateRange) || (0 == paramDateRange.length)) {
			// 参数名：开始时间
			final String param_createdatestart = "createdatestart";
			// 参数名：结束时间
			final String param_createdateend = "createdateend";

			// 重置开始时间
			String createdatestart = DateTimeUtil
					.resetDateRangeStart(ObjectUtil.toString(paramMap.get(param_createdatestart)).trim());

			paramMap.put(param_createdatestart, createdatestart);

			// 重置结束时间
			String createdateend = DateTimeUtil
					.resetDateRangeEnd(ObjectUtil.toString(paramMap.get(param_createdateend)).trim());

			paramMap.put(param_createdateend, createdateend);
		} else {
			String[] dateRange = null;

			String dateStart = "";
			String paramDateStart = "";

			String dateEnd = "";
			String paramDateEnd = "";

			for (String src : paramDateRange) {
				if ((null != src) && (src = src.trim()).length() > 0) {
					dateRange = src.split("\\|");

					if (2 == dateRange.length) {

						/* 重置开始时间 */
						paramDateStart = dateRange[0].trim();

						dateStart = DateTimeUtil
								.resetDateRangeStart(ObjectUtil.toString(paramMap.get(paramDateStart)).trim());

						if (null != dateStart) {
							paramMap.put(paramDateStart, dateStart);
						}

						/* 重置结束时间 */
						paramDateEnd = dateRange[1].trim();

						dateEnd = DateTimeUtil
								.resetDateRangeEnd(ObjectUtil.toString(paramMap.get(paramDateEnd)).trim());

						if (null != dateEnd) {
							paramMap.put(paramDateEnd, dateEnd);
						}
					}
				}
			}
		}

	}

	/**
	 * 去掉字符串两端的空格
	 * 
	 * @author wangjunwen
	 * @date 2017年9月20日 上午11:21:12
	 *
	 * @param paramMap
	 *            客户端的输入参数
	 */
	public static void trimByString(Map<String, Object> paramMap) {
		if ((null == paramMap) || (paramMap.isEmpty())) {
			return;
		}

		Entry<String, Object> entry = null;
		String key = null;
		Object value = null;

		for (Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
			entry = iterator.next();

			key = entry.getKey();
			value = entry.getValue();

			if (value instanceof String) {
				paramMap.put(key, (value.toString().trim()));
			}
		}
	}

	/**
	 * 判断是否是分页查询
	 * 
	 * @author wangjunwen
	 * @date 2018年1月20日 下午4:47:34
	 *
	 * @param request
	 * @return
	 */
	public static boolean isPaginationQuery(HttpServletRequest request) {
		final Map<String, Object> paramMap = getPageParams(request);

		return ("currentPageIIIIIIIIIIIIIIII".equals(paramMap.get("currentPageIIIIIIIIIIIIIIII")));
	}

	/**
	 * 过滤客户端输入的值
	 * 
	 * <pre>
	 * 	1. 过滤 null 或者 空白字符串
	 *  2. 将字符串两端的空格去掉
	 *  3. 重置查询时间段
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月24日 下午1:53:40
	 *
	 * @param map
	 */
	public static void filterClientInputValue(Map<String, Object> map) {
		// 重新设置查询时间的范围
		HTMLUtil.resetDateRange(map);

		// 去掉字符串两端的空格
		HTMLUtil.trimByString(map);

		// 将空值过滤掉
		ObjectUtil.filterNullValue(map);
	}
	
	/**
	 * @Description: 集合按指定大小分割成多个集合
	 *
	 * @param source  待分割集合
	 * @param length  分割长度
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年7月27日 上午9:02:22
	 */
	public static <T> List<List<T>> splitList(List<T> source, int length) {
		if (source == null || source.isEmpty() || length < 1) {
			return Collections.emptyList();
		}
		List<List<T>> result = new ArrayList<>();
		int size = source.size();
		int part = (size + length - 1) / length;
		for (int i = 0; i < part; i++) {
			List<T> subList = source.subList(i * length, ((i + 1) * length > size ? size : length * (i + 1)));
			result.add(subList);
		}
		return result;
	}

}
