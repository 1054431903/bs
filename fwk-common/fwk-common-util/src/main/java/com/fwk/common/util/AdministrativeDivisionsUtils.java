package com.fwk.common.util;

/**
 * 行政区划处理的工具类
 * 
 * @author wangjunwen
 * @date 2018年3月28日 下午3:47:31
 *
 */
public class AdministrativeDivisionsUtils {

	/**
	 * 截取行政区划编号右端的所有 0
	 * 
	 * <pre>
	 * 	例如：120000000000120000000000 或者 120000000000
	 * 	最后返回的结果为：12
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年3月28日 下午3:50:15
	 *
	 * @param code
	 *            行政区划编号
	 * @return
	 */
	public static String truncateRightAllZero(final String code) {

		String addressCode = ((null == code) ? null : code.trim());

		if ((null == addressCode) || (addressCode.isEmpty())) {
			return null;
		}

		if (24 == addressCode.length()) {
			addressCode = addressCode.substring(0, 12);
		}

		StringBuffer addressCodeBuffer = new StringBuffer(addressCode);

		// 行政区划编号的左右端调个方向（例如：530000000000 变更为 000000000035）
		addressCodeBuffer.reverse();

		// 把左端的 0 全部替换为空（例如：000000000035 变更为 35）
		addressCode = addressCodeBuffer.toString().replaceFirst("[0]*", "");

		addressCodeBuffer = new StringBuffer(addressCode);

		// 再把行政区划编号的左右端调个方向（例如：35 变更为 53）
		addressCode = addressCodeBuffer.reverse().toString();

		return addressCode;
	}

}
