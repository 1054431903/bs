package com.fwk.common.util.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 所有字典数据的定义
 * 
 * @author wangjunwen
 * @date 2017-07-29 16:28:26
 *
 */
public enum EDict {

	// ======= 字典类型：地块从属 ===========
	// 1 parcel-dependence autotrophy 自营 1 地块从属-自营 2018-01-15 10:01:55 1
	/** 地块从属： 自营 */
	parcel_dependence_autotrophy(new Dict(EDictType.parcel_dependence.dicttype, "autotrophy", "自营", 1, "地块从属-自营")),
	// 2 parcel-dependence cooperation 合作 2 地块从属-合作 2018-01-15 10:01:55 1
	/** 地块从属： 自营 */
	parcel_dependence_cooperation(new Dict(EDictType.parcel_dependence.dicttype, "cooperation", "合作", 2, "地块从属-合作")),

	// ======= 字典类型：用户性别 ===========
	// 3 user-sex 1 男 1 性别 2018-01-15 13:24:51 1
	/** 用户性别：男 */
	user_sex_male(new Dict(EDictType.user_sex.dicttype, "1", "男", 1, "性别")),
	// 4 user-sex 2 女 2 性别 2018-01-15 13:25:15 1
	/** 用户性别：女 */
	user_sex_female(new Dict(EDictType.user_sex.dicttype, "2", "女", 2, "性别")),

	// ======= 字典类型：农户贫困状态 ===========
	// 5 peasant-poverty 1 是 1 农户贫困状态-是 2018-01-15 13:26:12 1
	/** 农户贫困状态：是 */
	peasant_poverty_yes(new Dict(EDictType.peasant_poverty.dicttype, "poverty-yes", "是", 1, "农户贫困状态-是")),
	// 6 peasant-poverty 2 否 2 农户贫困状态-否 2018-01-15 13:26:15 1
	/** 农户贫困状态：否 */
	peasant_poverty_no(new Dict(EDictType.peasant_poverty.dicttype, "poverty-no", "否", 2, "农户贫困状态-否")),

	// ======= 字典类型：文化程度 ===========
	// 36 peasant-culture 1 小学以下 1 文化程度-小学以下 2018-01-17 14:45:27 1
	/** 文化程度：小学以下 */
	peasant_culture_below_the_primary_school(new Dict(EDictType.peasant_culture.dicttype, "culture-underprimary", "小学以下", 1, "文化程度-小学以下")),
	// 7 peasant-culture 2 小学 2 文化程度-小学 2018-01-15 13:32:29 1
	/** 文化程度：小学 */
	peasant_culture_primary_school(new Dict(EDictType.peasant_culture.dicttype, "culture-primary", "小学", 2, "文化程度-小学")),
	// 8 peasant-culture 3 初中 3 文化程度-初中 2018-01-15 13:32:29 1
	/** 文化程度：初中 */
	peasant_culture_middle_school(new Dict(EDictType.peasant_culture.dicttype, "culture-juniorhign", "初中", 3, "文化程度-初中")),
	// 9 peasant-culture 4 高中 4 文化程度-高中 2018-01-15 13:32:29 1
	/** 文化程度：高中 */
	peasant_culture_high_school(new Dict(EDictType.peasant_culture.dicttype, "culture-senior", "高中", 4, "文化程度-高中")),
	// 35 peasant-culture 5 技校 5 文化程度-技校 2018-01-17 14:45:27 1
	/** 文化程度：技校 */
	peasant_culture_technical_school(new Dict(EDictType.peasant_culture.dicttype, "culture-technical", "技校", 5, "文化程度-技校")),
	// 37 peasant-culture 6 中专 6 文化程度-中专 2018-01-17 14:45:27 1
	/** 文化程度：中专 */
	peasant_culture_secondary_specialized_school(new Dict(EDictType.peasant_culture.dicttype, "culture-secondary", "中专", 6, "文化程度-中专")),
	// 34 peasant-culture 7 大专 7 文化程度-大专 2018-01-17 14:45:27 1
	/** 文化程度：大专 */
	peasant_culture_junior_college(new Dict(EDictType.peasant_culture.dicttype, "culture-junior", "大专", 7, "文化程度-大专")),
	// 10 peasant-culture 8 本科 8 文化程度-大学 2018-01-15 13:32:29 1
	/** 文化程度：本科 */
	peasant_culture_university_degree(new Dict(EDictType.peasant_culture.dicttype, "culture-undergraduate", "本科", 8, "文化程度-本科")),
	// 11 peasant-culture 9 硕士 9 文化程度-硕士 2018-01-15 13:32:29 1
	/** 文化程度：硕士 */
	peasant_culture_master(new Dict(EDictType.peasant_culture.dicttype, "culture-graduate", "硕士", 9, "文化程度-硕士")),
	// 12 peasant-culture 10 博士 10 文化程度-博士 2018-01-15 13:32:29 1
	/** 文化程度：博士 */
	peasant_culture_learned_scholar(new Dict(EDictType.peasant_culture.dicttype, "culture-doctor", "博士", 10, "文化程度-博士")),

