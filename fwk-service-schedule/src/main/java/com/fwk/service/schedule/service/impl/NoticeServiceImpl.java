package com.fwk.service.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fwk.service.schedule.service.ConfigurationService;
import com.fwk.service.schedule.service.NoticeService;

@Primary
@Service
public class NoticeServiceImpl extends AbstractServiceImpl implements NoticeService {
	
	@Autowired
	protected ConfigurationService configurationService;
	
	private String getPusherUrl() {
		String url = configurationService.getFwkServicePusherUrl();
		if (url.endsWith("/")) {
			return url;
		} else {
			return url.concat("/");
		}
	}
	
	@Override
	public void noticeTaskNearTheStartByTaskIdAndStaffId(String taskId, String staffId) throws Exception {
		StringBuilder builder = new StringBuilder(getPusherUrl());
		builder.append("task/noticeNearTheStart");
		builder.append("/");
		builder.append(taskId);
		builder.append("/");
		builder.append(staffId);
		restTemplate.getForObject(builder.toString(), Void.class);
	}

	@Override
	public void noticeTaskNearTheEndByTaskIdAndStaffId(String taskId, String staffId) throws Exception {
		StringBuilder builder = new StringBuilder(getPusherUrl());
		builder.append("task/noticeNearTheEnd");
		builder.append("/");
		builder.append(taskId);
		builder.append("/");
		builder.append(staffId);
		restTemplate.getForObject(builder.toString(), Void.class);
	}

	@Override
	public void noticePlanContentNearTheStartByPlanContentIdAndStaffId(String planContentId, String staffId) throws Exception {
		StringBuilder builder = new StringBuilder(getPusherUrl());
		builder.append("planContent/noticeNearTheStart");
		builder.append("/");
		builder.append(planContentId);
		builder.append("/");
		builder.append(staffId);
		restTemplate.getForObject(builder.toString(), Void.class);
	}

	@Override
	public void noticePlanContentNearTheEndByPlanContentIdAndStaffId(String planContentId, String staffId) throws Exception {
		StringBuilder builder = new StringBuilder(getPusherUrl());
		builder.append("planContent/noticeNearTheEnd");
		builder.append("/");
		builder.append(planContentId);
		builder.append("/");
		builder.append(staffId);
		restTemplate.getForObject(builder.toString(), Void.class);
	}

	@Override
	public void notice(String messageId) {
		StringBuilder builder = new StringBuilder(getPusherUrl());
		builder.append("message/notice");
		builder.append("/");
		builder.append(messageId);
		restTemplate.getForObject(builder.toString(), Void.class);
	}

}
