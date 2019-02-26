package com.fwk.common.util.constants;

/**
 * 日程类型
 * 
 * @author wangjunwen
 * @date 2018年3月29日 上午9:39:31
 *
 */
public enum ScheduleType {

	/** 计划 */
	plan("plan", "计划", 1),

	/** 任务 */
	task("task", "任务", 2),

	/** 约会 */
	metting("metting", "约会", 3),

	// 枚举结束标记
	;

	/** 日程类型 */
	public final String type;
	/** 日程类型名称 */
	public final String typeName;
	/** 排序号 */
	public final Integer orderNo;

	private ScheduleType(String type, String typeName, Integer orderNo) {
		this.type = type;
		this.typeName = typeName;
		this.orderNo = orderNo;
	}

	/**
	 * 获取日程类别
	 * 
	 * @author wangjunwen
	 * @date 2018年3月29日 上午9:43:15
	 *
	 * @param type
	 *            日程类别字符串
	 * @return
	 */
	public static ScheduleType getScheduleType(String type) {

		if ((null == type) || (type.isEmpty())) {
			return null;
		}

		for (ScheduleType scheduleType : ScheduleType.values()) {
			if (scheduleType.type.equals(type)) {
				return scheduleType;
			}
		}

		return null;
	}

}
