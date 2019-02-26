package com.fwk.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("rawtypes")
public class ObjectUtil extends ObjectUtils {

	public static String SPLIT_CHAR = ",";
	public static String REPORT_DEVICE_TYPES = "1,2,4,9,11,17";

	// public static String formatID(){
	// for(int i=1;i<=1000;i++){
	// String newStr = "YY"+String.format("%04d",i);
	// System.out.println(newStr);
	// }
	// }

	public static boolean isNotBlank(Object obj) {
		if (null == obj) {
			return false;
		}
		if ("".equals(obj.toString().trim())) {
			return false;
		}
		//2018年5月16日修改bugid为8076，null字符串为合法
		// if ("null".equals(obj.toString().trim())) {
		// return false;
		// }
		return true;
	}

	public static boolean isBlank(Object obj) {
		return !isNotBlank(obj);
	}

	public static String toString(Object obj) {
		if (isNotBlank(obj)) {
			return obj.toString();
		}
		return "";
	}

	public static String defaultBlank(Object src, String plan) {
		if (isNotBlank(src)) {
			return toString(src);
		}
		return toString(plan);
	}

	public static Object defaultNull(Object src, Object plan) {
		return src != null ? src : plan;
	}

	public static boolean equals(Object obj, Object obj1) {
		return toString(obj).equals(toString(obj1));
	}

	public static List<String> parseStr(String str, String splitchar) {
		List<String> strList = new ArrayList<String>();
		if (ObjectUtil.isNotBlank(str)) {
			if (!ObjectUtil.isNotBlank(splitchar)) {
				splitchar = "[^0-9a-zA-Z]";
			}
			String[] strings = str.trim().split(splitchar.trim());
			for (String strs : strings) {
				if (ObjectUtil.isNotBlank(strs)) {
					strList.add(strs.trim());
				}
			}
			return strList;
		}
		return strList;
	}

	/**
	 * @param vals
	 * @return
	 */
	public static Collection parseList(Object vals) {
		if (ObjectUtil.isNotBlank(vals)) {
			if (vals instanceof String) {
				return parseStr(ObjectUtil.toString(vals), null);
			}
			if (vals instanceof Object[]) {
				return Arrays.asList(vals);
			}
			if (vals instanceof Collection && ((Collection) vals).size() > 0) {
				return (Collection) vals;
			}
		} else {
			return new ArrayList();
		}
		return Arrays.asList(vals);
	}

	public static Integer parseInteger(Object obj) {
		if (isNotBlank(obj)) {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 解析路径参数
	 * 
	 * @param baseurl
	 * @param requestUri
	 * @return
	 */
	public static Map<String, Object> parsePathVals(String baseurl, String requestUri) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (ObjectUtil.isNotBlank(baseurl) && ObjectUtil.isNotBlank(requestUri)) {
			String[] baseNames = baseurl.trim().substring(baseurl.indexOf("/gstar/") + "/gstar/".length())
					.replaceAll("[{}]", "").split("[/]");
			String[] vals = requestUri.trim().substring(baseurl.indexOf("/gstar/") + "/gstar/".length()).split("[/]");
			if (baseNames.length == vals.length) {
				for (int i = 0; i < baseNames.length; i++) {
					paramsMap.put(baseNames[i], vals[i]);
				}
			}
		}
		return paramsMap;
	}

	/**
	 * 去掉首尾分割符
	 * 
	 * @param src
	 * @param splitchar
	 * @return
	 */
	public static String trimHeadTailChar(String src, String splitchar) {
		if (ObjectUtil.isNotBlank(src)) {
			String newSrc = src.trim();
			if (newSrc.startsWith(splitchar)) {
				newSrc = newSrc.substring(1);
			}
			if (newSrc.endsWith(splitchar)) {
				newSrc = newSrc.substring(0, (newSrc.length() - 1));
			}
			return newSrc;
		}

		return src;
	}

	/**
	 * 获取随机码
	 * 
	 * @param length:随机码长度
	 */
	public static String getRandNum(Integer length) {
		return (Math.random() * 1000000000 + "").replace(".", "").subSequence(0, length) + "";
	}

	/**
	 * 更新对象的值
	 * 
	 * <pre>
	 * 	<b>温馨提示：方法中引用了 apache 的 commons-beanutils 包，在调用时请注意是否已经导入了该 jar 包。</b>
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2017年8月30日 下午4:35:07
	 *
	 * @param src
	 *            从该对象中取值
	 * @param target
	 *            更新到该对象中
	 * @param fieldNameSet
	 *            要更新的字段
	 * @author wangjunwen
	 * @return
	 * @date 2017年8月30日 下午5:16:56
	 *
	 * @return 如果目标对象的属性值被更新，那么返回 true，否则返回 false
	 * @throws Exception
	 */
	public static boolean updateObject(final Object src, final Object target, Set<String> fieldNameSet)
			throws Exception {

		if ((null == src) || (null == target)) {
			return false;
		} else if ((null == fieldNameSet) || (fieldNameSet.isEmpty())) {
			return false;
		}

		// 从该对象中取值
		JSONObject srcJSONObject = JSONObject.parseObject(JSONObject.toJSONString(src));

		Iterator<Entry<String, Object>> iterator = srcJSONObject.entrySet().iterator();
		Entry<String, Object> entry = null;

		boolean isUpdated = false;// 目标对象的属性值是否有被更新
		while (iterator.hasNext()) {
			entry = iterator.next();
			if (fieldNameSet.contains(entry.getKey())) {
				isUpdated = true;
				// 把原来的值替换掉
				BeanUtils.copyProperty(target, entry.getKey(), entry.getValue());
			}
		}

		return isUpdated;
	}

	/**
	 * 将 null 值或者空字符串的元素过滤掉
	 * 
	 * @author wangjunwen
	 * @date 2017年8月31日 下午2:51:03
	 *
	 * @param map
	 *            java.util.Map对象
	 */
	public static void filterNullValue(Map<String, Object> map) {
		if ((null == map) || (map.isEmpty())) {
			return;
		}

		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			if (isBlank(iterator.next().getValue())) {
				iterator.remove();
			}
		}
	}

