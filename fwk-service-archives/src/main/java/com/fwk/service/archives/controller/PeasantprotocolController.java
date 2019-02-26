package com.fwk.service.archives.controller;

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
import com.fwk.service.archives.entity.PeasantEXT;
import com.fwk.service.archives.entity.PeasantprotocolEXT;
import com.fwk.service.archives.service.PeasantprotocolService;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.service.CommonService;

/**
 * 档案管理模块 — 农户合作协议管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/peasantprotocol")
public class PeasantprotocolController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(PeasantprotocolController.class);

	// 档案管理模块 — 农户合作协议管理的业务逻辑处理类
	@Autowired
	private PeasantprotocolService mPeasantprotocolService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农户合作协议
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
			PeasantprotocolEXT paramPeasantprotocolEXT = HTMLUtil.getMapToEntity(PeasantprotocolEXT.class, map);

			// 非空验证
			if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getDocument())) {// 协议编号
				resultWrap.setParamIsnotEmpty("document");
				return;
			}
			// else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getName()))
			// {// 协议名称
			// resultWrap.setParamIsnotEmpty("name");
			// return;
			// }
			else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getBegintime())) {// 协议执行开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getEndtime())) {// 协议执行截止时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getPeasantid())) {// 合作农户
				resultWrap.setParamIsnotEmpty("peasantid");
				return;
			} else if ((paramPeasantprotocolEXT.getBegintime().getTime() > paramPeasantprotocolEXT.getEndtime()
					.getTime())) {// 协议执行截至时间必须大于协议执行开始时间
				resultWrap.error(
						LanguageUtil.getLanguageByKey("fwk-archives-peasantprotocol-endtime-less-than-begintime"));
				return;
			} else {

				// 设置农户合作协议的执行状态
				mPeasantprotocolService.setPeasantprotocolActstate(paramPeasantprotocolEXT);

			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPeasantprotocolService.add(paramPeasantprotocolEXT);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PeasantprotocolEXT peasantprotocolEXT = mPeasantprotocolService.queryById(paramPeasantprotocolEXT);

				// 设置响应客户端的数据
				resultWrap.setData(peasantprotocolEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveFailure();// 添加失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "add", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 编辑农户合作协议
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
			PeasantprotocolEXT paramPeasantprotocolEXT = HTMLUtil.getMapToEntity(PeasantprotocolEXT.class, paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getDocument())) {// 协议编号
				resultWrap.setParamIsnotEmpty("document");
				return;
			}
			// else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getName()))
			// {// 协议名称
			// resultWrap.setParamIsnotEmpty("name");
			// return;
			// }
			else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getBegintime())) {// 协议执行开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getEndtime())) {// 协议执行截止时间
				resultWrap.setParamIsnotEmpty("endtime");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantprotocolEXT.getPeasantid())) {// 合作农户
				resultWrap.setParamIsnotEmpty("peasantid");
				return;
			} else if ((paramPeasantprotocolEXT.getBegintime().getTime() > paramPeasantprotocolEXT.getEndtime()
					.getTime())) {// 协议执行截至时间必须大于协议执行开始时间
				resultWrap.error(
						LanguageUtil.getLanguageByKey("fwk-archives-peasantprotocol-endtime-less-than-begintime"));
				return;
			} else if (null == paramPeasantprotocolEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			} else {

				// 设置农户合作协议的执行状态
				mPeasantprotocolService.setPeasantprotocolActstate(paramPeasantprotocolEXT);

			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPeasantprotocolService.edit(paramPeasantprotocolEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PeasantprotocolEXT peasantprotocolEXT = mPeasantprotocolService.queryById(paramPeasantprotocolEXT);

				// 设置响应客户端的数据
				resultWrap.setData(peasantprotocolEXT);

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
	 * 删除农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月16日 上午9:22:16
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			PeasantprotocolEXT paramPeasantprotocolEXT = HTMLUtil.getMapToEntity(PeasantprotocolEXT.class, map);

			// 非空验证
			if ((null == paramPeasantprotocolEXT.getId()) && ((null == paramPeasantprotocolEXT.getIdlist())
					|| (paramPeasantprotocolEXT.getIdlist().isEmpty()))) {// 农资发放的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农资发放记录
			Integer deleteResult = null;

			if (((null == paramPeasantprotocolEXT.getIdlist()) || (paramPeasantprotocolEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mPeasantprotocolService.deletebyid(paramPeasantprotocolEXT);
			} else {
				// 批量删除
				deleteResult = mPeasantprotocolService.batchdeletebyids(paramPeasantprotocolEXT.getIdlist());
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
	 * 查看农户合作协议
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
			PeasantprotocolEXT paramPeasantprotocolEXT = HTMLUtil.getMapToEntity(PeasantprotocolEXT.class, map);

			// 查询出指定ID的农资发放管理
			PeasantprotocolEXT peasantprotocolEXT = mPeasantprotocolService.queryById(paramPeasantprotocolEXT);

			// 设置响应客户端的数据
			resultWrap.setData(peasantprotocolEXT);
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
	 * 检索农户合作协议
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
			PeasantprotocolEXT paramPeasantprotocolEXT = HTMLUtil.getMapToEntity(PeasantprotocolEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 只能查询自己组织下的农户合作协议
				paramPeasantprotocolEXT.setSupportorgid(loginUserInfo.getLoginorgid());
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramPeasantprotocolEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<PeasantprotocolEXT> peasantprotocolEXTList = mPeasantprotocolService
					.queryList(paramPeasantprotocolEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mPeasantprotocolService.queryListTotalCount(paramPeasantprotocolEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(peasantprotocolEXTList);

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
	 * 获取合作农户
	 * 
	 * @author wangjunwen
	 * @date 2018年1月29日 下午3:59:16
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querypeasantlist")
	public void queryPeasantList(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			final PeasantEXT paramPeasantEXT = new PeasantEXT();

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 只能查询自己组织下的农户
				paramPeasantEXT.setOrgid(loginUserInfo.getLoginorgid());
			}

			// 获取合作农户
			List<PeasantEXT> list = mPeasantprotocolService.queryPeasantList(paramPeasantEXT);

			// 设置响应客户端的数据
			resultWrap.setData(list);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryPeasantList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

}
