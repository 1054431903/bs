package com.fwk.service.common.entity;

/**
 * 精简版的行政区划
 */
public class PositionCustomer {
	// 行政区划编号
	private String code;
	// 行政区划名称
	private String name;
	// 父级的行政区划编号
	private String parentcode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

}
