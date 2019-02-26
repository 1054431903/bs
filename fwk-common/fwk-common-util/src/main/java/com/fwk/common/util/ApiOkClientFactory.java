package com.fwk.common.util;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP请求的工具类
 * 
 * @author wangjunwen
 *
 */
public class ApiOkClientFactory {

	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

	/**
	 * 发起 http 的 GET 请求
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 下午5:09:48
	 *
	 * @param url
	 *            URL地址
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {

		// String url = "https://www.baidu.com/";
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = okHttpClient.newCall(request);
		Response response = call.execute();

		if ((null != response) && (null != response.body())) {
			return (response.body().string().replaceAll("\\n", ""));
		}

		return null;
	}

}
