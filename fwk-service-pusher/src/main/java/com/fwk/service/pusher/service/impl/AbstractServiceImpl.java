package com.fwk.service.pusher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractServiceImpl {

	protected static final String TENANT_STAFF_EXT_AGGREGATE_API_ADDR = "tenant.staff/aggregate";
	protected static final String TENANT_STAFF_AGGREGATE_API_ADDR = "tenant.staff/aggregate";
	protected static final String MESSAGE_API_ADDR = "message";
	protected static final String TENANT_AGGREGATE_API_ADDR = "sys.tenant/aggregate";
	
	@Value("${support.ip}")
	private String issIp;

	@Value("${support.port}")
	private String issPort;

	@Value("${support.path}")
	private String issPath;

	@Value("${fwk.ip}")
	private String fwkIp;

	@Value("${fwk.port}")
	private String fwkPort;

	@Value("${fwk.path}")
	private String fwkPath;

	public String getIssIp() {
		return issIp;
	}

	public void setIssIp(String issIp) {
		this.issIp = issIp;
	}

	public String getIssPort() {
		return issPort;
	}

	public void setIssPort(String issPort) {
		this.issPort = issPort;
	}

	public String getIssPath() {
		return issPath;
	}

	public void setIssPath(String issPath) {
		this.issPath = issPath;
	}

	public String getFwkIp() {
		return fwkIp;
	}

	public void setFwkIp(String fwkIp) {
		this.fwkIp = fwkIp;
	}

	public String getFwkPort() {
		return fwkPort;
	}

	public void setFwkPort(String fwkPort) {
		this.fwkPort = fwkPort;
	}

	public String getFwkPath() {
		return fwkPath;
	}

	public void setFwkPath(String fwkPath) {
		this.fwkPath = fwkPath;
	}

	public String getFwkBaseUrl() {
		return String.format("http://%s:%s%s", fwkIp, fwkPort, fwkPath.startsWith("/") ? fwkPath : "/".concat(fwkPath));
	}

	public String getIssBaseUrl() {
		return String.format("http://%s:%s%s", issIp, issPort, issPath.startsWith("/") ? issPath : "/".concat(issPath));
	}

	protected abstract Logger getLogger();

	protected RestTemplate restTemplate = new RestTemplate();
	protected ObjectMapper objectMapper = new ObjectMapper();

	protected <T> T postForObject(String url, Object body, Class<T> clazz) throws Exception {
		getLogger().debug("HTTP URL:{}", url);
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String req = JSONObject.valueToString(body);
		getLogger().debug("HTTP REQ:{}", req);
		HttpEntity<String> httpEntity = new HttpEntity<>(req, headers);
		T response = restTemplate.postForObject(url.toString(), httpEntity, clazz);
		String res = objectMapper.writeValueAsString(response);
		getLogger().debug("HTTP RES:{}", res);
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
