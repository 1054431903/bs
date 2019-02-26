package com.fwk.common.util.constants;

/**
 * 支持导出的文件类型
 * 
 * @author wangjunwen
 * @date 2017年9月7日 下午4:20:15
 *
 */
public enum ESupportExportFileType {

	/** 支持导出 xls 格式的文件（默认支持导出的文件类型） */
	XLS("xls"),

	/** 支持导出 CVS 格式的文件 */
	CVS("cvs"),

	// 枚举的结束标识
	;

	private ESupportExportFileType(String fileType) {
		this.fileType = fileType;
		this.extentionName = ("." + fileType);
		this.regexExtentionName = ("*." + fileType);
	}

	/**
	 * 支持导出的文件类型
	 */
	public final String fileType;

	/**
	 * 文件扩展名。例如：.xls
	 */
	public final String extentionName;

	/**
	 * 文件扩展名，包含正则表达式的扩展名。例如：*.xls
	 */
	public final String regexExtentionName;

	/**
	 * 获取指定的文件类型
	 * 
	 * @author wangjunwen
	 * @date 2017年9月7日 下午4:30:38
	 *
	 * @param fileType
	 *            文件类型
	 * @return 如果指定的文件类型不存在将返回默认支持导出的文件类型
	 */
	public static ESupportExportFileType getFileTypeIfNotContainsReturnDefault(String fileType) {
		if ((null == fileType) || (fileType = fileType.trim()).isEmpty()) {
			return XLS;
		}

		for (ESupportExportFileType obj : ESupportExportFileType.values()) {
			if ((obj.fileType.equals(fileType)) || (obj.extentionName.equals(fileType))
					|| (obj.regexExtentionName.equals(fileType))) {
				return obj;
			}
		}

		return XLS;
	}

}
