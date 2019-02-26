package com.fwk.service.common.weather.moji;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 墨迹天气配置文件类
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月14日 下午5:23:58
 */
@Component
@PropertySource(value = {"classpath:weather_moji/api_config.properties"},encoding="utf-8")
@ConfigurationProperties(prefix="api.weather_moji")
public final class ApiConfig {
	
	private String key;
	
	private String url;
	
	private String headerName;
	
	private String headerValue;
	
	private List<String> weartherCategorys = new ArrayList<>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public List<String> getWeartherCategorys() {
		return weartherCategorys;
	}

	public void setWeartherCategorys(List<String> weartherCategorys) {
		this.weartherCategorys = weartherCategorys;
	}
}