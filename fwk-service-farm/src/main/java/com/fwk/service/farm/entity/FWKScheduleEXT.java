package com.fwk.service.farm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 日程实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月31日 下午5:10:15
 *
 */
public class FWKScheduleEXT extends FWKSchedule {

	// 我的日程
	private List<FWKSchedule> myschedulelist = new ArrayList<FWKSchedule>();

	// 我给别人安排的日程
	private List<FWKSchedule> scheduleforotherslist = new ArrayList<FWKSchedule>();

	public List<FWKSchedule> getMyschedulelist() {
		return myschedulelist;
	}

	public void setMyschedulelist(List<FWKSchedule> myschedulelist) {
		this.myschedulelist = myschedulelist;
	}

	public List<FWKSchedule> getScheduleforotherslist() {
		return scheduleforotherslist;
	}

	public void setScheduleforotherslist(List<FWKSchedule> scheduleforotherslist) {
		this.scheduleforotherslist = scheduleforotherslist;
	}

}
