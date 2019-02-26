package com.fwk.service.pusher.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fwk.service.pusher.domain.Notice;
import com.fwk.service.pusher.service.MessageService;
import com.fwk.service.pusher.service.PushService;

@Primary
@Service
public class MessageServiceImpl extends AbstractServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	private static final String MESSAGE_AGGREGATE_API_ADDR = "message/aggregate";

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Autowired
	protected PushService pushService;

	@Override
	public void notice(String messageId) throws Exception {
		notice(query(messageId));
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> query(String messageId) throws Exception {
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        		    put("_id", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", messageId);
        		    }});
        		}});
    	    }});
    	}};
    	// @formatter:on)
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return null;
		}
		Map<String, Object> message = response[0];
		if (ObjectUtils.isEmpty(message)) {
			return null;
		}
		return message;
	}

	public void notice(Map<String, Object> message) throws Exception {
		if (message == null) {
			return;
		}
		notice(buildNotice(message));
	}

	@SuppressWarnings("unchecked")
	public Notice buildNotice(Map<String, Object> message) throws Exception {
		String messageId = (String)((Map<String, Object>) message.get("_id")).get("$oid");
		Map<String, Object> param = (Map<String, Object>) message.get("param");
		Notice notice = new Notice();
		notice.setCategory((String) message.get("type"));
		notice.setTenent((String) ((Map<String, Object>) message.get("tenant")).get("$oid"));
		notice.setTenentStaff((String) ((Map<String, Object>) message.get("staff")).get("$oid"));
		notice.setTitle((String) param.get("title"));
		notice.setSubTitle((String) param.get("subTitle"));
		
		switch (notice.getCategory()) {
			case "notice.plan.nearTheStart": {
				notice.setExtra(buildPlanContentNoticeParams(messageId, param));
				break;
			}
			case "notice.task.waitingForApproval":
			case "notice.task.waitingForExecution":
			case "notice.task.nearTheStart":
			case "notice.task.nearTheEnd": {
				notice.setExtra(buildTaskNoticeParams(messageId, param));
				break;
			}
			case "notice.agri.original":
			case "notice.agri.reference": {
				notice.setExtra(buildAgriNoticeParams(messageId, param));
				break;
			}
		}
		return notice;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> buildTaskNoticeParams(String messageId, Map<String, Object> param) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("message", messageId);
		params.put("task", (String) ((Map<String, Object>) param.get("task")).get("$oid"));
		params.put("title", (String) param.get("title"));
		params.put("subTitle", (String) param.get("subTitle"));
		return params;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> buildPlanContentNoticeParams(String messageId, Map<String, Object> param) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("message", messageId);
		params.put("planContent", (String) ((Map<String, Object>) param.get("planContent")).get("$oid"));
		params.put("title", (String) param.get("title"));
		params.put("subTitle", (String) param.get("subTitle"));
		return params;
	}

	public Map<String, String> buildAgriNoticeParams(String messageId, Map<String, Object> param) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("message", messageId);
		params.put("article", (String) param.get("article"));
		params.put("title", (String) param.get("title"));
		params.put("subTitle", (String) param.get("subTitle"));
		return params;
	}

	public void notice(Notice notice) throws Exception {
		if (notice == null) {
			return;
		}
		String tenent = queryLoginTenentByStaffId(notice.getTenentStaff());
		if(tenent == null) {
			return;
		}
		if(!tenent.equals(notice.getTenent())) {
			return;
		}
		pushService.push(notice.getTenent(), notice.getTenentStaff(), notice.getTitle(), notice.getSubTitle(),
		        notice.getCategory(), notice.getExtra());
	}

}
