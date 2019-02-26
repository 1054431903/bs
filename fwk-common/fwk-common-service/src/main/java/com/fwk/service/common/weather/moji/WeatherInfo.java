package com.fwk.service.common.weather.moji;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 天气信息
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月15日 下午1:30:00
 */
public class WeatherInfo {

	/**
	 * 城市信息
	 */
	@JSONField(name = "city")
	private WeatherCity weatherCity;

	/**
	 * 实况天气
	 */
	@JSONField(name = "condition")
	private WeatherCondition weatherCondition;

	/**
	 * 未来15天天气预报
	 */
	@JSONField(name = "forecast")
	private List<WeatherForecastByDay> weatherForecast15Days;

	/**
	 * 天气预警
	 */
	@JSONField(name = "alert")
	private List<WeatherAlert> weatherAlert;

	/**
	 * 未来24小时天气预报
	 */
	@JSONField(name = "hourly")
	private List<WeatherForecastByHour> weatherForecast24Hours;

	/**
	 * 空气质量指数
	 */
	@JSONField(name = "aqi")
	private WeatherAQI weatherAQI;

	/**
	 * 生活指数
	 */
	@JSONField(name = "liveIndex", serialize = false)
	private Map<String, List<WeatherLiveIndex>> weatherLiveIndex;

	/**
	 * 墨迹天气请求响应结果信息
	 */
	@JSONField(name = "rc")
	private ResponseInfo rc;

	public WeatherCity getWeatherCity() {
		return weatherCity;
	}

	public void setWeatherCity(WeatherCity weatherCity) {
		this.weatherCity = weatherCity;
	}

	public WeatherCondition getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(WeatherCondition weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public List<WeatherForecastByDay> getWeatherForecast15Days() {
		return weatherForecast15Days;
	}

	public void setWeatherForecast15Days(List<WeatherForecastByDay> weatherForecast15Days) {
		this.weatherForecast15Days = weatherForecast15Days;
	}

	public List<WeatherAlert> getWeatherAlert() {
		return weatherAlert;
	}

	public void setWeatherAlert(List<WeatherAlert> weatherAlert) {
		this.weatherAlert = weatherAlert;
	}

	public List<WeatherForecastByHour> getWeatherForecast24Hours() {
		return weatherForecast24Hours;
	}

	public void setWeatherForecast24Hours(List<WeatherForecastByHour> weatherForecast24Hours) {
		this.weatherForecast24Hours = weatherForecast24Hours;
	}

	public WeatherAQI getWeatherAQI() {
		return weatherAQI;
	}

	public void setWeatherAQI(WeatherAQI weatherAQI) {
		this.weatherAQI = weatherAQI;
	}

	public Map<String, List<WeatherLiveIndex>> getWeatherLiveIndex() {
		return weatherLiveIndex;
	}

	public void setWeatherLiveIndex(Map<String, List<WeatherLiveIndex>> weatherLiveIndex) {
		this.weatherLiveIndex = weatherLiveIndex;
	}

	public ResponseInfo getRc() {
		return rc;
	}

	public void setRc(ResponseInfo rc) {
		this.rc = rc;
	}

	@JSONField(name = "liveIndex", deserialize = false)
	public List<WeatherLiveIndex> getWeatherLiveIndexValue() {
		if (!CollectionUtils.isEmpty(this.weatherLiveIndex)) {
			for (List<WeatherLiveIndex> weatherLiveIndexList : this.weatherLiveIndex.values()) {
				return weatherLiveIndexList;
			}
		}
		return null;
	}
}
