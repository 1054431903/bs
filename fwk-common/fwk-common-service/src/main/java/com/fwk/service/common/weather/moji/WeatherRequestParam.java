package com.fwk.service.common.weather.moji;

public class WeatherRequestParam {

	/**
	 * 经度
	 */
	private String lon;

	/**
	 * 纬度
	 */
	private String lat;

	/**
	 * 请求token
	 */
	private String token;
	
	/**
	 * 天气类别
	 */
	private String weatherCategorys;

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWeatherCategorys() {
		return weatherCategorys;
	}

	public void setWeatherCategorys(String weatherCategorys) {
		this.weatherCategorys = weatherCategorys;
	}
}
