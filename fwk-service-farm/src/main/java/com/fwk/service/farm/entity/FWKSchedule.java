package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.util.Page;
import com.fwk.common.util.constants.ScheduleType;

/**
 * 日程实体类
 * 
 * @author wangjunwen
 * @date 2018年3月29日 上午9:34:27
 *
 */
public class FWKSchedule implements Comparable<FWKSchedule> {

	@Override
	public int compareTo(FWKSchedule other) {
		if ((null == this.getEndtime()) || (null == other.getEndtime())) {
			return -1;
		} else if ((null != this.getEndtime()) && (null != other.getEndtime())) {
			if (this.getEndtime().getTime() == other.getEndtime().getTime()) {
				return 0;// 两个截止时间相同
			} else if (this.getEndtime().before(other.getEndtime())) {
				return -1;
			} else {
				return 1;
			}
		} else if ((null == this.getEndtime()) && (null != other.getEndtime())) {
			return 1;
		} else if ((null != this.getEndtime()) && (null == other.getEndtime())) {
			return -1;
		}
		return 0;
	}

	// 分页查询对象
	private Page page;
	// 排序的SQL语句
	private String orderBySQL;
	// 开始时间
	private String createdatestart;
	// 截至时间
	private String createdateend;
	// 业务主键列表
	private List<Integer> idlist;
	// 查询的关键字（记录编号、农户名称）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 对象的主键
	private Integer id;
	// 添加时间
	private Date creattime;
	// 删除状态
	private Integer delstate;
	// 日程内容
	private String content;
	// 开始时间
	private Date begintime;
	// 结束时间
	private Date endtime;
	// 创建人
	private Integer createuserid;
	// 修改时间
	private Date edittime;
	// 日程类型（计划、任务、约会）
	private String scheduletype;
	// 日程类型名称
	private String scheduletypename;
	// 排序号
	private Integer orderno;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getOrderBySQL() {
		return orderBySQL;
	}

	public void setOrderBySQL(String orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

	public String getCreatedatestart() {
		return createdatestart;
	}

	public void setCreatedatestart(String createdatestart) {
		this.createdatestart = createdatestart;
	}

	public String getCreatedateend() {
		return createdateend;
	}

	public void setCreatedateend(String createdateend) {
		this.createdateend = createdateend;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreattime() {
		return creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public Integer getDelstate() {
		return delstate;
	}

	public void setDelstate(Integer delstate) {
		this.delstate = delstate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Integer createuserid) {
		this.createuserid = createuserid;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getScheduletype() {
		return scheduletype;
	}

	public void setScheduletype(String scheduletype) {
		ScheduleType scheduleType = ScheduleType.getScheduleType(scheduletype);

		if (null != scheduleType) {
			this.scheduletype = scheduleType.type;
			this.setScheduletypename(scheduleType.typeName);
			this.setOrderno(scheduleType.orderNo);
		}
	}

	public String getScheduletypename() {
		return scheduletypename;
	}

	public void setScheduletypename(String scheduletypename) {
		this.scheduletypename = scheduletypename;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

}
