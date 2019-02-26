package com.fwk.service.common.weather.weatherdtcom;

import java.io.Serializable;

/**
 * 用户已订购的天气数据（元数据）
 * 
 * @author wangjunwen
 *
 */
public class OrderElement implements Serializable {

	private static final long serialVersionUID = 1L;

	// 数据大类（英文名）
	private String typeNameEN = null;

	// 数据大类（简体中文名）
	private String typeNameZhCn = null;

	// 频次
	private String frequency = null;

	// 预报时长
	private String duration = null;

	public String getTypeNameEN() {
		return typeNameEN;
	}

	public void setTypeNameEN(String typeNameEN) {
		this.typeNameEN = typeNameEN;
	}

	public String getTypeNameZhCn() {
		return typeNameZhCn;
	}

	public void setTypeNameZhCn(String typeNameZhCn) {
		this.typeNameZhCn = typeNameZhCn;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
