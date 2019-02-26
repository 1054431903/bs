package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 天气预警信息
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月15日 下午12:28:41
 */
public class WeatherAlert {

	/**
	 * 预警内容
	 */
	private String content;
	
	/**
	 * 预警id
	 */
	private String infoid;
	
	/**
	 * 预警等级
	 */
	private String level;
	
	/**
	 * 预警名称
	 */
	private String name;
	
	/**
	 * 发布时间
	 */
	private String pub_time;
	
	/**
	 * 更新时间
	 */
	private String update_time;
	
	/**
	 * 预警标题
	 */
	private String title;
	
	/**
	 * 预警类型
	 */
	private String type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
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

	public String getPub_time() {
		return pub_time;
	}

	public void setPub_time(String pub_time) {
		this.pub_time = pub_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
