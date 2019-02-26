package com.fwk.common.util.constants;

/**
 * 字典实体对象
 * 
 * <pre>
 * 	所有属性值均不能在外部被修改，所以所有字段都已经使用 final 关键字修饰（只允许在初始化时从构造函数给字段赋值）
 * 	<b>警告：防止使用反射来修改属性值</b>
 * </pre>
 * 
 * @author wangjunwen
 * @date 2017-07-29 13:50:13
 *
 */
public class Dict implements Comparable<Dict> {

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("dictType=").append(dictType).append(";dictValue=").append(dictValue).append(";")
				.append("dictName=").append(dictName).append(";sort=").append(sort).append(";memo=").append(memo);

		return buffer.toString();
	}

	public int compareTo(Dict o) {
		if ((null != this.sort) && (null != o.sort)) {
			return (this.sort.intValue() > o.sort.intValue() ? 1 : -1);
		} else if ((null == this.sort) && (null != o.sort)) {
			return 1;
		} else if ((null != this.sort) && (null == o.sort)) {
			return -1;
		}

		return 0;
	}

	/** 字典类型 */
	private final String dictType;
	/** 字典值 */
	private final String dictValue;
	/** 字典名称 */
	private final String dictName;
	/** 排序号 */
	private final Integer sort;
	/** 备注 */
	private final String memo;

	/**
	 * 构造函数
	 * 
	 * @param dictType
	 *            字典类型
	 * @param dictValue
	 *            字典值
	 * @param dictName
	 *            字典名称
	 * @param sort
	 *            排序号
	 * @param memo
	 *            备注
	 */
	public Dict(String dictType, String dictValue, String dictName, Integer sort, String memo) {
		super();
		this.dictType = dictType;
		this.dictValue = dictValue;
		this.dictName = dictName;
		this.sort = sort;
		this.memo = memo;
	}

	public String getDictType() {
		return dictType;
	}

	public String getDictValue() {
		return dictValue;
	}

	public String getDictName() {
		return dictName;
	}

	public Integer getSort() {
		return sort;
	}

	public String getMemo() {
		return memo;
	}

}
