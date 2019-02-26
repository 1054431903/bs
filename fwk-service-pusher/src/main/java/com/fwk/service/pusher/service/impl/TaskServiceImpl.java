package com.fwk.service.pusher.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fwk.service.pusher.domain.Notice;
import com.fwk.service.pusher.service.PushService;
import com.fwk.service.pusher.service.TaskService;

@Primary
@Service
public class TaskServiceImpl extends AbstractServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	private static final String TYPE_TASK_WAITING_FOR_APPROVAL = "notice.task.waitingForApproval";
	private static final String TYPE_TASK_WAITING_FOR_EXECUTION = "notice.task.waitingForExecution";
	private static final String TITLE = "任务提醒";
	private static final String CONTENT_WAITING_FOR_APPROVAL = "有一个需要您审批的任务";
	private static final String CONTENT_WAITING_FOR_EXECUTION = "您有一个新任务";

	private static final String TASK_AGGREGATE_API_ADDR = "task/aggregate";

	@Autowired
	PushService pushService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void noticeWaitingForApprovalByTaskId(String taskId) throws Exception {
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(TASK_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        		    put("_id", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", taskId);
        		    }});
        		}});
    	    }});
    	}};
    	// @formatter:on
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return;
		}
		Map<String, Object> task = response[0];
		if (ObjectUtils.isEmpty(task)) {
			return;
		}
		Map<String, Object> endOn = (Map<String, Object>) task.get("endOn");
		if (ObjectUtils.isEmpty(endOn)) {
			return;
		}
		String endOn$numberLong = (String) endOn.get("$numberLong");
		Date endOnDate = new Date(Long.parseLong(endOn$numberLong));
		if (endOnDate.getTime() < new Date().getTime()) {
			return;
		}
		Map<String, Object> _id = (Map<String, Object>) task.get("_id");
		if (ObjectUtils.isEmpty(_id)) {
			return;
		}
		String $oid = (String) _id.get("$oid");
		if (ObjectUtils.isEmpty($oid)) {
			return;
		}
		Map<String, Object> approver = (Map<String, Object>) task.get("approver");
		if (ObjectUtils.isEmpty(approver)) {
			return;
		}
		String approver$oid = (String) approver.get("$oid");
		if (ObjectUtils.isEmpty(approver$oid)) {
			return;
		}

		{
			StringBuilder builderSave = new StringBuilder(getFwkBaseUrl());
			builderSave.append(MESSAGE_API_ADDR);
			// @formatter:off
			@SuppressWarnings("serial")
			Map<?, ?> data = new LinkedHashMap<String, Object>() {{
				put("type", TYPE_TASK_WAITING_FOR_APPROVAL);
				put("param", new LinkedHashMap<String, Object>() {{
					put("task", task.get("_id"));
					put("title", TITLE);
					put("subTitle", CONTENT_WAITING_FOR_APPROVAL);
				}});
				put("tenant", task.get("tenant"));
				put("staff", approver);
				put("readed", false);
				put("stage", "creation");
			}};
			// @formatter:on
			postForObject(builderSave.toString(), data, Map.class);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void noticeWaitingForExecutionByTaskId(String taskId) throws Exception {
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(TASK_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        		    put("_id", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", taskId);
        		    }});
        		}});
    	    }});
    	}};
    	// @formatter:on)
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return;
		}
		Map<String, Object> task = response[0];
		if (ObjectUtils.isEmpty(task)) {
			return;
		}
		Map<String, Object> endOn = (Map<String, Object>) task.get("endOn");
		if (ObjectUtils.isEmpty(endOn)) {
			return;
		}
		String endOn$numberLong = (String) endOn.get("$numberLong");
		Date endOnDate = new Date(Long.parseLong(endOn$numberLong));
		if (endOnDate.getTime() < new Date().getTime()) {
			return;
		}
		Map<String, Object> _id = (Map<String, Object>) task.get("_id");
		if (ObjectUtils.isEmpty(_id)) {
			return;
		}
		String $oid = (String) _id.get("$oid");
		if (ObjectUtils.isEmpty($oid)) {
			return;
		}
		List<Map<String, Object>> participators = (List<Map<String, Object>>) task.get("participators");
		if (ObjectUtils.isEmpty(participators)) {
			return;
		}
		Set<String> participatorIds = participators.stream().filter(participator -> {
			return !ObjectUtils.isEmpty(participator);
		}).map(participator -> {
			return (String) participator.get("$oid");
		}).filter(participator$oid -> {
			return !ObjectUtils.isEmpty(participator$oid);
		}).collect(Collectors.toSet());

		{
			for (String participatorId : participatorIds) {
				StringBuilder builderSave = new StringBuilder(getFwkBaseUrl());
				builderSave.append(MESSAGE_API_ADDR);
				// @formatter:off
				@SuppressWarnings("serial")
				Map<?, ?> data = new LinkedHashMap<String, Object>() {{
					put("type", TYPE_TASK_WAITING_FOR_EXECUTION);
					put("param", new LinkedHashMap<String, Object>() {{
						put("task", task.get("_id"));
						put("title", TITLE);
						put("subTitle", CONTENT_WAITING_FOR_EXECUTION);
					}});
					put("tenant", task.get("tenant"));
					put("staff", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", participatorId);
        		    }});
					put("readed", false);
					put("stage", "creation");
				}};
				// @formatter:on
				postForObject(builderSave.toString(), data, Map.class);
			}
		}
	}
}
