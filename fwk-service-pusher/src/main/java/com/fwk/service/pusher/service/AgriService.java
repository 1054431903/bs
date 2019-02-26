package com.fwk.service.pusher.service;

public interface AgriService {

	void publish(String tenant, String title, String subTitle, String articleId, String articleType)
	        throws Exception;
}
