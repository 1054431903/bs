package com.fwk.service.pusher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.service.pusher.service.PlanContentService;

@RestController
@RequestMapping("planContent")
public class PlanContentController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	PlanContentService planContentService;

	@GetMapping("noticeWaitingForExecution/{planContentId}")
	public void noticeWaitingForExecutionByPlanContentId(@PathVariable String planContentId) throws Exception {
		planContentService.noticeWaitingForExecutionByPlanContentId(planContentId);
	}

}
