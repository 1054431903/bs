package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecordpartuserEXTMapper;
import com.fwk.service.farm.entity.RecordpartuserEXT;

/**
 * 农事管理模块 — 农事记录的参与人的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordpartuserService {

	// 农事管理模块 — 农事记录的参与人的数据库处理类
	@Autowired
	private RecordpartuserEXTMapper mRecordpartuserEXTMapper;

	/**
	 * 新增农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordpartuserEXT pRecordpartuserEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordpartuserEXTMapper.add(pRecordpartuserEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的参与人对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<RecordpartuserEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordpartuserEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordpartuserEXT pRecordpartuserEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordpartuserEXTMapper.update(pRecordpartuserEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordpartuserEXT pRecordpartuserEXT, final Map<String, Object> paramMap) throws Exception {

		final RecordpartuserEXT originalRecordpartuserEXT = this.queryById(pRecordpartuserEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordpartuserEXT, originalRecordpartuserEXT, paramMap);

		return this.update(originalRecordpartuserEXT);

	}

	/**
	 * 删除指定ID的农事记录的参与人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordpartuserEXT pRecordpartuserEXT) throws Exception {
		return mRecordpartuserEXTMapper.deletebyid(pRecordpartuserEXT);
	}

	/**
	 * 根据主键批量删除农事记录的参与人
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
		return mRecordpartuserEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public RecordpartuserEXT queryById(RecordpartuserEXT pRecordpartuserEXT) throws Exception {
		RecordpartuserEXT paramRecordpartuserEXT = new RecordpartuserEXT();
		paramRecordpartuserEXT.setId(pRecordpartuserEXT.getId());

		List<RecordpartuserEXT> pRecordpartuserEXTList = this.queryList(paramRecordpartuserEXT);

		return ((null != pRecordpartuserEXTList) && (pRecordpartuserEXTList.size() > 0) ? pRecordpartuserEXTList.get(0)
				: null);
	}

	/**
	 * 检索农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordpartuserEXT> queryList(RecordpartuserEXT pRecordpartuserEXT) throws Exception {
		return mRecordpartuserEXTMapper.queryList(pRecordpartuserEXT);
	}

	/**
	 * 检索农事记录的参与人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordpartuserEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordpartuserEXT pRecordpartuserEXT) throws Exception {
		return mRecordpartuserEXTMapper.queryListTotalCount(pRecordpartuserEXT);
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
	 *            农事记录的参与人列表
	 * @return
	 */
	public void asynchronizationUpdate(List<RecordpartuserEXT> updateRecordpartuserList) {

		// 使用线程执行需要更新的农事记录
		RecordpartuserThread taskparticsThread = (this.new RecordpartuserThread(this, updateRecordpartuserList));

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
	private class RecordpartuserThread implements Runnable {

		public RecordpartuserThread(RecordpartuserService taskparticsService,
				List<RecordpartuserEXT> updateRecordpartuserList) {
			super();
			this.taskparticsService = taskparticsService;
			this.updateRecordpartuserList = updateRecordpartuserList;
		}

		private RecordpartuserService taskparticsService;

		// 需要更新状态的农事记录的参与人
		List<RecordpartuserEXT> updateRecordpartuserList;

		@Override
		public void run() {

			if ((null != taskparticsService) && (null != updateRecordpartuserList)
					|| (updateRecordpartuserList.size() > 0)) {
				for (RecordpartuserEXT taskparticsEXT : updateRecordpartuserList) {
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
