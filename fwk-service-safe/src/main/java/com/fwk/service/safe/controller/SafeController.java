package com.fwk.service.safe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/safe")
public class SafeController {
	
	@RequestMapping("/safeTest")
	public void safe(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("safe test");
	}
	
}