	// ======= 字典类型：合作状态 ===========
	// 13 peasant-cooperstate 1 未开始 2 合作状态-未开始 2018-01-15 13:41:04 1
	/** 合作状态：未开始 */
	peasant_cooperstate_no_start(new Dict(EDictType.peasant_cooperstate.dicttype, "cooper-nostart", "未开始", 2, "合作状态-未开始")),
	// 14 peasant-cooperstate 2 进行中 1 合作状态-进行中 2018-01-15 13:41:42 1
	/** 合作状态：进行中 */
	peasant_cooperstate_continue(new Dict(EDictType.peasant_cooperstate.dicttype, "cooper-underway", "进行中", 1, "合作状态-进行中")),
	// 15 peasant-cooperstate 3 已结束 3 合作状态-已结束 2018-01-15 13:41:42 1
	/** 合作状态：已结束 */
	peasant_cooperstate_finish(new Dict(EDictType.peasant_cooperstate.dicttype, "cooper-over", "已结束", 3, "合作状态-已结束")),

	// ======= 字典类型：农户协议状态 ===========
	// 17 peasant-protocolstate 1 未开始 1 农户协议状态-未开始 2018-01-15 13:41:04 1
	/** 农户协议状态：未开始 */
	peasant_protocolstate_no_start(new Dict(EDictType.peasant_protocolstate.dicttype, "protocol-nostart", "未开始", 1, "农户协议状态-未开始")),
	// 18 peasant-protocolstate 2 进行中 2 农户协议状态-进行中 2018-01-15 13:41:42 1
	/** 农户协议状态：进行中 */
	peasant_protocolstate_continue(new Dict(EDictType.peasant_protocolstate.dicttype, "protocol-underway", "进行中", 2, "农户协议状态-进行中")),
	// 19 peasant-protocolstate 3 已中止 3 农户协议状态-已中止 2018-01-15 13:41:42 1
	/** 农户协议状态：已中止 */
	peasant_protocolstate_discontinue(new Dict(EDictType.peasant_protocolstate.dicttype, "protocol-stop", "已中止", 3, "农户协议状态-已中止")),
	// 20 peasant-protocolstate 4 已完成 4 农户协议状态-已完成 2018-01-15 13:41:42 1
	/** 农户协议状态：已完成 */
	peasant_protocolstate_finish(new Dict(EDictType.peasant_protocolstate.dicttype, "protocol-complete", "已完成", 4, "农户协议状态-已完成")),

	// ======= 字典类型：农事计划提醒方式 ===========
	// 27 plan-warntype 1 系统消息 1 农事计划提醒方式-系统消息 2018-01-16 11:05:28 1
	/** 农事计划提醒方式：系统消息 */
	plan_warntype_system(new Dict(EDictType.plan_warntype.dicttype, "remind-system", "系统消息", 1, "农事计划提醒方式-系统消息")),
	// 28 plan-warntype 2 电子邮件 2 农事计划提醒方式-电子邮件 2018-01-16 11:05:28 1
	/** 农事计划提醒方式：电子邮件 */
	plan_warntype_email(new Dict(EDictType.plan_warntype.dicttype, "remind-email", "电子邮件", 2, "农事计划提醒方式-电子邮件")),
	// 29 plan-warntype 3 手机短信 3 农事计划提醒方式-手机短信 2018-01-16 11:05:28 1
	/** 农事计划提醒方式：手机短信 */
	plan_warntype_sms(new Dict(EDictType.plan_warntype.dicttype, "remind-note", "手机短信", 3, "农事计划提醒方式-手机短信")),

	// ======= 字典类型：农事任务类别 ===========
	// 30 task-type 1 业务拜访 1 农事任务类别-业务拜访 2018-01-16 11:05:28 1
	/** 农事任务类别：业务拜访 */
	task_type_business_visit(new Dict(EDictType.task_type.dicttype, "task-visit", "业务拜访", 1, "农事任务类别-业务拜访")),
	// 31 task-type 2 会议邀请 2 农事任务类别-会议邀请 2018-01-16 11:05:28 1
	/** 农事任务类别：会议邀请 */
	task_type_meeting_invitation(new Dict(EDictType.task_type.dicttype, "task-invite", "会议邀请", 2, "农事任务类别-会议邀请")),
	// 32 task-type 3 农事任务 3 农事任务类别-农事任务 2018-01-16 11:05:28 1
	/** 农事任务类别：农事任务 */
	task_type_agricultural_task(new Dict(EDictType.task_type.dicttype, "task-work", "农事任务", 3, "农事任务类别-农事任务")),
	// 33 task-type 4 其他 4 农事任务类别-其他 2018-01-16 11:05:28 1
	/** 农事任务类别：其他 */
	task_type_other(new Dict(EDictType.task_type.dicttype, "task-other", "其他", 4, "农事任务类别-其他")),

