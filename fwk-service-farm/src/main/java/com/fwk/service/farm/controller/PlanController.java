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
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.farm.entity.FWKScheduleEXT;
import com.fwk.service.farm.entity.PlanEXT;
import com.fwk.service.farm.entity.PlanparcelEXT;
import com.fwk.service.farm.entity.PlanparticsEXT;
import com.fwk.service.farm.entity.PlanwarnEXT;
import com.fwk.service.farm.service.PlanService;

/**
 * 农事管理模块 — 农事计划管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	// 农事管理模块 — 农事计划管理的业务逻辑处理对象
	@Autowired
	private PlanService mPlanService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;
	
	@Autowired
	private SupportUserService supportUserService;

	/**
	 * 新增农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramPlanEXT.getDescribe())) {// 农事计划内容
				resultWrap.setParamIsnotEmpty("describe");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getBegintime())) {// 开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getEndtime())) {// 结束时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getPlanparticslist())) {// 参与人
				resultWrap.setParamIsnotEmpty("planparticslist");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 设置发布人
				paramPlanEXT.setCreateuserid(loginUserInfo.getId());

				// --------- 农事计划的地块列表 ----------------
				if ((null != paramPlanEXT.getPlanparcellist())) {

					for (PlanparcelEXT planparcelEXT : paramPlanEXT.getPlanparcellist()) {

						if (null == planparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("planparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<PlanparcelEXT> iterator = paramPlanEXT.getPlanparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					PlanparcelEXT tempPlanparcelEXT = null;
					while (iterator.hasNext()) {
						tempPlanparcelEXT = iterator.next();

						if (finalList.contains(tempPlanparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempPlanparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// ----------- 农事计划的参人列表 --------------
				if ((null != paramPlanEXT.getPlanparticslist())) {

					for (PlanparticsEXT planparticsEXT : paramPlanEXT.getPlanparticslist()) {

						if (null == planparticsEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("planparticslist.userid");
							return;
						}
					}

					// 去重
					Iterator<PlanparticsEXT> iterator = paramPlanEXT.getPlanparticslist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					PlanparticsEXT tempPlanparticsEXT = null;
					while (iterator.hasNext()) {
						tempPlanparticsEXT = iterator.next();

						if (finalList.contains(tempPlanparticsEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempPlanparticsEXT.getUserid());
						}
					}

					finalList = null;

				}

				// --------------- 农事计划提醒方式列表 ---------------------
				if ((null != paramPlanEXT.getPlanwarnlist())) {

					for (PlanwarnEXT planwarnEXT : paramPlanEXT.getPlanwarnlist()) {

						// 获取农事计划的提醒方式
						EDict farmWarnType = EDict.getEDictByType(EDictType.plan_warntype, planwarnEXT.getType());

						// 获取农事计划的提醒方式的时间单位
						EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, planwarnEXT.getTimeunit());

						if ((null == planwarnEXT.getType())) {// 提醒方式不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.type");
							return;
						} else if ((null == farmWarnType)) {// 提醒方式无效
							resultWrap.setParamInvalid("planwarnlist.type");
							return;
						} else if ((null == planwarnEXT.getTimeunit())) {// 提醒方式的时间单位不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.timeunit");
							return;
						} else if ((null == farmTimeUnit)) {// 提醒方式的时间单位无效
							resultWrap.setParamInvalid("planwarnlist.timeunit");
							return;
						} else if ((null == planwarnEXT.getWarntimes())) {// 提醒方式的时间不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.warntimes");
							return;
						} else if ((planwarnEXT.getWarntimes().intValue() < 1)) {// 提醒方式的时间无效
							resultWrap.setParamInvalid("planwarnlist.warntimes");
							return;
						} else {

							// 设置任务提醒的时间
							mPlanService.setPlanwarntime(planwarnEXT, paramPlanEXT);

							if (planwarnEXT.getWarntime().after(paramPlanEXT.getBegintime())) {
								// 开始时间在提醒时间之后，违约“在开始时间前多长时间提醒”的业务逻辑。所以开始时间、结束时间、任务结束前提醒时间三个参数无效
								resultWrap.setParamInvalid("begintime | endtime | planwarnlist.warntimes");
								return;
							}
						}

					}

				}

			} // 非空验证结束

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPlanService.add(paramPlanEXT, loginUserInfo);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PlanEXT planEXT = mPlanService.queryById(paramPlanEXT);

				// 设置响应客户端的数据
				resultWrap.setData(planEXT);

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
	 * 编辑农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, paramMap);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramPlanEXT.getDescribe())) {// 农事计划内容
				resultWrap.setParamIsnotEmpty("describe");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getBegintime())) {// 开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getEndtime())) {// 结束时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramPlanEXT.getPlanparticslist())) {// 参与人
				resultWrap.setParamIsnotEmpty("planparticslist");
				return;
			} else if (null == paramPlanEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 设置发布人
				paramPlanEXT.setCreateuserid(loginUserInfo.getId());

				// --------- 农事计划的地块列表 ----------------
				if ((null != paramPlanEXT.getPlanparcellist())) {

					for (PlanparcelEXT planparcelEXT : paramPlanEXT.getPlanparcellist()) {

						if (null == planparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("planparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<PlanparcelEXT> iterator = paramPlanEXT.getPlanparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					PlanparcelEXT tempPlanparcelEXT = null;
					while (iterator.hasNext()) {
						tempPlanparcelEXT = iterator.next();

						if (finalList.contains(tempPlanparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempPlanparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// ----------- 农事计划的参人列表 --------------
				if ((null != paramPlanEXT.getPlanparticslist())) {

					for (PlanparticsEXT planparticsEXT : paramPlanEXT.getPlanparticslist()) {

						if (null == planparticsEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("planparticslist.userid");
							return;
						}
					}

					// 去重
					Iterator<PlanparticsEXT> iterator = paramPlanEXT.getPlanparticslist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					PlanparticsEXT tempPlanparticsEXT = null;
					while (iterator.hasNext()) {
						tempPlanparticsEXT = iterator.next();

						if (finalList.contains(tempPlanparticsEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempPlanparticsEXT.getUserid());
						}
					}

					finalList = null;

				}

				// --------------- 农事计划提醒方式列表 ---------------------
				if ((null != paramPlanEXT.getPlanwarnlist())) {

					for (PlanwarnEXT planwarnEXT : paramPlanEXT.getPlanwarnlist()) {

						// 获取农事计划的提醒方式
						EDict farmWarnType = EDict.getEDictByType(EDictType.plan_warntype, planwarnEXT.getType());

						// 获取农事计划的提醒方式的时间单位
						EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, planwarnEXT.getTimeunit());

						if ((null == planwarnEXT.getType())) {// 提醒方式不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.type");
							return;
						} else if ((null == farmWarnType)) {// 提醒方式无效
							resultWrap.setParamInvalid("planwarnlist.type");
							return;
						} else if ((null == planwarnEXT.getTimeunit())) {// 提醒方式的时间单位不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.timeunit");
							return;
						} else if ((null == farmTimeUnit)) {// 提醒方式的时间单位无效
							resultWrap.setParamInvalid("planwarnlist.timeunit");
							return;
						} else if ((null == planwarnEXT.getWarntimes())) {// 提醒方式的时间不能为空
							resultWrap.setParamIsnotEmpty("planwarnlist.warntimes");
							return;
						} else if ((planwarnEXT.getWarntimes().intValue() < 1)) {// 提醒方式的时间无效
							resultWrap.setParamInvalid("planwarnlist.warntimes");
							return;
						} else {

							// 设置任务提醒的时间
							mPlanService.setPlanwarntime(planwarnEXT, paramPlanEXT);

							if (planwarnEXT.getWarntime().after(paramPlanEXT.getBegintime())) {
								// 开始时间在提醒时间之后，违约“在开始时间前多长时间提醒”的业务逻辑。所以开始时间、结束时间、任务结束前提醒时间三个参数无效
								resultWrap.setParamInvalid("begintime | endtime | planwarnlist.warntimes");
								return;
							}
						}

					}

				}

			} // 非空验证结束

			// 获取原始的农事计划
			final PlanEXT originalPlanEXT = mPlanService.queryById(paramPlanEXT);

			if ((null == originalPlanEXT)) {
				// 农事计划不存在
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-farm-plan-object-is-not-exists"));
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPlanService.edit(paramPlanEXT, originalPlanEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PlanEXT planEXT = mPlanService.queryById(paramPlanEXT);

				// 设置响应客户端的数据
				resultWrap.setData(planEXT);

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
	 * 删除农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, map);

			// 非空验证
			if ((null == paramPlanEXT.getId())
					&& ((null == paramPlanEXT.getIdlist()) || (paramPlanEXT.getIdlist().isEmpty()))) {// 农资发放的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农资发放记录
			Integer deleteResult = null;

			if (((null == paramPlanEXT.getIdlist()) || (paramPlanEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mPlanService.deletebyid(paramPlanEXT);
			} else {
				// 批量删除
				deleteResult = mPlanService.batchdeletebyids(paramPlanEXT.getIdlist());
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
			ErrorUtil.writeLoggerError(logger, "queryById", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查看农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if (null == paramPlanEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 查询出指定ID的农资发放管理
			PlanEXT planEXT = mPlanService.queryById(paramPlanEXT);

			// 该农事计划的参与人已读计划
			mPlanService.setPlanparticsToReaded(planEXT, loginUserInfo);

			// 设置响应客户端的数据
			resultWrap.setData(planEXT);
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
	 * 检索农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			// 农事计划开始时间
			String begintimeStr = (null == paramPlanEXT.getBegintime() ? null : map.get("begintime").toString());
			// 农事计划结束时间
			String endtimeStr = (null == paramPlanEXT.getEndtime() ? null : map.get("endtime").toString());

			if ((null != begintimeStr)) {
				// 重置开始时间
				begintimeStr = DateTimeUtil.resetDateRangeStart(begintimeStr);

				paramPlanEXT.setBegintimestr(begintimeStr);

				paramPlanEXT.setBegintime(DateTimeUtil.parse(paramPlanEXT.getBegintimestr()));
			}

			if ((null != endtimeStr)) {

				endtimeStr = (endtimeStr.endsWith(" 00:00:00") ? endtimeStr.replaceAll(" 00:00:00", "") : endtimeStr);

				// 重置结束时间
				endtimeStr = DateTimeUtil.resetDateRangeEnd(endtimeStr);

				paramPlanEXT.setEndtimestr(endtimeStr);

				paramPlanEXT.setEndtime(DateTimeUtil.parse(paramPlanEXT.getEndtimestr()));
			}

			// 如果客户端值传了开始时间，那么查询的是包含在该日期内的所有计划
			if ((null != paramPlanEXT.getBegintime()) && (null == paramPlanEXT.getEndtime())) {
				// 重置结束时间（只是查询当天的数据）
				endtimeStr = DateTimeUtil.resetDateRangeEnd(begintimeStr);

				paramPlanEXT.setEndtimestr(endtimeStr);
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramPlanEXT.setPage(page);
			}
			
			loginUserInfo.setUserIds(supportUserService.getDepartUsersByLoginerId(map.get("loginerid")+"",map.get("loginorgid")+""));

			// 设置数据的查询权限
			mPlanService.setQueryPermisions(paramPlanEXT, loginUserInfo);

			if(loginUserInfo.getUserIds() != null && loginUserInfo.getUserIds().size() >= 1) {
				paramPlanEXT.setUserIds(loginUserInfo.getUserIds());
			}
			
			// 将数据保存到数据库并获取添加结果
			List<PlanEXT> planEXTList = mPlanService.queryList(paramPlanEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mPlanService.queryListTotalCount(paramPlanEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(planEXTList);

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
	 * 检索显示到首页上的农事计划
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
			PlanEXT paramPlanEXT = HTMLUtil.getMapToEntity(PlanEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			// 农事计划开始时间
			String begintimeStr = (null == paramPlanEXT.getBegintime() ? null : map.get("begintime").toString());
			// 农事计划结束时间
			String endtimeStr = (null == paramPlanEXT.getEndtime() ? null : map.get("endtime").toString());

			if ((null != begintimeStr)) {
				// 重置开始时间
				begintimeStr = DateTimeUtil.resetDateRangeStart(begintimeStr);

				paramPlanEXT.setBegintimestr(begintimeStr);

				paramPlanEXT.setBegintime(DateTimeUtil.parse(paramPlanEXT.getBegintimestr()));
			}

			if ((null != endtimeStr)) {
				// 重置结束时间
				endtimeStr = DateTimeUtil.resetDateRangeEnd(endtimeStr);

				paramPlanEXT.setEndtimestr(endtimeStr);

				paramPlanEXT.setEndtime(DateTimeUtil.parse(paramPlanEXT.getEndtimestr()));
			}

			// 如果客户端值传了开始时间，那么查询的是包含在该日期内的所有计划
			if ((null != paramPlanEXT.getBegintime()) && (null == paramPlanEXT.getEndtime())) {
				// 重置结束时间（只是查询当天的数据）
				endtimeStr = DateTimeUtil.resetDateRangeEnd(begintimeStr);

				paramPlanEXT.setEndtimestr(endtimeStr);
			}

			FWKScheduleEXT fwkScheduleEXT = mPlanService.queryFWKScheduleEXT(paramPlanEXT, loginUserInfo);

			// 设置响应客户端的数据
			resultWrap.setData(fwkScheduleEXT);

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
	 * 查询出在首页上显示的未读的日程数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:15:29
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querycountnotreadscheduletohomepage")
	public void queryCountNotReadScheduleToHomePage(HttpServletRequest request, HttpServletResponse response) {
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

			Map<String, Object> countNotReadMap = mPlanService.queryCountNotReadScheduleToHomePage(loginUserInfo);

			// 设置响应客户端的数据
			resultWrap.setData(countNotReadMap);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryCountNotReadScheduleToHomePage", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

}
