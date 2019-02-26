package com.fwk.common.util;

/**
 * 以Base64方式上传图片的请求参数类
 * 
 * @author wangjunwen
 * @date 2017年9月24日 下午4:45:11
 *
 */
public class RequestUploadBase64Image {
	// Base64 的数据
	private String base64Data;
	// 文件格式
	private String format;
	// 资源名称
	private String resouceName;

	public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResouceName() {
		return resouceName;
	}

	public void setResouceName(String resouceName) {
		this.resouceName = resouceName;
	}

}
