package com.fwk.service.common.weather.moji;

public class WeatherResponse {

	private String code;
	
	private String msg;
	
	private WeatherInfo data;
	
	private ResponseInfo rc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public WeatherInfo getData() {
		return data;
	}

	public void setData(WeatherInfo data) {
		this.data = data;
	}

	public ResponseInfo getRc() {
		return rc;
	}

	public void setRc(ResponseInfo rc) {
		this.rc = rc;
	}
}