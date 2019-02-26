package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.PlanparticsEXTMapper;
import com.fwk.service.farm.entity.PlanparticsEXT;

/**
 * 农事管理模块 — 农事计划的参与人的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PlanparticsService {

	// 农事管理模块 — 农事计划的参与人的数据库处理类
	@Autowired
	private PlanparticsEXTMapper mPlanparticsEXTMapper;

	/**
	 * 新增农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PlanparticsEXT pPlanparticsEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mPlanparticsEXTMapper.add(pPlanparticsEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事计划的参与人对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<PlanparticsEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mPlanparticsEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PlanparticsEXT pPlanparticsEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPlanparticsEXTMapper.update(pPlanparticsEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(PlanparticsEXT pPlanparticsEXT, final Map<String, Object> paramMap) throws Exception {

		final PlanparticsEXT originalPlanparticsEXT = this.queryById(pPlanparticsEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pPlanparticsEXT, originalPlanparticsEXT, paramMap);

		return this.update(originalPlanparticsEXT);

	}

	/**
	 * 删除指定ID的农事计划的参与人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PlanparticsEXT pPlanparticsEXT) throws Exception {
		return mPlanparticsEXTMapper.deletebyid(pPlanparticsEXT);
	}

	/**
	 * 根据主键批量删除农事计划的参与人
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
		return mPlanparticsEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	public PlanparticsEXT queryById(PlanparticsEXT pPlanparticsEXT) throws Exception {
		PlanparticsEXT paramPlanparticsEXT = new PlanparticsEXT();
		paramPlanparticsEXT.setId(pPlanparticsEXT.getId());

		List<PlanparticsEXT> pPlanparticsEXTList = this.queryList(paramPlanparticsEXT);

		return ((null != pPlanparticsEXTList) && (pPlanparticsEXTList.size() > 0) ? pPlanparticsEXTList.get(0) : null);
	}

	/**
	 * 检索农事计划的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	public List<PlanparticsEXT> queryList(PlanparticsEXT pPlanparticsEXT) throws Exception {
		return mPlanparticsEXTMapper.queryList(pPlanparticsEXT);
	}

	/**
	 * 检索农事计划的参与人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPlanparticsEXT
	 *            农事计划的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PlanparticsEXT pPlanparticsEXT) throws Exception {
		return mPlanparticsEXTMapper.queryListTotalCount(pPlanparticsEXT);
	}

	//
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ------------ 分隔符 -----------
	// 以下部分为异步执行的方法
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 异步更新农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 上午9:41:36
	 *
	 * @param updatePlanList
	 *            农事计划的参与人列表
	 * @return
	 */
	public void asynchronizationUpdate(List<PlanparticsEXT> updatePlanparticsList) {

		// 使用线程执行需要更新的农事计划
		PlanparticsThread taskparticsThread = (this.new PlanparticsThread(this, updatePlanparticsList));

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
	private class PlanparticsThread implements Runnable {

		public PlanparticsThread(PlanparticsService taskparticsService, List<PlanparticsEXT> updatePlanparticsList) {
			super();
			this.taskparticsService = taskparticsService;
			this.updatePlanparticsList = updatePlanparticsList;
		}

		private PlanparticsService taskparticsService;

		// 需要更新状态的农事计划的参与人
		List<PlanparticsEXT> updatePlanparticsList;

		@Override
		public void run() {

			if ((null != taskparticsService) && (null != updatePlanparticsList) || (updatePlanparticsList.size() > 0)) {
				for (PlanparticsEXT taskparticsEXT : updatePlanparticsList) {
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
