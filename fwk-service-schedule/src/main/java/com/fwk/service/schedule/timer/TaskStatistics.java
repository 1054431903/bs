package com.fwk.service.schedule.timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Copyright (c) 2018 gstar
 * 
 * @Description: 组织内任务信息统计
 * 
 * @author: zhangyq
 * @date: 2018年10月23日 下午3:32:10
 */
@Service
public class TaskStatistics extends AbstractTimer {

	private static final Logger logger = LoggerFactory.getLogger(TaskStatistics.class);

	@Override
	protected Logger getLogger() {
		return logger;
	}

	private static final String TASK_AGGREGATE_API_ADDR = "task/aggregate";
	private static final String STATISTICS_API_ADDR = "statistics";

	private TenantTaskWorker tenantTaskWorker;

	private TenantStaffTaskWorker tenantStaffTaskWorker;
	
	private TenantTaskAddIndicator tenantTaskAddIndicator;
	
	private TenantTaskCompletedIndicator tenantTaskCompletedIndicator;

	@Autowired
	private ExecutorService executorService;

	@Scheduled(cron = "0 0 * * * *")
	private void tenantTaskStatisticsScheduled() {
		if (tenantTaskWorker == null) {
			synchronized (this) {
				if (tenantTaskWorker == null) {
					tenantTaskWorker = new TenantTaskWorker();
					tenantTaskWorker.setListener(() -> tenantTaskWorker = null);
					executorService.execute(tenantTaskWorker);
				}
			}
		}

	}

	@Scheduled(cron = "0 0 * * * *")
	private void tenantStaffTaskStatisticsScheduled() {
		if (tenantStaffTaskWorker == null) {
			synchronized (this) {
				if (tenantStaffTaskWorker == null) {
					tenantStaffTaskWorker = new TenantStaffTaskWorker();
					tenantStaffTaskWorker.setListener(() -> tenantStaffTaskWorker = null);
					executorService.execute(tenantStaffTaskWorker);
				}
			}
		}
	}
	
	@Scheduled(cron = "0 0 2 * * *")
	private void tenantTaskCompletedIndicatorStatisticsScheduled() {
		if (tenantTaskCompletedIndicator == null) {
			synchronized (this) {
				if (tenantTaskCompletedIndicator == null) {
					tenantTaskCompletedIndicator = new TenantTaskCompletedIndicator();
					tenantTaskCompletedIndicator.setListener(() -> tenantTaskCompletedIndicator = null);
					executorService.execute(tenantTaskCompletedIndicator);
				}	
			}
		}
	}
	
	
	@Scheduled(cron = "0 0 2 * * *")
	private void tenantTaskAddIndicatorStatisticsScheduled() {
		if (tenantTaskAddIndicator == null) {
			synchronized (this) {
				if (tenantTaskAddIndicator == null) {
					tenantTaskAddIndicator = new TenantTaskAddIndicator();
					tenantTaskAddIndicator.setListener(() -> tenantTaskAddIndicator = null);
					executorService.execute(tenantTaskAddIndicator);
				}	
			}
		}
	}
	

	@FunctionalInterface
	private interface TenantTaskWorkerListener {
		void onFinished();
	}

	public class TenantTaskWorker implements Runnable {
		private final UUID taskId = UUID.randomUUID();
		private Date moment;
		private TenantTaskWorkerListener listener;

		public void setListener(TenantTaskWorkerListener workerListener) {
			this.listener = workerListener;
		}

		@Override
		public void run() {
			try {
				logger.debug("TaskId：{}，按组织维度统计组织内任务相关指标工作开始", taskId);
				statistics();
				logger.debug("TaskId：{}，按组织统计组织内任务相关指标工作结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，按组织统计组织内任务相关指标工作失败", taskId, e);
			} finally {
				if (listener != null) {
					listener.onFinished();
				}
			}
		}

