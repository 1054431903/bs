package com.fwk.service.schedule.timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class PlanContentPushTimer extends AbstractTimer {

	private static final Logger logger = LoggerFactory.getLogger(PlanContentPushTimer.class);

	@Override
	protected Logger getLogger() {
		return logger;
	}

	private static final String PLAN_CONTENT_AGGREGATE_API_ADDR = "plan.content/aggregate";
	private static final String PLAN_CONTENT_MESSAGE_AGGREGATE_API_ADDR = "plan.content.message.generate/aggregate";
	private static final String PLAN_CONTENT_MESSAGE_API_ADDR = "plan.content.message.generate";
	private static final String MESSAGE_API_ADDR = "message";
	
	// 工作线程
	private NearTheStartScanWork nearTheStartScanWorker;
	private NearTheStartPushWorker nearTheStartPushWorker;

	@Autowired
	private ExecutorService executorService;


	@Scheduled(cron = "0 0/10 * * * *")
	public void ScanScheduled() {
		// 执行扫描任务
		if (nearTheStartScanWorker == null) {
			synchronized (this) {
				if (nearTheStartScanWorker == null) {
					nearTheStartScanWorker = new NearTheStartScanWork();
					nearTheStartScanWorker.setListener(() -> nearTheStartScanWorker = null);
					executorService.execute(nearTheStartScanWorker);
				}
			}
		}
	}

	@Scheduled(cron = "0 0 9 * * *")
	private void PushScheduled() {
		if (nearTheStartPushWorker == null) {
			synchronized (this) {
				if (nearTheStartPushWorker == null) {
					nearTheStartPushWorker = new NearTheStartPushWorker();
					nearTheStartPushWorker.setListener(() -> nearTheStartPushWorker = null);
					executorService.execute(nearTheStartPushWorker);
				}
			}
		}
	}

	@FunctionalInterface
	private interface ScanWorkerListener {
		void onFinished();
	}

	public abstract class ScanWork implements Runnable {
		private final UUID taskId = UUID.randomUUID();
		private ScanWorkerListener listener;

		public abstract String getMessageType();

		public void setListener(ScanWorkerListener listener) {
			this.listener = listener;
		}

		@Override
		public void run() {
			try {
				logger.debug("TaskId：{}，计划内容消息通知工作开始", taskId);
				work();
				logger.debug("TaskId：{}，计划内容消息通知工作结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，计划内容消息通知工作失败", taskId, e);
			} finally {
				if (listener != null) {
					listener.onFinished();
				}
			}
		}

		private void work() throws Exception {
			Collection<Map<String, Object>> entities = null;
			try {
				logger.debug("TaskId：{}，计划内容扫描开始", taskId);
				entities = scan();
				logger.debug("TaskId：{}，计划内容扫描结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，计划内容扫描失败", taskId, e);
				throw e;
			}
			if (CollectionUtils.isEmpty(entities)) {
				return;
			}
			try {
				logger.debug("TaskId：{}，生成计划内容推送消息开始", taskId);
				generateMessage(entities);
				logger.debug("TaskId：{}，生成计划内容推送消息结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，生成计划内容推送消息失败", taskId, e);
				throw e;
			}
		}

		@SuppressWarnings({ "serial", "unchecked" })
		private Collection<Map<String, Object>> scan() throws Exception {
			// 查询计划内容
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_AGGREGATE_API_ADDR);
			ArrayList<Map<String, Object>> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			if (ObjectUtils.isEmpty(response)) {
				return null;
			}
			Collection<Map<String, Object>> planContents = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> entity : response) {
				Map<String, Object> id = (Map<String, Object>) entity.get("_id");
				Map<String, Object> parent = (Map<String, Object>) entity.get("parent");
				Map<String, Object> staff = (Map<String, Object>) parent.get("participators");
				Map<String, Object> tenant= (Map<String, Object>) parent.get("tenant");
				Map<String, Object> startOn = (Map<String, Object>) entity.get("startOn");
				Map<String, Object> endOn = (Map<String, Object>) entity.get("endOn");
				// @formatter:off
				planContents.add(new LinkedHashMap<String, Object>() {{
					put("planContentId", id);
					put("staffId", staff);
					put("tenantId", tenant);
					put("startOn", startOn);
					put("endOn", endOn);
				}});
				// @formatter:on
			}
			return planContents;
		}

		@SuppressWarnings({ "serial", "unchecked" })
		private void generateMessage(Collection<Map<String, Object>> entities) throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_MESSAGE_API_ADDR);
			for (Map<String, Object> entity : entities) {
				// @formatter:off
				Map<String, Object> data = new LinkedHashMap<String, Object>(){{
					put("planContent", entity.get("planContentId"));
					put("staff", entity.get("staffId"));
					put("tenant", entity.get("tenantId"));
					put("startOn", entity.get("startOn"));
					put("endOn", entity.get("endOn"));
					put("stage", "creation");
					put("type", getMessageType());
					put("readed", false);
				}};
				// @formatter:on
				Map<String, Object> response = postForObject(builder.toString(), data, Map.class);
				logger.info("消息结果:{}", response);
			}
		}

		@SuppressWarnings("serial")
		private ArrayList<Map<String, Object>> buildQuery() {
			// 当前时间的毫秒数
			long current = System.currentTimeMillis();
			// 今天零点零分零秒的毫秒数
			long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
			// 今天23点59分59秒的毫秒数
			long twelve = zero + 24 * 60 * 60 * 1000 - 1;
			// @formatter:off
			ArrayList<Map<String, Object>> aggregate = new ArrayList<Map<String, Object>>() {{
				add(new LinkedHashMap<String, Object>() {{
					put("$match", new LinkedHashMap<String, Object>() {{
						put("_removed", new LinkedHashMap<String, Object>() {{
							put("$exists", false);
						}});
						put("startOn", new LinkedHashMap<String, Object>() {{
							put("$gte", zero);
							put("$lte", twelve);
						}});
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$lookup", new LinkedHashMap<String, Object>() {{
						put("from", "plan");
						put("localField", "plan");
						put("foreignField", "_id");
						put("as", "parent");
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$unwind", new LinkedHashMap<String, Object>() {{
						put("path", "$parent");
						put("preserveNullAndEmptyArrays", false);
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$unwind", new LinkedHashMap<String, Object>() {{
						put("path", "$parent.participators");
						put("preserveNullAndEmptyArrays", false);
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$lookup", new LinkedHashMap<String, Object>() {{
						put("from", PLAN_CONTENT_MESSAGE_API_ADDR);
						put("let", new LinkedHashMap<String, Object>() {{
							put("planContent", "$_id");
							put("participators", "$parent.participators");
						}});
						put("pipeline", new ArrayList<Map<String, Object>>() {{
							add(new LinkedHashMap<String, Object>() {{
								put("$match", new LinkedHashMap<String, Object>() {{
									put("$expr", new LinkedHashMap<String, Object>() {{
										put("$and", new ArrayList<Map<String, Object>>() {{
											add(new LinkedHashMap<String, Object>() {{
												put("$eq", new ArrayList<String>() {{
													add("$staff");
													add("$$participators");
												}});
												put("$eq", new ArrayList<String>() {{
													add("$planContent");
													add("$$planContent");
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
	}

	private class NearTheStartScanWork extends ScanWork {

		@Override
		public String getMessageType() {
			return "notice.planContent.nearTheStart";
		}

	}

	@FunctionalInterface
	private interface PushWorkerListener {
		void onFinish();
	}

	private abstract class PushWorker implements Runnable {
		protected static final int DEFAULT_PAGE_SIZE = 100;
		private PushWorkerListener listener;

		public void setListener(PushWorkerListener listener) {
			this.listener = listener;
		}

		public abstract String getMessageType();
		public abstract String getTile();
		public abstract String getSubTitle();

		@Override
		public void run() {
			try {
				work();
			} catch (Exception e) {
				logger.warn("推送计划内容消息异常", e);
			} finally {
				if (listener != null) {
					listener.onFinish();
				}
			}
		}

		private void work() throws Exception {
			while (true) {
				Collection<Map<String, Object>> entities = scan();
				if (CollectionUtils.isEmpty(entities)) {
					return;
				}
				for (Map<String, Object> entity : entities) {
					try {
						push(entity);
						pushFinish(entity);
					} catch (Exception e) {
						pushFailure(entity);
					}
				}
			}
		}

		@SuppressWarnings("serial")
		private void push(Map<String, Object> entity) throws Exception {
			logger.debug("Push -> TaskId:{},StaffId:{}", entity.get("_id"), entity.get("staff"));
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_MESSAGE_API_ADDR);
			// @formatter:off
			Map<String, Object> data = new LinkedHashMap<String,Object>() {{
				put("filter", new LinkedHashMap<String, Object>() {{
					put("_id", entity.get("_id"));
				}});
				put("update", new LinkedHashMap<String, Object>() {{
					put("$set", new LinkedHashMap<String, Object>() {{
						put("stage", "push");
					}});
				}});
			}};
			// @formatter:on
			putForObject(builder.toString(), data, Map.class);
			if (StringUtils.isEmpty(entity.get("type"))) {
				return;
			}
			// 把推送信息转存到message表，然后统一推送
			builder = new StringBuilder(getFwkBaseUrl());
			builder.append(MESSAGE_API_ADDR);
			// @formatter:off
			Map<String, Object> body = new LinkedHashMap<String, Object>() {{
				put("type", getMessageType());
				put("param", new LinkedHashMap<String, Object>() {{
					put("planContent", entity.get("planContent"));
					put("title", getTile());
					put("subTitle", getSubTitle());
					
				}});
				put("tenant",entity.get("tenant"));
				put("staff", entity.get("staff"));
				put("stage", "creation");
				put("readed", false);
			}};
			// @formatter:on
			postForObject(builder.toString(), body, Map.class);
		}

		@SuppressWarnings("serial")
		private void pushFinish(Map<String, Object> entity) throws Exception {
			logger.debug("Push Finish -> TaskId:{},StaffId:{}", entity.get("_id"), entity.get("staff"));
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_MESSAGE_API_ADDR);
			// @formatter:off
			Map<String,Object> data = new LinkedHashMap<String,Object>() {{
				put("filter", new LinkedHashMap<String, Object>() {{
					put("_id", entity.get("_id"));
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

		@SuppressWarnings("serial")
		private void pushFailure(Map<String, Object> entity) throws Exception {
			logger.debug("Push Failure -> TaskId:{},StaffId:{}", entity.get("_id"), entity.get("staff"));
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_MESSAGE_API_ADDR);
			// @formatter:off
			Map<String,Object> data = new LinkedHashMap<String,Object>() {{
				put("filter", new LinkedHashMap<String, Object>() {{
					put("_id", entity.get("_id"));
				}});
				put("update", new LinkedHashMap<String, Object>() {{
					put("$set", new LinkedHashMap<String, Object>() {{
						put("stage", "finish");
						put("failure", true);
					}});
				}});
			}};
			// @formatter:on
			putForObject(builder.toString(), data, Map.class);
		}

		@SuppressWarnings("unchecked")
		private Collection<Map<String, Object>> scan() throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(PLAN_CONTENT_MESSAGE_AGGREGATE_API_ADDR);
			ArrayList<Map<String, Object>> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			if (ArrayUtils.isEmpty(response)) {
				return null;
			}
			return Arrays.asList(response);
		}

		@SuppressWarnings("serial")
		private ArrayList<Map<String, Object>> buildQuery() {
			// 当前时间的毫秒数
			long current = System.currentTimeMillis();
			// 今天零点零分零秒的毫秒数
			long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
			// 今天23点59分59秒的毫秒数
			long twelve = zero + 24 * 60 * 60 * 1000 - 1;
			// @formatter:off
			ArrayList<Map<String, Object>> aggregate = new ArrayList<Map<String, Object>>() {{
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$match", new LinkedHashMap<String, Object>() {{
	        		    put("_removed", new LinkedHashMap<String, Object>() {{
	        		    	put("$exists", false);
	        		    }});
	        		    put("startOn", new LinkedHashMap<String, Object>() {{
	        		    	put("$gte", zero);
	        		    	put("$lte", twelve);
	        		    }});
	        		    put("type", getMessageType());
	        		    put("stage", new LinkedHashMap<String, Object>() {{
	        		    	put("$in", new ArrayList<Object>() {{
	        		    		add("creation");
	        		    		add("push");
	        		    	}});
	        		    }});
	        		}});
	    	    }});
	    	    add(new LinkedHashMap<String, Object>() {{
	        		put("$limit", DEFAULT_PAGE_SIZE);
	    	    }});
	    	}};
			// @formatter:on
			return aggregate;
		}

	}

	private class NearTheStartPushWorker extends PushWorker {

		@Override
		public String getMessageType() {
			return "notice.planContent.nearTheStart";
		}

		@Override
		public String getTile() {
			return "计划提醒";
		}

		@Override
		public String getSubTitle() {
			return "您有一个计划即将开始";
		}

	}
}
