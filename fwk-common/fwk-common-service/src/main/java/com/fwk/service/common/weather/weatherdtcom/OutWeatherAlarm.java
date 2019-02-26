package com.fwk.service.common.weather.weatherdtcom;

/**
 * 天气预警信息
 * 
 * @author wangjunwen
 *
 */
@A8nWeatherCategoryCode(code = OutWeatherAlarm.categoryCode)
public class OutWeatherAlarm {

	/** 气象数据大的类别 */
	public static final String categoryCode = "1001003";

	/** 001 预警发布单位的省级名称 河北省 */
	@A8nWeatherCode(code = "001")
	private String releaseProvinceName;

	/** 002 预警发布单位的市级名称 廊坊市 */
	@A8nWeatherCode(code = "002")
	private String releaseCityName;

	/** 003 预警发布单位的县级名称 霸州市（县级） */
	@A8nWeatherCode(code = "003")
	private String releaseCountryName;

	/** 004 预警类别编号 13 */
	@A8nWeatherCode(code = "004")
	private String alarmCode;

	/** 005 预警类别名称 霾 */
	@A8nWeatherCode(code = "005")
	private String alarmName;

	/** 006 预警级别编号 02 */
	@A8nWeatherCode(code = "006")
	private String alarmLevelCode;

	/** 007 预警级别名称 黄色 */
	@A8nWeatherCode(code = "007")
	private String alarmLevelName;

	/** 008 预警发布时间 2016-01-14 11:34 */
	@A8nWeatherCode(code = "008")
	private String releaseDate;

	/** 009 预警发布内容 霸州气象台1月14日11时发布霾黄色预警信号 */
	@A8nWeatherCode(code = "009")
	private String alarmContent;

	/** 010 预警信息 201601141133545183霾黄色 */
	@A8nWeatherCode(code = "010")
	private String alarmInfo;

	/** 011 天气网跳转地址 101090608-20160114113400-1302.html */
	@A8nWeatherCode(code = "011")
	private String jumpToPage;

	public String getReleaseProvinceName() {
		return releaseProvinceName;
	}

	public void setReleaseProvinceName(String releaseProvinceName) {
		this.releaseProvinceName = releaseProvinceName;
	}

	public String getReleaseCityName() {
		return releaseCityName;
	}

	public void setReleaseCityName(String releaseCityName) {
		this.releaseCityName = releaseCityName;
	}

	public String getReleaseCountryName() {
		return releaseCountryName;
	}

	public void setReleaseCountryName(String releaseCountryName) {
		this.releaseCountryName = releaseCountryName;
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAlarmLevelCode() {
		return alarmLevelCode;
	}

	public void setAlarmLevelCode(String alarmLevelCode) {
		this.alarmLevelCode = alarmLevelCode;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getJumpToPage() {
		return jumpToPage;
	}

	public void setJumpToPage(String jumpToPage) {
		this.jumpToPage = jumpToPage;
	}

	public static String getCategorycode() {
		return categoryCode;
	}

}
