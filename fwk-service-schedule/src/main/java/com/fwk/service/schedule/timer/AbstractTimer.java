package com.fwk.service.schedule.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.fwk.service.schedule.ext.RestTemplateExt;
import com.fwk.service.schedule.service.ConfigurationService;

public abstract class AbstractTimer {
	
	protected static final String TENANT_STAFF_EXT_AGGREGATE_API_ADDR = "tenant.staff/aggregate";
	
	protected abstract Logger getLogger();
	
	@Autowired
	protected ConfigurationService configurationService;
	
	public String getFwkBaseUrl() {
		return configurationService.getFwkBaseUrl();
	}

	public String getIssBaseUrl() {
		return configurationService.getIssBaseUrl();
	}
	
	protected RestTemplateExt restTemplate = new RestTemplateExt();
	
	protected ObjectMapper objectMapper = new ObjectMapper();

	protected <T> T postForObject(String url, Object body, Class<T> clazz) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String req = JSONObject.valueToString(body);
		getLogger().debug("[POST]HTTP REQ:{}", req);
		HttpEntity<String> httpEntity = new HttpEntity<>(req, headers);
		T response = restTemplate.postForObject(url.toString(), httpEntity, clazz);
		String res = objectMapper.writeValueAsString(response);
		getLogger().debug("[POST]HTTP RES:{}", res);
		return response;
	}
	
	protected <T> T putForObject(String url, Object body, Class<T> clazz) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String req = JSONObject.valueToString(body);
		getLogger().debug("[PUT]HTTP REQ:{}", req);
		HttpEntity<String> httpEntity = new HttpEntity<>(req, headers);

		T response = restTemplate.putForObject(url.toString(), httpEntity, clazz);
		String res = objectMapper.writeValueAsString(response);
		getLogger().debug("[PUT]HTTP RES:{}", res);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	protected String queryLoginTenentByStaffId(String staffId) throws Exception {
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(TENANT_STAFF_EXT_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        		    put("_id", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", staffId);
        		    }});
        		}});
    	    }});
    	}};
    	// @formatter:on
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return null;
		}
		Map<String, Object> staff = response[0];
		if (ObjectUtils.isEmpty(staff)) {
			return null;
		}
		Map<String, Object> loginTime = (Map<String, Object>) staff.get("loginTime");
		if (ObjectUtils.isEmpty(loginTime)) {
			return null;
		}
		String loginTime$numberLong = (String) loginTime.get("$numberLong");
		Date loginTimeDate = new Date(Long.parseLong(loginTime$numberLong));
		if (loginTimeDate.getTime() + 24 * 60 * 60 * 1000 < new Date().getTime()) {
			return null;
		}
		Map<String, Object> loginTenant = (Map<String, Object>) staff.get("loginTenant");
		if (ObjectUtils.isEmpty(loginTenant)) {
			return null;
		}
		String loginTenant$oid = (String) loginTenant.get("$oid");
		if (ObjectUtils.isEmpty(loginTenant$oid)) {
			return null;
		}
		return loginTenant$oid;
	}
}
