package com.fwk.common.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings({ "rawtypes" })
public class BaseAccess {
	/** 删除标记的参数名称 */
	public static final String PARAM_NAME_DELETED = "deleted";

	/** 当前操作者的id */
	public static final String PARAM_NAME_OPERATORID = "operatorid";

	/** 获取PC端验证码的参数名 */
	public static final String PARAM_NAME_LASTLOGINCODE = "lastlogincode";

	/**
	 * 解析参数 Map格式 或者 [{"name":"xxx","value":"xxxx"},{}]格式
	 * 获取全量的Request参数，此参数不能直接传递给MyBatis 某些数据库类型不匹配
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getParamMap(HttpServletRequest request) throws IOException {

		// 常规获取
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		Map<String, Object> ioParams = new HashMap<String, Object>();
		Map<String, Object> requestParamObjMap = new HashMap<String, Object>();

		if (requestMap != null && requestMap.size() > 0) {
			Set<String> keys = requestMap.keySet();
			for (String key : keys) {
				String[] vals = requestMap.get(key);
				if (vals != null) {
					if (vals.length > 1) {
						List<Object> valList = new ArrayList<Object>(Arrays.asList(vals));
						queryParam.put(key, valList);
					} else {
						queryParam.put(key, vals[0]);
					}
				}
			}
		}

		// 流获取参数
		try {
			String jsonParam = IOUtils.toString(request.getInputStream(), "utf-8");
			if (jsonParam != null && !"".equals(jsonParam.trim())) {
				ioParams = RequestUtil.getOriginalRequestParamMap(jsonParam);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}

		// 从request中 attribute 中获取
		Object requestParamObj = request.getAttribute("requestParam");
		if (requestParamObj != null && requestParamObj instanceof Map) {
			requestParamObjMap = (Map) requestParamObj;
		}
		Map<String, Object> defaulParamMap = new HashMap<String, Object>();
		defaulParamMap.putAll(ioParams);
		defaulParamMap.putAll(queryParam);

		// 设置用于判断客户端是否设置了分页查询（必须从原始的请求参数中取值）
		this.setIsPaginationQuery(defaulParamMap);

		defaulParamMap.putAll(requestParamObjMap);

		return RequestUtil.setPageInfo(defaulParamMap);
	}

	/**
	 * 设置用于判断客户端是否设置了分页查询
	 * 
	 * @author wangjunwen
	 * @date 2018年2月27日 上午9:03:52
	 *
	 * @param requestParamMap
	 *            客户端请求的原始参数
	 */
	private void setIsPaginationQuery(Map<String, Object> requestParamMap) {
		if ((null == requestParamMap)) {
			return;
		}

		Object currentPage = requestParamMap.get("currentPage");

		if ((ObjectUtil.toString(currentPage).matches("[1-9]\\d*"))) {
			// 是客户端设置了分页查询（为了避免与客户端的参数名重名，这里特意设置一个比较特殊的Key，用于是否是客户端需要分页查询的业务判断）
			requestParamMap.put("currentPageIIIIIIIIIIIIIIII", "currentPageIIIIIIIIIIIIIIII");
		}
	}

	protected void sendJson(HttpServletResponse response, Object resultWrap) throws IOException {
		sendJson(response, resultWrap, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
	}

	protected void sendJson(HttpServletResponse response, Object resultWrap, String dateFormat) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		// 1、使用自定义的日期个数输出；2、DisableCircularReferenceDetect来禁止循环引用检测；
		response.getWriter().write(JSON.toJSONStringWithDateFormat(resultWrap, dateFormat,
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect));
	}

