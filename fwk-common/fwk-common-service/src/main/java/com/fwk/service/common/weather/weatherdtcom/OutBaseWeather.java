package com.fwk.service.common.weather.weatherdtcom;

/**
 * 天气预报的基础信息
 * 
 * @author wangjunwen
 *
 */
@A8nWeatherCategoryCode(code = "")
public class OutBaseWeather {

	// 气象编码（阴/雨/晴）
	@A8nWeatherCode(code = "")
	private String weatherCode;

	// 气象名称（阴/雨/晴）
	@A8nWeatherCode(code = "")
	private String weatherName;

	// 最高温度
	@A8nWeatherCode(code = "")
	private String maxTemperature;

	// 最低温度
	@A8nWeatherCode(code = "")
	private String minTemperature;

	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}

	public String getWeatherName() {
		return weatherName;
	}

	public void setWeatherName(String weatherName) {
		this.weatherName = weatherName;
	}

	public String getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(String maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(String minTemperature) {
		this.minTemperature = minTemperature;
	}

}
