package com.fwk.service.pusher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.service.pusher.service.TaskService;

@RestController
@RequestMapping("task")
public class TaskController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	TaskService taskService;

	@GetMapping("noticeWaitingForApproval/{taskId}")
	public void noticeWaitingForApprovalByTaskId(@PathVariable String taskId) throws Exception {
		taskService.noticeWaitingForApprovalByTaskId(taskId);
	}

	@GetMapping("noticeWaitingForExecution/{taskId}")
	public void noticeWaitingForExecutionByTaskId(@PathVariable String taskId) throws Exception {
		taskService.noticeWaitingForExecutionByTaskId(taskId);
	}
	
}
