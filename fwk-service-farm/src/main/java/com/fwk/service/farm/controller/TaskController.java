package com.fwk.service.farm.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.ErrorUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.entity.TaskEXT;
import com.fwk.service.farm.entity.TaskcommentEXT;
import com.fwk.service.farm.entity.TaskcopyEXT;
import com.fwk.service.farm.entity.TaskparcelEXT;
import com.fwk.service.farm.entity.TaskparticsEXT;
import com.fwk.service.farm.entity.TaskwarnEXT;
import com.fwk.service.farm.service.TaskService;
import com.fwk.service.farm.service.TaskcommentService;
import com.fwk.service.farm.service.TaskparticsService;

/**
 * 农事管理模块 — 农事任务管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/task")
public class TaskController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	// 农事管理模块 — 农事任务管理的业务逻辑处理对象
	@Autowired
	private TaskService mTaskService;

	// 农事管理模块 — 农事任务的参与人的业务逻辑处理对象
	@Autowired
	private TaskparticsService mTaskparticsService;

	// 公共业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农事任务的评论的业务逻辑处理对象
	@Autowired
	private TaskcommentService mTaskcommentService;

	/**
	 * 新增农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:19:02
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramTaskEXT.getDescribe())) {// 农事任务内容
				resultWrap.setParamIsnotEmpty("describe");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getType())) {// 农事任务类别
				resultWrap.setParamIsnotEmpty("type");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getBegintime())) {// 开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getEndtime())) {// 结束时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getTaskparticslist())) {// 参与人
				resultWrap.setParamIsnotEmpty("taskparticslist");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getIsaffirmpartics())) {// 是否需要参与人确认
				resultWrap.setParamIsnotEmpty("isaffirmpartics");
				return;
			} else if ((1 != paramTaskEXT.getIsaffirmpartics().intValue())
					&& (0 != paramTaskEXT.getIsaffirmpartics().intValue())) {// 是否需要参与人确认的参数无效
				resultWrap.setParamInvalid("isaffirmpartics");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 设置发布人
				paramTaskEXT.setCreateuserid(loginUserInfo.getId());

				// 农事任务类型
				EDict taskType = EDict.getEDictByType(EDictType.task_type, paramTaskEXT.getType());

				if (null == taskType) {// 农事任务类型无效
					resultWrap.setParamInvalid("type");
					return;
				}

				// --------- 农事任务的地块列表 ----------------
				if ((null != paramTaskEXT.getTaskparcellist())) {

					for (TaskparcelEXT taskparcelEXT : paramTaskEXT.getTaskparcellist()) {

						if (null == taskparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("taskparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<TaskparcelEXT> iterator = paramTaskEXT.getTaskparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskparcelEXT tempTaskparcelEXT = null;
					while (iterator.hasNext()) {
						tempTaskparcelEXT = iterator.next();

						if (finalList.contains(tempTaskparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// ----------- 农事任务的参人列表 --------------
				if ((null != paramTaskEXT.getTaskparticslist())) {

					for (TaskparticsEXT taskparticsEXT : paramTaskEXT.getTaskparticslist()) {

						if (null == taskparticsEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("taskparticslist.userid");
							return;
						}

						// 是否需要参与人确认的默认值设置
						if (new Integer(CommonConstantsUtil.YES_OR_NO_YES).equals(paramTaskEXT.getIsaffirmpartics())) {// 1：需要参与人确认

							// 需要参与人确认时，默认将参与人的农事任务参与状态置为：待确认
							taskparticsEXT.setTaskparticsstatus(
									EDict.task_partics_status_waitingconfirm.getDict().getDictValue());

						} else if (new Integer(CommonConstantsUtil.YES_OR_NO_NO)
								.equals(paramTaskEXT.getIsaffirmpartics())) {// 0：不需要参与人确认

							// 不需要参与人确认时，默认将参与人的农事任务参与状态置为：参与
							taskparticsEXT
									.setTaskparticsstatus(EDict.task_partics_status_partake.getDict().getDictValue());

							// 农事任务参与人的农事任务状态
							taskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());
						}
					}

					// 去重
					Iterator<TaskparticsEXT> iterator = paramTaskEXT.getTaskparticslist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskparticsEXT tempTaskparticsEXT = null;
					while (iterator.hasNext()) {
						tempTaskparticsEXT = iterator.next();

						if (finalList.contains(tempTaskparticsEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskparticsEXT.getUserid());
						}
					}

					finalList = null;

				}

				// -------------- 农事任务的抄送人列表 ---------------
				if ((null != paramTaskEXT.getTaskcopylist())) {

					for (TaskcopyEXT taskcopyEXT : paramTaskEXT.getTaskcopylist()) {

						if (null == taskcopyEXT.getUserid()) {// 抄送人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("taskcopylist.userid");
							return;
						}
					}

					// 去重
					Iterator<TaskcopyEXT> iterator = paramTaskEXT.getTaskcopylist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskcopyEXT tempTaskcopyEXT = null;
					while (iterator.hasNext()) {
						tempTaskcopyEXT = iterator.next();

						if (finalList.contains(tempTaskcopyEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskcopyEXT.getUserid());
						}
					}

					finalList = null;

				}

				// --------------- 农事任务提醒方式列表 ---------------------
				if ((null != paramTaskEXT.getTaskwarnlist())) {

					for (TaskwarnEXT taskwarnEXT : paramTaskEXT.getTaskwarnlist()) {

						// 获取农事任务的提醒方式
						EDict farmWarnType = EDict.getEDictByType(EDictType.plan_warntype, taskwarnEXT.getType());

						// 获取农事任务的提醒方式的时间单位
						EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, taskwarnEXT.getTimeunit());

						if ((null == taskwarnEXT.getType())) {// 提醒方式不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.type");
							return;
						} else if ((null == farmWarnType)) {// 提醒方式无效
							resultWrap.setParamInvalid("taskwarnlist.type");
							return;
						} else if ((null == taskwarnEXT.getTimeunit())) {// 提醒方式的时间单位不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.timeunit");
							return;
						} else if ((null == farmTimeUnit)) {// 提醒方式的时间单位无效
							resultWrap.setParamInvalid("taskwarnlist.timeunit");
							return;
						} else if ((null == taskwarnEXT.getWarntimes())) {// 提醒方式的时间不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.warntimes");
							return;
						} else if ((taskwarnEXT.getWarntimes().intValue() < 1)) {// 提醒方式的时间无效
							resultWrap.setParamInvalid("taskwarnlist.warntimes");
							return;
						} else {

							// 设置任务提醒的时间
							mTaskService.setTaskwarntime(taskwarnEXT, paramTaskEXT);

							if (paramTaskEXT.getBegintime().after(taskwarnEXT.getWarntime())) {
								// 开始时间在提醒时间之后，违约“在结束时间前多长时间提醒”的业务逻辑。所以开始时间、结束时间、任务结束前提醒时间三个参数无效
								resultWrap.setParamInvalid("begintime | endtime | taskwarnlist.warntimes");
								return;
							}
						}

					}

				}

			} // 非空验证结束

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mTaskService.add(paramTaskEXT, loginUserInfo);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				TaskEXT taskEXT = mTaskService.queryById(paramTaskEXT);

				// 设置响应客户端的数据
				resultWrap.setData(taskEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveFailure();// 添加失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "add", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 编辑农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:19:46
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> paramMap = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramTaskEXT.getEndtime())) {// 结束时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getTaskparticslist())) {// 参与人
				resultWrap.setParamIsnotEmpty("taskparticslist");
				return;
			} else if (ObjectUtil.isBlank(paramTaskEXT.getIsaffirmpartics())) {// 是否需要参与人确认
				resultWrap.setParamIsnotEmpty("isaffirmpartics");
				return;
			} else if ((1 != paramTaskEXT.getIsaffirmpartics().intValue())
					&& (0 != paramTaskEXT.getIsaffirmpartics().intValue())) {// 是否需要参与人确认的参数无效
				resultWrap.setParamInvalid("isaffirmpartics");
				return;
			} else if (null == paramTaskEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			} else {

				// --------- 农事任务的地块列表 ----------------
				if ((null != paramTaskEXT.getTaskparcellist())) {

					for (TaskparcelEXT taskparcelEXT : paramTaskEXT.getTaskparcellist()) {

						if (null == taskparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("taskparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<TaskparcelEXT> iterator = paramTaskEXT.getTaskparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskparcelEXT tempTaskparcelEXT = null;
					while (iterator.hasNext()) {
						tempTaskparcelEXT = iterator.next();

						if (finalList.contains(tempTaskparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// ----------- 农事任务的参人列表 --------------
				if ((null != paramTaskEXT.getTaskparticslist())) {

					for (TaskparticsEXT taskparticsEXT : paramTaskEXT.getTaskparticslist()) {

						if (null == taskparticsEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("taskparticslist.userid");
							return;
						}

						// 是否需要参与人确认的默认值设置
						if (new Integer(1).equals(paramTaskEXT.getTaskparticsuserid())) {// 1：需要参与人确认

							// 需要参与人确认时，默认将参与人的农事任务参与状态置为：待确认
							taskparticsEXT.setTaskparticsstatus(
									EDict.task_partics_status_waitingconfirm.getDict().getDictValue());

							// 农事任务参与人的农事任务状态
							taskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());

						} else if (new Integer(0).equals(paramTaskEXT.getTaskparticsuserid())) {// 0：不需要参与人确认

							// 不需要参与人确认时，默认将参与人的农事任务参与状态置为：参与
							taskparticsEXT
									.setTaskparticsstatus(EDict.task_partics_status_partake.getDict().getDictValue());

							// 农事任务参与人的农事任务状态
							taskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());

						}
					}

					// 去重
					Iterator<TaskparticsEXT> iterator = paramTaskEXT.getTaskparticslist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskparticsEXT tempTaskparticsEXT = null;
					while (iterator.hasNext()) {
						tempTaskparticsEXT = iterator.next();

						if (finalList.contains(tempTaskparticsEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskparticsEXT.getUserid());
						}
					}

					finalList = null;

				}

				// -------------- 农事任务的抄送人列表 ---------------
				if ((null != paramTaskEXT.getTaskcopylist())) {

					for (TaskcopyEXT taskcopyEXT : paramTaskEXT.getTaskcopylist()) {

						if (null == taskcopyEXT.getUserid()) {// 抄送人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("taskcopylist.userid");
							return;
						}
					}

					// 去重
					Iterator<TaskcopyEXT> iterator = paramTaskEXT.getTaskcopylist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					TaskcopyEXT tempTaskcopyEXT = null;
					while (iterator.hasNext()) {
						tempTaskcopyEXT = iterator.next();

						if (finalList.contains(tempTaskcopyEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempTaskcopyEXT.getUserid());
						}
					}

					finalList = null;
				}

				// --------------- 农事任务提醒方式列表 ---------------------
				if ((null != paramTaskEXT.getTaskwarnlist())) {

					for (TaskwarnEXT taskwarnEXT : paramTaskEXT.getTaskwarnlist()) {

						// 获取农事任务的提醒方式
						EDict farmWarnType = EDict.getEDictByType(EDictType.plan_warntype, taskwarnEXT.getType());

						// 获取农事任务的提醒方式的时间单位
						EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, taskwarnEXT.getTimeunit());

						if ((null == taskwarnEXT.getType())) {// 提醒方式不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.type");
							return;
						} else if ((null == farmWarnType)) {// 提醒方式无效
							resultWrap.setParamInvalid("taskwarnlist.type");
							return;
						} else if ((null == taskwarnEXT.getTimeunit())) {// 提醒方式的时间单位不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.timeunit");
							return;
						} else if ((null == farmTimeUnit)) {// 提醒方式的时间单位无效
							resultWrap.setParamInvalid("taskwarnlist.timeunit");
							return;
						} else if ((null == taskwarnEXT.getWarntimes())) {// 提醒方式的时间不能为空
							resultWrap.setParamIsnotEmpty("taskwarnlist.warntimes");
							return;
						} else if ((taskwarnEXT.getWarntimes().intValue() < 1)) {// 提醒方式的时间无效
							resultWrap.setParamInvalid("taskwarnlist.warntimes");
							return;
						} else {

							// 设置任务提醒的时间
							mTaskService.setTaskwarntime(taskwarnEXT, paramTaskEXT);

							if (paramTaskEXT.getBegintime().after(taskwarnEXT.getWarntime())) {
								// 开始时间在提醒时间之后，违约“在结束时间前多长时间提醒”的业务逻辑。所以开始时间、结束时间、任务结束前提醒时间三个参数无效
								resultWrap.setParamInvalid("begintime | endtime | taskwarnlist.warntimes");
								return;
							}
						}

					}

				}

			} // 非空验证结束

			// 获取原始的农事任务
			final TaskEXT originalTaskEXT = mTaskService.queryById(paramTaskEXT);

			if ((null == originalTaskEXT)) {
				// 农事任务不存在
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-farm-task-object-is-not-exists"));
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mTaskService.edit(paramTaskEXT, originalTaskEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				TaskEXT taskEXT = mTaskService.queryById(paramTaskEXT);

				// 设置响应客户端的数据
				resultWrap.setData(taskEXT);

				resultWrap.setUpdateSuccess();// 更新成功
			} else {
				resultWrap.setUpdateFailure();// 更新失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "update", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 删除农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月16日 上午9:22:16
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/deletebyid")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 非空验证
			if ((null == paramTaskEXT.getId())
					&& ((null == paramTaskEXT.getIdlist()) || (paramTaskEXT.getIdlist().isEmpty()))) {// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农事任务记录
			Integer deleteResult = null;

			if (((null == paramTaskEXT.getIdlist()) || (paramTaskEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mTaskService.deletebyid(paramTaskEXT);
			} else {
				// 批量删除
				deleteResult = mTaskService.batchdeletebyids(paramTaskEXT.getIdlist());
			}

			if ((null != deleteResult) && (deleteResult.intValue() > 0)) {
				resultWrap.setDeleteSuccess();// 删除成功
			} else {
				resultWrap.setDeleteFailure();// 删除失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "deletebyid", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查看农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:21:12
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querybyid")
	public void queryById(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			if (null == paramTaskEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			// 查询出指定ID的农事任务管理
			TaskEXT taskEXT = mTaskService.queryById(paramTaskEXT);

			if ((null != taskEXT)) {
				// 设置农事任务是否可保存到农事记录
				mTaskService.setIscansavetorecordByTask(taskEXT, taskEXT.getTaskparticslist(), loginUserInfo);

				// 该农事任务的参与人已读记录
				mTaskService.setTaskparticsToReaded(taskEXT, loginUserInfo);

				// 该农事任务的抄送人已读记录
				mTaskService.setTaskcopyToReaded(taskEXT, loginUserInfo);
			}

			// 设置响应客户端的数据
			resultWrap.setData(taskEXT);
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryById", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 检索农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querylist")
	public void queryList(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if (ObjectUtil.isNotBlank(paramTaskEXT.getTaskparticsuserid())) {// 查询我参与的任务
				if (ObjectUtil.isNotBlank(paramTaskEXT.getTaskstatus())) {
					// 设置我参与的任务状态
					paramTaskEXT.setTaskparticsusertaskstatus(paramTaskEXT.getTaskstatus());
					paramTaskEXT.setTaskstatus(null);
				}
			}

			// 农事任务开始时间
			String begintimeStr = (null == paramTaskEXT.getBegintime() ? null : map.get("begintime").toString());
			// 农事任务结束时间
			String endtimeStr = (null == paramTaskEXT.getEndtime() ? null : map.get("endtime").toString());

			if ((null != begintimeStr)) {
				// 重置开始时间
				begintimeStr = DateTimeUtil.resetDateRangeStart(begintimeStr);

				paramTaskEXT.setBegintimestr(begintimeStr);

				paramTaskEXT.setBegintime(DateTimeUtil.parse(paramTaskEXT.getBegintimestr()));
			}

			if ((null != endtimeStr)) {
				// 重置结束时间
				endtimeStr = DateTimeUtil.resetDateRangeEnd(endtimeStr);

				paramTaskEXT.setEndtimestr(endtimeStr);

				paramTaskEXT.setEndtime(DateTimeUtil.parse(paramTaskEXT.getEndtimestr()));
			}

			// 如果客户端值传了开始时间，那么查询的是包含在该日期内的所有计划
			if ((null != paramTaskEXT.getBegintime()) && (null == paramTaskEXT.getEndtime())) {
				// 重置结束时间（只是查询当天的数据）
				endtimeStr = DateTimeUtil.resetDateRangeEnd(begintimeStr);

				paramTaskEXT.setEndtimestr(endtimeStr);
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramTaskEXT.setPage(page);
			}

			// 设置数据的查询权限
			mTaskService.setQueryPermisions(paramTaskEXT, loginUserInfo);

			// 将数据保存到数据库并获取添加结果
			List<TaskEXT> taskEXTList = mTaskService.queryList(paramTaskEXT);

			if ((null != taskEXTList) && (taskEXTList.size() > 0)) {

				// 重置任务状态
				mTaskService.resetTaskStatus(taskEXTList);

				// 查询我参与的任务时的任务状态处理
				if ((null != paramTaskEXT.getTaskparticsuserid())) {

					for (TaskEXT taskEXT : taskEXTList) {

						for (TaskparticsEXT taskparticsEXT : taskEXT.getTaskparticslist()) {

							// 设置我参与的任务确认状态
							if ((taskEXT.getId().equals(taskparticsEXT.getTaskid()))
									&& (taskparticsEXT.getUserid().equals(paramTaskEXT.getTaskparticsuserid()))) {

								// 设置农事任务是否可保存到农事记录
								mTaskService.setIscansavetorecordByTask(taskEXT, taskEXT.getTaskparticslist(),
										loginUserInfo);

								// 设置参与人的确认参与农事任务的状态
								taskEXT.setConfirmtaskparticsstatus(taskparticsEXT.getTaskparticsstatus());
								// 设置参与人的确认参与农事任务的状态名称
								taskEXT.setConfirmtaskparticsstatusname(taskparticsEXT.getTaskparticsstatusname());

								// 设置我参与的农事任务的状态
								taskEXT.setTaskstatus(taskparticsEXT.getTaskstatus());
								// 设置我参与的农事任务的状态名称
								taskEXT.setTaskstatusname(taskparticsEXT.getTaskstatusname());

								// 查询的是我参与的农事任务
								break;

							} // 判断是否是该参与人的农事任务 (if)结束
						} // 循环农事任务的参与人 (for)结束
					} // 循环农事任务 (for)结束
				}

			}

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mTaskService.queryListTotalCount(paramTaskEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(taskEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 获取地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/queryparcellist")
	public void queryParcelList(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			ParcelEXT paramParcelEXT = HTMLUtil.getMapToEntity(ParcelEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 该用户所登录的组织（该用户所属的组织）
				paramParcelEXT.setOrgid(loginUserInfo.getLoginorgid());
				// 该用户所登录的组织的部门（该用户组织的所属的部门）
				paramParcelEXT.setDepartmentidlist(loginUserInfo.getDepartmentsupportidlist());
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramParcelEXT.setPage(page);
			}

			// 获取地块
			List<ParcelEXT> parcelEXTList = mCommonService.queryParcelList(paramParcelEXT);

			// 设置响应客户端的数据
			resultWrap.setData(parcelEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryParcelList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 确认农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 下午4:05:44
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/confirmtask")
	public void confirmTask(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskparticsEXT paramTaskparticsEXT = HTMLUtil.getMapToEntity(TaskparticsEXT.class, map);

			// 获取农事参与人的参与状态
			EDict taskparticsstatus = null;

			// 非空验证
			if ((null == paramTaskparticsEXT.getTaskid())) {// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("taskid");
				return;
			} else if ((null == paramTaskparticsEXT.getUserid())) {// 农事任务的参与人的ID不能为空
				resultWrap.setParamIsnotEmpty("userid");
				return;
			} else if ((null == paramTaskparticsEXT.getTaskparticsstatus())) {// 农事任务参与人的状态不能为空
				resultWrap.setParamIsnotEmpty("taskparticsstatus");
				return;
			} else {

				// 获取农事参与人的参与状态
				taskparticsstatus = EDict.getEDictByType(EDictType.task_partics_status,
						paramTaskparticsEXT.getTaskparticsstatus());

				if ((null == taskparticsstatus)) {
					resultWrap.setParamInvalid("taskparticsstatus");
					return;
				}

			}

			// 查询参数
			TaskparticsEXT queryParamTaskparticsEXT = new TaskparticsEXT();

			queryParamTaskparticsEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);// 只查询未删除的
			queryParamTaskparticsEXT.setUserid(paramTaskparticsEXT.getUserid());
			queryParamTaskparticsEXT.setTaskid(paramTaskparticsEXT.getTaskid());

			List<TaskparticsEXT> taskparticsEXTList = mTaskparticsService.queryList(queryParamTaskparticsEXT);

			if ((null == taskparticsEXTList) || (taskparticsEXTList.isEmpty()) || (taskparticsEXTList.size() > 1)) {

				resultWrap.setParamInvalid("userid", "taskid");
				return;

			} else {
				// 需要更新的对象
				TaskparticsEXT updateTaskparticsEXT = taskparticsEXTList.get(0);

				HTMLUtil.updateObject(paramTaskparticsEXT, updateTaskparticsEXT, map);

				if (EDict.task_partics_status_partake.equals(taskparticsstatus)) {// 确认参与

					// 将该参与人的农事任务参与状态变更为：进行中
					updateTaskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());

				} else if (EDict.task_partics_status_refuse.equals(taskparticsstatus)) {// 已拒绝

					// 将该参与人的农事任务参与状态变更为：已拒绝
					updateTaskparticsEXT.setTaskstatus(EDict.task_status_refuse.getDict().getDictValue());

				}

				// 返回更新的结果
				Integer updateResult = mTaskparticsService.confirmTask(updateTaskparticsEXT);

				if ((null == updateResult) || (0 == updateResult.intValue())) {
					resultWrap.setActFailure();// 操作失败
				} else {
					resultWrap.setActSuccess();// 操作成功
				}
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "confirmTask", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 添加农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 下午4:39:46
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/addtaskcomment")
	public void addTaskcomment(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskcommentEXT paramTaskcommentEXT = HTMLUtil.getMapToEntity(TaskcommentEXT.class, map);

			// 非空验证
			if ((null == paramTaskcommentEXT.getTaskid())) {// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("taskid");
				return;
			} else if ((null == paramTaskcommentEXT.getUserid())) {// 农事任务的参与人的ID不能为空
				resultWrap.setParamIsnotEmpty("userid");
				return;
			} else if ((ObjectUtil.isBlank(paramTaskcommentEXT.getContent()))) {// 农事任务的评论不能为空
				resultWrap.setParamIsnotEmpty("content");
				return;
			}

			// 返回新增的的结果
			Integer addResult = mTaskcommentService.add(paramTaskcommentEXT);

			if ((null == addResult) || (0 == addResult.intValue())) {
				resultWrap.setSaveFailure();// 添加失败
			} else {
				resultWrap.setSaveSuccess();// 添加成功
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "addTaskcomment", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查看任务动态
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 下午4:58:05
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querytaskdynamic")
	public void queryTaskdynamic(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskcommentEXT paramTaskcommentEXT = HTMLUtil.getMapToEntity(TaskcommentEXT.class, map);

			// 非空验证
			if ((null == paramTaskcommentEXT.getTaskid())) {// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("taskid");
				return;
			}

			// 检索农事任务的评论
			List<TaskcommentEXT> taskcommentEXTList = mTaskcommentService.queryList(paramTaskcommentEXT);

			if ((null != taskcommentEXTList) && (taskcommentEXTList.size() > 0)) {
				// 完成任务的任务动态标题
				final String commentTitle = LanguageUtil.getLanguageByKey("fwk-farm-task-finish-task");

				for (TaskcommentEXT taskcommentEXT : taskcommentEXTList) {
					if (!(commentTitle.equals(taskcommentEXT.getTitle()))) {
						// 只有已完成的农事动态才有农事记录
						taskcommentEXT.setRecordeid(null);
					}
				}
			}

			// 设置响应客户端的数据
			resultWrap.setData(taskcommentEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryTaskdynamic", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 完成农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月16日 上午9:22:16
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/updateasfinish")
	public void updateAsFinish(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskparticsEXT paramTaskparticsEXT = HTMLUtil.getMapToEntity(TaskparticsEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == paramTaskparticsEXT.getTaskid())) {// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("taskid");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				paramTaskparticsEXT.setUserid(loginUserInfo.getId());
			}

			Integer resultUpdate = mTaskService.updateAsFinish(paramTaskparticsEXT);

			if ((null != resultUpdate) && (resultUpdate.intValue() > 0)) {
				resultWrap.setActSuccess();// 操作成功
			} else {
				resultWrap.setActFailure();// 操作失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "updateAsFinish", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 从回收站删除农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:28:34
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/deleteallfromtrash")
	public void deleteAllFromTrash(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 没有执行要清空的是何种类型的任务时，直接清空与该操作人相关的所有在回收站中的任务
				if ((null == paramTaskEXT.getCreateuserid()) && (null == paramTaskEXT.getTaskparticsuserid())
						&& (null == paramTaskEXT.getTaskcopyuserid())) {

					paramTaskEXT.setCreateuserid(loginUserInfo.getId());// 我发布的任务

					paramTaskEXT.setTaskparticsuserid(loginUserInfo.getId());// 我参与的任务

					paramTaskEXT.setTaskcopyuserid(loginUserInfo.getId());// 抄送我的任务
				}
			}

			Integer resultUpdate = mTaskService.deleteAllFromTrash(paramTaskEXT, loginUserInfo.getId());

			if ((null != resultUpdate) && (resultUpdate.intValue() >= 0)) {
				resultWrap.setDeleteSuccess();// 删除成功
			} else {
				resultWrap.setDeleteFailure();// 删除失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "deleteAllFromTrash", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 从回收站恢复农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:28:34
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/revertfromtrash")
	public void revertFromTrash(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if ((null == paramTaskEXT.getId())
					&& ((null == paramTaskEXT.getIdlist()) || (paramTaskEXT.getIdlist().isEmpty()))) {
				// ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			Integer resultUpdate = mTaskService.revertFromTrash(paramTaskEXT, loginUserInfo.getId());

			if ((null != resultUpdate) && (resultUpdate.intValue() >= 0)) {
				resultWrap.setActSuccess();// 操作成功
			} else {
				resultWrap.setActFailure();// 操作失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "revertFromTrash", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 保存到农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年3月6日 下午2:39:46
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/savetorecord")
	public void saveToRecord(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			TaskparticsEXT paramTaskparticsEXT = HTMLUtil.getMapToEntity(TaskparticsEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if ((null == paramTaskparticsEXT.getTaskid())) {
				// 农事任务的ID不能为空
				resultWrap.setParamIsnotEmpty("taskid");
				return;
			} else if ((null == paramTaskparticsEXT.getRecordeid())) {
				// 农事记录的ID不能为空
				resultWrap.setParamIsnotEmpty("recordeid");
				return;
			} else {
				// 设置参与人的ID
				paramTaskparticsEXT.setUserid(loginUserInfo.getId());
			}

			Integer resultUpdate = mTaskparticsService.saveToRecord(paramTaskparticsEXT);

			if ((null != resultUpdate) && (resultUpdate.intValue() > 0)) {
				resultWrap.setActSuccess();// 操作成功
			} else {
				resultWrap.setActFailure();// 操作失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "saveToRecord", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 检索显示到首页上的农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querylisttohomepage")
	public void queryListToHomePage(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			TaskEXT paramTaskEXT = HTMLUtil.getMapToEntity(TaskEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 当前用户参与的
				paramTaskEXT.setTaskparticsuserid(loginUserInfo.getId());
				// 用户当前登录的组织
				paramTaskEXT.setUserpermisionscreateuserorgid(ObjectUtil.toString(loginUserInfo.getLoginorgid()));
				// 用户当前登录的部门
				paramTaskEXT.setUserpermisionscreateuserdepartmentidlist(loginUserInfo.getDepartmentsupportidlist());
				// 未删除
				paramTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);

				final List<String> taskstatuslist = new ArrayList<String>();
				taskstatuslist.add(EDict.task_status_started.getDict().getDictValue());// 进行中
				taskstatuslist.add(EDict.task_status_overdue.getDict().getDictValue());// 已逾期

				paramTaskEXT.setTaskstatuslist(taskstatuslist);

				// 取最新添加的几条记录，然后再按开始时间倒序排序
				paramTaskEXT.setOrderBySQL(" endtime DESC ");
			}

			// 农事任务开始时间
			String begintimeStr = (null == paramTaskEXT.getBegintime() ? null : map.get("begintime").toString());
			// 农事任务结束时间
			String endtimeStr = (null == paramTaskEXT.getEndtime() ? null : map.get("endtime").toString());

			if ((null != begintimeStr)) {
				// 重置开始时间
				begintimeStr = DateTimeUtil.resetDateRangeStart(begintimeStr);

				paramTaskEXT.setBegintimestr(begintimeStr);

				paramTaskEXT.setBegintime(DateTimeUtil.parse(paramTaskEXT.getBegintimestr()));
			}

			if ((null != endtimeStr)) {
				// 重置结束时间
				endtimeStr = DateTimeUtil.resetDateRangeEnd(endtimeStr);

				paramTaskEXT.setEndtimestr(endtimeStr);

				paramTaskEXT.setEndtime(DateTimeUtil.parse(paramTaskEXT.getEndtimestr()));
			}

			// 如果客户端值传了开始时间，那么查询的是包含在该日期内的所有计划
			if ((null != paramTaskEXT.getBegintime()) && (null == paramTaskEXT.getEndtime())) {
				// 重置结束时间（只是查询当天的数据）
				endtimeStr = DateTimeUtil.resetDateRangeEnd(begintimeStr);

				paramTaskEXT.setEndtimestr(endtimeStr);
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramTaskEXT.setPage(page);
			}

			// 设置数据的查询权限
			mTaskService.setQueryPermisions(paramTaskEXT, loginUserInfo);

			// 将数据保存到数据库并获取添加结果
			List<TaskEXT> taskEXTList = mTaskService.queryList(paramTaskEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mTaskService.queryListTotalCount(paramTaskEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(taskEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryListToHomePage", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询出在首页上显示的未读的任务数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:15:29
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querycountnotreadtasktohomepage")
	public void queryCountNotReadTaskToHomePage(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			Map<String, Object> countNotReadMap = mTaskService.queryCountNotReadTaskToHomePage(loginUserInfo);

			// 设置响应客户端的数据
			resultWrap.setData(countNotReadMap);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryCountNotReadTaskToHomePage", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
}