	// ======= 字典类型：产出量单位 ===========
	// 38 productive-quantityunit 1 吨 1 产出量单位-吨 2018-01-17 16:24:21 1
	/** 产出量单位：吨 */
	productive_quantityunit_t(new Dict(EDictType.productive_quantityunit.dicttype, "output-ton", "吨", 1, "产出量单位-吨")),
	// 39 productive-quantityunit 2 千克 2 产出量单位-千克 2018-01-17 16:24:21 1
	/** 产出量单位：千克 */
	productive_quantityunit_kg(new Dict(EDictType.productive_quantityunit.dicttype, "output-kg", "千克", 2, "产出量单位-千克")),
	// 40 productive-quantityunit 3 克 3 产出量单位-克 2018-01-17 16:24:21 1
	/** 产出量单位：克 */
	productive_quantityunit_g(new Dict(EDictType.productive_quantityunit.dicttype, "output-g", "克", 3, "产出量单位-克")),
	// 41 productive-quantityunit 4 袋 4 产出量单位-袋 2018-01-17 16:24:21 1
	/** 产出量单位：袋 */
	productive_quantityunit_bag(new Dict(EDictType.productive_quantityunit.dicttype, "output-bag", "袋", 4, "产出量单位-袋")),
	// 42 productive-quantityunit 5 箱 5 产出量单位-箱 2018-01-17 16:24:21 1
	/** 产出量单位：箱 */
	productive_quantityunit_box(new Dict(EDictType.productive_quantityunit.dicttype, "output-box", "箱", 5, "产出量单位-箱")),

	// ======= 字典类型：组织性质 ===========
	// 43 org-nature 1 个人 1 组织性质-个人 2018-01-17 16:24:21 1
	/** 组织性质：个人 */
	org_nature_personal(new Dict(EDictType.org_nature.dicttype, "4", "个人", 1, "组织性质-个人")),
	// 44 org-nature 2 公司 2 组织性质-公司 2018-01-17 16:24:21 1
	/** 组织性质：公司 */
	org_nature_company(new Dict(EDictType.org_nature.dicttype, "2", "公司", 2, "组织性质-公司")),
	// 45 org-nature 3 事业单位 3 组织性质-事业单位 2018-01-17 16:24:21 1
	/** 组织性质：事业单位 */
	org_nature_institution(new Dict(EDictType.org_nature.dicttype, "1", "事业单位", 3, "组织性质-事业单位")),
	// 46 org-nature 4 合作社 4 组织性质-合作社 2018-01-17 16:24:21 1
	/** 组织性质：合作社 */
	org_nature_cooperative(new Dict(EDictType.org_nature.dicttype, "3", "合作社", 4, "组织性质-合作社")),

	// ======= 字典类型：组织规模 ===========
	// 47 org-scale 1 100人以下 1 组织规模-100人以下 2018-01-17 16:24:21 1
	/** 组织规模：100人以下 */
	org_scale_less_than_100(new Dict(EDictType.org_scale.dicttype, "1", "100人以下", 1, "组织规模-100人以下")),
	// 48 org-scale 2 100-500人 2 组织规模-100-500人 2018-01-17 16:24:21 1
	/** 组织规模：100人以下 */
	org_scale_between_100_and_500(new Dict(EDictType.org_scale.dicttype, "2", "100-500人", 2, "组织规模-100-500人")),
	// 49 org-scale 3 500人以上 3 组织规模-500人以上 2018-01-17 16:24:21 1
	/** 组织规模：500人以上 */
	org_scale_more_than_500(new Dict(EDictType.org_scale.dicttype, "3", "500人以上", 3, "组织规模-500人以上")),

	// ======= 字典类型：日志类型 ===========
	// 52 logger_type log_execute 业务日志 3 日志类型-业务 2018-01-24 09:51:03 1
	/** 日志类型：业务日志 */
	logger_type_bussiness(new Dict(EDictType.logger_type.dicttype, "log_execute", "业务日志", 3, "日志类型-业务")),
	// 51 logger_type log_operate 操作日志 2 日志类型-操作 2018-01-24 09:51:03 1
	/** 日志类型：操作日志 */
	logger_type_operate(new Dict(EDictType.logger_type.dicttype, "log_operate", "操作日志", 2, "日志类型-操作")),
	// 50 logger_type log_login 登录日志 1 日志类型-登录 2018-01-24 09:51:03 1
	/** 日志类型：登录日志 */
	logger_type_login(new Dict(EDictType.logger_type.dicttype, "log_login", "登录日志", 1, "日志类型-登录")),

