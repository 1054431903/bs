package com.fwk.service.archives.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.ErrorUtil;
import com.fwk.common.util.FileUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.PoiUtils;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.archives.entity.PeasantEXT;
import com.fwk.service.archives.service.PeasantService;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.service.FileUploadDownloadService;
import com.fwk.service.common.service.FileUploadDownloadService.FileUploadEXT;

/**
 * 档案管理模块 — 合作农户档案管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/peasant")
public class PeasantController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(PeasantController.class);

	// 档案管理模块 — 合作农户档案管理的业务逻辑处理类
	@Autowired
	private PeasantService mPeasantEXTService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 文件上传或者下载的处理工具
	@Autowired
	private FileUploadDownloadService mFileUploadDownloadService;

	/**
	 * 新增合作农户档案
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
			PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, map);

			// 非空验证
			if (ObjectUtil.isBlank(paramPeasantEXT.getName())) {// 农户姓名
				resultWrap.setParamIsnotEmpty("name");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getLinkphone())) {// 联系电话
				resultWrap.setParamIsnotEmpty("linkphone");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getAddresscode())) {// 地址编号
				resultWrap.setParamIsnotEmpty("addresscode");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getSex())) {// 性别
				resultWrap.setParamIsnotEmpty("sex");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getIdentity())) {// 身份证号
				resultWrap.setParamIsnotEmpty("identity");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getBirthday())) {// 出生日期
				resultWrap.setParamIsnotEmpty("birthday");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getCulture())) {// 文化程度
				resultWrap.setParamIsnotEmpty("culture");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getOrgid())) {// 责任部门
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getCooperstate())) {// 当前合作状态
				resultWrap.setParamIsnotEmpty("cooperstate");
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPeasantEXTService.add(paramPeasantEXT);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PeasantEXT peasantEXT = mPeasantEXTService.queryById(paramPeasantEXT);

				// 设置响应客户端的数据
				resultWrap.setData(peasantEXT);

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
	 * 编辑合作农户档案
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
			PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramPeasantEXT.getName())) {// 农户姓名
				resultWrap.setParamIsnotEmpty("name");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getLinkphone())) {// 联系电话
				resultWrap.setParamIsnotEmpty("linkphone");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getAddresscode())) {// 地址编号
				resultWrap.setParamIsnotEmpty("addresscode");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getSex())) {// 性别
				resultWrap.setParamIsnotEmpty("sex");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getIdentity())) {// 身份证号
				resultWrap.setParamIsnotEmpty("identity");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getBirthday())) {// 出生日期
				resultWrap.setParamIsnotEmpty("birthday");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getCulture())) {// 文化程度
				resultWrap.setParamIsnotEmpty("culture");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getOrgid())) {// 责任部门
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			} else if (ObjectUtil.isBlank(paramPeasantEXT.getCooperstate())) {// 当前合作状态
				resultWrap.setParamIsnotEmpty("cooperstate");
				return;
			} else if (null == paramPeasantEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mPeasantEXTService.edit(paramPeasantEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				PeasantEXT peasantEXT = mPeasantEXTService.queryById(paramPeasantEXT);

				// 设置响应客户端的数据
				resultWrap.setData(peasantEXT);

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
	 * 删除合作农户档案
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
			PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, map);

			// 非空验证
			if ((null == paramPeasantEXT.getId())
					&& ((null == paramPeasantEXT.getIdlist()) || (paramPeasantEXT.getIdlist().isEmpty()))) {// 农资发放的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农资发放记录
			Integer deleteResult = null;

			if (((null == paramPeasantEXT.getIdlist()) || (paramPeasantEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mPeasantEXTService.deletebyid(paramPeasantEXT);
			} else {
				// 批量删除
				deleteResult = mPeasantEXTService.batchdeletebyids(paramPeasantEXT.getIdlist());
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
	 * 查看合作农户档案
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
			PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, map);

			// 查询出指定ID的农资发放管理
			PeasantEXT peasantEXT = mPeasantEXTService.queryById(paramPeasantEXT);

			// 设置响应客户端的数据
			resultWrap.setData(peasantEXT);
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
	 * 检索合作农户档案
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
			PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 只能查询自己组织下的合作农户档案
				paramPeasantEXT.setOrgid(loginUserInfo.getLoginorgid());

				// 重置行政区划编号（可以LIKE的方式查询。例如：行政区划编号为：120000000000120000000000，那么最终查询的SQL为：LIKE'12%'）
				// paramPeasantEXT.setAddresscode(
				// AdministrativeDivisionsUtils.truncateRightAllZero(paramPeasantEXT.getAddresscode()));
			}
			
			if (ObjectUtil.isNotBlank(paramPeasantEXT.getAddresscode())) {
				String addressCode = paramPeasantEXT.getAddresscode();
				String province = addressCode.substring(0, 2);
				String city = addressCode.substring(2, 4);
				String county = addressCode.substring(4, 6);
				String town = addressCode.substring(6, 9);
				String village = addressCode.substring(9, 12);
				if (!"10".equals(province)) {
					addressCode = province;
				} else {
					addressCode = null;
				}
				if (!"00".equals(city)) {
					addressCode += city;
				}
				if (!"00".equals(county)) {
					addressCode += county;
				}
				if (!"000".equals(town)) {
					addressCode += town;
				}
				if (!"000".equals(village)) {
					addressCode += village;
				}
				paramPeasantEXT.setAddresscode(addressCode);
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramPeasantEXT.setPage(page);
			}

			// 将数据保存到数据库并获取添加结果
			List<PeasantEXT> peasantEXTList = mPeasantEXTService.queryList(paramPeasantEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mPeasantEXTService.queryListTotalCount(paramPeasantEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(peasantEXTList);

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
	 * 导入合作农户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/importpeasant")
	public void importPeasant(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		// 数据导入之后需要删除本地文件
		final List<File> importFileList = new ArrayList<File>();

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

			// 导入合作农户档案
			Integer importCount = null;

			List<FileUploadEXT> fileUploadEXTList = mFileUploadDownloadService.uploadProtogenesis(request, response,
					false);

			final ArrayList<ArrayList<Object>> allExcelDataList = new ArrayList<ArrayList<Object>>();

			if ((null != fileUploadEXTList) && (fileUploadEXTList.size() > 0)) {

				File excelFile = null;
				String fileExtensionName = null;
				ArrayList<ArrayList<Object>> excelDataList = null;

				// 数据列
				final int[] containsColumns = new int[15];
				for (int i = 0; i < containsColumns.length; i++) {
					containsColumns[i] = i;
				}

				for (FileUploadEXT fileUploadEXT : fileUploadEXTList) {
					excelFile = new File(fileUploadEXT.getLocalpath());

					if (!(excelFile.exists())) {
						continue;
					}
					excelFile.deleteOnExit();

					importFileList.add(excelFile);

					// 获取文件的扩展名
					fileExtensionName = FileUtils.getFileExtentionName(excelFile);

					if (".xls".equals(fileExtensionName) || ".xlsx".equals(fileExtensionName)) {

						excelDataList = PoiUtils.readExcelData(excelFile, containsColumns);

						if (null != excelDataList) {
							allExcelDataList.addAll(excelDataList);
						}
					}

				}

				// 导入合作农户档案
				importCount = mPeasantEXTService.importPeasant(allExcelDataList, loginUserInfo);
			}

			if ((null == importCount) || (importCount.intValue() < 1)) {
				resultWrap.error("农户信息导入失败。");// 导入失败
			} else {
				resultWrap.msg("成功导入 " + importCount.intValue() + " 个农户信息。");// 导入成功
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "importPeasant", e);
		} finally {

			// 数据导入之后需要删除本地文件
			if (importFileList.size() > 0) {
				for (File file : importFileList) {
					file.delete();
				}
			}

			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 下载合作农户信息的导入模板
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/downloadpeasanttemplate")
	public void downloadPeasantTemplate(HttpServletRequest request, HttpServletResponse response) {
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

			final String templateFileName = "农事系统农户信息模板.xlsx";
			try (InputStream inputStream = Thread.currentThread().getClass()
					.getResourceAsStream("/" + templateFileName);
					ServletOutputStream servletOutputStream = response.getOutputStream();) {

				response.setHeader("Content-disposition",
						"attachment;filename=" + new String(templateFileName.getBytes("utf-8"), "ISO-8859-1"));

				final byte[] bytes = new byte[1024];

				int readSize = -1;
				while ((readSize = inputStream.read(bytes)) > 0) {
					servletOutputStream.write(bytes, 0, readSize);
				}

			} catch (Exception e) {
				throw e;
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "downloadPeasantTemplate", e);
		} finally {

			if (ConstantUtils.RESULTWRAP_FLAG_ERROR.equals(resultWrap.getFlag())) {
				HTMLUtil.sendWrap(response, resultWrap, request);
			}
		}
	}

}
