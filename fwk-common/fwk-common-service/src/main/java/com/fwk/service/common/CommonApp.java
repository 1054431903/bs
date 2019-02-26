package com.fwk.service.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 公共服务
 */
@SpringBootApplication
@ComponentScan("com.fwk")
@MapperScan("com.fwk")
public class CommonApp extends WebMvcConfigurerAdapter
{
    public static void main( String[] args )
    {
        SpringApplication.run(CommonApp.class, args);
    }
   
}