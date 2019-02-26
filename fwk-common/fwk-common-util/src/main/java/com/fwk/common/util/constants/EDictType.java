package com.fwk.common.util.constants;

/**
 * 所有字典类型的定义
 * 
 * @author wangjunwen
 * @date 2017-08-01 15:18:58
 *
 */
public enum EDictType {

	/** 字典类型：地块从属 */
	parcel_dependence("parcel-dependence"),

	/** 字典类型：用户性别 */
	user_sex("user-sex"),

	/** 字典类型：农户贫困状态 */
	peasant_poverty("peasant-poverty"),

	/** 字典类型：文化程度 */
	peasant_culture("peasant-culture"),

	/** 字典类型：合作状态 */
	peasant_cooperstate("peasant-cooperstate"),

	/** 字典类型：农户协议状态 */
	peasant_protocolstate("peasant-protocolstate"),

	/** 字典类型：农事计划提醒方式 */
	plan_warntype("plan-warntype"),

	/** 字典类型：农事任务类别 */
	task_type("task-type"),

	/** 字典类型：产出量单位 */
	productive_quantityunit("productive-quantityunit"),

	/** 字典类型：组织性质 */
	org_nature("org-nature"),

	/** 字典类型：组织规模 */
	org_scale("org-scale"),
	
	/** 字典类型：日志类型 */
	logger_type("logger_type"),
	
	/** 字典类型：农事记录来源 */
	farm_source("farm-source"),
	
	/** 字典类型：农事任务状态 */
	task_status("task-status"),
	
	/** 字典类型：农事任务参与状态 */
	task_partics_status("task-partics-status"),
	
	/** 字典类型：农事提醒时间单位 */
	farm_time_unit("farm-time-unit"),
	
	/** 字典类型：行政区划的级别 */
	position_level("position_level"),

	// 枚举结束标识符
	;

	// ============== 以下为方法和字段的定义 ==========================
	private EDictType(String dicttype) {
		this.dicttype = dicttype;
	}

	/** 字典类别 */
	public final String dicttype;

}
