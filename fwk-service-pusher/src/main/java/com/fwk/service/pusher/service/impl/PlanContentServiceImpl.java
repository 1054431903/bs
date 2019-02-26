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
import com.fwk.service.pusher.service.PlanContentService;
import com.fwk.service.pusher.service.PushService;

@Primary
@Service
public class PlanContentServiceImpl extends AbstractServiceImpl implements PlanContentService {

	private static final Logger logger = LoggerFactory.getLogger(PlanContentServiceImpl.class);

	public static final String DEFAULT_TITLE = "瞰农";

	public static final String CATEGORY_PLAN_CONTENT_WAITING_FOR_EXECUTION = "plan_waiting_for_execution";
	public static final String ALERT_WAITING_FOR_EXECUTION = "您有一个新计划";

	private static final String PARAM_PLAN_ID = "planId";
	private static final String PARAM_PLAN_CONTENT_ID = "planContentId";
	private static final String PLAN_AGGREGATE_API_ADDR = "plan/aggregate";
	private static final String PLAN_CONTENT_AGGREGATE_API_ADDR = "plan.content/aggregate";
	private static final String TENANT_STAFF_EXT_AGGREGATE_API_ADDR = "tenant.staff/aggregate";

	@Autowired
	PushService pushService;

	@Override
	protected Logger getLogger() {
		return logger;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Notice> buildNoticeWaitingForExecutionByPlanContentId(String planContentId)
	        throws Exception {
		String id = null;
		String planId = null;
		{// 查询计划内容信息
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_AGGREGATE_API_ADDR);
			// @formatter:off
        	@SuppressWarnings("serial")
        	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
        	    add(new LinkedHashMap<String, Object>() {{
            		put("$match", new LinkedHashMap<String, Object>() {{
            		    put("_id", new LinkedHashMap<String, Object>() {{
            		    	put("$oid", planContentId);
            		    }});
            		}});
        	    }});
        	}};
        	// @formatter:on

			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			if (ObjectUtils.isEmpty(response)) {
				return null;
			}
			Map<String, Object> planContent = response[0];
			if (ObjectUtils.isEmpty(planContent)) {
				return null;
			}

			Map<String, Object> _id = (Map<String, Object>) planContent.get("_id");
			if (ObjectUtils.isEmpty(_id)) {
				return null;
			}
			String $oid = (String) _id.get("$oid");
			if (ObjectUtils.isEmpty($oid)) {
				return null;
			}
			id = $oid;

			Map<String, Object> plan = (Map<String, Object>) planContent.get("plan");
			if (ObjectUtils.isEmpty(plan)) {
				return null;
			}
			String plan$oid = (String) plan.get("$oid");
			if (ObjectUtils.isEmpty(plan$oid)) {
				return null;
			}
			planId = plan$oid;
		}
		final String pId = planId;
		Set<String> participatorIds = null;
		{// 查询计划信息
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_AGGREGATE_API_ADDR);
			// @formatter:off
    		@SuppressWarnings("serial")
    		ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    		    add(new LinkedHashMap<String, Object>() {{
    		    	put("$match", new LinkedHashMap<String, Object>() {{
        			    put("_id", new LinkedHashMap<String, Object>() {{
        			    	put("$oid", pId);
        			    }});
    				}});
    		    }});
    		}};
			// @formatter:on

			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			if (ObjectUtils.isEmpty(response)) {
				return null;
			}
			Map<String, Object> plan = response[0];
			if (ObjectUtils.isEmpty(plan)) {
				return null;
			}

			Map<String, Object> _id = (Map<String, Object>) plan.get("_id");
			if (ObjectUtils.isEmpty(_id)) {
				return null;
			}
			String $oid = (String) _id.get("$oid");
			if (ObjectUtils.isEmpty($oid)) {
				return null;
			}

			List<Map<String, Object>> participators = (List<Map<String, Object>>) plan.get("participators");
			if (ObjectUtils.isEmpty(participators)) {
				return null;
			}
			participatorIds = participators.stream().filter(participator -> {
				return !ObjectUtils.isEmpty(participator);
			}).map(participator -> {
				return (String) participator.get("$oid");
			}).filter(participator$oid -> {
				return !ObjectUtils.isEmpty(participator$oid);
			}).collect(Collectors.toSet());
		}
		Collection<Notice> notices = new ArrayList<>();
		for (String participatorId : participatorIds) {
			Notice notice = new Notice();
//			notice.setPlanId(planId);
//			notice.setPlanContentId(id);
//			notice.setTenent(queryLoginTenentByStaffId(participatorId));
//			notice.setTenentStaffIds(new HashSet<>());
//			notice.getTenentStaffIds().add(participatorId);
//			notice.setTitle(DEFAULT_TITLE);
//			notice.setAlert(ALERT_WAITING_FOR_EXECUTION);
//			notice.setCategory(CATEGORY_PLAN_CONTENT_WAITING_FOR_EXECUTION);
//			notice.setExtra(null);
//			notices.add(notice);
		}
		return notices;
	}

	@Override
	public void noticeWaitingForExecutionByPlanContentId(String planContentId) throws Exception {
		Collection<Notice> notices = buildNoticeWaitingForExecutionByPlanContentId(planContentId);
		if (ObjectUtils.isEmpty(notices)) {
			return;
		}
		for (Notice notice : notices) {
			pushService.push(notice.getTenent(), notice.getTenentStaff(), notice.getTitle(), notice.getSubTitle(),
			        notice.getCategory(), notice.getExtra());
		}
	}

}
