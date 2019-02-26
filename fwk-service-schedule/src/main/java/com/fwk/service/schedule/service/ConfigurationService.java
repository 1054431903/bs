package com.fwk.service.schedule.service;

public interface ConfigurationService {

	public String getIssIp();

	public String getIssPort();

	public String getIssPath();

	public String getFwkIp();

	public String getFwkPort();

	public String getFwkPath();
	
	public String getFwkBaseUrl();

	public String getIssBaseUrl();
	
	public String getFwkServiceArchivesUrl();

	public String getFwkServiceFarmUrl();

	public String getFwkServiceParcelUrl();

	public String getFwkServicePusherUrl();

	public String getFwkServiceSafeUrl();

	public String getFwkServiceScheduleUrl();

	public String getFwkServiceSystemUrl();

	public String getFwkServiceUserUrl();
}
