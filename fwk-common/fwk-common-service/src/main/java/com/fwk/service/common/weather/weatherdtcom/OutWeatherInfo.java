package com.fwk.service.common.weather.weatherdtcom;

import java.util.List;

/**
 * 天气信息
 * 
 * @author wangjunwen
 *
 */
public class OutWeatherInfo {
	// 天气实况
	private OutWeatherObserve weatherObserve;
	// 天气预警信息
	private OutWeatherAlarm weatherAlarm;
	// 未来几天的天气预报
	private OutFutureDaysWeather futureDaysWeather;
	// 未来24小时的天气预报
	private List<OutBaseWeatherForecast> future24HWeather;
	// 天气预报的基础信息（当天的。例如：最高温度、最低温度、气象（阴/晴/雨/……））
	private OutBaseWeather baseWeather;

	public OutWeatherObserve getWeatherObserve() {
		return weatherObserve;
	}

	public void setWeatherObserve(OutWeatherObserve weatherObserve) {
		this.weatherObserve = weatherObserve;
	}

	public OutWeatherAlarm getWeatherAlarm() {
		return weatherAlarm;
	}

	public void setWeatherAlarm(OutWeatherAlarm weatherAlarm) {
		this.weatherAlarm = weatherAlarm;
	}

	public OutFutureDaysWeather getFutureDaysWeather() {
		return futureDaysWeather;
	}

	public void setFutureDaysWeather(OutFutureDaysWeather futureDaysWeather) {
		this.futureDaysWeather = futureDaysWeather;
	}

	public List<OutBaseWeatherForecast> getFuture24HWeather() {
		return future24HWeather;
	}

	public void setFuture24HWeather(List<OutBaseWeatherForecast> future24hWeather) {
		future24HWeather = future24hWeather;
	}

	public OutBaseWeather getBaseWeather() {
		return baseWeather;
	}

	public void setBaseWeather(OutBaseWeather baseWeather) {
		this.baseWeather = baseWeather;
	}

}
