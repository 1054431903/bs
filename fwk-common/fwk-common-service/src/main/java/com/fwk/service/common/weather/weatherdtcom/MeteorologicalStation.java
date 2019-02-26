package com.fwk.service.common.weather.weatherdtcom;

/**
 * 站点信息实体类
 * 
 * @author wangjunwen
 *
 */
public class MeteorologicalStation {
	private String stationId = null;// 站点编号
	private String stationName = null;// 站点所在地
	private String airQuality = null;// 空气质量
	private String areaProvince = null;// 省
	private String areaCity = null;// 市
	private String areaCounty = null;// 区/县
	private String areaTown = null;// 乡镇

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAirQuality() {
		return airQuality;
	}

	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}

	public String getAreaProvince() {
		return areaProvince;
	}

	public void setAreaProvince(String areaProvince) {
		this.areaProvince = areaProvince;
	}

	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}

	public String getAreaCounty() {
		return areaCounty;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public String getAreaTown() {
		return areaTown;
	}

	public void setAreaTown(String areaTown) {
		this.areaTown = areaTown;
	}

}
