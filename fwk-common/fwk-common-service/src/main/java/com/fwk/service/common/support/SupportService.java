package com.fwk.service.common.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.rapidoid.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HttpClientUtils;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;

/**
 * 调用支撑平台数据
 */
@Service
public class SupportService {

	@Value("${support.ip}")
	private String supportIP;

	@Value("${support.port}")
	private String supportPORT;

	@Value("${support.path}")
	private String supportTitle;
	
	@Value("${fwk.ip}")
	private String fwkIP;

	@Value("${fwk.port}")
	private String fwkPORT;

	@Value("${fwk.path}")
	private String fwkTitle;

	private String URL = "";

	public boolean isUserSupport = true;
	
	public boolean isfwk = false;

	@Autowired
	private RestTemplate restTemplate;

	// 初始化请求地址及发送请求并返回结果,GET请求(2018-07-25备份，取消使用全局变量)
	public Object requestSupportGetBak(String AllJsonRequestUrl, SupportRequestParam supportRequestParam,boolean... isfwkp) {
		if(isfwkp != null && isfwkp.length >= 1) {
			isfwk = true;
		}else {
			isfwk = false;
		}
		// 初始化请求地址
		initUrl(AllJsonRequestUrl);
		// 拼接过滤条件地址
		if (ObjectUtil.isNotBlank(supportRequestParam)) {
			// 添加过滤条件
			if (ObjectUtil.isNotBlank(supportRequestParam.getFilterJson())) {
				appendUrlFilter(supportRequestParam.getFilterJson());
			}
			// 分页
			if (ObjectUtil.isNotBlank(supportRequestParam.getPageJson())) {
				appendUrlPage(supportRequestParam.getPageJson());
			}
		}
		// 请求地址后并返回结果
		return requestUrlGet();
	}

	// 初始化请求地址及发送请求并返回结果,GET请求
	public Object requestSupportGet(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean... isfwkp) {
		if (isfwkp != null && isfwkp.length >= 1) {
			isfwk = true;
		} else {
			isfwk = false;
		}
		return requestSupportGetNew(AllJsonRequestUrl, supportRequestParam,isfwk);
	}

	public Object requestSupportGetNew(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		return this.requestSupportGetNew(AllJsonRequestUrl, supportRequestParam, false);
	}
	
	public Object requestSupportGetNew(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean isUseFwk) {
		this.isfwk = isUseFwk;
		// 初始化请求地址
		String url = initUrl(AllJsonRequestUrl);
		// 拼接过滤条件地址
		if (ObjectUtil.isNotBlank(supportRequestParam)) {
			// 添加过滤条件
			if (ObjectUtil.isNotBlank(supportRequestParam.getFilterJson())) {
				JSONObject jsonObject = supportRequestParam.getFilterJson();
				jsonObject.fluentPut("_removed", null);
				url += "?filter=" + JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
			}
			// 分页
			if (ObjectUtil.isNotBlank(supportRequestParam.getPageJson())) {
				JSONObject jsonObject = supportRequestParam.getPageJson();
				if (url.contains("?filter=")) {// 包含过滤条件
					// limit:显示条数，skip：开始条数
					url += "&limit=" + jsonObject.get("limit") + "&skip=" + jsonObject.get("skip") + "";
				} else {// 不包含过滤条件
					url += "?limit=" + jsonObject.get("limit") + "&skip=" + jsonObject.get("skip") + "";
				}
			}
		}
		// 请求地址后并返回结果
		return requestUrlGet(url);
	}

	// 初始化请求地址及发送请求并返回结果,POST请求
	public ResultWrap requestSupportPOST(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		// 初始化请求地址
		initUrl(AllJsonRequestUrl);
		return requestUrlPost(supportRequestParam.getDataJson().toString());
	}

	// 初始化请求地址及发送请求并返回结果,PUT请求
	public Object requestSupportPUT(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		// 初始化请求地址
		initUrl(AllJsonRequestUrl);
//		return requestUrlPut(supportRequestParam.getDataJson().toString());
		return requestUrlPut(JSONObject.toJSONString(supportRequestParam.getDataJson(), SerializerFeature.WriteMapNullValue));
	}

	// 初始化请求地址及发送请求并返回结果,DELETE请求
	public Object requestSupportDELETE(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		// 初始化请求地址
		initUrl(AllJsonRequestUrl);
		return requestUrlDelete(supportRequestParam.getDataJson().toString());
	}

	// 拼接查询过滤条件
	private void appendUrlFilter(JSONObject jsonObject) {
		jsonObject.fluentPut("_removed", null);
		URL += "?filter=" + JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
	}

