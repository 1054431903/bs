package com.fwk.service.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.fwk")
public class ScheduleApp {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApp.class, args);
	}
}
