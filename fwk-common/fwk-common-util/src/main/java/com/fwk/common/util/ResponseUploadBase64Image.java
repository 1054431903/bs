package com.fwk.common.util;

/**
 * 以Base64方式上传图片的应答参数类
 * 
 * @author wangjunwen
 * @date 2017年9月24日 下午4:45:41
 *
 */
public class ResponseUploadBase64Image {

	// 资源名称
	private String resouceName;
	// 资源的URL地址
	private String resouceURL;

	public String getResouceName() {
		return resouceName;
	}

	public void setResouceName(String resouceName) {
		this.resouceName = resouceName;
	}

	public String getResouceURL() {
		return resouceURL;
	}

	public void setResouceURL(String resouceURL) {
		this.resouceURL = resouceURL;
	}

}