	// 拼接分页
	private void appendUrlPage(JSONObject jsonObject) {
		if (URL.contains("?filter=")) {// 包含过滤条件
			// limit:显示条数，skip：开始条数
			URL += "&limit=" + jsonObject.get("limit") + "&skip=" + jsonObject.get("skip") + "";
		} else {// 不包含过滤条件
			URL += "?limit=" + jsonObject.get("limit") + "&skip=" + jsonObject.get("skip") + "";
		}
	}

	// 用户请求地址初始化
	private String initUrl(String AllJsonRequestUrl) {
		String url = baseUrl() + AllJsonRequestUrl;
		URL = url;
		return url;
	}

	// 支撑平台根级地址
	private String baseUrl() {
//		if(isfwk) {
//			supportTitle = "/api/mongo/fwk/";
//		}else {
//			supportTitle = "/api/mongo/iov/";
//		}
//		return "http://" + supportIP + ":" + supportPORT + supportTitle;
		
		return isfwk ? String.format("http://%s:%s%s", fwkIP, fwkPORT, fwkTitle)
				: String.format("http://%s:%s%s", supportIP, supportPORT, supportTitle);
	}

	/**
	 * 发送get请求
	 */
	private Object requestUrlGet() {
		try {
			System.out.println("get:" + URL);
			return HttpClientUtils.httpURLConnectionGET(URL);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	private Object requestUrlGet(String url) {
		try {
			System.out.println("get:" + url);
			return HttpClientUtils.httpURLConnectionGET(url);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 发送post请求
	 */
	private ResultWrap requestUrlPost(String dataJson) {
		try {
			System.out.println("post:" + URL + ";dataJson:" + dataJson);
			return HttpClientUtils.doPost(URL, dataJson);
			// return HttpClientUtils.httpURLConnectionPOST(URL,dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 发送put请求
	 */
	private Object requestUrlPut(String dataJson) {
		try {
			System.out.println("put:" + URL + ";dataJson:" + dataJson);
			return HttpClientUtils.httpURLConnectionPUT(URL, dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 发送delete请求
	 */
	private Object requestUrlDelete(String dataJson) {
		try {
			System.out.println("delete:" + URL + ";dataJson:" + dataJson);
			return HttpClientUtils.httpURLConnectionDELETE(URL, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * @Description: put请求
	 *
	 * @param dataJson
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:35:57
	 */
	public ResultWrap putRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		return this.putRequest(AllJsonRequestUrl, supportRequestParam, false);
	}
	
	/**
	 * @Description: put请求
	 *
	 * @param dataJson
	 * @param isUseFwk 是否查询农事库，true:查询农事库(fwk)，false:查询支撑库(iov)
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:35:57
	 */
	public ResultWrap putRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean isUseFwk) {
		this.isfwk = isUseFwk;
		String url = initUrl(AllJsonRequestUrl);
		System.out.println("put:" + url + ";dataJson:" + supportRequestParam.getDataJson().toString());
		// return sendHttpRequest(url, supportRequestParam.getDataJson().toString(), new
		// HashMap<String, Object>(),
		// HttpMethod.PUT);
		return sendHttpRequest(url,
				JSONObject.toJSONString(supportRequestParam.getDataJson(), SerializerFeature.WriteMapNullValue),
				new HashMap<String, Object>(), HttpMethod.PUT);
	}
	
	/**
	 * @Description: 该函数的功能描述
	 *
	 * @param AllJsonRequestUrl
	 * @param supportRequestParam
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年6月15日 下午5:25:27
	 */
	public ResultWrap getRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		return this.getRequest(AllJsonRequestUrl, supportRequestParam, false);
	}
	
	/**
	 * @Description: get请求
	 *
	 * @param AllJsonRequestUrl
	 * @param supportRequestParam
	 * @param isUseFwk 是否查询农事库，true:查询农事库(fwk)，false:查询支撑库(iov)
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:56:26
	 */
	public ResultWrap getRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean isUseFwk) {
		this.isfwk = isUseFwk;
		String url = initUrl(AllJsonRequestUrl);
		System.out.println("get:" + url);
		Map<String, Object> uriVariables = new HashMap<>();
		// 拼接过滤条件地址
		if (ObjectUtil.isNotBlank(supportRequestParam)) {
			if (ObjectUtil.isNotBlank(supportRequestParam.getSortJson())) {
				url += "?sort={sort}";
				uriVariables.put("sort", supportRequestParam.getSortJson());
			} else {
				url += "?sort={sort}";
				uriVariables.put("sort", new JSONObject().fluentPut("createOn", -1));
			}
			// 添加过滤条件
			if (ObjectUtil.isNotBlank(supportRequestParam.getFilterJson())) {
				url += "&filter={filter}";
				uriVariables.put("filter", supportRequestParam.getFilterJson());
			}
			// 分页
			if (ObjectUtil.isNotBlank(supportRequestParam.getPageJson())) {
				// limit:显示条数，skip：开始条数
				url += "&limit={limit}&skip={skip}";
				uriVariables.put("skip", supportRequestParam.getPageJson().get("skip"));
				uriVariables.put("limit", supportRequestParam.getPageJson().get("limit"));
			}
			// 指定查询列
			if (ObjectUtil.isNotBlank(supportRequestParam.getProjectionJson())) {
				url += "&projection={projection}";
				uriVariables.put("projection", supportRequestParam.getProjectionJson());
			}

			System.out.println("get:" + url + "filter:" + supportRequestParam.getSortJson()
					+ supportRequestParam.getFilterJson() + supportRequestParam.getPageJson());
		}
		return sendHttpRequest(url, null, uriVariables, HttpMethod.GET);
	}
	
	/**
	 * @Description: post请求
	 *
	 * @param AllJsonRequestUrl
	 * @param supportRequestParam
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:55:00
	 */
	public ResultWrap postRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		return this.postRequest(AllJsonRequestUrl, supportRequestParam, false);
	}
	
	/**
	 * @Description: post请求
	 *
	 * @param AllJsonRequestUrl
	 * @param supportRequestParam
	 * @param isUseFwk 是否查询农事库，true:查询农事库(fwk)，false:查询支撑库(iov)
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:55:00
	 */
	public ResultWrap postRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean isUseFwk) {
		this.isfwk = isUseFwk;
		String url = initUrl(AllJsonRequestUrl);
		System.out.println("post:" + url + ";dataJson:" + supportRequestParam.getDataJson().toString());
		return sendHttpRequest(url, supportRequestParam.getDataJson().toString(), new HashMap<String, Object>(),
				HttpMethod.POST);
	}
	
	/**
	 * @Description: post请求(参数为List对象)
	 *
	 * @param AllJsonRequestUrl
	 * @param supportRequestParam
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年5月11日 下午2:10:13
	 */
	public ResultWrap postRequestWithListParam(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		String url = initUrl(AllJsonRequestUrl);
		System.out.println("post:" + url + ";dataJson:" + supportRequestParam.getDataListJson().toString());
		return sendHttpRequestWithArrayListData(url, supportRequestParam.getDataListJson().toString(), new HashMap<String, Object>(),
				HttpMethod.POST);
	}

	/**
	 * @Description: delete请求
	 *
	 * @param dataJson
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:34:32
	 */
	public ResultWrap deleteRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam) {
		return this.deleteRequest(AllJsonRequestUrl, supportRequestParam, false);
	}
	/**
	 * @Description: delete请求
	 *
	 * @param dataJson
	 * @param isUseFwk 是否查询农事库，true:查询农事库(fwk)，false:查询支撑库(iov)
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:34:32
	 */
	public ResultWrap deleteRequest(String AllJsonRequestUrl, SupportRequestParam supportRequestParam, boolean isUseFwk) {
		this.isfwk = isUseFwk;
		String url = initUrl(AllJsonRequestUrl);
		System.out.println("delete:" + url + ";dataJson:" + supportRequestParam.getDataJson().toString());
		return sendHttpRequest(url, supportRequestParam.getDataJson().toString(), new HashMap<String, Object>(),
				HttpMethod.DELETE);
	}

	/**
	 * @Description: 支撑平台RestTemplate请求
	 *
	 * @param dataJson
	 * @param httpMethod
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月11日 下午4:16:07
	 */
	private ResultWrap sendHttpRequest(String url, String dataJson, Map<String, Object> uriVariables, HttpMethod httpMethod) {
		ResultWrap rw = new ResultWrap();
		try {
			// RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<String> requestEntity = new HttpEntity<String>(dataJson, headers);
			ResponseEntity<JSONObject> responseEntity = null;
			responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, JSONObject.class, uriVariables);
			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败,错误代码：" + responseEntity.getStatusCode());
				return rw;
			}
			rw.setData(responseEntity.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
			rw.setMessage("请求出错：" + e.getMessage());
		}
		return rw;
	}
	
	/**
	 * @Description: 支撑平台RestTemplate请求(请求参数为list对象)
	 *
	 * @param url
	 * @param dataJson(list对象)
	 * @param uriVariables
	 * @param httpMethod
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年5月11日 下午2:08:40
	 */
	private ResultWrap sendHttpRequestWithArrayListData(String url, String dataJson, Map<String, Object> uriVariables, HttpMethod httpMethod) {
		ResultWrap rw = new ResultWrap();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<String> requestEntity = new HttpEntity<String>(dataJson, headers);
			ResponseEntity<?> responseEntity = null;
			responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, ArrayList.class, uriVariables);
			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
				rw.setMessage("请求失败,错误代码：" + responseEntity.getStatusCode());
				return rw;
			}
			rw.setData(responseEntity.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			rw.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
			rw.setMessage("请求出错：" + e.getMessage());
		}
		return rw;
	}
}
