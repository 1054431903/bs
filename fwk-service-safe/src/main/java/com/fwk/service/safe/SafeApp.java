package com.fwk.service.safe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fwk.service.safe.center.RequestCenter;


/**
 * 等保及过滤
 */
@SpringBootApplication
@ComponentScan("com.fwk")
public class SafeApp extends WebMvcConfigurerAdapter
{
    public static void main( String[] args )
    {
        SpringApplication.run(SafeApp.class, args);
    }
    
    @Bean
    public RequestCenter pagePopulationInterceptor() {  
        return new RequestCenter();  
    }
    
//    @Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(pagePopulationInterceptor());
	}
   
}