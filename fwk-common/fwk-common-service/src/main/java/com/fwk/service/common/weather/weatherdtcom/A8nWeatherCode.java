package com.fwk.service.common.weather.weatherdtcom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 气象编号
 * 
 * @author wangjunwen
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface A8nWeatherCode {
	public String code();
}
