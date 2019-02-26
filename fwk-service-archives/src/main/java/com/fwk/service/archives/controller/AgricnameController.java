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
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.archives.entity.AgricnameEXT;
import com.fwk.service.archives.service.AgricnameService;

/**
 * 档案管理模块 — 农资名称管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/agricname")
public class AgricnameController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(AgricnameController.class);

	// 档案管理模块 — 农资名称管理的业务逻辑处理类
	@Autowired
	private AgricnameService mAgricnameEXTService;

	/**
	 * 新增农资名称
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
			AgricnameEXT paramAgricnameEXT = HTMLUtil.getMapToEntity(AgricnameEXT.class, map);

			// 非空验证
			if (null == paramAgricnameEXT.getName()) {// 农资名称不能为空
				resultWrap.setParamIsnotEmpty("name");
				return;
			} else if (null == paramAgricnameEXT.getTypeid()) {// 农资类别不能为空
				resultWrap.setParamIsnotEmpty("typeid");
				return;
			} else if (null == paramAgricnameEXT.getSpecif()) {// 规格不能为空
				resultWrap.setParamIsnotEmpty("specif");
				return;
			} else if (null == paramAgricnameEXT.getSpecifunit()) {// 规格单位不能为空
				resultWrap.setParamIsnotEmpty("specifunit");
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mAgricnameEXTService.add(paramAgricnameEXT);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				AgricnameEXT agricnameEXTEXT = mAgricnameEXTService.queryById(paramAgricnameEXT);

				// 设置响应客户端的数据
				resultWrap.setData(agricnameEXTEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveSuccess();// 添加失败
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
	 * 编辑农资名称
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
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			AgricnameEXT paramAgricnameEXT = HTMLUtil.getMapToEntity(AgricnameEXT.class, map);

			// 非空验证
			if (null == paramAgricnameEXT.getName()) {// 农资名称不能为空
				resultWrap.setParamIsnotEmpty("name");
				return;
			} else if (null == paramAgricnameEXT.getTypeid()) {// 农资类别不能为空
				resultWrap.setParamIsnotEmpty("typeid");
				return;
			} else if (null == paramAgricnameEXT.getSpecif()) {// 规格不能为空
				resultWrap.setParamIsnotEmpty("specif");
				return;
			} else if (null == paramAgricnameEXT.getSpecifunit()) {// 规格单位不能为空
				resultWrap.setParamIsnotEmpty("specifunit");
				return;
			} else if (null == paramAgricnameEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mAgricnameEXTService.update(paramAgricnameEXT);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				AgricnameEXT agricnameEXTEXT = mAgricnameEXTService.queryById(paramAgricnameEXT);

				// 设置响应客户端的数据
				resultWrap.setData(agricnameEXTEXT);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveSuccess();// 添加失败
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
	 * 删除农资名称
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
			AgricnameEXT paramAgricnameEXT = HTMLUtil.getMapToEntity(AgricnameEXT.class, map);

			// 非空验证
			if ((null == paramAgricnameEXT.getId())
					&& ((null == paramAgricnameEXT.getIdlist()) || (paramAgricnameEXT.getIdlist().isEmpty()))) {// 农资发放的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农资发放记录
			Integer deleteResult = null;

			if (((null == paramAgricnameEXT.getIdlist()) || (paramAgricnameEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mAgricnameEXTService.deletebyid(paramAgricnameEXT);
			} else {
				// 批量删除
				deleteResult = mAgricnameEXTService.batchdeletebyids(paramAgricnameEXT.getIdlist());
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
	 * 查看农资名称
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
			AgricnameEXT paramAgricnameEXT = HTMLUtil.getMapToEntity(AgricnameEXT.class, map);

			// 查询出指定ID的农资发放管理
			AgricnameEXT agricnameEXTEXT = mAgricnameEXTService.queryById(paramAgricnameEXT);

			// 设置响应客户端的数据
			resultWrap.setData(agricnameEXTEXT);
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
	 * 检索农资名称
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
			AgricnameEXT paramAgricnameEXT = HTMLUtil.getMapToEntity(AgricnameEXT.class, map);

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramAgricnameEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<AgricnameEXT> agricnameEXTEXTList = mAgricnameEXTService.queryList(paramAgricnameEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mAgricnameEXTService.queryListTotalCount(paramAgricnameEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(agricnameEXTEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

}
