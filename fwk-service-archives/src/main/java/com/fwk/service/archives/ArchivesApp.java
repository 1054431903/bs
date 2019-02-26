package com.fwk.service.archives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fwk.service.common.safe.RequestCenter;

/**
 * 档案管理
 */
@SpringBootApplication
@ComponentScan("com.fwk")
public class ArchivesApp extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ArchivesApp.class, args);
	}

	// 添加拦截器
	@Bean
	public RequestCenter pagePopulationInterceptor() {
		return new RequestCenter();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(pagePopulationInterceptor());
	}
}
