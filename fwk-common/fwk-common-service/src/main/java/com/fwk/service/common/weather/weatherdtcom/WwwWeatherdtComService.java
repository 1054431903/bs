package com.fwk.service.common.weather.weatherdtcom;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ApiOkClientFactory;
import com.fwk.common.util.DateTimeUtil;

/**
 * 中国气象数据服务
 * 
 * @author wangjunwen
 *
 */
@Service
public class WwwWeatherdtComService {

	/**
	 * 获取天气信息
	 * 
	 * @param pMeteorologicalStation
	 *            站点信息（站点编号/站点地址（二选一））
	 * @param weatherDataCategoryList
	 *            需要获取的天气数据类型（实况/预警/预报）
	 * @throws Exception
	 */
	public OutWeatherInfo getWeather(MeteorologicalStation pMeteorologicalStation,
			WeatherDataCategory... pWeatherDataCategorys) throws Exception {

		if (null == pMeteorologicalStation) {
			return null;
		}

		// 如果不指定查询哪个类型的天气信息，那么默认查询所有的天气信息
		if ((null == pWeatherDataCategorys) || (0 == pWeatherDataCategorys.length)) {
			pWeatherDataCategorys = OrderElementCache.getUserWeatherDataCategorys();
		}

		// 查询的数据类型
		final StringBuffer typeBuffer = new StringBuffer();
		for (int i = 0; i < pWeatherDataCategorys.length; i++) {
			if (i > 0) {
				typeBuffer.append("|");
			}

			typeBuffer.append(pWeatherDataCategorys[i].name());
		}

		MeteorologicalStation tempMeteorologicalStation = null;

		// 如果用户设置了站点编号，那么优先使用站点编号，否则再通过省、市、区县、乡镇等信息获取相应的站点编号
		if (null != pMeteorologicalStation.getStationId()) {
			tempMeteorologicalStation = pMeteorologicalStation;
		} else {
			tempMeteorologicalStation = MeteorologicalStationUtil.getStationByArea(
					pMeteorologicalStation.getAreaProvince(), pMeteorologicalStation.getAreaCity(),
					pMeteorologicalStation.getAreaCounty(), pMeteorologicalStation.getAreaTown());

			if (null == tempMeteorologicalStation) {
				return null;
			}
		}

		// http://api.weatherdt.com/common/?area=站号&type=数据大类[数据小类]&key=密钥
		String apiURL = ApiConfig.getApiURL();

		apiURL = MessageFormat.format(apiURL, tempMeteorologicalStation.getStationId(), typeBuffer.toString(),
				ApiConfig.getApiKey());

		String jsonText = ApiOkClientFactory.doGet(apiURL);

		OutWeatherInfo outWeatherInfo = new OutWeatherInfo();

		this.setAttrFromJson(tempMeteorologicalStation, outWeatherInfo, pWeatherDataCategorys, jsonText);

		return outWeatherInfo;
	}

