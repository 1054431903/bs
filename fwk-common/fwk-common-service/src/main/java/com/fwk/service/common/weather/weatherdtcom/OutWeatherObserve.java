package com.fwk.service.common.weather.weatherdtcom;

/**
 * 天气实况
 * 
 * @author wangjunwen
 *
 */
@A8nWeatherCategoryCode(code = OutWeatherObserve.categoryCode)
public class OutWeatherObserve {

	/** 气象数据大的类别 */
	public static final String categoryCode = "1001002";

	/** 000 实况发布时间(小时：分钟) 10:25 */
	@A8nWeatherCode(code = "000")
	private String releaseTime;

	/** 002 当前温度(整数，单位摄氏度) 0 */
	@A8nWeatherCode(code = "002")
	private String nowTemperature;

	/** 003 当前风力(单位是级,不用转码) 2 */
	@A8nWeatherCode(code = "003")
	private String windPower;

	/** 004 当前风向编号（转码到风向） 1 */
	@A8nWeatherCode(code = "004")
	private String windDirectionCode;

	/** 当前风向（在调用 setWindDirectionCode 方法时自动赋值） */
	private String windDirection;

	/** 005 当前相对湿度(整数，单位%) 31 */
	@A8nWeatherCode(code = "005")
	private String humidity;

	/** 006 当前降水量(精确一位，单位毫米) 0.2 */
	@A8nWeatherCode(code = "006")
	private String rainfall;

	/** 007 本站气压(整数，单位百帕) 1014 */
	@A8nWeatherCode(code = "007")
	private String pressure;

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getNowTemperature() {
		return nowTemperature;
	}

	public void setNowTemperature(String nowTemperature) {
		this.nowTemperature = nowTemperature;
	}

	public String getWindPower() {
		return windPower;
	}

	public void setWindPower(String windPower) {
		if (null != windPower) {
			windPower += "级";
		}

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

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

}
