package com.common.util.model;

import java.util.Date;
import java.util.List;

public class BeanTestHTMLUtil {

	private String name;
	private Date birthday;
	private String[] hobbys;
	private Integer[] ids;
	private List<SubBeanTestHTMLUtil> subBeanTestHTMLUtilList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String[] getHobbys() {
		return hobbys;
	}

	public void setHobbys(String[] hobbys) {
		this.hobbys = hobbys;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public List<SubBeanTestHTMLUtil> getSubBeanTestHTMLUtilList() {
		return subBeanTestHTMLUtilList;
	}

	public void setSubBeanTestHTMLUtilList(List<SubBeanTestHTMLUtil> subBeanTestHTMLUtilList) {
		this.subBeanTestHTMLUtilList = subBeanTestHTMLUtilList;
	}

}
