package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.TaskparcelEXTMapper;
import com.fwk.service.farm.entity.TaskparcelEXT;

/**
 * 农事管理模块 — 农事任务的地块的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskparcelService {

	// 农事管理模块 — 农事任务的地块的数据库处理类
	@Autowired
	private TaskparcelEXTMapper mTaskparcelEXTMapper;

	/**
	 * 新增农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskparcelEXT pTaskparcelEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskparcelEXTMapper.add(pTaskparcelEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的地块对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskparcelEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskparcelEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskparcelEXT pTaskparcelEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskparcelEXTMapper.update(pTaskparcelEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskparcelEXT pTaskparcelEXT, final Map<String, Object> paramMap) throws Exception {

		final TaskparcelEXT originalTaskparcelEXT = this.queryById(pTaskparcelEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskparcelEXT, originalTaskparcelEXT, paramMap);

		return this.update(originalTaskparcelEXT);

	}

	/**
	 * 删除指定ID的农事任务的地块
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskparcelEXT pTaskparcelEXT) throws Exception {
		return mTaskparcelEXTMapper.deletebyid(pTaskparcelEXT);
	}

	/**
	 * 根据主键批量删除农事任务的地块
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
		return mTaskparcelEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	public TaskparcelEXT queryById(TaskparcelEXT pTaskparcelEXT) throws Exception {
		TaskparcelEXT paramTaskparcelEXT = new TaskparcelEXT();
		paramTaskparcelEXT.setId(pTaskparcelEXT.getId());

		List<TaskparcelEXT> pTaskparcelEXTList = this.queryList(paramTaskparcelEXT);

		return ((null != pTaskparcelEXTList) && (pTaskparcelEXTList.size() > 0) ? pTaskparcelEXTList.get(0) : null);
	}

	/**
	 * 检索农事任务的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskparcelEXT> queryList(TaskparcelEXT pTaskparcelEXT) throws Exception {
		return mTaskparcelEXTMapper.queryList(pTaskparcelEXT);
	}

	/**
	 * 检索农事任务的地块的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskparcelEXT
	 *            农事任务的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskparcelEXT pTaskparcelEXT) throws Exception {
		return mTaskparcelEXTMapper.queryListTotalCount(pTaskparcelEXT);
	}

}
