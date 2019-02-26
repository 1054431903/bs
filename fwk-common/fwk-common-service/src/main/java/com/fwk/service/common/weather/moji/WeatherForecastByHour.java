package com.fwk.service.common.weather.moji;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 24小时天气预报
 * 
 * @author: zhangyuqiang
 * @date: 2018年3月15日 下午1:20:24
 */
public class WeatherForecastByHour {

	/**
	 * 天气状况
	 */
	private String condition;

	/**
	 * 预报日期
	 */
	private String date;

	/**
	 * 小时
	 */
	private String hour;

	/**
	 * 湿度
	 */
	private String humidity;

	/**
	 * 降冰量
	 */
	private String ice;

	/**
	 * 白天icon
	 */
	private String iconDay;

	/**
	 * 夜间icon
	 */
	private String iconNight;

	/**
	 * 降水概率
	 */
	private String pop;

	/**
	 * 气压
	 */
	private String pressure;

	/**
	 * 定量降水预报（mm）
	 */
	private String qpf;

	/**
	 * 体感温度
	 */
	private String realFeel;

	/**
	 * 降雪量
	 */
	private String snow;

	/**
	 * 实时温度
	 */
	private String temp;

	/**
	 * 更新时间
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
	 * 风速
	 */
	private String windSpeed;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getIconDay() {
		return iconDay;
	}

	public void setIconDay(String iconDay) {
		this.iconDay = iconDay;
	}

	public String getIconNight() {
		return iconNight;
	}

	public void setIconNight(String iconNight) {
		this.iconNight = iconNight;
	}

	public String getPop() {
		return pop;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getQpf() {
		return qpf;
	}

	public void setQpf(String qpf) {
		this.qpf = qpf;
	}

	public String getRealFeel() {
		return realFeel;
	}

	public void setRealFeel(String realFeel) {
		this.realFeel = realFeel;
	}

	public String getSnow() {
		return snow;
	}

	public void setSnow(String snow) {
		this.snow = snow;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
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

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
}
