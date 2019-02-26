package com.fwk.service.common.weather.weatherdtcom;

import java.util.List;

/**
 * 未来几天的天气预报
 * 
 * @author wangjunwen
 *
 */
public class OutFutureDaysWeather {
	// 第一行的值（星期）
	private List<String> weekList;
	// 第二行的值（气象名称）
	private List<String> weatherNameList;
	// 第三行的值（温度）
	private List<String> temperatureList;
	// 第四行（风向）
	private List<String> windDirectionList;
	// 第五行（风力）
	private List<String> windPowerList;

	public List<String> getWeekList() {
		return weekList;
	}

	public void setWeekList(List<String> weekList) {
		this.weekList = weekList;
	}

	public List<String> getWeatherNameList() {
		return weatherNameList;
	}

	public void setWeatherNameList(List<String> weatherNameList) {
		this.weatherNameList = weatherNameList;
	}

	public List<String> getTemperatureList() {
		return temperatureList;
	}

	public void setTemperatureList(List<String> temperatureList) {
		this.temperatureList = temperatureList;
	}

	public List<String> getWindDirectionList() {
		return windDirectionList;
	}

	public void setWindDirectionList(List<String> windDirectionList) {
		this.windDirectionList = windDirectionList;
	}

	public List<String> getWindPowerList() {
		return windPowerList;
	}

	public void setWindPowerList(List<String> windPowerList) {
		this.windPowerList = windPowerList;
	}

}
