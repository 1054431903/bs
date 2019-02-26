package com.fwk.service.schedule.service.impl;

import org.codehaus.jackson.map.ObjectMapper;

import com.fwk.service.schedule.ext.RestTemplateExt;

public abstract class AbstractServiceImpl {
	
	protected RestTemplateExt restTemplate = new RestTemplateExt();
	
	protected ObjectMapper objectMapper = new ObjectMapper();
	
}
