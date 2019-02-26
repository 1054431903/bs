package com.fwk.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * action 的相关信息的注解
 * 
 * @author wangjunwen
 * @date 2017年9月13日 上午9:07:44
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionInfo {

	/**
	 * 操作名
	 * 
	 * @author wangjunwen
	 * @date 2017年9月13日 上午9:11:59
	 *
	 * @return
	 */
	String operationKey();

	/**
	 * 操作的描述
	 * 
	 * @author wangjunwen
	 * @date 2017年9月13日 上午9:14:18
	 *
	 * @return
	 */
	String operationValue();

}
