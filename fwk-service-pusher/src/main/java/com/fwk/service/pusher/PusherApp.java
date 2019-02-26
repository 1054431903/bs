package com.fwk.service.pusher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fwk")
public class PusherApp {

	public static void main(String[] args) {
		SpringApplication.run(PusherApp.class, args);
	}
}
