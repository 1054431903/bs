package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 城市信息
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月15日 下午1:38:23
 */
public class WeatherCity {

	/**
	 * 城市ID
	 */
	private String cityId;

	/**
	 * 国家名称
	 */
	private String counname;

	/**
	 * 城市名称
	 */
	private String name;

	/**
	 * 上级区域名称
	 */
	private String pname;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCounname() {
		return counname;
	}

	public void setCounname(String counname) {
		this.counname = counname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}
