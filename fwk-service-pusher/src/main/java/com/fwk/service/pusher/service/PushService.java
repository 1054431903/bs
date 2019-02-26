package com.fwk.service.pusher.service;

import java.util.Map;
import java.util.Set;

public interface PushService {
	void push(String tenantId, String tenantStaffId, String title, String subTitle, String category) throws Exception;

	void push(String tenantId, String tenantStaffId, String title, String subTitle, String category, Map<String, String> params)
	        throws Exception;
}