	/**
	 * 获取行政区划的前缀，将后面的0全部去掉
	 */
	public static String getPositionCodeProfix(String code) {
		if (isNotBlank(code)) {
			while (code.length() > 0) {
				if (code.substring(code.length() - 1, code.length()).equals("0")) {
					code = code.substring(0, code.length() - 1);
				} else {
					break;
				}
			}
		}
		return code;
	}

	/**
	 * 将字符串的首字母大写
	 * 
	 * @param src
	 * @return
	 */
	public static String toUpperCaseFirstLetter(String src) {
		if ((null == src) || (src.isEmpty())) {
			return null;
		} else {
			String first = src.substring(0, 1);

			return (src.replaceFirst(first, first.toUpperCase()));
		}
	}

	/**
	 * 将对象转换为字符串，并去掉两端的空格
	 * 
	 * @author wangjunwen
	 * @date 2018年4月9日 上午10:12:56
	 *
	 * @param obj
	 * @return
	 */
	public static String toStringAndTrim(Object obj) {
		if (isNotBlank(obj)) {
			return obj.toString().trim();
		}
		return "";
	}

	/**
	 * 将字符串转换为数值列表
	 * 
	 * <pre>
	 * 	如果是浮点型转换为整型，那么小数部分会将忽略掉。
	 * 		例如：123.45 转换为整型，那么结果为：123
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年4月20日 下午1:53:48
	 *
	 * @param src
	 *            源字符串（例如："123.12,186,7542,9481"）
	 * @param clazz
	 *            要转换成的数值类型
	 * @param separator
	 *            分隔符
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List convertToNumberList(String src, Class<? extends Number> clazz, String... separator) {

		if (ObjectUtil.isBlank(src) || (null == clazz)) {
			return null;
		}

		// 默认的分隔符
		final String defaultSeparator = ",";

		String[] strs = null;// 最终分隔后的字符串数组

		if ((null != separator) && (separator.length > 0)) {
			for (String str : separator) {
				strs = ObjectUtil.toString(src).split(str);

				if ((null != strs) && (strs.length > 0)) {
					break;
				}
			}
		} else {
			strs = ObjectUtil.toString(src).split(defaultSeparator);
		}

		final List list = new ArrayList();

		for (String strNum : strs) {
			try {
				if (strNum.matches("\\d+(\\.\\d+)?")) {
					if (clazz.getName().endsWith(Double.class.getName())) {
						list.add(Double.valueOf(strNum));
					} else if (clazz.getName().endsWith(Float.class.getName())) {
						list.add(Float.valueOf(strNum));
					} else {

						if (strNum.matches("\\d+(\\.\\d+)")) {
							strNum = strNum.substring(0, strNum.indexOf("."));
						}

						if (clazz.getName().endsWith(Integer.class.getName())) {
							list.add(Integer.valueOf(strNum));
						} else if (clazz.getName().equals(Long.class.getName())) {
							list.add(Long.valueOf(strNum));
						} else if (clazz.getName().endsWith(Short.class.getName())) {
							list.add(Short.valueOf(strNum));
						} else if (clazz.getName().endsWith(Byte.class.getName())) {
							list.add(Byte.valueOf(strNum));
						}
					}
				}
			} catch (Exception e) {
				// 忽略异常
			}

		}

		return list;
	}

	/**
	 * List<String>去重
	 * 
	 * @param List<String>
	 */
	public static void cutRepetListString(List<String> listStr) {
		if (isNotBlank(listStr) && listStr.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < listStr.size(); i++) {
				String str = listStr.get(i);
				if (sb.toString().contains(str)) {
					listStr.remove(i);
				} else {
					sb.append(str + ",");
				}
			}
			sb = null;
		}
	}

}
