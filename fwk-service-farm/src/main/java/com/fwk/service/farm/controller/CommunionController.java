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

import com.fwk.common.util.ErrorUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.entity.CommunionEXT;
import com.fwk.service.farm.entity.CommunioncategoryEXT;
import com.fwk.service.farm.entity.CommunioncommentEXT;
import com.fwk.service.farm.entity.CommunioncommentreversionEXT;
import com.fwk.service.farm.entity.CommunionparcelEXT;
import com.fwk.service.farm.service.CommunionService;
import com.fwk.service.farm.service.CommunioncommentService;
import com.fwk.service.farm.service.CommunioncommentreversionService;

/**
 * 农事管理模块 — 农事交流管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/communion")
public class CommunionController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(CommunionController.class);

	// 农事管理模块 — 农事交流管理的业务逻辑处理对象
	@Autowired
	private CommunionService mCommunionService;

	// 农事交流的评论的业务逻辑处理对象
	@Autowired
	private CommunioncommentService mCommunioncommentService;

	// 农事交流的评论的回复的业务逻辑处理对象
	@Autowired
	private CommunioncommentreversionService mCommunioncommentreversionService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农事交流
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
			CommunionEXT paramCommunionEXT = HTMLUtil.getMapToEntity(CommunionEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunionEXT.getCommunioncontent())) {// 提问内容不能为空
				resultWrap.setParamIsnotEmpty("communioncontent");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 设置发布人
				paramCommunionEXT.setCreateuserid(loginUserInfo.getId());

				if (ObjectUtil.isBlank(paramCommunionEXT.getLocation())) {
					// 设置用户所在的地理位置
					mCommunionService.setLocation(request, paramCommunionEXT);
				}

				// 农事交流的品类列表
				if ((null != paramCommunionEXT.getCommunioncategorylist())) {

					for (CommunioncategoryEXT communioncategoryEXT : paramCommunionEXT.getCommunioncategorylist()) {

						if (null == communioncategoryEXT.getCategoryid()) {// 品类的ID不能为空
							resultWrap.setParamIsnotEmpty("communioncategorylist.categoryid");
							return;
						}
					}

					// 去重
					Iterator<CommunioncategoryEXT> iterator = paramCommunionEXT.getCommunioncategorylist().iterator();
					List<String> finalList = new ArrayList<String>();

					CommunioncategoryEXT tempCommunioncategoryEXT = null;
					while (iterator.hasNext()) {
						tempCommunioncategoryEXT = iterator.next();

						if (finalList.contains(tempCommunioncategoryEXT.getCategoryid())) {
							iterator.remove();
						} else {
							finalList.add(tempCommunioncategoryEXT.getCategoryid());
						}
					}

					finalList = null;

				}

				// 农事交流的地块列表
				if ((null != paramCommunionEXT.getCommunionparcellist())) {

					for (CommunionparcelEXT communionparcelEXT : paramCommunionEXT.getCommunionparcellist()) {

						if (null == communionparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("communionparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<CommunionparcelEXT> iterator = paramCommunionEXT.getCommunionparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					CommunionparcelEXT tempCommunionparcelEXT = null;
					while (iterator.hasNext()) {
						tempCommunionparcelEXT = iterator.next();

						if (finalList.contains(tempCommunionparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempCommunionparcelEXT.getParcelid());
						}
					}

					finalList = null;
				}

			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mCommunionService.add(paramCommunionEXT, loginUserInfo);

			if (addResult.intValue() > 0) {

				// 添加成功之后把产品信息返回到客户端
				CommunionEXT queryParamCommunionEXT = new CommunionEXT();
				queryParamCommunionEXT.setId(paramCommunionEXT.getId());

				CommunionEXT communionEXT = mCommunionService.queryById(queryParamCommunionEXT);

				// 设置响应客户端的数据
				resultWrap.setData(communionEXT);

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
	 * 编辑农事交流
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
			CommunionEXT paramCommunionEXT = HTMLUtil.getMapToEntity(CommunionEXT.class, paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunionEXT.getCommunioncontent())) {// 提问内容不能为空
				resultWrap.setParamIsnotEmpty("communioncontent");
				return;
			} else if (null == paramCommunionEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			} else {

				// 农事交流的品类列表
				if ((null != paramCommunionEXT.getCommunioncategorylist())) {

					for (CommunioncategoryEXT communioncategoryEXT : paramCommunionEXT.getCommunioncategorylist()) {

						if (null == communioncategoryEXT.getCategoryid()) {// 品类的ID不能为空
							resultWrap.setParamIsnotEmpty("communioncategorylist.categoryid");
							return;
						}
					}

					// 去重
					Iterator<CommunioncategoryEXT> iterator = paramCommunionEXT.getCommunioncategorylist().iterator();
					List<String> finalList = new ArrayList<String>();

					CommunioncategoryEXT tempCommunioncategoryEXT = null;
					while (iterator.hasNext()) {
						tempCommunioncategoryEXT = iterator.next();

						if (finalList.contains(tempCommunioncategoryEXT.getCategoryid())) {
							iterator.remove();
						} else {
							finalList.add(tempCommunioncategoryEXT.getCategoryid());
						}
					}

					finalList = null;

				}

				// 农事交流的地块列表
				if ((null != paramCommunionEXT.getCommunionparcellist())) {

					for (CommunionparcelEXT communionparcelEXT : paramCommunionEXT.getCommunionparcellist()) {

						if (null == communionparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("communionparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<CommunionparcelEXT> iterator = paramCommunionEXT.getCommunionparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					CommunionparcelEXT tempCommunionparcelEXT = null;
					while (iterator.hasNext()) {
						tempCommunionparcelEXT = iterator.next();

						if (finalList.contains(tempCommunionparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempCommunionparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

			}

			// 从数据库中检索出原来的农事交流
			final CommunionEXT originalCommunionEXT = mCommunionService.queryById(paramCommunionEXT);

			if ((null == originalCommunionEXT)) {
				// 农事交流不存在
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-farm-communion-object-is-not-exists"));
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mCommunionService.edit(paramCommunionEXT, originalCommunionEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				CommunionEXT communionEXT = mCommunionService.queryById(paramCommunionEXT);

				// 设置响应客户端的数据
				resultWrap.setData(communionEXT);

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
	 * 删除农事交流
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
			CommunionEXT paramCommunionEXT = HTMLUtil.getMapToEntity(CommunionEXT.class, map);

			// 非空验证
			if ((null == paramCommunionEXT.getId())
					&& ((null == paramCommunionEXT.getIdlist()) || (paramCommunionEXT.getIdlist().isEmpty()))) {// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农事交流记录
			Integer deleteResult = null;

			if (((null == paramCommunionEXT.getIdlist()) || (paramCommunionEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mCommunionService.deletebyid(paramCommunionEXT);
			} else {
				// 批量删除
				deleteResult = mCommunionService.batchdeletebyids(paramCommunionEXT.getIdlist());
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
			ErrorUtil.writeLoggerError(logger, "delete", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查看农事交流
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
			CommunionEXT paramCommunionEXT = HTMLUtil.getMapToEntity(CommunionEXT.class, map);

			if (null == paramCommunionEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 查询出指定ID的农事交流管理
			CommunionEXT communionEXT = mCommunionService.queryById(paramCommunionEXT);

			// 设置响应客户端的数据
			resultWrap.setData(communionEXT);
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
	 * 检索农事交流
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
			CommunionEXT paramCommunionEXT = HTMLUtil.getMapToEntity(CommunionEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 用户当前登录的组织
				paramCommunionEXT.setUserloginorgid(loginUserInfo.getLoginorgid());

			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramCommunionEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<CommunionEXT> communionEXTList = mCommunionService.queryList(paramCommunionEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mCommunionService.queryListTotalCount(paramCommunionEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置详情
			mCommunionService.setDetail(communionEXTList);

			// 设置响应客户端的数据
			resultWrap.setData(communionEXTList);

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
	 * 新增农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:31:37
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentcontent/add")
	public void addCommentcontent(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentEXT paramCommunioncommentEXT = HTMLUtil.getMapToEntity(CommunioncommentEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunioncommentEXT.getCommentcontent())) {// 评论内容不能为空
				resultWrap.setParamIsnotEmpty("commentcontent");
				return;
			} else if (ObjectUtil.isBlank(paramCommunioncommentEXT.getCommunionid())) {// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("communionid");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 设置发布人
				paramCommunioncommentEXT.setCommentuserid(loginUserInfo.getId());
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mCommunioncommentService.add(paramCommunioncommentEXT);

			if (addResult.intValue() > 0) {

				// 添加成功之后把产品信息返回到客户端
				CommunioncommentEXT queryParamCommunioncommentEXT = new CommunioncommentEXT();
				queryParamCommunioncommentEXT.setId(paramCommunioncommentEXT.getId());

				CommunioncommentEXT communioncommentEXT = mCommunioncommentService
						.queryById(queryParamCommunioncommentEXT);

				// 设置响应客户端的数据
				resultWrap.setData(communioncommentEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveFailure();// 添加失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "addCommentcontent", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 删除农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午2:43:51
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentcontent/deletebyid")
	public void deleteCommunioncomment(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentEXT paramCommunioncommentEXT = HTMLUtil.getMapToEntity(CommunioncommentEXT.class, map);

			// 非空验证
			if ((null == paramCommunioncommentEXT.getId()) && ((null == paramCommunioncommentEXT.getIdlist())
					|| (paramCommunioncommentEXT.getIdlist().isEmpty()))) {// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农事交流记录
			Integer deleteResult = null;

			if (((null == paramCommunioncommentEXT.getIdlist()) || (paramCommunioncommentEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mCommunioncommentService.deletebyid(paramCommunioncommentEXT);
			} else {
				// 批量删除
				deleteResult = mCommunioncommentService.batchdeletebyids(paramCommunioncommentEXT.getIdlist());
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
			ErrorUtil.writeLoggerError(logger, "deleteCommunioncomment", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 新增农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:31:37
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentreversion/add")
	public void addCommentreversion(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentreversionEXT paramCommunioncommentreversionEXT = HTMLUtil
					.getMapToEntity(CommunioncommentreversionEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunioncommentreversionEXT.getReversioncontent())) {// 评论内容不能为空
				resultWrap.setParamIsnotEmpty("reversioncontent");
				return;
			} else if (ObjectUtil.isBlank(paramCommunioncommentreversionEXT.getCommunionid())) {// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("communionid");
				return;
			} else if (ObjectUtil.isBlank(paramCommunioncommentreversionEXT.getCommunioncommentid())) {// 农事交流的评论的ID不能为空
				resultWrap.setParamIsnotEmpty("communioncommentid");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 设置发布人
				paramCommunioncommentreversionEXT.setReversionuserid(loginUserInfo.getId());
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mCommunioncommentreversionService.add(paramCommunioncommentreversionEXT);

			if (addResult.intValue() > 0) {

				// 添加成功之后把产品信息返回到客户端
				CommunioncommentreversionEXT queryParamCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
				queryParamCommunioncommentreversionEXT.setId(paramCommunioncommentreversionEXT.getId());

				CommunioncommentreversionEXT communioncommentEXT = mCommunioncommentreversionService
						.queryById(queryParamCommunioncommentreversionEXT);

				// 设置响应客户端的数据
				resultWrap.setData(communioncommentEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveFailure();// 添加失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "addCommentreversion", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 删除农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午3:30:51
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentcontentreversion/deletebyid")
	public void deleteCommunioncommentreversion(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentreversionEXT paramCommunioncommentreversionEXT = HTMLUtil
					.getMapToEntity(CommunioncommentreversionEXT.class, map);

			// 非空验证
			if ((null == paramCommunioncommentreversionEXT.getId())
					&& ((null == paramCommunioncommentreversionEXT.getIdlist())
							|| (paramCommunioncommentreversionEXT.getIdlist().isEmpty()))) {// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农事交流记录
			Integer deleteResult = null;

			if (((null == paramCommunioncommentreversionEXT.getIdlist())
					|| (paramCommunioncommentreversionEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mCommunioncommentreversionService.deletebyid(paramCommunioncommentreversionEXT);
			} else {
				// 批量删除
				deleteResult = mCommunioncommentreversionService
						.batchdeletebyids(paramCommunioncommentreversionEXT.getIdlist());
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
			ErrorUtil.writeLoggerError(logger, "deleteCommunioncommentreversion", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询某个农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年3月7日 下午1:46:00
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentcontent/querylistbycommunionid")
	public void queryCommunioncommentListByCommunionid(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentEXT paramCommunioncommentEXT = HTMLUtil.getMapToEntity(CommunioncommentEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunioncommentEXT.getCommunionid())) {
				// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("communionid");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramCommunioncommentEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<CommunioncommentEXT> communioncommentEXTList = mCommunioncommentService
					.queryList(paramCommunioncommentEXT);

			if ((null != communioncommentEXTList) && (communioncommentEXTList.size() > 0)) {

				for (CommunioncommentEXT communioncommentEXT : communioncommentEXTList) {

					// 是自己发布的评论可以删除
					if (communioncommentEXT.getCommentuserid().equals(loginUserInfo.getId())) {
						communioncommentEXT.setEditable(true);
					}
				}

			}

			// 是分页查询
			if (null != page) {
				// 查询出总的记录数量
				Integer totalCount = mCommunioncommentService.queryListTotalCount(paramCommunioncommentEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(communioncommentEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryCommunioncommentListByCommunionid", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询某个农事交流的评论的所有回复
	 * 
	 * @author wangjunwen
	 * @date 2018年3月7日 下午1:46:00
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/commentcontentreversion/querylistbycommunioncommentid")
	public void queryCommunioncommentreversionListByCommunioncommentid(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			CommunioncommentreversionEXT paramCommunioncommentreversionEXT = HTMLUtil
					.getMapToEntity(CommunioncommentreversionEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if (ObjectUtil.isBlank(paramCommunioncommentreversionEXT.getCommunioncommentid())) {
				// 农事交流的ID不能为空
				resultWrap.setParamIsnotEmpty("communioncommentid");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramCommunioncommentreversionEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<CommunioncommentreversionEXT> CommunioncommentreversionEXTList = mCommunioncommentreversionService
					.queryList(paramCommunioncommentreversionEXT);

			if ((null != CommunioncommentreversionEXTList) && (CommunioncommentreversionEXTList.size() > 0)) {

				CommunionEXT paramCommunionEXT = new CommunionEXT();
				paramCommunionEXT.setId(CommunioncommentreversionEXTList.get(0).getCommunionid());

				CommunionEXT communionEXT = mCommunionService.queryById(paramCommunionEXT);

				for (CommunioncommentreversionEXT communioncommentreversionEXT : CommunioncommentreversionEXTList) {

					// 是楼主
					if (communioncommentreversionEXT.getReversionuserid().equals(communionEXT.getCreateuserid())) {
						communioncommentreversionEXT.setReversionuserisownercommunion(true);
					}

					// 是自己发布的回复可以删除
					if (communioncommentreversionEXT.getReversionuserid().equals(loginUserInfo.getId())) {
						communioncommentreversionEXT.setEditable(true);
					}
				}

			}

			// 是分页查询
			if (null != page) {
				// 查询出总的记录数量
				Integer totalCount = mCommunioncommentreversionService
						.queryListTotalCount(paramCommunioncommentreversionEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(CommunioncommentreversionEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryCommunioncommentreversionListByCommunioncommentid", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

}
