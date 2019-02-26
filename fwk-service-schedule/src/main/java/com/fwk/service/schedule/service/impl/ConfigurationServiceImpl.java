package com.fwk.service.schedule.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fwk.service.schedule.service.ConfigurationService;

@Primary
@Service
public class ConfigurationServiceImpl extends AbstractServiceImpl implements ConfigurationService {
	
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

	@Value("${fwk.service.archives.url}")
	private String fwkServiceArchivesUrl;
	
	@Value("${fwk.service.farm.url}")
	private String fwkServiceFarmUrl;
	
	@Value("${fwk.service.parcel.url}")
	private String fwkServiceParcelUrl;
	
	@Value("${fwk.service.pusher.url}")
	private String fwkServicePusherUrl;
	
	@Value("${fwk.service.safe.url}")
	private String fwkServiceSafeUrl;
	
	@Value("${fwk.service.schedule.url}")
	private String fwkServiceScheduleUrl;
	
	@Value("${fwk.service.system.url}")
	private String fwkServiceSystemUrl;
	
	@Value("${fwk.service.user.url}")
	private String fwkServiceUserUrl;
		
	public String getIssIp() {
		return issIp;
	}

	public String getIssPort() {
		return issPort;
	}

	public String getIssPath() {
		return issPath;
	}

	public String getFwkIp() {
		return fwkIp;
	}

	public String getFwkPort() {
		return fwkPort;
	}

	public String getFwkPath() {
		return fwkPath;
	}
	
	public String getFwkServiceArchivesUrl() {
		return fwkServiceArchivesUrl;
	}

	public String getFwkServiceFarmUrl() {
		return fwkServiceFarmUrl;
	}

	public String getFwkServiceParcelUrl() {
		return fwkServiceParcelUrl;
	}

	public String getFwkServicePusherUrl() {
		return fwkServicePusherUrl;
	}

	public String getFwkServiceSafeUrl() {
		return fwkServiceSafeUrl;
	}

	public String getFwkServiceScheduleUrl() {
		return fwkServiceScheduleUrl;
	}

	public String getFwkServiceSystemUrl() {
		return fwkServiceSystemUrl;
	}

	public String getFwkServiceUserUrl() {
		return fwkServiceUserUrl;
	}

	public String getFwkBaseUrl() {
		return String.format("http://%s:%s/%s", fwkIp, fwkPort, fwkPath);
	}

	public String getIssBaseUrl() {
		return String.format("http://%s:%s/%s", issIp, issPort, issPath);
	}
}
