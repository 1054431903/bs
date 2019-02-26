package com.fwk.service.common.weather.weatherdtcom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户已订购的天气数据（元数据缓存）
 * 
 * @author wangjunwen
 *
 */
public class OrderElementCache {

	private static final Map<String, OrderElement> orderElementMap = new HashMap<String, OrderElement>();

	/** 用户已经订阅的气象数据类别 */
	private static final List<WeatherDataCategory> userWeatherDataCategoryList = new ArrayList<WeatherDataCategory>();
	/** 用户已经订阅的气象数据类别 */
	private static WeatherDataCategory[] userWeatherDataCategorys = null;

	static {
		try {
			// 数据来源于：product_12904.csv
			List<OrderElement> list = WeatherElementsUtil.getOrderElementList();

			for (OrderElement obj : list) {
				orderElementMap.put(obj.getTypeNameEN(), obj);

				for (WeatherDataCategory type : WeatherDataCategory.values()) {
					if (type.name().equals(obj.getTypeNameEN())) {
						userWeatherDataCategoryList.add(type);
						break;
					}
				}
			}

			// 不允许在外部修改元数据
			Collections.unmodifiableMap(orderElementMap);
			Collections.unmodifiableList(userWeatherDataCategoryList);

			userWeatherDataCategorys = userWeatherDataCategoryList.toArray(new WeatherDataCategory[] {});

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 获取用户已经订阅的气象数据类别
	 * 
	 * @return
	 */
	public static List<WeatherDataCategory> getUserWeatherDataCategoryList() {
		return userWeatherDataCategoryList;
	}

	/**
	 * 获取用户已经订阅的气象数据类别
	 * 
	 * @return
	 */
	public static WeatherDataCategory[] getUserWeatherDataCategorys() {
		return userWeatherDataCategorys;
	}

	/**
	 * 根据订阅的气象类别名称获取气象类别信息
	 * 
	 * @param typeNameEn
	 * @return
	 */
	public static OrderElement getOrderElement(String typeNameEn) {
		return orderElementMap.get(typeNameEn);
	}

}
