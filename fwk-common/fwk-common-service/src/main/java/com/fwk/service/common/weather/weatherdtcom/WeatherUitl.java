package com.fwk.service.common.weather.weatherdtcom;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;

/**
 * 气象处理的工具类
 * 
 * @author wangjunwen
 *
 */
public class WeatherUitl {

	/**
	 * 将json数据赋值给有注解的对象
	 * 
	 * @param object
	 *            实体对象
	 * @param jsonObject
	 *            JSONObject对象
	 * @throws Exception
	 */
	public static void json2Object(Object object, JSONObject jsonObject) throws Exception {

		A8nWeatherCode a8nWeatherCode = null;

		Field[] fields = null;
		Method[] methods = null;
		for (Class<?> clazz = object.getClass(); Object.class != clazz; clazz = clazz.getSuperclass()) {
			fields = clazz.getDeclaredFields();
			methods = clazz.getDeclaredMethods();

			for (Field field : fields) {
				a8nWeatherCode = field.getDeclaredAnnotation(A8nWeatherCode.class);

				if (null != a8nWeatherCode) {
					for (Method method : methods) {
						if (method.getName().equals(("set" + ObjectUtil.toUpperCaseFirstLetter(field.getName())))) {
							method.invoke(object, jsonObject.get(a8nWeatherCode.code()));
							break;
						}
					}
				}
			}
		}

	}

}
