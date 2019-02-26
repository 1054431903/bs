package com.fwk.service.common.entity;

import com.fwk.common.entity.TCresourcehistory;

/**
 * 资源上传历史实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年1月22日 下午4:01:10
 *
 */
public class ResourcehistoryEXT extends TCresourcehistory {
	// 资源的访问地址
	private String resourcepath;
	// 资源名称
	private String resourcename;
	// 音、视频的时长
	private Integer timelength;

	public String getResourcepath() {
		return resourcepath;
	}

	public void setResourcepath(String resourcepath) {
		this.resourcepath = resourcepath;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public Integer getTimelength() {
		return timelength;
	}

	public void setTimelength(Integer timelength) {
		this.timelength = timelength;
	}

}
