package com.fwk.common.util;

import java.util.Date;

public class Files {

	// 上传时间
	private Date uploadDate;

	// 文件数据
	private byte[] data;

	// 文件扩展名
	private String extName;//JPG

	// 文件格式
	private String format;//MP4/MUTYI

	// 图片原始名称
	private String resName;//filename

	// 上传路径
	private String url;//不填

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
