package com.fwk.service.common.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.common.util.ErrorUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.entity.DictEXT;
import com.fwk.service.common.entity.PositionCustomer;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.service.DictService;
import com.fwk.service.common.service.FileUploadDownloadService;
import com.fwk.service.common.service.FileUploadDownloadService.FileUploadEXT;
import com.fwk.service.common.weather.moji.WeatherInfo;
import com.fwk.service.common.weather.moji.WeatherRequestParam;
import com.fwk.service.common.weather.moji.WeatherService;
import com.fwk.service.common.weather.weatherdtcom.OutWeatherInfo;

/**
 * @author wangjunwen
 * @date 2018年1月15日 下午5:03:09
 *
 */
@RestController
@RequestMapping("/common")
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	// 数据字典的业务逻辑处理对象
	@Autowired
	private DictService mDictEXTService;

	// 公共的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private FileUploadDownloadService fileUploadDownloadService;

	/**
	 * 上传文件（图片、视频、……）
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:05:44
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/fileservice/upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			List<FileUploadEXT> fileUploadEXTList = fileUploadDownloadService.uploadProtogenesis(request, response);

			// 设置响应客户端的数据
			resultWrap.setData(fileUploadEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "upload", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 以Base64的方式上传图片
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:05:56
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/fileservice/base64/upload", method = RequestMethod.POST)
	public void uploadImageByBase64(HttpServletRequest request, HttpServletResponse response) {

		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			List<FileUploadEXT> fileUploadEXTList = fileUploadDownloadService.uploadImageByBase64EXT(request, response);

			// 设置响应客户端的数据
			resultWrap.setData(fileUploadEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "uploadImageByBase64", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询数据字典列表
	 * 
	 * @author wangjunwen
	 * @date 2018年1月20日 上午10:44:34
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/dictservice/querydictlist")
	public void querydictlist(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			DictEXT paramDictEXT = HTMLUtil.getMapToEntity(DictEXT.class, map);

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramDictEXT.setPage(page);
			}

			List<DictEXT> mDictEXTList = mDictEXTService.queryList(paramDictEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mDictEXTService.queryListTotalCount(paramDictEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 设置响应客户端的数据
			resultWrap.setData(mDictEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "querydictlist", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 获取像数据字典一样的行政区划地址
	 * 
	 * @author wangjunwen
	 * @date 2018年1月26日 下午12:14:50
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querypositionlistasdictionary")
	public void querypositionlistasdictionary(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {

			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			PositionEXT paramPositionEXT = HTMLUtil.getMapToEntity(PositionEXT.class, map);

			// 非空验证
			if (null == paramPositionEXT.getCode()) {
				resultWrap.setParamIsnotEmpty("code");
				return;
			}

			// 获取像数据字典一样的行政区划地址
			List<List<PositionCustomer>> positionList = mCommonService
					.querypositionlistasdictionary(paramPositionEXT.getCode());

			if ((null != positionList) && (positionList.size() > 0)) {

				Iterator<List<PositionCustomer>> iterator = positionList.iterator();

				List<PositionCustomer> list = null;
				while (iterator.hasNext()) {
					list = iterator.next();

					for (PositionCustomer positionCustomer : list) {
						// 把国家级的数据排除掉，只需要从省级开始
						if ("0".equals(positionCustomer.getParentcode())) {
							iterator.remove();
							break;
						}
					}
				}

			}

			// 设置响应客户端的数据
			resultWrap.setData(positionList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "querydictlist", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 根据行政区划编号获取该行政区划的详细信息
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 上午10:05:03
	 *
	 * @param code
	 *            行政区划编号
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querypositionbycode")
	public void queryPositionByCode(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			PositionEXT paramPositionEXT = HTMLUtil.getMapToEntity(PositionEXT.class, map);

			// 非空验证
			if (null == paramPositionEXT.getCode()) {
				resultWrap.setParamIsnotEmpty("code");
				return;
			}

			// 根据行政区划编号获取该行政区划的详细信息
			PositionEXT positionEXT = mCommonService.queryPositionByCode(paramPositionEXT.getCode());

			// 设置响应客户端的数据
			resultWrap.setData(positionEXT);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryPositionByCode", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询公共天气
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 下午5:12:21
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querypublicweather")
	public void queryPublicWeather(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			PositionEXT paramPositionEXT = HTMLUtil.getMapToEntity(PositionEXT.class, map);

			// 非空验证
			if (null == paramPositionEXT.getCode()) {
				resultWrap.setParamIsnotEmpty("code");
				return;
			}

			// 根据行政区划编号获取该行政区划的详细信息
			PositionEXT positionEXT = mCommonService.queryPositionByCode(paramPositionEXT.getCode());

			// 根据行政区划查询公共天气
			OutWeatherInfo outWeatherInfo = mCommonService.queryPublicWeather(positionEXT);

			// 设置响应客户端的数据
			resultWrap.setData(outWeatherInfo);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "querypublicweather", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	@RequestMapping("/queryMoJiWeather")
	public void queryMoJiWeather(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			WeatherRequestParam param = HTMLUtil.getMapToEntity(WeatherRequestParam.class, requestMap);
			if (ObjectUtil.isBlank(param.getLon())) {
				resultWrap.setParamIsnotEmpty("lon");
				return;
			}
			if (ObjectUtil.isBlank(param.getLat())) {
				resultWrap.setParamIsnotEmpty("lat");
				return;
			}
			if (ObjectUtil.isBlank(param.getWeatherCategorys())) {
				resultWrap.setParamIsnotEmpty("weatherCategorys");
				return;
			}
			WeatherInfo weatherInfo = weatherService.getWeather(param, param.getWeatherCategorys());
			if (!"0".equals(weatherInfo.getRc().getC())) {
				resultWrap.setActFailure();
				resultWrap.setMessage(weatherInfo.getRc().getP());
				return;
			}
			resultWrap.setData(weatherInfo);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
		}finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
}
