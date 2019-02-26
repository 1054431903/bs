package com.fwk.service.common.weather.weatherdtcom;

import com.fwk.common.util.DateTimeUtil;

/**
 * 天气预报
 * 
 * @author wangjunwen
 *
 */
@A8nWeatherCategoryCode(code = OutBaseWeatherForecast.categoryCode)
public class OutBaseWeatherForecast {

	/** 气象数据大的类别 */
	public static final String categoryCode = "1001001";

	/** 000 预报时间 20160115080000~20160115200000 */
	@A8nWeatherCode(code = "000")
	private String forecastDate;

	/** 星期几（在调用 setForecastDate 方法时自动赋值） */
	private String forecastWeek;

	/** 001 天气现象编码 53 */
	@A8nWeatherCode(code = "001")
	private String weatherCode;

	/** 气象名称（在调用 setWeatherCode 方法时自动赋值） */
	private String weatherName;

	/** 002 该时间内的最高温（单位摄氏度） -5 */
	@A8nWeatherCode(code = "002")
	private String maxTemperature;

	/** 003 该时间内的最低温（单位摄氏度） 0 */
	@A8nWeatherCode(code = "003")
	private String minTemperature;

	/** 004 风力编码（转码） 0 */
	@A8nWeatherCode(code = "004")
	private String windPowerCode;

	/** 风力（在调用 setWindPowerCode 方法时自动赋值） */
	private String windPower;

	/** 005 风向编码 （转码） */
	@A8nWeatherCode(code = "005")
	private String windDirectionCode;

	/** 风向（在调用 setWindDirectionCode 方法时自动赋值） */
	private String windDirection;

	public String getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(String forecastDate) {
		if ((null != forecastDate)) {
			if (forecastDate.indexOf("~") > 0) {
				this.setForecastWeek(DateTimeUtil.getWeek(forecastDate.split("~")[0], "yyyyMMddHHmmss"));
			} else {
				this.setForecastWeek(DateTimeUtil.getWeek(forecastDate, "yyyyMMddHHmmss"));
			}
		}

		this.forecastDate = forecastDate;
	}

	public String getForecastWeek() {
		return forecastWeek;
	}

	public void setForecastWeek(String forecastWeek) {
		this.forecastWeek = forecastWeek;
	}

	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		// 根据天气现象的编号获取天气现象的名称
		this.setWeatherName(WeatherElementConfig.getWeatherPhenomenonByCode(weatherCode));
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

	public String getWindPowerCode() {
		return windPowerCode;
	}

	public void setWindPowerCode(String windPowerCode) {
		// 根据风力编号获取风力的名称
		this.setWindPower(WeatherElementConfig.getWindPowerByCode(windPowerCode));
		this.windPowerCode = windPowerCode;
	}

	public String getWindPower() {
		return windPower;
	}

	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}

	public String getWindDirectionCode() {
		return windDirectionCode;
	}

	public void setWindDirectionCode(String windDirectionCode) {
		// 根据风向编号获取风向的名称
		this.setWindDirection(WeatherElementConfig.getWindDirectionByCode(windDirectionCode));
		this.windDirectionCode = windDirectionCode;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public static String getCategorycode() {
		return categoryCode;
	}

}
