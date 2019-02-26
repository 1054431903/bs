package com.fwk.service.common.weather.weatherdtcom;

import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author wangjunwen
 *
 */
public final class ApiConfig {
	private ApiConfig() {

	}

	/** 配置文件 */
	private static final Properties apiConfigProperties = new Properties();

	static {

		try (InputStream apiConfigFileInputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("www_weatherdt_com/api_config.properties");) {
			apiConfigProperties.load(apiConfigFileInputStream);

			String value = "";
			for (Entry<Object, Object> entry : apiConfigProperties.entrySet()) {
				value = (entry.getValue() + "").trim();

				if ((null == entry.getValue()) || (value.isEmpty())) {
					apiConfigProperties.remove(entry.getKey());
				} else {
					apiConfigProperties.put(entry.getKey(), value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 加载配置文件出错就直接退出程序
			System.exit(1);
		}

	}

	/**
	 * 将配置文件中的用户名和密码赋值给输入对象
	 * 
	 * <pre>
	 * 	设置值的优先顺序：配置文件 > 字段的默认值
	 * </pre>
	 * 
	 * @param input
	 *            输入对象
	 * @throws Exception
	 */
	public static void setPropFromConfigFile(BaseInput input) throws Exception {

		if ((null == input.getInterfaceId()) || (input.getInterfaceId().isEmpty())) {

			// 接口ID不能为空
			throw new Exception("interfaceId may not be null or empty !");

		} else if (!(apiConfigProperties.containsKey(("api.data.cma.cn.interfaceId." + input.getInterfaceId())))) {

			// 接口ID不存在
			throw new Exception("interfaceId may not exists !");

		} else {

			// 设置调用API的URL地址
			Object api_data_cma_cn_apiURL = apiConfigProperties.get("api.data.cma.cn.apiURL");
			if ((null != api_data_cma_cn_apiURL)) {
				input.setApiURL(api_data_cma_cn_apiURL.toString());
			}

			// 设置返回的数据格式，目前仅支持json格式
			Object api_data_cma_cn_response_dataFormat = apiConfigProperties.get("api.data.cma.cn.response.dataFormat");
			if ((null != api_data_cma_cn_response_dataFormat)) {
				input.setDataFormat(api_data_cma_cn_response_dataFormat.toString());
			}

			// 设置用户名
			input.setUserId(apiConfigProperties
					.getProperty(("api.data.cma.cn.interfaceId." + input.getInterfaceId() + ".userId")));
			// 设置密码
			input.setPwd(apiConfigProperties
					.getProperty(("api.data.cma.cn.interfaceId." + input.getInterfaceId() + ".pwd")));

		}
	}

	private static class ApiConfigPropeties {
		/** 调用 API 的密钥 */
		private static String apiKey = apiConfigProperties.getProperty(("api.key.www_weatherdt_com"));

		/** 调用 API 的URL地址 */
		private static String apiURL = apiConfigProperties.getProperty(("api.url.www_weatherdt_com"));
	}

	/**
	 * 获取调用 API 的密钥
	 * 
	 * @return
	 */
	public static String getApiKey() {

		return ApiConfigPropeties.apiKey;
	}

	/**
	 * 获取调用 API 的URL地址
	 * 
	 * @return
	 */
	public static String getApiURL() {
		return ApiConfigPropeties.apiURL;
	}

}
