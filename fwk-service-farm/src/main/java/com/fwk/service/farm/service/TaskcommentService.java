package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.TaskcommentEXTMapper;
import com.fwk.service.farm.entity.TaskcommentEXT;

/**
 * 农事管理模块 — 农事任务的评论的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskcommentService {

	// 农事管理模块 — 农事任务的评论的数据库处理类
	@Autowired
	private TaskcommentEXTMapper mTaskcommentEXTMapper;

	// 公共业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskcommentEXT pTaskcommentEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcommentEXTMapper.add(pTaskcommentEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的评论对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskcommentEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskcommentEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskcommentEXT pTaskcommentEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskcommentEXTMapper.update(pTaskcommentEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskcommentEXT pTaskcommentEXT, final Map<String, Object> paramMap) throws Exception {

		final TaskcommentEXT originalTaskcommentEXT = this.queryById(pTaskcommentEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskcommentEXT, originalTaskcommentEXT, paramMap);

		return this.update(originalTaskcommentEXT);

	}

	/**
	 * 删除指定ID的农事任务的评论
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskcommentEXT pTaskcommentEXT) throws Exception {
		return mTaskcommentEXTMapper.deletebyid(pTaskcommentEXT);
	}

	/**
	 * 根据主键批量删除农事任务的评论
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
		return mTaskcommentEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	public TaskcommentEXT queryById(TaskcommentEXT pTaskcommentEXT) throws Exception {
		TaskcommentEXT paramTaskcommentEXT = new TaskcommentEXT();
		paramTaskcommentEXT.setId(pTaskcommentEXT.getId());

		List<TaskcommentEXT> pTaskcommentEXTList = this.queryList(paramTaskcommentEXT);

		return ((null != pTaskcommentEXTList) && (pTaskcommentEXTList.size() > 0) ? pTaskcommentEXTList.get(0) : null);
	}

	/**
	 * 检索农事任务的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskcommentEXT> queryList(TaskcommentEXT pTaskcommentEXT) throws Exception {
		List<TaskcommentEXT> list = mTaskcommentEXTMapper.queryList(pTaskcommentEXT);

		if ((null == list) || (list.isEmpty())) {
			return list;
		}

		List<Integer> userIdList = new ArrayList<Integer>();

		for (TaskcommentEXT taskcommentEXT : list) {
			// 创建人的ID
			userIdList.add(taskcommentEXT.getUserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (TaskcommentEXT taskcommentEXT : list) {
			this.setCreateUserInfo(taskcommentEXT, supportUserEXTMap);
		}

		return list;
	}

	/**
	 * 设置农事任务动态的发布人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param taskcommentEXT
	 *            农事任务动态对象
	 * @throws Exception
	 */
	public void setCreateUserInfo(final TaskcommentEXT taskcommentEXT,
			final Map<String, SupportUserEXT> supportUserEXTMap) throws Exception {

		if ((null == taskcommentEXT) || (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		SupportUserEXT supportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(taskcommentEXT.getUserid()));

		if (null != supportUserEXT) {
			taskcommentEXT.setUseridname(supportUserEXT.getName());
			taskcommentEXT.setUseridnickname(supportUserEXT.getNickname());
			taskcommentEXT.setHeadportraiturl(supportUserEXT.getPhotourl());
		}

	}

	/**
	 * 检索农事任务的评论的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskcommentEXT
	 *            农事任务的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskcommentEXT pTaskcommentEXT) throws Exception {
		return mTaskcommentEXTMapper.queryListTotalCount(pTaskcommentEXT);
	}

}
