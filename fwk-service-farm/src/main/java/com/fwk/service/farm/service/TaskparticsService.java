package com.fwk.service.farm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.farm.dao.TaskparticsEXTMapper;
import com.fwk.service.farm.entity.TaskEXT;
import com.fwk.service.farm.entity.TaskcommentEXT;
import com.fwk.service.farm.entity.TaskparticsEXT;

/**
 * 农事管理模块 — 农事任务的参与人的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskparticsService {

	// 农事管理模块 — 农事任务的参与人的数据库处理类
	@Autowired
	private TaskparticsEXTMapper mTaskparticsEXTMapper;

	// 农事任务的评论的业务逻辑处理对象
	@Autowired
	private TaskcommentService mTaskcommentService;

	// 农事管理模块 — 农事任务的业务逻辑处理对象
	@Autowired
	private TaskService mTaskService;

	/**
	 * 新增农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskparticsEXT pTaskparticsEXT) throws Exception {

		// 重置农事记录的ID
		this.resetRecordId(pTaskparticsEXT);

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskparticsEXTMapper.add(pTaskparticsEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的参与人对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskparticsEXT> list) throws Exception {

		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		for (TaskparticsEXT taskparticsEXT : list) {
			// 重置农事记录的ID
			this.resetRecordId(taskparticsEXT);
		}

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskparticsEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskparticsEXT pTaskparticsEXT) throws Exception {

		// 重置农事记录的ID
		this.resetRecordId(pTaskparticsEXT);

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskparticsEXTMapper.update(pTaskparticsEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Taskpartics failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskparticsEXT pTaskparticsEXT, final Map<String, Object> paramMap) throws Exception {

		final TaskparticsEXT originalTaskparticsEXT = this.queryById(pTaskparticsEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskparticsEXT, originalTaskparticsEXT, paramMap);

		return this.update(originalTaskparticsEXT);

	}

	/**
	 * 删除指定ID的农事任务的参与人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskparticsEXT pTaskparticsEXT) throws Exception {
		return mTaskparticsEXTMapper.deletebyid(pTaskparticsEXT);
	}

	/**
	 * 根据主键批量删除农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 下午3:34:49
	 *
	 * @param idList
	 *            业务主键列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchdeletebyids(List<Integer> idList) throws Exception {
		return mTaskparticsEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public TaskparticsEXT queryById(TaskparticsEXT pTaskparticsEXT) throws Exception {
		TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
		paramTaskparticsEXT.setId(pTaskparticsEXT.getId());

		List<TaskparticsEXT> pTaskparticsEXTList = this.queryList(paramTaskparticsEXT);

		return ((null != pTaskparticsEXTList) && (pTaskparticsEXTList.size() > 0) ? pTaskparticsEXTList.get(0) : null);
	}

	/**
	 * 检索农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskparticsEXT> queryList(TaskparticsEXT pTaskparticsEXT) throws Exception {
		return mTaskparticsEXTMapper.queryList(pTaskparticsEXT);
	}

	/**
	 * 检索农事任务的参与人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskparticsEXT pTaskparticsEXT) throws Exception {
		return mTaskparticsEXTMapper.queryListTotalCount(pTaskparticsEXT);
	}

	/**
	 * 确认农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 下午5:21:04
	 *
	 * @param pTaskparticsEXT
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer confirmTask(TaskparticsEXT pTaskparticsEXT) throws Exception {

		Integer resultAddOrUpdate = null;

		resultAddOrUpdate = this.update(pTaskparticsEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("update Taskpartics failure !");
		}

		// 获取农事参与人的参与状态
		EDict taskparticsstatus = EDict.getEDictByType(EDictType.task_partics_status,
				pTaskparticsEXT.getTaskparticsstatus());

		// ----- 添加农事任务的评论 -----------------------------------------
		TaskcommentEXT tempTaskcommentEXT = new TaskcommentEXT();
		tempTaskcommentEXT.setTaskid(pTaskparticsEXT.getTaskid());
		tempTaskcommentEXT.setUserid(pTaskparticsEXT.getUserid());

		if (EDict.task_partics_status_partake.equals(taskparticsstatus)) {
			// 确认参与任务
			tempTaskcommentEXT.setTitle(LanguageUtil.getLanguageByKey("fwk-farm-task-confirm-partake"));
		} else if (EDict.task_partics_status_refuse.equals(taskparticsstatus)) {
			// 拒绝参与任务
			tempTaskcommentEXT.setTitle(LanguageUtil.getLanguageByKey("fwk-farm-task-refuse-partake"));
		}

		resultAddOrUpdate = mTaskcommentService.add(tempTaskcommentEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Taskcomment failure !");
		}

		// ---------- 变更农事任务状态的业务处理 -----------------------
		TaskEXT paramTaskEXT = new TaskEXT();
		paramTaskEXT.setId(pTaskparticsEXT.getTaskid());

		TaskEXT taskEXT = mTaskService.queryById(paramTaskEXT);

		if (null == taskEXT) {

			ResultWrap resultWrap = new ResultWrap();

			resultWrap.setParamInvalid("taskid");

			// 农事任务不存在（taskid 无效）
			throw new Exception(resultWrap.getMessage());

		} else {

			// 农事任务参与人个人的农事任务状态
			EDict taskparticsTaskStatus = null;

			// 所有参与人的农事任务状态
			final Set<EDict> taskparticsTaskStatusSet = new HashSet<EDict>();

			for (TaskparticsEXT taskparticsEXT : taskEXT.getTaskparticslist()) {
				taskparticsTaskStatus = EDict.getEDictByType(EDictType.task_status, taskparticsEXT.getTaskstatus());

				taskparticsTaskStatusSet.add(taskparticsTaskStatus);
			}

			// 只要所有人都拒绝，那么该农事任务的状态应该为：已拒绝
			if ((1 == taskparticsTaskStatusSet.size())
					&& (taskparticsTaskStatusSet.contains(EDict.task_status_refuse))) {

				taskEXT.setTaskstatus(EDict.task_status_refuse.getDict().getDictValue());

				resultAddOrUpdate = mTaskService.update(taskEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Task.taskstatus failure !");
				}
			}

		}

		return resultAddOrUpdate;
	}

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deleteAllFromTrash(TaskparticsEXT pTaskparticsEXT) throws Exception {
		return mTaskparticsEXTMapper.deleteAllFromTrash(pTaskparticsEXT);
	}

	/**
	 * 保存到农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年3月6日 下午3:18:09
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer saveToRecord(TaskparticsEXT pTaskparticsEXT) throws Exception {
		return mTaskparticsEXTMapper.saveToRecord(pTaskparticsEXT);
	}

	/**
	 * 重置农事记录的ID
	 * 
	 * @author wangjunwen
	 * @date 2018年4月20日 下午3:01:40
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @throws Exception
	 */
	public void resetRecordId(TaskparticsEXT pTaskparticsEXT) throws Exception {
		if (null == pTaskparticsEXT) {
			return;
		}

		if (ObjectUtil.isBlank(pTaskparticsEXT.getRecordeid())) {
			pTaskparticsEXT.setRecordeid(null);
		} else {
			@SuppressWarnings("unchecked")
			List<Integer> recordIdList = ObjectUtil
					.convertToNumberList(ObjectUtil.toString(pTaskparticsEXT.getRecordeid()).trim(), Integer.class);

			if ((null != recordIdList) && (recordIdList.size() > 0)) {
				StringBuffer recordIdBuffer = new StringBuffer();
				for (int i = 0; i < recordIdList.size(); i++) {
					if (i > 0) {
						recordIdBuffer.append(",");
					}

					recordIdBuffer.append(recordIdList.get(i));
				}

				pTaskparticsEXT.setRecordeid(recordIdBuffer.toString());
			}
		}
	}

	//
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ------------ 分隔符 -----------
	// 以下部分为异步执行的方法
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 异步更新农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 上午9:41:36
	 *
	 * @param updateTaskList
	 *            农事任务的参与人列表
	 * @return
	 */
	public void asynchronizationUpdate(List<TaskparticsEXT> updateTaskparticsList) {

		// 使用线程执行需要更新的农事任务
		TaskparticsThread taskparticsThread = (this.new TaskparticsThread(this, updateTaskparticsList));

		Thread thread = new Thread(taskparticsThread);
		thread.start();

	}

	/**
	 * 农事参与人的线程执行类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 下午12:34:56
	 *
	 */
	private class TaskparticsThread implements Runnable {

		public TaskparticsThread(TaskparticsService taskparticsService, List<TaskparticsEXT> updateTaskparticsList) {
			super();
			this.taskparticsService = taskparticsService;
			this.updateTaskparticsList = updateTaskparticsList;
		}

		private TaskparticsService taskparticsService;

		// 需要更新状态的农事任务的参与人
		List<TaskparticsEXT> updateTaskparticsList;

		@Override
		public void run() {

			if ((null != taskparticsService) && (null != updateTaskparticsList) || (updateTaskparticsList.size() > 0)) {
				for (TaskparticsEXT taskparticsEXT : updateTaskparticsList) {
					try {
						taskparticsService.update(taskparticsEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