	// ======= 字典类型：农事记录来源 ===========
	// 53 farm-source source-task 任务 1 农事记录来源-任务 2018-02-03 17:09:53 1
	/** 农事记录来源：任务 */
	farm_source_task(new Dict(EDictType.farm_source.dicttype, "source-task", "任务", 1, "农事记录来源-任务")),
	// 54 farm-source source-record 记录 2 农事记录来源-记录 2018-02-03 17:09:59 1
	/** 农事记录来源：记录 */
	farm_source_record(new Dict(EDictType.farm_source.dicttype, "source-record", "记录", 1, "农事记录来源-记录")),

	// ======= 字典类型：农事任务状态 ===========
	// 55 task-status task-status-started 进行中 1 农事任务状态-进行中 2018-02-07 09:16:49 1
	/** 农事任务状态：进行中 */
	task_status_started(new Dict(EDictType.task_status.dicttype, "task-status-started", "进行中", 1, "农事任务状态-进行中")),
	// 56 task-status task-status-finish 已完成 2 农事任务状态-已完成 2018-02-07 09:16:56 1
	/** 农事任务状态：已完成 */
	task_status_finish(new Dict(EDictType.task_status.dicttype, "task-status-finish", "已完成", 2, "农事任务状态-已完成")),
	// 57 task-status task-status-refuse 已拒绝 3 农事任务状态-已拒绝 2018-02-07 09:17:10 1
	/** 农事任务状态：已拒绝 */
	task_status_refuse(new Dict(EDictType.task_status.dicttype, "task-status-refuse", "已拒绝", 3, "农事任务状态-已拒绝")),
	// 58 task-status task-status-overdue 已逾期 4 农事任务状态-已逾期 2018-02-07 09:17:15 1
	/** 农事任务状态：已逾期 */
	task_status_overdue(new Dict(EDictType.task_status.dicttype, "task-status-overdue", "已逾期", 4, "农事任务状态-已逾期")),

	// ======= 字典类型：农事任务参与状态 ===========
	// 59 task-partics-status task-partics-status-waitingconfirm 待确认 1
	// 农事任务参与状态-待确认 2018-02-07 09:59:02 1
	/** 农事任务参与状态：待确认 */
	task_partics_status_waitingconfirm(new Dict(EDictType.task_partics_status.dicttype, "task-partics-status-waitingconfirm", "待确认", 1, "农事任务参与状态-待确认")),
	// 60 task-partics-status task-partics-status-partake 参与 2 农事任务参与状态-参与
	// 2018-02-07 09:59:50 1
	/** 农事任务参与状态：参与 */
	task_partics_status_partake(new Dict(EDictType.task_partics_status.dicttype, "task-partics-status-partake", "参与", 2, "农事任务参与状态-参与")),
	// 61 task-partics-status task-partics-status-refuse 已拒绝 3 农事任务参与状态-已拒绝
	// 2018-02-07 09:59:54 1
	/** 农事任务参与状态：已拒绝 */
	task_partics_status_refuse(new Dict(EDictType.task_partics_status.dicttype, "task-partics-status-refuse", "已拒绝", 3, "农事任务参与状态-已拒绝")),

	// ======= 字典类型： 农事提醒时间单位 ===========
	// 62 farm-time-unit farm-time-unit-minutes 分钟 1 农事提醒时间单位-分钟 2018-02-07
	// 11:33:07 1
	/** 农事提醒时间单位：分钟 */
	farm_time_unit_minutes(new Dict(EDictType.farm_time_unit.dicttype, "farm-time-unit-minutes", "分钟", 1, "农事提醒时间单位-分钟")),
	// 63 farm-time-unit farm-time-unit-hour 小时 2 农事提醒时间单位-小时 2018-02-07
	// 11:34:25 1
	/** 农事提醒时间单位：小时 */
	farm_time_unit_hour(new Dict(EDictType.farm_time_unit.dicttype, "farm-time-unit-hour", "小时", 1, "农事提醒时间单位-小时")),
	// 64 farm-time-unit farm-time-unit-day 天 3 农事提醒时间单位-天 2018-02-07 11:34:30 1
	/** 农事提醒时间单位：天 */
	farm_time_unit_day(new Dict(EDictType.farm_time_unit.dicttype, "farm-time-unit-day", "天", 1, "农事提醒时间单位-天")),

