package com.fwk.service.schedule.timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fwk.service.schedule.service.NoticeService;

@Service
public class MessagePushTimer extends AbstractTimer {

	private static final Logger logger = LoggerFactory.getLogger(MessagePushTimer.class);

	private static final int DEFAULT_PAGE_SIZE = 100;
	private static final String MESSAGE_API_ADDR = "message";
	private static final String MESSAGE_AGGREGATE_API_ADDR = "message/aggregate";

	private static Date moment;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Autowired
	private NoticeService noticeService;

	@Scheduled(fixedDelay = 5000)
	public synchronized void scheduled() {
		// 执行任务
		run();
	}

	public void run() {
		try {
			init();
			job();
		} catch (Exception e) {
			logger.warn("推送异常", e);
		} finally {
			finish();
		}
	}

	private void init() {

	}

	private void finish() {

	}

	private void job() throws Exception {
		moment = new Date();
		for (;;) {
			Collection<Map<String, Object>> entities = scan();
			if (CollectionUtils.isEmpty(entities)) {
				break;
			}
			for (Map<String, Object> entity : entities) {
				try {
					push(entity);
					pushFinish(entity);
				} catch (Exception e) {
					pushFailure(entity);
					throw e;
				}
			}
		}
	}

	@SuppressWarnings("serial")
	private ArrayList<Map<String, Object>> buildQuery() {
		// @formatter:off
		ArrayList<Map<String, Object>> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        		    put("_removed", new LinkedHashMap<String, Object>() {{
        		    	put("$exists", false);
        		    }});
        		    put("createdOn", new LinkedHashMap<String, Object>() {{
        		    	put("$lt", moment.getTime());
        		    }});
        		    put("updatedOn", new LinkedHashMap<String, Object>() {{
        		    	put("$lt", moment.getTime());
        		    }});
        		    put("readed", new LinkedHashMap<String, Object>() {{
        		    	put("$not", new LinkedHashMap<String, Object>() {{
        		    		put("$eq", true);
        		    	}});
        		    }});
        		    put("stage", new LinkedHashMap<String, Object>() {{
        		    	put("$in", new ArrayList<Object>() {{
        		    		add("creation");
        		    		add("push");
        		    	}});
        		    }});
        		}});
    	    }});
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$sort", new LinkedHashMap<String, Object>() {{
	        		put("createdOn", 1);
	        		put("updatedOn", 1);
	        		put("_id", 1);
	    	    }});
    	    }});
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$limit", DEFAULT_PAGE_SIZE);
    	    }});
    	}};
    	// @formatter:on
		return aggregate;
	}

	@SuppressWarnings({ "unchecked" })
	public Collection<Map<String, Object>> scan() throws Exception {
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_AGGREGATE_API_ADDR);
		ArrayList<?> aggregate = buildQuery();
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ArrayUtils.isEmpty(response)) {
			return null;
		}
		return Arrays.asList(response);
	}

	@SuppressWarnings("unchecked")
	private void push(Map<String, Object> entity) throws Exception {
		String _id = (String) ((Map<String, Object>) entity.get("_id")).get("$oid");
		String staffId = (String) ((Map<String, Object>) entity.get("staff")).get("$oid");
		String tenantId = (String) ((Map<String, Object>) entity.get("tenant")).get("$oid");
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_API_ADDR);
		// @formatter:off
		@SuppressWarnings("serial")
		Map<?,?> data = new LinkedHashMap<String,Object>() {{
			put("filter", new LinkedHashMap<String, Object>() {{
				put("_id", new LinkedHashMap<String, Object>() {{
					put("$oid", _id);
				}});
			}});
			put("update", new LinkedHashMap<String, Object>() {{
				put("$set", new LinkedHashMap<String, Object>() {{
					put("stage", "push");
				}});
			}});
		}};
		// @formatter:on
		putForObject(builder.toString(), data, Map.class);
		String loginTenant = queryLoginTenentByStaffId(staffId);
		if(StringUtils.isEmpty(loginTenant)) {
			return;
		}
		if(!loginTenant.equals(tenantId)) {
			return;
		}
		noticeService.notice(_id);
	}

	@SuppressWarnings("unchecked")
	private void pushFinish(Map<String, Object> entity) throws Exception {
		String _id = (String) ((Map<String, Object>) entity.get("_id")).get("$oid");
		logger.debug("Push Finish -> MessageId:{}", _id);
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_API_ADDR);
		// @formatter:off
		@SuppressWarnings("serial")
		Map<?,?> data = new LinkedHashMap<String,Object>() {{
			put("filter", new LinkedHashMap<String, Object>() {{
				put("_id", new LinkedHashMap<String, Object>() {{
					put("$oid", _id);
				}});
			}});
			put("update", new LinkedHashMap<String, Object>() {{
				put("$set", new LinkedHashMap<String, Object>() {{
					put("stage", "finish");
				}});
			}});
		}};
		// @formatter:on
		putForObject(builder.toString(), data, Map.class);
	}

	@SuppressWarnings("unchecked")
	private void pushFailure(Map<String, Object> entity) throws Exception {
		String _id = (String) ((Map<String, Object>) entity.get("_id")).get("$oid");
		logger.debug("Push Failure -> MessageId:{}", _id);
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_API_ADDR);
		// @formatter:off
		@SuppressWarnings("serial")
		Map<?,?> data = new LinkedHashMap<String,Object>() {{
			put("filter", new LinkedHashMap<String, Object>() {{
				put("_id", new LinkedHashMap<String, Object>() {{
					put("$oid", _id);
				}});
			}});
			put("update", new LinkedHashMap<String, Object>() {{
				put("$set", new LinkedHashMap<String, Object>() {{
					put("stage", "finish");
					put("failure", "true");
				}});
			}});
		}};
		// @formatter:on
		putForObject(builder.toString(), data, Map.class);
	}

}
