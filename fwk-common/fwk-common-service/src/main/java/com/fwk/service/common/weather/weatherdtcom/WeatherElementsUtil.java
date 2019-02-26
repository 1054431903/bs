package com.fwk.service.common.weather.weatherdtcom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 天气要素
 * 
 * @author wangjunwen
 *
 */
public class WeatherElementsUtil {
	/** 从文件读取的元数据（天气要素） */
	private static final List<String> weatherElementsList = new ArrayList<String>();
	private static final List<OrderElement> orderElementList = new ArrayList<OrderElement>();

	static {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("www_weatherdt_com/product_12904.csv");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			while (bufferedReader.ready()) {
				weatherElementsList.add(bufferedReader.readLine());
			}

			// 初始化订阅的气象要素
			initOrderWeatherElement();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * 初始化订阅的气象要素
	 * 
	 * @throws Exception
	 */
	private static void initOrderWeatherElement() throws Exception {
		String temp = null;

		Set<String> elementsSet = new HashSet<String>();

		for (int i = 1; i < weatherElementsList.size(); i++) {
			temp = weatherElementsList.get(i);

			elementsSet.add(temp.substring((temp.indexOf(",") + 1)));
		}

		OrderElement tempOrderElement = null;

		String[] elements = null;
		for (String elementStr : elementsSet) {
			elements = elementStr.split(",");

			tempOrderElement = new OrderElement();

			// 数据大类（简体中文名）
			tempOrderElement.setTypeNameZhCn(elements[0]);
			// 数据大类（英文名）
			tempOrderElement.setTypeNameEN(elements[1]);
			// 频次
			tempOrderElement.setFrequency(elements[2]);
			// 预报时长
			tempOrderElement.setDuration(elements[3]);

			orderElementList.add(tempOrderElement);
		}

		Collections.unmodifiableList(orderElementList);

	}

	/**
	 * 获取用户已订购的天气要素
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午8:58:12
	 *
	 * @return
	 */
	public static List<OrderElement> getOrderElementList() {
		return orderElementList;
	}

}
