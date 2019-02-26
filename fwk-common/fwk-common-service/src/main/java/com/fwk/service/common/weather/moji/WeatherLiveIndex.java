package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 生活指数-基础信息
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月16日 下午3:11:30
 */
public class WeatherLiveIndex {

	/**
	 * 指数代码
	 */
	private String code;

	/**
	 * 日期
	 */
	private String day;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 等级
	 */
	private String level;

	/**
	 * 指数名称
	 */
	private String name;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 更新时间
	 */
	private String updatetime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
