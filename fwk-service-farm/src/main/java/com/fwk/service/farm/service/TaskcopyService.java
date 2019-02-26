package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.TaskcopyEXTMapper;
import com.fwk.service.farm.entity.TaskcopyEXT;

/**
 * 农事管理模块 — 农事任务的抄送人的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskcopyService {

	// 农事管理模块 — 农事任务的抄送人的数据库处理类
	@Autowired
	private TaskcopyEXTMapper mTaskcopyEXTMapper;

	/**
	 * 新增农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskcopyEXT pTaskcopyEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcopyEXTMapper.add(pTaskcopyEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Taskcopy failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的抄送人对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskcopyEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcopyEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Taskcopy failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskcopyEXT pTaskcopyEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskcopyEXTMapper.update(pTaskcopyEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskcopyEXT pTaskcopyEXT, final Map<String, Object> paramMap) throws Exception {

		final TaskcopyEXT originalTaskcopyEXT = this.queryById(pTaskcopyEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskcopyEXT, originalTaskcopyEXT, paramMap);

		return this.update(originalTaskcopyEXT);

	}

	/**
	 * 删除指定ID的农事任务的抄送人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskcopyEXT pTaskcopyEXT) throws Exception {
		return mTaskcopyEXTMapper.deletebyid(pTaskcopyEXT);
	}

	/**
	 * 根据主键批量删除农事任务的抄送人
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
		return mTaskcopyEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public TaskcopyEXT queryById(TaskcopyEXT pTaskcopyEXT) throws Exception {
		TaskcopyEXT paramTaskcopyEXT = new TaskcopyEXT();
		paramTaskcopyEXT.setId(pTaskcopyEXT.getId());

		List<TaskcopyEXT> pTaskcopyEXTList = this.queryList(paramTaskcopyEXT);

		return ((null != pTaskcopyEXTList) && (pTaskcopyEXTList.size() > 0) ? pTaskcopyEXTList.get(0) : null);
	}

	/**
	 * 检索农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskcopyEXT> queryList(TaskcopyEXT pTaskcopyEXT) throws Exception {
		return mTaskcopyEXTMapper.queryList(pTaskcopyEXT);
	}

	/**
	 * 检索农事任务的抄送人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskcopyEXT pTaskcopyEXT) throws Exception {
		return mTaskcopyEXTMapper.queryListTotalCount(pTaskcopyEXT);
	}

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deleteAllFromTrash(TaskcopyEXT pTaskcopyEXT) throws Exception {
		return mTaskcopyEXTMapper.deleteAllFromTrash(pTaskcopyEXT);
	}

	//
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ------------ 分隔符 -----------
	// 以下部分为异步执行的方法
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 异步更新农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 上午9:41:36
	 *
	 * @param updatePlanList
	 *            农事记录的抄送人列表
	 * @return
	 */
	public void asynchronizationUpdate(List<TaskcopyEXT> updateTaskcopyList) {

		// 使用线程执行需要更新的农事记录
		TaskcopyThread taskcopyThread = (this.new TaskcopyThread(this, updateTaskcopyList));

		Thread thread = new Thread(taskcopyThread);
		thread.start();

	}

	/**
	 * 农事抄送人的线程执行类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 下午12:34:56
	 *
	 */
	private class TaskcopyThread implements Runnable {

		public TaskcopyThread(TaskcopyService taskcopyService, List<TaskcopyEXT> updateTaskcopyList) {
			super();
			this.taskcopyService = taskcopyService;
			this.updateTaskcopyList = updateTaskcopyList;
		}

		private TaskcopyService taskcopyService;

		// 需要更新状态的农事记录的抄送人
		List<TaskcopyEXT> updateTaskcopyList;

		@Override
		public void run() {

			if ((null != taskcopyService) && (null != updateTaskcopyList) || (updateTaskcopyList.size() > 0)) {
				for (TaskcopyEXT taskcopyEXT : updateTaskcopyList) {
					try {
						taskcopyService.update(taskcopyEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
