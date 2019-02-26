package com.fwk.service.pusher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.service.pusher.service.MessageService;

@RestController
@RequestMapping("message")
public class MessageController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	protected MessageService messageService;
	
	@GetMapping("notice/{messageId}")
	public void notice(@PathVariable String messageId) throws Exception {
		messageService.notice(messageId);
	}
	
}