	/**
	 * 将 JSONObject 对象转换为实体对象
	 * 
	 * @param pOutWeatherInfo
	 *            气象输出对象
	 * @param pWeatherDataCategorys
	 *            气象数据类别
	 * @param jsonText
	 *            json字符串
	 * @throws Exception
	 */
	private void setAttrFromJson(final MeteorologicalStation pMeteorologicalStation, OutWeatherInfo pOutWeatherInfo,
			WeatherDataCategory[] pWeatherDataCategorys, String jsonText) throws Exception {

		if ((null == pMeteorologicalStation) || (null == pWeatherDataCategorys)
				|| (0 == pWeatherDataCategorys.length)) {
			return;
		}

		// 示例数据{"alarm":{"101290108":{"1001003":{}}},"forecast":{"12h":{"101290108":{"000":"201801170800","1001001":[{"000":"20180117080000~20180117200000","001":"00","002":"17","003":"6","004":"0","005":"0"},{"000":"20180117200000~20180118080000","001":"00","002":"11","003":"6","004":"0","005":"0"},{"000":"20180118080000~20180118200000","001":"00","002":"17","003":"5","004":"0","005":"0"},{"000":"20180118200000~20180119080000","001":"00","002":"11","003":"5","004":"0","005":"0"},{"000":"20180119080000~20180119200000","001":"00","002":"15","003":"6","004":"0","005":"0"},{"000":"20180119200000~20180120080000","001":"00","002":"10","003":"5","004":"0","005":"0"},{"000":"20180120080000~20180120200000","001":"00","002":"18","003":"7","004":"0","005":"0"},{"000":"20180120200000~20180121080000","001":"00","002":"11","003":"7","004":"0","005":"0"}]}}},"observe":{"101290108":{"1001002":{"000":"09:10","002":"8","003":"2","004":"5","005":"82","006":"0.0","007":"802"}}}}
		JSONObject jsonObject = JSONObject.parseObject(jsonText);

		if ((null == jsonObject)) {
			return;
		}

		for (WeatherDataCategory type : pWeatherDataCategorys) {

			// 设置天气预警信息
			if (WeatherDataCategory.alarm.equals(type)) {

				JSONObject alarmJSONObject = jsonObject.getJSONObject(type.name());

				if ((null != alarmJSONObject) && (alarmJSONObject.containsKey(pMeteorologicalStation.getStationId()))) {
					alarmJSONObject = alarmJSONObject.getJSONObject(pMeteorologicalStation.getStationId());
				}

				if ((null != alarmJSONObject) && (alarmJSONObject.containsKey(OutWeatherAlarm.categoryCode))) {
					// 有天气预警信息的情况下JSON格式为：
					// {"1001003":[{"011":"10129-20180223152000-0501.html","012":"云南省发布大风蓝色预警","001":"云南省","002":"","003":"","004":"05","005":"大风","006":"01","007":"蓝色","008":"2018-02-23
					// 15:20","009":"云南省气象台2018年2月23日15时20分发布大风蓝色预警：预计未来6小时，丽江、大理、楚雄、昆明、曲靖、玉溪、红河北部、文山北部将受大风影响，平均风力可达6级，阵风7到9级，请注意防范。","010":"201802231520567782大风蓝色"}]}
					JSONArray alarmJSONArray = null;

					try {
						// 没有天气预警信息的情况下JSON格式为：{"1001003":{}}
						alarmJSONArray = alarmJSONObject.getJSONArray(OutWeatherAlarm.categoryCode);
					} catch (Exception e) {
						alarmJSONArray = null;
					}

					if ((null == alarmJSONArray) || (0 == alarmJSONArray.size())) {
						alarmJSONObject = null;
					} else {
						alarmJSONObject = alarmJSONArray.getJSONObject(0);
					}
				}

				if ((null != alarmJSONObject) && (alarmJSONObject.keySet().size() > 0)) {
					OutWeatherAlarm weatherAlarm = new OutWeatherAlarm();

					WeatherUitl.json2Object(weatherAlarm, alarmJSONObject);

					pOutWeatherInfo.setWeatherAlarm(weatherAlarm);
				}
			}

			// 设置天气实况信息
			if (WeatherDataCategory.observe.equals(type)) {

				JSONObject observeJSONObject = jsonObject.getJSONObject(type.name());

				if ((null != observeJSONObject)
						&& (observeJSONObject.containsKey(pMeteorologicalStation.getStationId()))) {
					observeJSONObject = observeJSONObject.getJSONObject(pMeteorologicalStation.getStationId());
				}

				if ((null != observeJSONObject) && (observeJSONObject.containsKey(OutWeatherObserve.categoryCode))) {
					observeJSONObject = observeJSONObject.getJSONObject(OutWeatherObserve.categoryCode);
				}

				if ((null != observeJSONObject) && (observeJSONObject.keySet().size() > 0)) {

					OutWeatherObserve weatherObserve = new OutWeatherObserve();

					WeatherUitl.json2Object(weatherObserve, observeJSONObject);

					pOutWeatherInfo.setWeatherObserve(weatherObserve);
				}
			}

			// 设置天气预报信息
			if (WeatherDataCategory.forecast.equals(type)) {

				OrderElement orderElement = OrderElementCache.getOrderElement(type.name());

				JSONObject forecastJSONObject = jsonObject.getJSONObject(orderElement.getTypeNameEN());

				if ((null != forecastJSONObject) && (forecastJSONObject.containsKey(orderElement.getFrequency()))) {
					forecastJSONObject = forecastJSONObject.getJSONObject(orderElement.getFrequency());
				}

				if ((null != forecastJSONObject)
						&& (forecastJSONObject.containsKey(pMeteorologicalStation.getStationId()))) {
					forecastJSONObject = forecastJSONObject.getJSONObject(pMeteorologicalStation.getStationId());
				}

				if ((null != forecastJSONObject)
						&& (forecastJSONObject.containsKey(OutBaseWeatherForecast.categoryCode))) {

					JSONArray forecastJSONArray = forecastJSONObject.getJSONArray(OutBaseWeatherForecast.categoryCode);

					if ((null != forecastJSONArray) && (forecastJSONArray.size() > 0)) {
						// 设置天气预报信息
						this.setWeatherForecast(pOutWeatherInfo, forecastJSONArray);
					}
				}

			}

		}

	}

