package com.fwk.service.pusher.domain;

import java.util.Map;

public class Notice {

	private String tenent;
	private String tenentStaff;
	private String title;
	private String subTitle;
	private String category;
	private Map<String, String> extra;

	public String getTenent() {
		return tenent;
	}

	public void setTenent(String tenent) {
		this.tenent = tenent;
	}

	public String getTenentStaff() {
		return tenentStaff;
	}

	public void setTenentStaff(String tenentStaff) {
		this.tenentStaff = tenentStaff;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Map<String, String> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}

}