	// ======= 字典类型：行政区划的级别 ===========
	/** 行政区划的级别：国家 */
	position_level_nation(new Dict(EDictType.position_level.dicttype, "1", "国家", 1, "行政区划的级别-国家")),
	/** 行政区划的级别：省 */
	position_level_province(new Dict(EDictType.position_level.dicttype, "2", "省", 2, "行政区划的级别-省")),
	/** 行政区划的级别：市 */
	position_level_city(new Dict(EDictType.position_level.dicttype, "3", "市", 3, "行政区划的级别-市")),
	/** 行政区划的级别：区/县 */
	position_level_county(new Dict(EDictType.position_level.dicttype, "4", "区/县", 4, "行政区划的级别-区/县")),
	/** 行政区划的级别：乡/镇 */
	position_level_town(new Dict(EDictType.position_level.dicttype, "5", "乡/镇", 5, "行政区划的级别-乡/镇")),
	/** 行政区划的级别：村委会 */
	position_level_village(new Dict(EDictType.position_level.dicttype, "6", "村委会", 6, "行政区划的级别-村委会")),

	// 枚举结束标识符
	;

	EDict(Dict dict) {
		this.dict = dict;
	}

	private final Dict dict;

	private static Set<Dict> dictSet = new HashSet<Dict>();
	private static List<Dict> dictList = new ArrayList<Dict>();