	protected void sendJsonWithNull(HttpServletResponse response, Object resultWrap) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.getWriter()
				.write(JSON.toJSONStringWithDateFormat(resultWrap, DateTimeUtil.DEFAULT_DATETIME_FORMAT,
						SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue));
	}

	protected DataTablePage getDataTablePage(Map<String, Object> requestParam) throws Exception {
		return RequestUtil.getDataTablePage(requestParam);
	}

	protected void setStartRow(DataTablePage dataTablePage, Map<String, Object> requestParam) {
		requestParam.put("startRow", dataTablePage.getStartRow());
	}

	protected ResultWrap getResultWrap() {
		ResultWrap rw = new ResultWrap();
		rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_OK);
		return rw;
	}

	protected ResultWrap getInitResultWrap() {
		ResultWrap rw = new ResultWrap();
		rw.error("操作失败！");
		return rw;
	}

	protected Map<String, Object> getValidSqlParamMap(Map<String, Object> requestParam, Class entityClass) {
		return RequestUtil.getValidSqlParamMap(requestParam, entityClass);
	}

	/**
	 * 根据实体类的属性类型获取合法的Map参数 返回值的Map中的key只会在Class对象的属性中
	 * 
	 * @param request
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getValidSqlParamMap(HttpServletRequest request, Class entityClass) throws Exception {
		return getValidSqlParamMap(getParamMap(request), entityClass);
	}

	protected void sendServiceData(HttpServletRequest request, HttpServletResponse response, String resourceUrl) {
		ResultWrap resultWrap = getResultWrap();
		try {
			// String jsonParam = getRequestJson(request);
			Map<String, Object> requestParam = getParamMap(request);
			resultWrap = HttpClientUtils.doPost(resourceUrl, JSON.toJSONString(requestParam));
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			resultWrap.error(e.getMessage());
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				sendJson(response, resultWrap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void sendServiceDataWithNull(HttpServletRequest request, HttpServletResponse response,
			String resourceUrl) {
		ResultWrap resultWrap = getResultWrap();
		try {
			// String jsonParam = getRequestJson(request);
			Map<String, Object> requestParam = getParamMap(request);
			resultWrap = HttpClientUtils.doPost(resourceUrl, JSON.toJSONString(requestParam));
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			resultWrap.error(e.getMessage());
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				sendJsonWithNull(response, resultWrap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param request
	 * @param resourceUrl
	 *            服务地址全路径
	 * @return
	 */
	protected ResultWrap getServiceData(HttpServletRequest request, String resourceUrl) {
		try {
			// String jsonParam = getRequestJson(request);
			Map<String, Object> requestParam = getParamMap(request);
			ResultWrap resultWrap = HttpClientUtils.doPost(resourceUrl, JSON.toJSONString(requestParam));
			return resultWrap;
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultWrap();
	}

	/**
	 * @param requestParam
	 * @param response
	 * @param resourceUrl
	 *            服务地址全路径
	 */
	protected void sendServiceData(Map<String, Object> requestParam, HttpServletResponse response, String resourceUrl) {
		ResultWrap resultWrap = getResultWrap();
		try {
			String jsonParam = JSON.toJSONString(requestParam);
			resultWrap = HttpClientUtils.doPost(resourceUrl, jsonParam);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			resultWrap.error(e.getMessage());
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				sendJson(response, resultWrap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param requestParam
	 *            Map 格式的参数
	 * @param resourceUrl
	 * @return
	 */
	protected ResultWrap getServiceData(Object requestParam, String resourceUrl) {
		try {
			String jsonParam = JSON.toJSONString(requestParam);
			ResultWrap resultWrap = HttpClientUtils.doPost(resourceUrl, jsonParam);
			return resultWrap;
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultWrap();
	}

	protected String getRequestJson(HttpServletRequest request) {
		try {
			return IOUtils.toString(request.getInputStream(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	protected void sendPlainText(HttpServletResponse response, String jsonData) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.write(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendPlainText(HttpServletResponse response, String data, String contentType) {
		response.setCharacterEncoding("UTF-8");
		if (ObjectUtil.isNotBlank(contentType)) {
			response.setContentType(contentType);
		}
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String getCookies(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie o : cookies) {
				if (ObjectUtil.equals(key, o.getName())) {
					return o.getValue();
				}
			}
		}

		return "";
	}

	public Integer getUserid(HttpServletRequest request) {
		return ObjectUtil.parseInteger(request.getParameter("userId"));
	}

	public String getUsername(HttpServletRequest request) {
		return request.getParameter("userName");
	}

	public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!ObjectUtil.isNotBlank(ip) || ObjectUtil.equals(ip.toLowerCase(), "unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!ObjectUtil.isNotBlank(ip) || ObjectUtil.equals(ip.toLowerCase(), "unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!ObjectUtil.isNotBlank(ip) || ObjectUtil.equals(ip.toLowerCase(), "unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getUrlPath(HttpServletRequest request) {
		return request.getRequestURI();
	}

	@RequestMapping("/getAllUrls")
	public void getAllUrls(HttpServletRequest request, HttpServletResponse response) {
		Set<String> urlsSet = new HashSet<String>();
		@SuppressWarnings("deprecation")
		String filePath = request.getRealPath("/").replace("webapp", "java");
		String apppath = new String(filePath);
		apppath = apppath.substring(0, apppath.indexOf("\\src"));
		String appname = apppath.substring(apppath.lastIndexOf("\\") + 1);

		String classname = getClass().getName();
		String pakcagename = classname.substring(0, classname.lastIndexOf("."));
		String javaPath = filePath + pakcagename.replace(".", "/");
		try {

			File files = new File(javaPath);
			if (files.isDirectory()) {
				File[] fs = files.listFiles();
				for (File f : fs) {
					if (!f.isDirectory() && f.getName().endsWith("java")) {
						String clzName = pakcagename + "." + f.getName().replace(".java", "");
						try {
							Class clz = Class.forName(clzName);
							@SuppressWarnings("unchecked")
							Annotation annotation = clz.getAnnotation(RequestMapping.class);
							String funName = "";
							if (annotation != null) {
								RequestMapping requestMapping = (RequestMapping) annotation;
								String[] vals = requestMapping.value();
								if (null != vals && vals.length > 0) {
									funName = vals[0];
								}
								Method[] methods = clz.getDeclaredMethods();
								for (Method m : methods) {
									if (m.isAnnotationPresent(RequestMapping.class)) {
										RequestMapping requestMapping2 = (RequestMapping) m
												.getAnnotation(RequestMapping.class);
										String[] urls = requestMapping2.value();
										if (null != urls && urls.length > 0) {
											String murl = urls[0];
											if (murl.indexOf("/gstar/") >= 0) {
												murl = murl.substring(0, murl.indexOf("/gstar/"));
											}
											if (murl.indexOf("/{") >= 0) {
												murl = murl.substring(0, murl.indexOf("/{"));
											}
											// sb.append("acp-support-platform\t").append(funName).append(murl).append("\n");
											if (murl.indexOf("getAllUrls") < 0) {
												String url = appname + " " + funName + murl;
												urlsSet.add(url);
											}
										}
									}
								}
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} finally {
			StringBuffer sBuffer = new StringBuffer();
			if (null != urlsSet && urlsSet.size() > 0) {
				for (String str : urlsSet) {
					sBuffer.append(str).append("\r\n");
				}
			}

			sendPlainText(response, sBuffer.toString(), "text/plain");

		}

	}

	/**
	 * 从客户端传过来的参数中获取操作者的ID
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 上午10:17:00
	 *
	 * @param paramMap
	 * @return
	 */
	public Integer getOperatorId(Map<String, Object> paramMap) {
		String operatorid = ObjectUtil.toString(paramMap.get(PARAM_NAME_OPERATORID));

		return ((operatorid.matches("\\d+")) ? Integer.parseInt(operatorid) : null);
	}

	/**
	 * 是否是游客的身份访问
	 * 
	 * @author wangjunwen
	 * @date 2017年10月10日 上午10:04:58
	 *
	 * @param paramMap
	 * @return 是返回true，否则返回 false
	 */
	public boolean isVisitor(Map<String, Object> paramMap) {

		// 是游客的身份访问时返回 true，否则返回 false
		return ((ObjectUtil.isBlank(paramMap.get(PARAM_NAME_OPERATORID)))
				&& (ObjectUtil.isBlank(paramMap.get(PARAM_NAME_LASTLOGINCODE))));
	}

}
