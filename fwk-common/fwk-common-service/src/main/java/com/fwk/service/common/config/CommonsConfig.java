package com.fwk.service.common.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fwk.service.common.service.FileUploadDownloadService;

/**
 * 公共配置
 * 
 * @author wangjunwen
 * @date 2017年8月25日 上午10:56:05
 *
 */
@Configuration
public class CommonsConfig {
	
	@Autowired 
	private FileUploadDownloadService fileUploadDownloadService;

	/** 上传文件的大小限制 */
	private static final long MAX_UPLOAD_FILE_SIZE = (1024L * 1024L * 200L);

	/**
	 * 上传文件的相关配置
	 * 
	 * @author wangjunwen
	 * @date 2017年8月25日 上午10:57:07
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		// 文件大小限制
		factory.setMaxFileSize(MAX_UPLOAD_FILE_SIZE);
		
		// 文件上传临时路径
		factory.setLocation(fileUploadDownloadService.TEMP_FILE_UPLOAD_PATH);
		
		return factory.createMultipartConfig();
	}

}