	static {

		// ======= 字典类型：地块从属 ===========
		// 1 parcel-dependence autotrophy 自营 1 地块从属-自营 2018-01-15 10:01:55 1
		/** 地块从属： 自营 */
		dictSet.add(parcel_dependence_autotrophy.dict);
		// 2 parcel-dependence cooperation 合作 2 地块从属-合作 2018-01-15 10:01:55 1
		/** 地块从属： 自营 */
		dictSet.add(parcel_dependence_cooperation.dict);

		// ======= 字典类型：用户性别 ===========
		// 3 user-sex 1 男 1 性别 2018-01-15 13:24:51 1
		/** 用户性别：男 */
		dictSet.add(user_sex_male.dict);
		// 4 user-sex 2 女 2 性别 2018-01-15 13:25:15 1
		/** 用户性别：女 */
		dictSet.add(user_sex_female.dict);

		// ======= 字典类型：农户贫困状态 ===========
		// 5 peasant-poverty 1 是 1 农户贫困状态-是 2018-01-15 13:26:12 1
		/** 农户贫困状态：是 */
		dictSet.add(peasant_poverty_yes.dict);
		// 6 peasant-poverty 2 否 2 农户贫困状态-否 2018-01-15 13:26:15 1
		/** 农户贫困状态：否 */
		dictSet.add(peasant_poverty_no.dict);

		// ======= 字典类型：文化程度 ===========
		// 36 peasant-culture 1 小学以下 1 文化程度-小学以下 2018-01-17 14:45:27 1
		/** 文化程度：小学以下 */
		dictSet.add(peasant_culture_below_the_primary_school.dict);
		// 7 peasant-culture 2 小学 2 文化程度-小学 2018-01-15 13:32:29 1
		/** 文化程度：小学 */
		dictSet.add(peasant_culture_primary_school.dict);
		// 8 peasant-culture 3 初中 3 文化程度-初中 2018-01-15 13:32:29 1
		/** 文化程度：初中 */
		dictSet.add(peasant_culture_middle_school.dict);
		// 9 peasant-culture 4 高中 4 文化程度-高中 2018-01-15 13:32:29 1
		/** 文化程度：高中 */
		dictSet.add(peasant_culture_high_school.dict);
		// 35 peasant-culture 5 技校 5 文化程度-技校 2018-01-17 14:45:27 1
		/** 文化程度：技校 */
		dictSet.add(peasant_culture_technical_school.dict);
		// 37 peasant-culture 6 中专 6 文化程度-中专 2018-01-17 14:45:27 1
		/** 文化程度：中专 */
		dictSet.add(peasant_culture_secondary_specialized_school.dict);
		// 34 peasant-culture 7 大专 7 文化程度-大专 2018-01-17 14:45:27 1
		/** 文化程度：大专 */
		dictSet.add(peasant_culture_junior_college.dict);
		// 10 peasant-culture 8 本科 8 文化程度-大学 2018-01-15 13:32:29 1
		/** 文化程度：本科 */
		dictSet.add(peasant_culture_university_degree.dict);
		// 11 peasant-culture 9 硕士 9 文化程度-硕士 2018-01-15 13:32:29 1
		/** 文化程度：硕士 */
		dictSet.add(peasant_culture_master.dict);
		// 12 peasant-culture 10 博士 10 文化程度-博士 2018-01-15 13:32:29 1
		/** 文化程度：博士 */
		dictSet.add(peasant_culture_learned_scholar.dict);

		// ======= 字典类型：合作状态 ===========
		// 13 peasant-cooperstate 1 未开始 2 合作状态-未开始 2018-01-15 13:41:04 1
		/** 合作状态：未开始 */
		dictSet.add(peasant_cooperstate_no_start.dict);
		// 14 peasant-cooperstate 2 进行中 1 合作状态-进行中 2018-01-15 13:41:42 1
		/** 合作状态：进行中 */
		dictSet.add(peasant_cooperstate_continue.dict);
		// 15 peasant-cooperstate 3 已结束 3 合作状态-已结束 2018-01-15 13:41:42 1
		/** 合作状态：已结束 */
		dictSet.add(peasant_cooperstate_finish.dict);

		// ======= 字典类型：农户协议状态 ===========
		// 17 peasant-protocolstate 1 未开始 1 农户协议状态-未开始 2018-01-15 13:41:04 1
		/** 农户协议状态：未开始 */
		dictSet.add(peasant_protocolstate_no_start.dict);
		// 18 peasant-protocolstate 2 进行中 2 农户协议状态-进行中 2018-01-15 13:41:42 1
		/** 农户协议状态：进行中 */
		dictSet.add(peasant_protocolstate_continue.dict);
		// 19 peasant-protocolstate 3 已中止 3 农户协议状态-已中止 2018-01-15 13:41:42 1
		/** 农户协议状态：已中止 */
		dictSet.add(peasant_protocolstate_discontinue.dict);
		// 20 peasant-protocolstate 4 已完成 4 农户协议状态-已完成 2018-01-15 13:41:42 1
		/** 农户协议状态：已完成 */
		dictSet.add(peasant_protocolstate_finish.dict);

		// ======= 字典类型：农事计划提醒方式 ===========
		// 27 plan-warntype 1 系统消息 1 农事计划提醒方式-系统消息 2018-01-16 11:05:28 1
		/** 农事计划提醒方式：系统消息 */
		dictSet.add(plan_warntype_system.dict);
		// 28 plan-warntype 2 电子邮件 2 农事计划提醒方式-电子邮件 2018-01-16 11:05:28 1
		/** 农事计划提醒方式：电子邮件 */
		dictSet.add(plan_warntype_email.dict);
		// 29 plan-warntype 3 手机短信 3 农事计划提醒方式-手机短信 2018-01-16 11:05:28 1
		/** 农事计划提醒方式：手机短信 */
		dictSet.add(plan_warntype_sms.dict);

		// ======= 字典类型：农事任务类别 ===========
		// 30 task-type 1 业务拜访 1 农事任务类别-业务拜访 2018-01-16 11:05:28 1
		/** 农事任务类别：业务拜访 */
		dictSet.add(task_type_business_visit.dict);
		// 31 task-type 2 会议邀请 2 农事任务类别-会议邀请 2018-01-16 11:05:28 1
		/** 农事任务类别：会议邀请 */
		dictSet.add(task_type_meeting_invitation.dict);
		// 32 task-type 3 农事任务 3 农事任务类别-农事任务 2018-01-16 11:05:28 1
		/** 农事任务类别：农事任务 */
		dictSet.add(task_type_agricultural_task.dict);
		// 33 task-type 4 其他 4 农事任务类别-其他 2018-01-16 11:05:28 1
		/** 农事任务类别：其他 */
		dictSet.add(task_type_other.dict);

		// ======= 字典类型：产出量单位 ===========
		// 38 productive-quantityunit 1 吨 1 产出量单位-吨 2018-01-17 16:24:21 1
		/** 产出量单位：吨 */
		dictSet.add(productive_quantityunit_t.dict);
		// 39 productive-quantityunit 2 千克 2 产出量单位-千克 2018-01-17 16:24:21 1
		/** 产出量单位：千克 */
		dictSet.add(productive_quantityunit_kg.dict);
		// 40 productive-quantityunit 3 克 3 产出量单位-克 2018-01-17 16:24:21 1
		/** 产出量单位：克 */
		dictSet.add(productive_quantityunit_g.dict);
		// 41 productive-quantityunit 4 袋 4 产出量单位-袋 2018-01-17 16:24:21 1
		/** 产出量单位：袋 */
		dictSet.add(productive_quantityunit_bag.dict);
		// 42 productive-quantityunit 5 箱 5 产出量单位-箱 2018-01-17 16:24:21 1
		/** 产出量单位：箱 */
		dictSet.add(productive_quantityunit_box.dict);

		// ======= 字典类型：组织性质 ===========
		// 43 org-nature 1 个人 1 组织性质-个人 2018-01-17 16:24:21 1
		/** 组织性质：个人 */
		dictSet.add(org_nature_personal.dict);
		// 44 org-nature 2 公司 2 组织性质-公司 2018-01-17 16:24:21 1
		/** 组织性质：公司 */
		dictSet.add(org_nature_company.dict);
		// 45 org-nature 3 事业单位 3 组织性质-事业单位 2018-01-17 16:24:21 1
		/** 组织性质：事业单位 */
		dictSet.add(org_nature_institution.dict);
		// 46 org-nature 4 合作社 4 组织性质-合作社 2018-01-17 16:24:21 1
		/** 组织性质：合作社 */
		dictSet.add(org_nature_cooperative.dict);

		// ======= 字典类型：组织规模 ===========
		// 47 org-scale 1 100人以下 1 组织规模-100人以下 2018-01-17 16:24:21 1
		/** 组织规模：100人以下 */
		dictSet.add(org_scale_less_than_100.dict);
		// 48 org-scale 2 100-500人 2 组织规模-100-500人 2018-01-17 16:24:21 1
		/** 组织规模：100人以下 */
		dictSet.add(org_scale_between_100_and_500.dict);
		// 49 org-scale 3 500人以上 3 组织规模-500人以上 2018-01-17 16:24:21 1
		/** 组织规模：500人以上 */
		dictSet.add(org_scale_more_than_500.dict);

		// ======= 字典类型：日志类型 ===========
		// 52 logger_type log_execute 业务日志 3 日志类型-业务 2018-01-24 09:51:03 1
		/** 日志类型：业务日志 */
		dictSet.add(logger_type_bussiness.dict);
		// 51 logger_type log_operate 操作日志 2 日志类型-操作 2018-01-24 09:51:03 1
		/** 日志类型：操作日志 */
		dictSet.add(logger_type_operate.dict);
		// 50 logger_type log_login 登录日志 1 日志类型-登录 2018-01-24 09:51:03 1
		/** 日志类型：登录日志 */
		dictSet.add(logger_type_login.dict);

		// ======= 字典类型：农事记录来源 ===========
		// 53 farm-source source-task 任务 1 农事记录来源-任务 2018-02-03 17:09:53 1
		/** 农事记录来源：任务 */
		dictSet.add(farm_source_task.dict);
		// 54 farm-source source-record 记录 2 农事记录来源-记录 2018-02-03 17:09:59 1
		/** 农事记录来源：记录 */
		dictSet.add(farm_source_record.dict);

		// ======= 字典类型：农事任务状态 ===========
		// 55 task-status task-status-started 进行中 1 农事任务状态-进行中 2018-02-07
		// 09:16:49 1
		/** 农事任务状态：进行中 */
		dictSet.add(task_status_started.dict);
		// 56 task-status task-status-finish 已完成 2 农事任务状态-已完成 2018-02-07
		// 09:16:56 1
		/** 农事任务状态：已完成 */
		dictSet.add(task_status_finish.dict);
		// 57 task-status task-status-refuse 已拒绝 3 农事任务状态-已拒绝 2018-02-07
		// 09:17:10 1
		/** 农事任务状态：已拒绝 */
		dictSet.add(task_status_refuse.dict);
		// 58 task-status task-status-overdue 已逾期 4 农事任务状态-已逾期 2018-02-07
		// 09:17:15 1
		/** 农事任务状态：已逾期 */
		dictSet.add(task_status_overdue.dict);

		// ======= 字典类型：农事任务参与状态 ===========
		// 59 task-partics-status task-partics-status-waitingconfirm 待确认 1
		// 农事任务参与状态-待确认 2018-02-07 09:59:02 1
		/** 农事任务参与状态：待确认 */
		dictSet.add(task_partics_status_waitingconfirm.dict);
		// 60 task-partics-status task-partics-status-partake 参与 2 农事任务参与状态-参与
		// 2018-02-07 09:59:50 1
		/** 农事任务参与状态：参与 */
		dictSet.add(task_partics_status_partake.dict);
		// 61 task-partics-status task-partics-status-refuse 已拒绝 3 农事任务参与状态-已拒绝
		// 2018-02-07 09:59:54 1
		/** 农事任务参与状态：已拒绝 */
		dictSet.add(task_partics_status_refuse.dict);

		// ======= 字典类型： 农事提醒时间单位 ===========
		// 62 farm-time-unit farm-time-unit-minutes 分钟 1 农事提醒时间单位-分钟 2018-02-07
		// 11:33:07 1
		/** 农事提醒时间单位：分钟 */
		dictSet.add(farm_time_unit_minutes.dict);
		// 63 farm-time-unit farm-time-unit-hour 小时 2 农事提醒时间单位-小时 2018-02-07
		// 11:34:25 1
		/** 农事提醒时间单位：小时 */
		dictSet.add(farm_time_unit_hour.dict);
		// 64 farm-time-unit farm-time-unit-day 天 3 农事提醒时间单位-天 2018-02-07
		// 11:34:30 1
		/** 农事提醒时间单位：天 */
		dictSet.add(farm_time_unit_day.dict);

		// ======= 字典类型：行政区划的级别 ===========
		/** 行政区划的级别：国家 */
		dictSet.add(position_level_nation.dict);
		/** 行政区划的级别：省 */
		dictSet.add(position_level_province.dict);
		/** 行政区划的级别：市 */
		dictSet.add(position_level_city.dict);
		/** 行政区划的级别：区/县 */
		dictSet.add(position_level_county.dict);
		/** 行政区划的级别：乡/镇 */
		dictSet.add(position_level_town.dict);
		/** 行政区划的级别：村委会 */
		dictSet.add(position_level_village.dict);

		// 设置 Set 不可在外部修改
		dictSet = Collections.unmodifiableSet(dictSet);

		// 将 Set 转换为 List
		dictList.addAll(Arrays.asList(dictSet.toArray(new Dict[] {})));

		// 设置 List 不可在外部修改
		dictList = Collections.unmodifiableList(dictList);
	}