		private void statistics() throws Exception {
			Collection<Map<String, Object>> entities = null;
			moment = new Date();
			try {
				logger.debug("TaskId:{}, 开始获取组织内任务信息。", taskId);
				entities = getTasks();
				logger.debug("TaskId:{}, 获取组织内任务信息结束。", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，获取组织内任务信息出错", taskId, e);
				throw e;
			}
			if (CollectionUtils.isEmpty(entities)) {
				return;
			}
			try {
				logger.debug("TaskId:{}, 开始生成组织内任务统计信息。", taskId);
				generateStatistics(entities);
				logger.debug("TaskId:{}, 生成组织内任务统计信息结束。", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，生成组织内任务统计信息出错", taskId, e);
				throw e;
			}
		}

		@SuppressWarnings({ "serial", "unchecked" })
		private void generateStatistics(Collection<Map<String, Object>> entities) throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(STATISTICS_API_ADDR);
			for (Map<String, Object> entity : entities) {
				// @formatter:off
				 Map<String, Object> data = new LinkedHashMap<String, Object>() {{
					 put("tenant", entity.get("_id"));
					 put("all", entity.get("all"));
					 put("completed", entity.get("completed"));
					 put("uncompleted", entity.get("uncompleted"));
					 put("overdue", entity.get("overdue"));
					 put("type", "task.tenant");
					 // 统计时间
					 put("statisticsOn", moment.getTime());
				 }};
				// @formatter:on
				Map<String, Object> response = postForObject(builder.toString(), data, Map.class);
				logger.info("消息结果:{}", response);
			}
		}

		@SuppressWarnings("unchecked")
		private Collection<Map<String, Object>> getTasks() throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(TASK_AGGREGATE_API_ADDR);
			ArrayList<Map<String, Object>> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			return Arrays.asList(response);
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
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$addFields", new LinkedHashMap<String, Object>() {{
						put("all", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$or", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(false);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
												add(new LinkedHashMap<String, Object>() {{
													put("$eq", new ArrayList<Object>() {{
														add("$finish");
														add(true);
													}});
												}});
												add(new LinkedHashMap<String, Object>() {{
													put("$eq", new ArrayList<Object>() {{
														add("$stage");
														add("completion");
													}});
												}});
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("completed", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(true);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$stage");
												add("completion");
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("uncompleted", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$eq", new ArrayList<Object>() {{
										add("$finish");
										add(false);
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("overdue", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(false);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$stage");
												add("execution");
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$lt", new ArrayList<Object>() {{
												add("$endOn");
												add(moment.getTime());
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$group", new LinkedHashMap<String, Object>() {{
						put("_id", "$tenant");
						put("all", new LinkedHashMap<String, Object>() {{
							put("$sum", "$all");
						}});
						put("overdue", new LinkedHashMap<String, Object>() {{
							put("$sum", "$overdue");
						}});
						put("completed", new LinkedHashMap<String, Object>() {{
							put("$sum", "$completed");
						}});
						put("uncompleted", new LinkedHashMap<String, Object>() {{
							put("$sum", "$uncompleted");
						}});
					}});
				}});
			}};
			// @formatter:on
			return aggregate;
		}

	}

	@FunctionalInterface
	private interface TenantStaffTaskWorkerListener {
		void onFinished();
	}

	public class TenantStaffTaskWorker implements Runnable {

		private final UUID taskId = UUID.randomUUID();
		private Date moment;
		private TenantStaffTaskWorkerListener listener;

		public void setListener(TenantStaffTaskWorkerListener workerListener) {
			this.listener = workerListener;
		}

		@Override
		public void run() {
			try {
				logger.debug("TaskId：{}，按人员维度统计组织内任务相关指标工作开始", taskId);
				statistics();
				logger.debug("TaskId：{}，按人员维度统计组织内任务相关指标工作结束", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，按人员维度统计组织内任务相关指标工作失败", taskId, e);
			} finally {
				if (listener != null) {
					listener.onFinished();
				}
			}
		}

		private void statistics() throws Exception {
			Collection<Map<String, Object>> entities = null;
			moment = new Date();
			try {
				logger.debug("TaskId:{}, 按人员维度开始获取组织内任务信息。", taskId);
				entities = getTasks();
				logger.debug("TaskId:{}, 按人员维度获取组织内任务信息结束。", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，按人员维度获取组织内任务信息出错", taskId, e);
				throw e;
			}
			if (CollectionUtils.isEmpty(entities)) {
				return;
			}
			try {
				logger.debug("TaskId:{}, 按人员维度开始生成组织内任务统计信息。", taskId);
				generateStatistics(entities);
				logger.debug("TaskId:{}, 按人员维度生成组织内任务统计信息结束。", taskId);
			} catch (Exception e) {
				logger.warn("TaskId：{}，按人员维度生成组织内任务统计信息出错", taskId, e);
				throw e;
			}
		}

		@SuppressWarnings({ "serial", "unchecked" })
		private void generateStatistics(Collection<Map<String, Object>> entities) throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(STATISTICS_API_ADDR);
			for (Map<String, Object> entity : entities) {
				// @formatter:off
				 Map<String, Object> data = new LinkedHashMap<String, Object>() {{
					 Map<String, Object> _id = (Map<String, Object>) entity.get("_id");
					 put("tenant", _id.get("tenant"));
					 put("staff", _id.get("staff"));
					 put("all", entity.get("all"));
					 put("completed", entity.get("completed"));
					 put("uncompleted", entity.get("uncompleted"));
					 put("overdue", entity.get("overdue"));
					 put("type", "task.tenantStaff");
					 // 统计时间
					 put("statisticsOn", moment.getTime());
				 }};
				// @formatter:on
				Map<String, Object> response = postForObject(builder.toString(), data, Map.class);
				logger.info("消息结果:{}", response);
			}
		}

		@SuppressWarnings("unchecked")
		private Collection<Map<String, Object>> getTasks() throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(TASK_AGGREGATE_API_ADDR);
			ArrayList<Map<String, Object>> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			return Arrays.asList(response);
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
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$unwind", new LinkedHashMap<String, Object>() {{
						put("path", "$participators");
						put("preserveNullAndEmptyArrays", false);
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$addFields", new LinkedHashMap<String, Object>() {{
						put("all", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$or", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(false);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
												add(new LinkedHashMap<String, Object>() {{
													put("$eq", new ArrayList<Object>() {{
														add("$finish");
														add(true);
													}});
												}});
												add(new LinkedHashMap<String, Object>() {{
													put("$eq", new ArrayList<Object>() {{
														add("$stage");
														add("completion");
													}});
												}});
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("completed", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(true);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$stage");
												add("completion");
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("uncompleted", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$eq", new ArrayList<Object>() {{
										add("$finish");
										add(false);
									}});
								}});
								add(1);
								add(0);
							}});
						}});
						put("overdue", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$finish");
												add(false);
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$eq", new ArrayList<Object>() {{
												add("$stage");
												add("execution");
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$lt", new ArrayList<Object>() {{
												add("$endOn");
												add(moment.getTime());
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$group", new LinkedHashMap<String, Object>() {{
						put("_id", new LinkedHashMap<String, Object>() {{
							put("tenant", "$tenant");
							put("staff", "$participators");
						}});
						put("all", new LinkedHashMap<String, Object>() {{
							put("$sum", "$all");
						}});
						put("overdue", new LinkedHashMap<String, Object>() {{
							put("$sum", "$overdue");
						}});
						put("completed", new LinkedHashMap<String, Object>() {{
							put("$sum", "$completed");
						}});
						put("uncompleted", new LinkedHashMap<String, Object>() {{
							put("$sum", "$uncompleted");
						}});
					}});
				}});
			}};
			// @formatter:on
			return aggregate;
		}

	}

	@FunctionalInterface
	public interface SingleIndicatorWorkerListener{
		void onFinish();
	}
	
	private abstract class SingleIndicatorWorker implements Runnable {
		private final UUID taskId = UUID.randomUUID();
		private Date moment;
		private SingleIndicatorWorkerListener listener;
		
		public abstract String getTimeFiled();

		public abstract String getIndicatorName();
		
		public abstract long getStatisticsStartOn();
		
		public abstract long getStatisticsEndOn();
		
		public abstract String getIndicatorType();
		
		public void setListener(SingleIndicatorWorkerListener listener) {
			this.listener = listener;
		}

		@Override
		public void run() {
			try {
				logger.debug("TaskId：{}，统计组织内当天【{}】任务指标工作开始", taskId, getIndicatorName());
				statistics();
				logger.debug("TaskId：{}，统计组织内当天【{}】任务指标工作结束", taskId, getIndicatorName());
			} catch (Exception e) {
				logger.warn("TaskId：{}，统计组织内当天【{}】任务指标工作失败", taskId, getIndicatorName(), e);
			}finally {
				if (listener != null) {
					listener.onFinish();
				}
			}
		}

		private void statistics() throws Exception {
			Collection<Map<String, Object>> entities = null;
			moment = new Date();
			try {
				logger.debug("TaskId:{}, 开始获取组织内当天【{}】任务信息。", taskId, getIndicatorName());
				entities = getTasks();
				logger.debug("TaskId:{}, 结束获取组织内当天【{}】任务信息。", taskId, getIndicatorName());
			} catch (Exception e) {
				logger.warn("TaskId：{}，获取组织内当天【{}】任务信息出错", taskId, getIndicatorName(), e);
				throw e;
			}
			if (CollectionUtils.isEmpty(entities)) {
				return;
			}
			try {
				logger.debug("TaskId:{}, 开始生成组织内当天【{}】任务统计信息。", taskId, getIndicatorName());
				generateStatistics(entities);
				logger.debug("TaskId:{}, 生成组织内当天【{}】任务统计信息结束。", taskId, getIndicatorName());
			} catch (Exception e) {
				logger.warn("TaskId：{}，生成组织内当天【{}】任务统计信息出错", taskId, getIndicatorName(), e);
				throw e;
			}
		}

		@SuppressWarnings({ "unchecked", "serial" })
		private void generateStatistics(Collection<Map<String, Object>> entities) throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(STATISTICS_API_ADDR);
			for (Map<String, Object> entity : entities) {
				// @formatter:off
				 Map<String, Object> data = new LinkedHashMap<String, Object>() {{
					 put("tenant", entity.get("_id"));
					 put(getIndicatorName(), entity.get(getIndicatorName()));
					 put("type", getIndicatorType());
					 // 统计时间
					 put("statisticsOn", getStatisticsEndOn());
				 }};
				// @formatter:on
				Map<String, Object> response = postForObject(builder.toString(), data, Map.class);
				logger.info("消息结果:{}", response);
			}
		}

		@SuppressWarnings("unchecked")
		private Collection<Map<String, Object>> getTasks() throws Exception {
			StringBuilder builder = new StringBuilder(getFwkBaseUrl());
			builder.append(TASK_AGGREGATE_API_ADDR);
			ArrayList<Map<String, Object>> aggregate = buildQuery();
			Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
			return Arrays.asList(response);
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
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$addFields", new LinkedHashMap<String, Object>() {{
						put("flag", new LinkedHashMap<String, Object>() {{
							put("$cond", new ArrayList<Object>() {{
								add(new LinkedHashMap<String, Object>() {{
									put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
										add(new LinkedHashMap<String, Object>() {{
											put("$gte", new ArrayList<Object>() {{
												add(String.format("$%s", getTimeFiled()));
												add(getStatisticsStartOn());
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$lte", new ArrayList<Object>() {{
												add(String.format("$%s", getTimeFiled()));
												add(getStatisticsEndOn());
											}});
										}});
										add(new LinkedHashMap<String, Object>() {{
											put("$or", new ArrayList<LinkedHashMap<String, Object>>() {{
												add(new LinkedHashMap<String, Object>() {{
													put("$eq", new ArrayList<Object>() {{
														add("$finish");
														add(false);
													}});
												}});
												add(new LinkedHashMap<String, Object>() {{
													put("$and", new ArrayList<LinkedHashMap<String, Object>>() {{
														add(new LinkedHashMap<String, Object>() {{
															put("$eq", new ArrayList<Object>() {{
																add("$finish");
																add(true);
															}});
															put("$eq", new ArrayList<Object>() {{
																add("$stage");
																add("completion");
															}});
														}});
													}});
												}});
											}});
										}});
									}});
								}});
								add(1);
								add(0);
							}});
						}});
					}});
				}});
				add(new LinkedHashMap<String, Object>() {{
					put("$group", new LinkedHashMap<String, Object>() {{
						put("_id", "$tenant");
						put(getIndicatorName(), new LinkedHashMap<String, Object>() {{
							put("$sum", "$flag");
						}});
					}});
				}});
			}};
			// @formatter:on
			return aggregate;
		}
		
	}
	
	private class TenantTaskAddIndicator extends SingleIndicatorWorker{

		@Override
		public String getTimeFiled() {
			return "createdOn";
		}

		@Override
		public String getIndicatorName() {
			return "add";
		}

		@Override
		public long getStatisticsStartOn() {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTimeInMillis();
		}

		@Override
		public long getStatisticsEndOn() {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTimeInMillis();
		}

		@Override
		public String getIndicatorType() {
			return "task.add.tenant";
		}
		
	}
	
	private class TenantTaskCompletedIndicator extends SingleIndicatorWorker{

		@Override
		public String getTimeFiled() {
			return "finishOn";
		}

		@Override
		public String getIndicatorName() {
			return "completed";
		}

		@Override
		public long getStatisticsStartOn() {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTimeInMillis();
		}

		@Override
		public long getStatisticsEndOn() {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTimeInMillis();
		}

		@Override
		public String getIndicatorType() {
			return "task.completed.tenant";
		}
		
	}
}
