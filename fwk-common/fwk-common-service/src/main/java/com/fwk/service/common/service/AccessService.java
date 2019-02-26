package com.fwk.service.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

	/**
	 * 微服务主机IP
	 */
	@Value("${access.service.host}")
	public String SERVICE_HOST;// 内网的资源服务器

	/**
	 * trc-service-resource 上传图片端口
	 */
	@Value("${access.service.port}")
	public Integer SERVICE_DB_PORT;

	/**
	 * 公网资源服务器主机IP
	 */
	@Value("${access.service.resource.host}")
	public String RESOURCE_PUBLIC_HOST;// 内网的资源服务器

	/**
	 * 公网资源访问端口-FastDFS
	 */
	@Value("${access.service.resource.port}")
	public Integer SERVICE_PUBLIC_PORT;

	/**
	 * 上传资源文件
	 */
	@Value("${access.service.upload.url}")
	public String SERVICE_DB_UPLOADRESOURCE;

	/**
	 * 删除资源文件
	 */
	@Value("${access.service.delete.url}")
	public String SERVICE_DB_DELETERESOURCE;

	/**
	 * 获取资源调用地址(公网)
	 */
	@Value("${access.service.delete.url}")
	public String SERVICE_GETMDBRESOURCE;

	/**
	 * 阿里云的OSS服务
	 */
	@Value("${aliyun.oss.endpoint}")
	public String ALIYUN_OSS_ENDPOINT;

	/**
	 * 阿里云OSS的访问密钥
	 */
	@Value("${aliyun.oss.access_key_id}")
	public String ALIYUN_OSS_ACCESSKEYID;

	/**
	 * 阿里云OSS的签名密钥
	 */
	@Value("${aliyun.oss.access_key_secret}")
	public String ALIYUN_OSS_ACCESSKEYSECRET;

	/**
	 * 将资源传传到阿里云的哪个OSS库上
	 */
	@Value("${aliyun.oss.bukker}")
	public String ALIYUN_OSS_BUKKER;

}
