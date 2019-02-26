package com.fwk.service.pusher.service;

public interface TaskService {

	void noticeWaitingForApprovalByTaskId(String taskId) throws Exception;

	void noticeWaitingForExecutionByTaskId(String taskId) throws Exception;

}
