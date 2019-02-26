package com.fwk.service.common.weather.weatherdtcom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 气象的站点
 * 
 * @author wangjunwen
 *
 */
public class MeteorologicalStationUtil {

	/** 从文件读取的元数据 */
	private static final List<String> stationList = new ArrayList<String>();

	/** 站点与行政区划地址的缓存 */
	private static final Map<String, String> stationMap = new HashMap<String, String>();

	/** 实体类的缓存 */
	private static final Map<String, MeteorologicalStation> meteorologicalStationMap = new HashMap<String, MeteorologicalStation>();

	static {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("www_weatherdt_com/nation_12904.csv");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			while (bufferedReader.ready()) {
				stationList.add(bufferedReader.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {

		}

		// 初始化站点与地理位置的关系
		initStationMap();

	}

	private static void initStationMap() {
		String temp = null;

		String stationId = null;// 站点编号
		String stationName = null;// 站点所在地
		String[] area = null;// 地区
		String airQuality = null;// 空气质量

		MeteorologicalStation tempMeteorologicalStation = null;

		for (int i = 1; i < stationList.size(); i++) {
			temp = stationList.get(i);

			stationId = temp.substring(0, temp.indexOf(","));

			stationName = temp.substring((stationId.length() + 1), temp.lastIndexOf(","));
			area = stationName.split(",");
			stationName = "";
			for (int j = (area.length - 1); j >= 0; j--) {
				stationName += area[j];
			}

			airQuality = temp.substring((temp.lastIndexOf(",") + 1));

			stationMap.put(stationId, stationName);

			// ============ 将实体类添加到缓存 ==========================
			tempMeteorologicalStation = new MeteorologicalStation();
			tempMeteorologicalStation.setStationId(stationId);
			tempMeteorologicalStation.setStationName(stationName);
			tempMeteorologicalStation.setAirQuality(airQuality);
			tempMeteorologicalStation.setAreaProvince(area[area.length - 1]);// 省
			tempMeteorologicalStation.setAreaCity(area[area.length - 2]);// 市
			tempMeteorologicalStation.setAreaCounty(area[area.length - 3]);// 区县
			tempMeteorologicalStation.setAreaTown(area[area.length - 4]);// 乡镇

			meteorologicalStationMap.put(tempMeteorologicalStation.getStationId(), tempMeteorologicalStation);
		}

	}

	public static String getStationById(String stationId) {
		return (stationMap.containsKey(stationId) ? stationId : null);
	}

	public static MeteorologicalStation getStation(String stationId) {
		return (meteorologicalStationMap.get(stationId));
	}

	/**
	 * 根据行政区划名称获取站点信息
	 * 
	 * @param areaProvince
	 *            省份
	 * @param areaCity
	 *            市
	 * @param areaCounty
	 *            区/县
	 * @return
	 * @throws Exception
	 */
	public static MeteorologicalStation getStationByArea(String areaProvince, String areaCity, String areaCounty)
			throws Exception {

		return getStationByArea(areaProvince, areaCity, areaCounty, "");
	}

	/**
	 * 根据行政区划名称获取站点信息
	 * 
	 * @param areaProvince
	 *            省份
	 * @param areaCity
	 *            市
	 * @param areaCounty
	 *            区/县
	 * @param areaTown
	 *            乡镇
	 * @return
	 * @throws Exception
	 */
	public static MeteorologicalStation getStationByArea(String areaProvince, String areaCity, String areaCounty,
			String areaTown) throws Exception {

		if (((null == areaProvince) || (areaProvince = areaProvince.trim()).isEmpty())
				|| ((null == areaCity) || (areaCity = areaCity.trim()).isEmpty())
				|| ((null == areaCounty) || (areaCounty = areaCounty.trim()).isEmpty())) {
			// 省级、地市级、区/县级不能为空
			throw new Exception("perameter areaProvince, areaCity and areaCounty may not be null or empty .");
		} else {
			// 乡镇级别可以为空
			areaTown = (((null == areaTown) || (areaTown = areaTown.trim()).isEmpty()) ? "" : areaTown);
		}

		Iterator<MeteorologicalStation> iterator = meteorologicalStationMap.values().iterator();

		MeteorologicalStation tempMeteorologicalStation = null;
		while (iterator.hasNext()) {
			tempMeteorologicalStation = iterator.next();

			// 地址只需匹配前两个字就行
			if ((areaProvince + "  ").substring(0, 2)
					.equals((tempMeteorologicalStation.getAreaProvince() + "  ").substring(0, 2))
					&& (areaCity + "  ").substring(0, 2)
							.equals((tempMeteorologicalStation.getAreaCity() + "  ").substring(0, 2))
					&& (areaCounty + "  ").substring(0, 2)
							.equals((tempMeteorologicalStation.getAreaCounty() + "  ").substring(0, 2))
					&& (areaTown + "  ").substring(0, 2)
							.equals((tempMeteorologicalStation.getAreaTown() + "  ").substring(0, 2))) {

				return tempMeteorologicalStation;
			}
		}

		return null;
	}

}
