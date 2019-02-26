package com.fwk.service.common.entity;

import com.fwk.common.entity.TCposition;

/**
 * 行政区划实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月5日 上午10:52:15
 *
 */
public class PositionEXT extends TCposition {

	// 国家
	private String nationname;
	private String nationcode;
	// 省份
	private String provincename;
	private String provincecode;
	// 地市
	private String cityname;
	private String citycode;
	// 区县
	private String countyname;
	private String countycode;
	// 乡镇
	private String townname;
	private String towncode;
	// 行政村
	private String villagename;
	private String villagecode;
	// 行政区划
	private String allname;

	public String getNationname() {
		return nationname;
	}

	public void setNationname(String nationname) {
		this.nationname = nationname;
	}

	public String getNationcode() {
		return nationcode;
	}

	public void setNationcode(String nationcode) {
		this.nationcode = nationcode;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCountyname() {
		return countyname;
	}

	public void setCountyname(String countyname) {
		this.countyname = countyname;
	}

	public String getCountycode() {
		return countycode;
	}

	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}

	public String getTownname() {
		return townname;
	}

	public void setTownname(String townname) {
		this.townname = townname;
	}

	public String getTowncode() {
		return towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getVillagename() {
		return villagename;
	}

	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}

	public String getVillagecode() {
		return villagecode;
	}

	public void setVillagecode(String villagecode) {
		this.villagecode = villagecode;
	}

	public String getAllname() {
		return allname;
	}

	public void setAllname(String allname) {
		this.allname = allname;
	}

}
