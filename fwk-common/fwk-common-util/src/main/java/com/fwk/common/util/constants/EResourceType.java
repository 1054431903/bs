package com.fwk.common.util.constants;

/**
 * 资源的类型
 * 
 * @author wangjunwen
 * @date 2017年8月29日 下午3:00:27
 *
 */
public enum EResourceType {
	/** 图片 */
	PICTURE,

	/** 视频 */
	VIDEO,

	/** 音频 */
	AUDIO,

	// 枚举结束标志
	;

	/**
	 * 根据文件名获取问资源类型
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 上午9:46:04
	 *
	 * @param filePathOrFileName
	 *            文件的路径或者文件名
	 * @return
	 */
	public static final EResourceType getResourceTypeByFileExtentionName(String filePathOrFileName) {
		if ((null == filePathOrFileName) || (filePathOrFileName = filePathOrFileName.trim()).isEmpty()) {
			return null;
		} else if (-1 == filePathOrFileName.lastIndexOf(".")) {
			return null;
		}

		// 获取文件扩展名
		final String fileExtentionName = (filePathOrFileName.substring(filePathOrFileName.lastIndexOf("."))
				.toLowerCase());

		if (fileExtentionName.matches(".((jpg)|(jpeg)|(png)|(gif)|(bmp))")) {
			return PICTURE;
		} else if (fileExtentionName.matches(".((mp3)|(amr)|(wav)|(wma)|(rm)|(real)|(asf)|(ogg)|(mp3pro))")) {
			return AUDIO;
		} else if (fileExtentionName.matches(
				".((mp4)|(mpeg)|(mpg)|(3gp)|(rmvb)|(rm)|(avi)|(mkv)|(dat)|(asf)|(wmv)|(flv)|(mov)|(ogg)|(ogm))")) {
			return VIDEO;
		}

		return null;
	}

}
