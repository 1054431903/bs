package com.fwk.common.util.constants;

/**
 * SQL的排序方式
 * 
 * @author wangjunwen
 * @date 2018年2月27日 下午2:27:19
 *
 */
public enum EOrderBySQL {

	/** 升序排序 */
	ASC,

	/** 降序排序 */
	DESC,

	;

	/**
	 * 根据排序方式名称获取排序方式对象
	 * 
	 * @author wangjunwen
	 * @date 2018年2月27日 下午2:30:47
	 *
	 * @param name
	 *            排序方式的名称
	 * @return 如果参数排序方式名称与实际的排序方式名称不符时默认返回 升序 排序
	 */
	public static EOrderBySQL getDefaultByNameIfNull(String name) {

		if ((null == name) || ((name = name.trim()).isEmpty())) {
			return ASC;
		}

		for (EOrderBySQL eOrderBySQL : EOrderBySQL.values()) {
			if (eOrderBySQL.name().equalsIgnoreCase(name)) {
				return eOrderBySQL;
			}
		}

		return ASC;
	}

}
