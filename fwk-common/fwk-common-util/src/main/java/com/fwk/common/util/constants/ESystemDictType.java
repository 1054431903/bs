package com.fwk.common.util.constants;

/**
 * 系统字典类型的定义
 * 
 * @author wangjunwen
 * @date 2017-08-01 15:18:58
 *
 */
public enum ESystemDictType {

	/** 字典类型：农资类别 */
	agriculturaltype("agriculturaltype"),

	/** 字典类型：规格单位 */
	standunit("standunit"),

	/** 字典类型：数量单位 */
	numberunit("numberunit"),

	// 枚举结束标识符
	;

	// ============== 以下为方法和字段的定义 ==========================
	private ESystemDictType(String dicttype) {
		this.dicttype = dicttype;
	}

	/** 字典类别 */
	public final String dicttype;

}
