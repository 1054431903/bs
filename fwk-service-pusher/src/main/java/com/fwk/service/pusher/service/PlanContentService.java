package com.fwk.service.pusher.service;

public interface PlanContentService {

	void noticeWaitingForExecutionByPlanContentId(String planContentId) throws Exception;
	
}