	/** 所有字典类型 */
	public static final List<Dict> DICT_lIST = dictList;

	/**
	 * 获取字典
	 * 
	 * @author wangjunwen
	 * @date 2017-07-31 11:32:29
	 *
	 * @return
	 */
	public Dict getDict() {
		return dict;
	}

	/**
	 * 获取指定的字典类型的所有字典
	 * 
	 * @author wangjunwen
	 * @date 2017年8月31日 下午1:26:00
	 *
	 * @param eDictType
	 *            字典类型
	 * @return
	 */
	public static List<Dict> getDictByType(EDictType eDictType) {
		if (null == eDictType) {
			return null;
		}

		final String dictType = eDictType.name();
		List<Dict> dictList = new ArrayList<Dict>();
		Dict tempDict = null;
		for (EDict eDict : EDict.values()) {
			tempDict = eDict.dict;
			if (dictType.equals(tempDict.getDictType())) {
				dictList.add(tempDict);
			}
		}

		// 排序再返回
		Collections.sort(dictList);

		return dictList;
	}

	/**
	 * 根据字典类型和字典的值获取字典对象
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 上午9:47:19
	 *
	 * @param eDictType
	 *            字典类型
	 * @param dictValue
	 *            字典的值
	 * @return
	 */
	public static Dict getDictByType(EDictType eDictType, String dictValue) {
		List<Dict> dictList = getDictByType(eDictType);

		if ((null == dictList) || (dictList.isEmpty()) || (null == dictValue)
				|| (dictValue = dictValue.trim()).isEmpty()) {
			return null;
		} else {
			for (Dict dict : dictList) {
				if (dict.getDictValue().equals(dictValue)) {
					return dict;
				}
			}
		}

		return null;
	}

