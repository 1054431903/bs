package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 实况天气信息
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月15日 下午12:34:13
 */
public class WeatherCondition {

	/**
	 * 实时天气
	 */
	private String condition;
	
	/**
	 * 实时天气id
	 */
	private String conditionId;
	
	/**
	 * 湿度
	 */
	private String humidity;
	
	/**
	 * 天气icon
	 */
	private String icon;
	
	/**
	 * 过去一小时累计降水量
	 */
	private String precipitation = "0";
	
	/**
	 * 气压(单位：hPa)
	 */
	private String pressure;
	
	/**
	 * 雨雪判断
	 */
	private String rain_snow_type;
	
	/**
	 * 降水强度
	 */
	private String rainfall_intensity;
	
	/**
	 * 体感温度
	 */
	private String realFeel;
	
	/**
	 * 日出时间
	 */
	private String sunRise;
	
	/**
	 * 日落时间
	 */
	private String sunSet;
	
	/**
	 * 温度
	 */
	private String temp;
	
	/**
	 * 一句话提示
	 */
	private String tips;
	
	/**
	 * 发布时间
	 */
	private String updatetime;
	
	/**
	 * 紫外线强度
	 */
	private String uvi;
	
	/**
	 * 风向
	 */
	private String windDir;
	
	/**
	 * 风级(单位：级,有可能出现【3-4】这种类型)
	 */
	private String windLevel;
	
	/**
	 * 风速
	 */
	private String windSpeed;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getRain_snow_type() {
		return rain_snow_type;
	}

	public void setRain_snow_type(String rain_snow_type) {
		this.rain_snow_type = rain_snow_type;
	}

	public String getRainfall_intensity() {
		return rainfall_intensity;
	}

	public void setRainfall_intensity(String rainfall_intensity) {
		this.rainfall_intensity = rainfall_intensity;
	}

	public String getRealFeel() {
		return realFeel;
	}

	public void setRealFeel(String realFeel) {
		this.realFeel = realFeel;
	}

	public String getSunRise() {
		return sunRise;
	}

	public void setSunRise(String sunRise) {
		this.sunRise = sunRise;
	}

	public String getSunSet() {
		return sunSet;
	}

	public void setSunSet(String sunSet) {
		this.sunSet = sunSet;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getUvi() {
		return uvi;
	}

	public void setUvi(String uvi) {
		this.uvi = uvi;
	}

	public String getWindDir() {
		return windDir;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public String getWindLevel() {
		return windLevel;
	}

	public void setWindLevel(String windLevel) {
		this.windLevel = windLevel;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
}
