package com.fwk.service.schedule.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskScanTimer extends AbstractTimer {

	private static final Logger logger = LoggerFactory.getLogger(TaskScanTimer.class);

	private static final String TASK_COLEECTION = "task";
	private static final String TASK_API_ADDR = "task";
	private static final String TASK_AGGREGATE_API_ADDR = "task/aggregate";

	private static final String TASK_MESSAGE_COLEECTION = "task.message.generate";
	private static final String TASK_MESSAGE_API_ADDR = "task.message.generate";
	private static final String TASK_MESSAGE_AGGREGATE_API_ADDR = "task.message.generate/aggregate";

	private static final String MESSAGE_API_ADDR = "message";

	private static final long SCAN_OFFSET = 15 * 60 * 1000L;

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ExecutorService executorService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Scheduled(cron = "0/60 * * * * *")
	public void scheduled() {
		// 计算时间
		Date now = new Date();
		Date sTime = new Date(now.getTime());
		Date eTime = new Date(now.getTime() + SCAN_OFFSET);
		// 执行任务
		executorService
		        .execute(new Workers(new NearTheStartScanWork(sTime, eTime), new NearTheEndScanWork(sTime, eTime)));
	}

	private class Workers implements Runnable {
		
		private Runnable[] runnables;
		
		public Workers(Runnable... runnables) {
			this.runnables = runnables;
		}
		
		@Override
		public void run() {
			if (runnables != null) {
				for (int i = 0; i < runnables.length; i++) {
					runnables[i].run();
				}
			}
		}
		
	}

	private abstract class ScanWorker implements Runnable {
		
		private final UUID taskId = UUID.randomUUID();
		private final Date startTime;
		private final Date endTime;

		public abstract String getTimeFiled();
		public abstract String getMessageType();
		public abstract String getTitle();
		public abstract String getSubTitle();

		public ScanWorker(Date startTime, Date endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@Override
		public void run() {
			try {
				init();
				logger.debug("TaskId：{}，Type：{}，工作开始，时段{} ~ {}", taskId, getMessageType(), dateFormat.format(startTime),
				        dateFormat.format(endTime));
				job();
				logger.debug("TaskId：{}，Type：{}，工作结束", taskId, getMessageType(), taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，Type：{}，工作失败", taskId, getMessageType(), taskId, e);
			} finally {
				finish();
			}
		}

		private void init() {

		}

		private void finish() {

		}

		public void job() throws Exception {
			Collection<Map<String, Object>> entities = null;
			try {
				logger.debug("TaskId：{}，扫描开始", taskId);
				entities = scan();
				logger.debug("TaskId：{}，扫描结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，扫描失败", taskId, e);
				throw e;
			}
			if (CollectionUtils.isEmpty(entities)) {
				return;
			}
			try {
				logger.debug("TaskId：{}，生成开始", taskId);
				generateMessage(entities);
				logger.debug("TaskId：{}，生成结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，生成失败", taskId, e);
				throw e;
			}
		}

		public void generateMessage(Collection<Map<String, Object>> entities) throws Exception {
			for (Map<String, Object> entity : entities) {
				generateMessage(entity);
			}
		}

		public void generateMessage(Map<String, Object> entity) throws Exception {
			{
				StringBuilder builder = new StringBuilder(getFwkBaseUrl());
				builder.append(TASK_MESSAGE_API_ADDR);
				// @formatter:off
				@SuppressWarnings("serial")
				Map<?, ?> data = new LinkedHashMap<String, Object>() {{
					put("type", getMessageType());
					put("task", entity.get("_id"));
					put("taskUpdatedOn", entity.get("updatedOn"));
					put("staff", entity.get("participators"));
				}};
				// @formatter:on
				postForObject(builder.toString(), data, Map.class);
			}
			{
				StringBuilder builder = new StringBuilder(getFwkBaseUrl());
				builder.append(MESSAGE_API_ADDR);
				// @formatter:off
				@SuppressWarnings("serial")
				Map<?, ?> data = new LinkedHashMap<String, Object>() {{
					put("type", getMessageType());
					put("param", new LinkedHashMap<String, Object>() {{
						put("task", entity.get("_id"));
						put("title", getTitle());
						put("subTitle", getSubTitle());
					}});
					put("tenant", entity.get("tenant"));
					put("staff", entity.get("participators"));
					put("readed", false);
					put("stage", "creation");
				}};
				// @formatter:on
				postForObject(builder.toString(), data, Map.class);
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
	        		    put("finish", false);
	        		    put("stage", "execution");
	        		    put(getTimeFiled(), new LinkedHashMap<String, Object>() {{
	        		    	put("$gte", startTime.getTime());
	        		    	put("$lt", endTime.getTime());
	        		    }});
	        		}});
	    	    }});
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$unwind", new LinkedHashMap<String, Object>() {{
	        		    put("path", "$participators");
	        		    put("preserveNullAndEmptyArrays", true);
	        		}});
	    	    }});
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$lookup", new LinkedHashMap<String, Object>() {{
	        		    put("from", TASK_MESSAGE_COLEECTION);
	        		    put("let", new LinkedHashMap<String, Object>() {{
	        		    	put("task", "$_id");
	        		    	put("taskUpdatedOn", "$updatedOn");
	        		    	put("participators", "$participators");
	        		    }});
	        		    put("pipeline", new ArrayList<Object>() {{
	        		    	add(new LinkedHashMap<String, Object>() {{
	        		    		put("$match", new LinkedHashMap<String, Object>() {{
	        		    			put("$expr", new LinkedHashMap<String, Object>() {{
	        		    				put("$and", new ArrayList<Object>() {{
	        		    					add(new LinkedHashMap<String, Object>() {{
	        		    						put("$eq", new ArrayList<Object>() {{
	        		    							add("$staff");
	        		    							add("$$participators");
	        		    						}});
	        		    					}});
	        		    					add(new LinkedHashMap<String, Object>() {{
	        		    						put("$eq", new ArrayList<Object>() {{
	        		    							add("$taskUpdatedOn");
	        		    							add("$$taskUpdatedOn");
	        		    						}});
	        		    					}});
	        		    					add(new LinkedHashMap<String, Object>() {{
	        		    						put("$eq", new ArrayList<Object>() {{
	        		    							add("$task");
	        		    							add("$$task");
	        		    						}});
	        		    					}});
	        		    					add(new LinkedHashMap<String, Object>() {{
	        		    						put("$eq", new ArrayList<Object>() {{
	        		    							add("$type");
	        		    							add(getMessageType());
	        		    						}});
	        		    					}});
	        		    				}});
		        		    		}});
	        		    		}});
	        		    	}});
	        		    }});
	        		    put("as", "message");
	        		}});
	    	    }});
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$unwind", new LinkedHashMap<String, Object>() {{
	        		    put("path", "$message");
	        		    put("preserveNullAndEmptyArrays", true);
	        		}});
	    	    }});
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$match", new LinkedHashMap<String, Object>() {{
	        			put("message", new LinkedHashMap<String, Object>() {{
	        				put("$exists", false);
	        			}});
	        		}});
	    	    }});
	    	}};
	    	// @formatter:on
			return aggregate;
		}

		@SuppressWarnings({ "unchecked" })
		public Collection<Map<String, Object>> scan() throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(TASK_AGGREGATE_API_ADDR);
			ArrayList<?> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			if (ArrayUtils.isEmpty(response)) {
				return null;
			}
			return Arrays.asList(response);
		}
	}

	private class NearTheStartScanWork extends ScanWorker {

		public NearTheStartScanWork(Date startTime, Date endTime) {
			super(startTime, endTime);
		}

		@Override
		public String getTimeFiled() {
			return "startOn";
		}

		@Override
		public String getMessageType() {
			return "notice.task.nearTheStart";
		}

		@Override
		public String getTitle() {
			return "任务提醒";
		}

		@Override
		public String getSubTitle() {
			return "您有一个任务即将开始";
		}
		
	}

	private class NearTheEndScanWork extends ScanWorker {

		public NearTheEndScanWork(Date startTime, Date endTime) {
			super(startTime, endTime);
		}

		@Override
		public String getTimeFiled() {
			return "endOn";
		}

		@Override
		public String getMessageType() {
			return "notice.task.nearTheEnd";
		}

		@Override
		public String getTitle() {
			return "任务提醒";
		}

		@Override
		public String getSubTitle() {
			return "您有一个任务即将逾期";
		}
		
	}
}
