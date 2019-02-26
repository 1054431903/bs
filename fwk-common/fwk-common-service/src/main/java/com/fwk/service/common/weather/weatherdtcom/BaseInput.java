package com.fwk.service.common.weather.weatherdtcom;

/**
 * 输入
 * 
 * @author wangjunwen
 *
 */
public class BaseInput {

	/**
	 * 接口地址
	 */
	private String apiURL = "http://api.data.cma.cn:8090/api";

	/**
	 * 分配给您的帐号，从订单中获取
	 */
	private String userId;

	/**
	 * 分配给您的密码，从订单中获取
	 */
	private String pwd;

	/**
	 * 返回的数据格式，目前仅支持json格式
	 */
	private String dataFormat = "json";

	/**
	 * 此数据的接口ID
	 */
	private String interfaceId;

	/**
	 * 此数据的编码，全球高空气象站定时值资料的编码为UPAR_GLB_MUL_FTM
	 */
	private String dataCode;

	public String getApiURL() {
		return apiURL;
	}

	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

}
