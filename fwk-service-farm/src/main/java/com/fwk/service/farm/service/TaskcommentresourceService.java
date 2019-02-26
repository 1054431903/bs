package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.TaskcommentresourceEXTMapper;
import com.fwk.service.farm.entity.TaskcommentresourceEXT;

/**
 * 农事管理模块 — 农事任务评论的资源的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskcommentresourceService {

	// 农事管理模块 — 农事任务评论的资源的数据库处理类
	@Autowired
	private TaskcommentresourceEXTMapper mTaskcommentresourceEXTMapper;

	/**
	 * 新增农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcommentresourceEXTMapper.add(pTaskcommentresourceEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务评论的资源对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskcommentresourceEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcommentresourceEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskcommentresourceEXTMapper.update(pTaskcommentresourceEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskcommentresourceEXT pTaskcommentresourceEXT, final Map<String, Object> paramMap)
			throws Exception {

		final TaskcommentresourceEXT originalTaskcommentresourceEXT = this.queryById(pTaskcommentresourceEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskcommentresourceEXT, originalTaskcommentresourceEXT, paramMap);

		return this.update(originalTaskcommentresourceEXT);

	}

	/**
	 * 删除指定ID的农事任务评论的资源
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {
		return mTaskcommentresourceEXTMapper.deletebyid(pTaskcommentresourceEXT);
	}

	/**
	 * 根据主键批量删除农事任务评论的资源
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
		return mTaskcommentresourceEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	public TaskcommentresourceEXT queryById(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {
		TaskcommentresourceEXT paramTaskcommentresourceEXT = new TaskcommentresourceEXT();
		paramTaskcommentresourceEXT.setId(pTaskcommentresourceEXT.getId());

		List<TaskcommentresourceEXT> pTaskcommentresourceEXTList = this.queryList(paramTaskcommentresourceEXT);

		return ((null != pTaskcommentresourceEXTList) && (pTaskcommentresourceEXTList.size() > 0)
				? pTaskcommentresourceEXTList.get(0) : null);
	}

	/**
	 * 检索农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskcommentresourceEXT> queryList(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {
		return mTaskcommentresourceEXTMapper.queryList(pTaskcommentresourceEXT);
	}

	/**
	 * 检索农事任务评论的资源的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception {
		return mTaskcommentresourceEXTMapper.queryListTotalCount(pTaskcommentresourceEXT);
	}

}
