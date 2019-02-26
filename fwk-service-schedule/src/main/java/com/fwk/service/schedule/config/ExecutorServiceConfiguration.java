package com.fwk.service.schedule.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ExecutorServiceConfiguration {
	
	@Primary
	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}
	
}