	/**
	 * 获取指定字典类型的字典
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 上午10:00:04
	 *
	 * @param eDictType
	 *            字典类型
	 * @return
	 */
	public static List<EDict> getEDictByType(EDictType eDictType) {
		if (null == eDictType) {
			return null;
		}

		List<EDict> list = new ArrayList<EDict>();

		for (EDict eDict : EDict.values()) {
			if (eDict.getDict().getDictType().equals(eDictType.dicttype)) {
				list.add(eDict);
			}
		}

		return list;
	}

	/**
	 * 获取指定字典类型和字典值的字典
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 上午10:03:06
	 *
	 * @param eDictType
	 *            字典类型
	 * @param dictValue
	 *            字典的值
	 * @return
	 */
	public static EDict getEDictByType(EDictType eDictType, String dictValue) {
		List<EDict> eDictList = getEDictByType(eDictType);

		if ((null == eDictList) || (eDictList.isEmpty()) || (null == dictValue)
				|| (dictValue = dictValue.trim()).isEmpty()) {
			return null;
		} else {
			for (EDict eDict : eDictList) {
				if (eDict.getDict().getDictValue().equals(dictValue)) {
					return eDict;
				}
			}
		}

		return null;
	}

	/**
	 * 通过字典类型和字典的名称获取字典的值
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午4:39:13
	 *
	 * @param eDictType
	 *            字典类型
	 * @param dictName
	 *            字典名称
	 * @return
	 */
	public static String getDictValue(EDictType eDictType, String dictName) {
		if ((null == eDictType) || (null == dictName) || (dictName = dictName.trim()).isEmpty()) {
			return null;
		}

		List<EDict> eDictList = getEDictByType(eDictType);

		if ((null == eDictList) || (eDictList.isEmpty())) {
			return null;
		}

		for (EDict eDict : eDictList) {
			if (eDict.dict.getDictName().equals(dictName)) {
				return eDict.dict.getDictValue();
			}
		}

		return null;
	}

}