	/**
	 * 将逐小时的天气预报对象转换为预报的基础信息
	 * 
	 * @param pOutBaseWeatherForecast
	 *            逐小时的天气预报对象
	 * @return
	 */
	private OutBaseWeather convertOutBaseWeatherForecast2OutBaseWeather(
			OutBaseWeatherForecast pOutBaseWeatherForecast) {
		OutBaseWeather outBaseWeather = new OutBaseWeather();
		outBaseWeather.setWeatherCode(pOutBaseWeatherForecast.getWeatherCode());// 气象编号
		outBaseWeather.setWeatherName(pOutBaseWeatherForecast.getWeatherName());// 气象名称
		outBaseWeather.setMaxTemperature(pOutBaseWeatherForecast.getMaxTemperature());// 最高温度
		outBaseWeather.setMinTemperature(pOutBaseWeatherForecast.getMinTemperature());// 最低温度

		return outBaseWeather;
	}

	/**
	 * 设置天气预报信息
	 * 
	 * @author wangjunwen
	 * @date 2018年2月23日 上午10:36:43
	 *
	 * @param pOutWeatherInfo
	 *            天气信息对象
	 * @param forecastJSONArray
	 *            天气预报的 JSON数组对象
	 * @throws Exception
	 */
	private void setWeatherForecast(OutWeatherInfo pOutWeatherInfo, JSONArray forecastJSONArray) throws Exception {

		if ((null == pOutWeatherInfo) || (null == forecastJSONArray) || (0 == forecastJSONArray.size())) {
			return;
		}

		List<OutBaseWeatherForecast> baseWeatherForecastList = new ArrayList<OutBaseWeatherForecast>();

		OutBaseWeatherForecast baseWeatherForecast = null;
		JSONObject baseWeatherForecastJSONObject = null;
		for (int i = 0; i < forecastJSONArray.size(); i++) {
			baseWeatherForecast = new OutBaseWeatherForecast();
			baseWeatherForecastJSONObject = forecastJSONArray.getJSONObject(i);

			if ((null != baseWeatherForecastJSONObject) && (baseWeatherForecastJSONObject.keySet().size() > 0)) {
				WeatherUitl.json2Object(baseWeatherForecast, baseWeatherForecastJSONObject);

				baseWeatherForecastList.add(baseWeatherForecast);
			}

		}

		// ====== 设置当天的天气情况 =============
		// 预报时间
		String forecastDate = null;

		final long now = Long.valueOf(DateTimeUtil.getNow("yyyyMMddHHmmss"));// 当前时间
		long start = 0;// 起始时间
		long end = 0;// 截止时间
		String[] rangeDate = null;// 日期范围

		OutBaseWeatherForecast tempOutBaseWeatherForecast = null;

		for (int i = 0; i < baseWeatherForecastList.size(); i++) {

			tempOutBaseWeatherForecast = baseWeatherForecastList.get(i);

			forecastDate = tempOutBaseWeatherForecast.getForecastDate();

			if ((null != forecastDate) && (forecastDate.indexOf("~") > 0)) {
				rangeDate = forecastDate.split("~");
				start = Long.valueOf(rangeDate[0]);// 起始时间
				end = Long.valueOf(rangeDate[1]);// 截至时间

			} else {
				start = Long.valueOf(forecastDate);// 起始时间

				if (i == (baseWeatherForecastList.size() - 1)) {
					i = baseWeatherForecastList.size();

					end = Long
							.valueOf(baseWeatherForecastList.get(baseWeatherForecastList.size() - 1).getForecastDate());// 截至时间
				} else {
					end = Long.valueOf(baseWeatherForecastList.get(i + 1).getForecastDate());// 截至时间
				}
			}

			if ((start < now) && (end > now)) {
				OutBaseWeather baseWeather = convertOutBaseWeatherForecast2OutBaseWeather(tempOutBaseWeatherForecast);

				pOutWeatherInfo.setBaseWeather(baseWeather);

				break;
			}
		}

		// ========= 设置未来24小时的天气预报 ===============
		List<OutBaseWeatherForecast> future24hWeather = new ArrayList<OutBaseWeatherForecast>();
		pOutWeatherInfo.setFuture24HWeather(future24hWeather);

		for (OutBaseWeatherForecast obj : baseWeatherForecastList) {

			forecastDate = obj.getForecastDate();

			if ((null != forecastDate) && (forecastDate.indexOf("~") > 0)) {

				future24hWeather.addAll(baseWeatherForecastList.subList(0, 2));
				break;
			} else {
				future24hWeather.addAll(baseWeatherForecastList.subList(0, 24));
				break;
			}
		}

		// ========= 未来几天的天气预报 ====================
		List<String> weekList = new ArrayList<String>();// 星期
		List<String> weatherNameList = new ArrayList<String>();// 气象名称
		List<String> temperatureList = new ArrayList<String>();// 温度
		List<String> windDirectionList = new ArrayList<String>();// 风向
		List<String> windPowerList = new ArrayList<String>();// 风力

		for (int i = 0; i < baseWeatherForecastList.size(); i++) {

			tempOutBaseWeatherForecast = baseWeatherForecastList.get(i);

			forecastDate = tempOutBaseWeatherForecast.getForecastDate();

			if ((null != forecastDate) && (forecastDate.indexOf("~") > 0)) {

				int count = (baseWeatherForecastList.size() / 2);
				for (int j = 0; j < count; j++) {
					tempOutBaseWeatherForecast = ((start < now) && (now < end) ? baseWeatherForecastList.get(j * 2)
							: baseWeatherForecastList.get(j * 2 + 1));

					// 设置星期
					if (0 == j) {
						weekList.add("今天");
					} else if (1 == j) {
						weekList.add("明天");
					} else {
						weekList.add(tempOutBaseWeatherForecast.getForecastWeek());
					}

					// 设置气象名称
					weatherNameList.add(tempOutBaseWeatherForecast.getWeatherName());

					// 设置温度
					temperatureList.add(tempOutBaseWeatherForecast.getMaxTemperature() + "/"
							+ tempOutBaseWeatherForecast.getMinTemperature());

					// 设置风向
					windDirectionList.add(tempOutBaseWeatherForecast.getWindDirection());

					// 设置风力
					windPowerList.add(tempOutBaseWeatherForecast.getWindPower());

				}

				break;
			}
		}

		if (weekList.size() > 0) {
			OutFutureDaysWeather tempOutFutureDaysWeather = new OutFutureDaysWeather();
			tempOutFutureDaysWeather.setWeekList(weekList);// 星期
			tempOutFutureDaysWeather.setWeatherNameList(weatherNameList);// 气象名称
			tempOutFutureDaysWeather.setTemperatureList(temperatureList);// 温度
			tempOutFutureDaysWeather.setWindDirectionList(windDirectionList);// 风向
			tempOutFutureDaysWeather.setWindPowerList(windPowerList);// 风力

			pOutWeatherInfo.setFutureDaysWeather(tempOutFutureDaysWeather);
		}
	}

}
