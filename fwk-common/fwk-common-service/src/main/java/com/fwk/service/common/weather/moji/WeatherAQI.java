package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 空气质量指数
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月16日 下午3:09:57
 */
public class WeatherAQI {

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 一氧化碳指数
	 */
	private String co;

	/**
	 * 一氧化碳浓度
	 */
	private String coC;

	/**
	 * 二氧化氮指数
	 */
	private String no2;

	/**
	 * 二氧化氮浓度
	 */
	private String no2C;

	/**
	 * 臭氧指数
	 */
	private String o3;

	/**
	 * 臭氧浓度
	 */
	private String o3C;

	/**
	 * PM10指数
	 */
	private String pm10;

	/**
	 * PM10浓度
	 */
	private String pm10C;

	/**
	 * PM2.5指数
	 */
	private String pm25;

	/**
	 * PM2.5浓度
	 */
	private String pm25C;

	/**
	 * 发布时间戳
	 */
	private String pubtime;

	/**
	 * 全国排名
	 */
	private String rank;

	/**
	 * 二氧化硫
	 */
	private String so2;

	/**
	 * 二氧化硫浓度
	 */
	private String so2C;

	/**
	 * 空气质量指数值
	 */
	private String value;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCoC() {
		return coC;
	}

	public void setCoC(String coC) {
		this.coC = coC;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getNo2C() {
		return no2C;
	}

	public void setNo2C(String no2c) {
		no2C = no2c;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getO3C() {
		return o3C;
	}

	public void setO3C(String o3c) {
		o3C = o3c;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getPm10C() {
		return pm10C;
	}

	public void setPm10C(String pm10c) {
		pm10C = pm10c;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm25C() {
		return pm25C;
	}

	public void setPm25C(String pm25c) {
		pm25C = pm25c;
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public String getSo2C() {
		return so2C;
	}

	public void setSo2C(String so2c) {
		so2C = so2c;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
