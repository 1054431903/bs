package com.fwk.service.schedule.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 时区配置
 * 
 * @author Leesam
 *
 */
@Configuration
public class TimeZoneConfigurer {

	@Bean
	@Primary
	public TimeZone timeZone() {
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(timeZone);
		return timeZone;
	}

}
