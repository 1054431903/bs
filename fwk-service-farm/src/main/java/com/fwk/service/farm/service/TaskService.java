package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.el.ELException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.TaskEXTMapper;
import com.fwk.service.farm.entity.TaskEXT;
import com.fwk.service.farm.entity.TaskcommentEXT;
import com.fwk.service.farm.entity.TaskcopyEXT;
import com.fwk.service.farm.entity.TaskparcelEXT;
import com.fwk.service.farm.entity.TaskparticsEXT;
import com.fwk.service.farm.entity.TaskuserpermisionsEXT;
import com.fwk.service.farm.entity.TaskwarnEXT;

/**
 * 农事管理模块 — 农事任务的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class TaskService {

	// 农事管理模块 — 农事任务的数据库处理类
	@Autowired
	private TaskEXTMapper mTaskEXTMapper;

	// 农事管理模块 — 农事任务的参与人的业务逻辑处理对象
	@Autowired
	private TaskparticsService mTaskparticsService;

	// 农事管理模块 — 农事任务的抄送人的业务逻辑处理对象
	@Autowired
	private TaskcopyService mTaskcopyService;

	// 农事管理模块 — 农事任务的农事任务提醒的业务逻辑处理对象
	@Autowired
	private TaskwarnService mTaskwarnService;

	// 农事管理模块 — 农事任务的地块的业务逻辑处理对象
	@Autowired
	private TaskparcelService mTaskparcelService;

	// 农事任务的评论的业务逻辑处理对象
	@Autowired
	private TaskcommentService mTaskcommentService;

	// 农事任务的数据权限的业务逻辑处理对象
	@Autowired
	private TaskuserpermisionsService mTaskuserpermisionsService;

	// 公共业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农事管理模块的公共业务逻辑处理对象
	@Autowired
	private FarmCommonService mFarmCommonService;

	/**
	 * 新增农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @param loginUserInfo
	 *            当前操作的用户对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(TaskEXT pTaskEXT, final LoginUserInfo loginUserInfo) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		TaskEXT savedTaskEXT = null;// 已保存的地块
		final List<TaskEXT> savedTaskEXTList = new ArrayList<TaskEXT>();// 已保存到数据库的农事任务列表

		// 已保存到数据库的农事任务数量
		final AtomicInteger saveCounter = new AtomicInteger(0);

		// 需要保存的记录数量
		final int needSaveCount = 1;

		// 有几个地块就添加几条农事任务，如果没有地块那就只添加一条农事任务
		do {
			savedTaskEXT = new TaskEXT();

			BeanUtils.copyProperties(pTaskEXT, savedTaskEXT);

			// 将数据添加到数据库
			resultAddOrUpdate = mTaskEXTMapper.add(savedTaskEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Task failure !");
			} else {
				// 添加到已保存到数据库的农事任务列表中
				savedTaskEXTList.add(savedTaskEXT);
				pTaskEXT.setId(savedTaskEXT.getId());
			}

		} while ((saveCounter.incrementAndGet() < needSaveCount));// do while 结束

		// 农事任务的评论
		TaskcommentEXT tempTaskcommentEXT = null;

		// 农事记录的数据权限对象
		TaskuserpermisionsEXT tempTaskuserpermisionsEXT = null;

		// ============== 保存与农事任务是多对一的关系的其他记录 =======================
		for (TaskEXT taskEXT : savedTaskEXTList) {

			// -------- 农事任务的地块列表（农事任务和地块是一对一的关系，一个农事最多对应一个地块）-----------
			if ((null != pTaskEXT.getTaskparcellist()) && (pTaskEXT.getTaskparcellist().size() > 0)) {

				for (TaskparcelEXT taskparcelEXT : pTaskEXT.getTaskparcellist()) {
					taskparcelEXT.setTaskid(taskEXT.getId());// 设置任务的ID
				}

				resultAddOrUpdate = mTaskparcelService.batchAdd(pTaskEXT.getTaskparcellist());

				if ((null == resultAddOrUpdate)
						|| (pTaskEXT.getTaskparcellist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Taskparcel failure !");
				}
			} // 添加农事任务的地块结束

			// ----- 农事任务的参与人列表（农事任务与农事参与人是一对多的关系，一个农事可以有多个参与人）------------
			if ((null != pTaskEXT.getTaskparticslist()) && (pTaskEXT.getTaskparticslist().size() > 0)) {

				// 在把农事任务的参与人添加到数据库之前的相关属性设置
				this.setBeforeAddTaskparticslist(taskEXT);

				resultAddOrUpdate = mTaskparticsService.batchAdd(pTaskEXT.getTaskparticslist());

				if ((null == resultAddOrUpdate)
						|| (pTaskEXT.getTaskparticslist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Taskpartics failure !");
				}
			} // 添加农事任务的参与人结束

			// ----- 农事任务的抄送人列表（农事任务与农事抄送人是一对多的关系，一个农事可以有多个抄送人）------------
			if ((null != pTaskEXT.getTaskcopylist()) && (pTaskEXT.getTaskcopylist().size() > 0)) {

				for (TaskcopyEXT taskcopyEXT : pTaskEXT.getTaskcopylist()) {
					taskcopyEXT.setTaskid(taskEXT.getId());// 设置农事任务的ID
				}

				resultAddOrUpdate = mTaskcopyService.batchAdd(pTaskEXT.getTaskcopylist());

				if ((null == resultAddOrUpdate)
						|| (pTaskEXT.getTaskcopylist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Taskcopy failure !");
				}
			} // 添加农事任务的抄送人结束

			// ----- 农事任务的任务提醒列表（农事任务与农事任务提醒是一对多的关系，一个农事可以有多个任务提醒）------------
			if ((null != pTaskEXT.getTaskwarnlist()) && (pTaskEXT.getTaskwarnlist().size() > 0)) {

				for (TaskwarnEXT taskwarnEXT : pTaskEXT.getTaskwarnlist()) {

					taskwarnEXT.setTaskid(taskEXT.getId());// 设置农事任务的ID

				}

				resultAddOrUpdate = mTaskwarnService.batchAdd(pTaskEXT.getTaskwarnlist());

				if ((null == resultAddOrUpdate)
						|| (pTaskEXT.getTaskwarnlist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Taskwarn failure !");
				}
			} // 添加农事任务的任务提醒结束

			// ----- 添加农事任务的评论 -----------------------------------------
			tempTaskcommentEXT = new TaskcommentEXT();
			tempTaskcommentEXT.setTaskid(taskEXT.getId());
			tempTaskcommentEXT.setUserid(taskEXT.getCreateuserid());
			tempTaskcommentEXT.setTitle(LanguageUtil.getLanguageByKey("fwk-farm-task-release-task"));// 发布任务

			resultAddOrUpdate = mTaskcommentService.add(tempTaskcommentEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Taskcomment failure !");
			}

			// ----- 添加农事任务的数据权限 -----------------------------------------
			if ((null == loginUserInfo.getDepartmentsupportidlist())
					|| (loginUserInfo.getDepartmentsupportidlist().isEmpty())) {

				tempTaskuserpermisionsEXT = new TaskuserpermisionsEXT();
				tempTaskuserpermisionsEXT.setTaskid(taskEXT.getId());
				tempTaskuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
				tempTaskuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
				tempTaskuserpermisionsEXT.setCreateuserdepartmentid(null);

				resultAddOrUpdate = mTaskuserpermisionsService.add(tempTaskuserpermisionsEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("add Taskuserpermisions failure !");
				}

			} else {

				List<TaskuserpermisionsEXT> taskuserpermisionsEXTList = new ArrayList<TaskuserpermisionsEXT>();

				for (String departmentsupportid : loginUserInfo.getDepartmentsupportidlist()) {

					tempTaskuserpermisionsEXT = new TaskuserpermisionsEXT();
					tempTaskuserpermisionsEXT.setTaskid(taskEXT.getId());
					tempTaskuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
					tempTaskuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
					tempTaskuserpermisionsEXT.setCreateuserdepartmentid(departmentsupportid);

					taskuserpermisionsEXTList.add(tempTaskuserpermisionsEXT);
				}

				resultAddOrUpdate = mTaskuserpermisionsService.batchAdd(taskuserpermisionsEXTList);

				if ((null == resultAddOrUpdate) || (taskuserpermisionsEXTList.size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Taskuserpermisions failure !");
				}
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<TaskEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mTaskEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Taskpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(TaskEXT pTaskEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mTaskEXTMapper.update(pTaskEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Task failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @param originalTaskEXT
	 *            原始的农事任务对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(TaskEXT pTaskEXT, final TaskEXT originalTaskEXT, final Map<String, Object> paramMap)
			throws Exception {

		/* ====== 原来已有的数据执行更新，原来不存在的数据执行添加，原来有的数据现在没有了就执行删除 ==== */

		// ---------- 编辑地块 ----------------
		this.editTaskparcel(pTaskEXT, originalTaskEXT);

		// ---------- 编辑农事参数与人 ----------------
		this.editTaskpartics(pTaskEXT, originalTaskEXT, paramMap);

		// ---------- 编辑农事抄送与人 ----------------
		this.editTaskcopy(pTaskEXT, originalTaskEXT, paramMap);

		// ---------- 编辑农事任务的提醒 ----------------
		this.editTaskwarn(pTaskEXT, originalTaskEXT, paramMap);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pTaskEXT, originalTaskEXT, paramMap);

		return this.update(originalTaskEXT);

	}

	/**
	 * 删除指定ID的农事任务
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(TaskEXT pTaskEXT) throws Exception {
		return mTaskEXTMapper.deletebyid(pTaskEXT);
	}

	/**
	 * 根据主键批量删除农事任务
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
		return mTaskEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public TaskEXT queryById(TaskEXT pTaskEXT) throws Exception {
		TaskEXT paramTaskEXT = new TaskEXT();
		paramTaskEXT.setId(pTaskEXT.getId());

		List<TaskEXT> taskEXTList = this.queryList(paramTaskEXT);

		if ((null == taskEXTList) || (taskEXTList.isEmpty())) {
			return null;
		} else {

			for (TaskEXT taskEXT : taskEXTList) {

				// --------------- 设置农事任务的发布人的信息 ----------------------
				TaskparcelEXT paramTaskparcelEXT = new TaskparcelEXT();
				paramTaskparcelEXT.setTaskid(taskEXT.getId());

				List<TaskparcelEXT> taskparcellist = mTaskparcelService.queryList(paramTaskparcelEXT);

				taskEXT.setTaskparcellist(taskparcellist);

				// --------------- 设置农事任务的参与人 ----------------------
				// this.setTaskparticsList(taskEXT);// 已在列表中设置过了

				// --------------- 设置农事任务的发布人的信息 ----------------------
				// this.setCreateUserInfo(taskEXT);// 已在列表中设置过了

				// --------------- 设置农事任务的抄送人 ----------------------
				this.setTaskcopyList(taskEXT);

				// --------------- 设置农事任务的任务提醒 ----------------------
				TaskwarnEXT paramTaskwarnEXT = new TaskwarnEXT();
				paramTaskwarnEXT.setTaskid(taskEXT.getId());

				List<TaskwarnEXT> taskwarnlist = mTaskwarnService.queryList(paramTaskwarnEXT);

				taskEXT.setTaskwarnlist(taskwarnlist);

				break;
			}

			return taskEXTList.get(0);
		}

	}

	/**
	 * 设置农事任务的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @throws Exception
	 */
	public void setTaskparticsList(final TaskEXT taskEXT) throws Exception {

		if (null == taskEXT) {
			return;
		}

		TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
		paramTaskparticsEXT.setTaskid(taskEXT.getId());

		List<TaskparticsEXT> recordpartuserlist = mTaskparticsService.queryList(paramTaskparticsEXT);

		if ((null == recordpartuserlist) || (recordpartuserlist.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;

		SupportUserEXT paramSupportUserEXT = null;
		for (TaskparticsEXT taskparticsEXT : recordpartuserlist) {
			paramSupportUserEXT = new SupportUserEXT();
			paramSupportUserEXT.setId(taskparticsEXT.getUserid());
			paramSupportUserEXT.setSupportid(taskparticsEXT.getUsersupportid());

			supportUserEXT = mCommonService.querySupportUserEXTByIdOrSupportid(paramSupportUserEXT);

			if (null != supportUserEXT) {
				taskparticsEXT.setUsername(supportUserEXT.getName());
				taskparticsEXT.setUsernickname(supportUserEXT.getNickname());
				taskparticsEXT.setUserphotourl(supportUserEXT.getPhotourl());

				if (usernameBuffer.length() > 0) {
					usernameBuffer.append("、").append(taskparticsEXT.getUsername());
				} else {
					usernameBuffer.append(taskparticsEXT.getUsername());
				}

				if (useridBuffer.length() > 0) {
					useridBuffer.append(",").append(taskparticsEXT.getUserid());
				} else {
					useridBuffer.append(taskparticsEXT.getUserid());
				}
			}
		}

		taskEXT.setTaskparticsusernames(usernameBuffer.toString());
		taskEXT.setTaskparticsuserids(useridBuffer.toString());

		taskEXT.setTaskparticslist(recordpartuserlist);

	}

	/**
	 * 设置农事任务的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @param supportUserEXTMap
	 *            用户信息
	 * @throws Exception
	 */
	private void setTaskparticsList(final TaskEXT taskEXT, final Map<String, SupportUserEXT> supportUserEXTMap)
			throws Exception {

		if ((null == taskEXT) || (null == taskEXT.getTaskparticslist()) || (taskEXT.getTaskparticslist().isEmpty())
				|| (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;

		for (TaskparticsEXT taskparticsEXT : taskEXT.getTaskparticslist()) {

			supportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(taskparticsEXT.getUserid()));

			if (null != supportUserEXT) {
				taskparticsEXT.setUsername(supportUserEXT.getName());
				taskparticsEXT.setUsernickname(supportUserEXT.getNickname());
				taskparticsEXT.setUserphotourl(supportUserEXT.getPhotourl());

				if (usernameBuffer.length() > 0) {
					usernameBuffer.append("、").append(taskparticsEXT.getUsername());
				} else {
					usernameBuffer.append(taskparticsEXT.getUsername());
				}

				if (useridBuffer.length() > 0) {
					useridBuffer.append(",").append(taskparticsEXT.getUserid());
				} else {
					useridBuffer.append(taskparticsEXT.getUserid());
				}
			}
		}

		taskEXT.setTaskparticsusernames(usernameBuffer.toString());
		taskEXT.setTaskparticsuserids(useridBuffer.toString());
	}

	/**
	 * 设置农事任务的抄送人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @throws Exception
	 */
	private void setTaskcopyList(final TaskEXT taskEXT) throws Exception {

		if (null == taskEXT) {
			return;
		}

		TaskcopyEXT paramTaskcopyEXT = new TaskcopyEXT();
		paramTaskcopyEXT.setTaskid(taskEXT.getId());

		List<TaskcopyEXT> taskcopylist = mTaskcopyService.queryList(paramTaskcopyEXT);

		if ((null == taskcopylist) || (taskcopylist.isEmpty())) {
			return;
		}

		List<Integer> userIdList = new ArrayList<Integer>();
		for (TaskcopyEXT taskcopyEXT : taskcopylist) {
			userIdList.add(taskcopyEXT.getUserid());
		}

		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		SupportUserEXT supportUserEXT = null;
		for (TaskcopyEXT taskparticsEXT : taskcopylist) {

			supportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(taskparticsEXT.getUserid()));

			if (null != supportUserEXT) {
				taskparticsEXT.setUsername(supportUserEXT.getName());
				taskparticsEXT.setUsernickname(supportUserEXT.getNickname());
				taskparticsEXT.setUserphotourl(supportUserEXT.getPhotourl());
			}
		}

		taskEXT.setTaskcopylist(taskcopylist);

	}

	/**
	 * 设置农事任务的发布人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @throws Exception
	 */
	public void setCreateUserInfo(final TaskEXT taskEXT) throws Exception {

		if (null == taskEXT) {
			return;
		}

		SupportUserEXT paramSupportUserEXT = new SupportUserEXT();
		paramSupportUserEXT.setId(taskEXT.getCreateuserid());

		SupportUserEXT supportUserEXT = mCommonService.querySupportUserEXTByIdOrSupportid(paramSupportUserEXT);

		if (null != supportUserEXT) {
			taskEXT.setCreateuseridname(supportUserEXT.getName());
			taskEXT.setCreateuseridnickname(supportUserEXT.getNickname());
		}

	}

	/**
	 * 设置农事任务的发布人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @param supportUserEXTMap
	 *            用户信息
	 * @throws Exception
	 */
	private void setCreateUserInfo(final TaskEXT taskEXT, final Map<String, SupportUserEXT> supportUserEXTMap)
			throws Exception {

		if (null == taskEXT) {
			return;
		}

		SupportUserEXT supportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(taskEXT.getCreateuserid()));

		if (null != supportUserEXT) {
			taskEXT.setCreateuseridname(supportUserEXT.getName());
			taskEXT.setCreateuseridnickname(supportUserEXT.getNickname());
		}

	}

	/**
	 * 检索农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskEXT> queryList(TaskEXT pTaskEXT) throws Exception {
		List<TaskEXT> list = mTaskEXTMapper.queryList(pTaskEXT);

		if ((null == list) || (list.isEmpty())) {
			return list;
		}

		List<Integer> userIdList = new ArrayList<Integer>();
		List<Integer> taskIdList = new ArrayList<Integer>();

		for (TaskEXT taskEXT : list) {
			taskIdList.add(taskEXT.getId());

			// 创建人的ID
			userIdList.add(taskEXT.getCreateuserid());

			// 地块数组
			TaskparcelEXT paramTaskparcelEXT = new TaskparcelEXT();
			paramTaskparcelEXT.setTaskid(taskEXT.getId());
			List<TaskparcelEXT> taskparcellist = mTaskparcelService.queryList(paramTaskparcelEXT);
			taskEXT.setTaskparcellist(taskparcellist);
		}

		TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
		paramTaskparticsEXT.setTaskidlist(taskIdList);

		List<TaskparticsEXT> taskparticsEXTList = mTaskparticsService.queryList(paramTaskparticsEXT);

		for (TaskparticsEXT taskparticsEXT : taskparticsEXTList) {
			for (TaskEXT taskEXT : list) {
				if (taskEXT.getId().equals(taskparticsEXT.getTaskid())) {
					if (null == taskEXT.getTaskparticslist()) {
						taskEXT.setTaskparticslist(new ArrayList<TaskparticsEXT>());
					}

					// 添加到农事任务的参与人列表中
					taskEXT.getTaskparticslist().add(taskparticsEXT);
					break;
				}
			}

			// 参与人的ID
			userIdList.add(taskparticsEXT.getUserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (TaskEXT taskEXT : list) {
			if (null == taskEXT) {
				continue;
			}

			// --------------- 设置农事任务的参与人 ----------------------
			this.setTaskparticsList(taskEXT, supportUserEXTMap);

			// --------------- 设置农事任务的发布人的信息 ----------------------
			this.setCreateUserInfo(taskEXT, supportUserEXTMap);
		}

		return list;
	}

	/**
	 * 检索农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public List<TaskEXT> queryTaskList(TaskEXT pTaskEXT) throws Exception {
		List<TaskEXT> list = mTaskEXTMapper.queryList(pTaskEXT);

		return list;
	}

	/**
	 * 检索农事任务的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(TaskEXT pTaskEXT) throws Exception {
		return mTaskEXTMapper.queryListTotalCount(pTaskEXT);
	}

	/**
	 * 编辑地块
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramTaskEXT
	 *            从客户端传过来的农事任务对象
	 * @param originalTaskEXT
	 *            从数据库中检索出来的农事任务对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editTaskparcel(final TaskEXT paramTaskEXT, final TaskEXT originalTaskEXT) throws Exception {

		if ((null == paramTaskEXT.getTaskparcellist())) {
			paramTaskEXT.setTaskparcellist(new ArrayList<TaskparcelEXT>());
		}

		if ((null == originalTaskEXT.getTaskparcellist())) {
			originalTaskEXT.setTaskparcellist(new ArrayList<TaskparcelEXT>());
		}

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的地块列表
		Iterator<TaskparcelEXT> paramTaskparcelEXTIterator = paramTaskEXT.getTaskparcellist().iterator();

		TaskparcelEXT paramTaskparcelEXT = null;
		while (paramTaskparcelEXTIterator.hasNext()) {
			paramTaskparcelEXT = paramTaskparcelEXTIterator.next();

			// 设置农事任务的ID，在后续需要添加的时候农事任务的ID才不会为空
			paramTaskparcelEXT.setTaskid(paramTaskEXT.getId());

			for (TaskparcelEXT originalCommunionpartuserEX : originalTaskEXT.getTaskparcellist()) {
				if (originalCommunionpartuserEX.getParcelid().equals(paramTaskparcelEXT.getParcelid())) {
					// 该地块需要保留
					originalTaskEXT.getTaskparcellist().remove(originalCommunionpartuserEX);

					// 当前地块原来就已经存在，把他移除掉，最终新增不存在的地块，删除原来已存在并且不需要保留的地块
					paramTaskparcelEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的地块
		if (originalTaskEXT.getTaskparcellist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (TaskparcelEXT obj : originalTaskEXT.getTaskparcellist()) {
				idList.add(obj.getId());// 需要刪除掉的地块
			}

			addOrUpdateRsult = mTaskparcelService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Taskparcel failure !");
			}
		}

		// 添加新的地块（历史的地块已更新）
		if (paramTaskEXT.getTaskparcellist().size() > 0) {
			addOrUpdateRsult = mTaskparcelService.batchAdd(paramTaskEXT.getTaskparcellist());

			if ((null == addOrUpdateRsult)
					|| (paramTaskEXT.getTaskparcellist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Taskparcel failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramTaskEXT
	 *            从客户端传过来的农事任务对象
	 * @param originalTaskEXT
	 *            从数据库中检索出来的农事任务对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editTaskpartics(final TaskEXT paramTaskEXT, final TaskEXT originalTaskEXT,
			final Map<String, Object> paramMap) throws Exception {

		if (null == paramTaskEXT.getTaskparticslist()) {
			paramTaskEXT.setTaskparticslist(new ArrayList<TaskparticsEXT>());
		}

		if (null == originalTaskEXT.getTaskparticslist()) {
			originalTaskEXT.setTaskparticslist(new ArrayList<TaskparticsEXT>());
		}

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的参与人列表
		Iterator<TaskparticsEXT> paramTaskparticsEXTIterator = paramTaskEXT.getTaskparticslist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		TaskparticsEXT paramTaskparticsEXT = null;
		while (paramTaskparticsEXTIterator.hasNext()) {
			paramTaskparticsEXT = paramTaskparticsEXTIterator.next();

			// 设置农事任务的ID，在后续需要添加的时候农事任务的ID才不会为空
			paramTaskparticsEXT.setTaskid(paramTaskEXT.getId());

			for (TaskparticsEXT originalTaskparticsEXT : originalTaskEXT.getTaskparticslist()) {
				if (originalTaskparticsEXT.getUserid().equals(paramTaskparticsEXT.getUserid())) {

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("taskparticslist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramTaskparticsEXT, originalTaskparticsEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mTaskparticsService.update(originalTaskparticsEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Taskpartics is failure !");
					}

					// 该参与人需要保留
					originalTaskEXT.getTaskparticslist().remove(originalTaskparticsEXT);

					// 当前参与人原来就已经存在，把他移除掉，最终新增不存在的参与人，删除原来已存在并且不需要保留的参与人
					paramTaskparticsEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的参与人
		if (originalTaskEXT.getTaskparticslist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (TaskparticsEXT obj : originalTaskEXT.getTaskparticslist()) {
				idList.add(obj.getId());// 需要刪除掉的参与人
			}

			addOrUpdateRsult = mTaskparticsService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Taskpartics failure !");
			}
		}

		// 添加新的参与人（历史的参与人已更新）
		if (paramTaskEXT.getTaskparticslist().size() > 0) {

			// 在把农事任务的参与人添加到数据库之前的相关属性设置
			this.setBeforeAddTaskparticslist(paramTaskEXT);

			addOrUpdateRsult = mTaskparticsService.batchAdd(paramTaskEXT.getTaskparticslist());

			if ((null == addOrUpdateRsult)
					|| (paramTaskEXT.getTaskparticslist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Taskpartics failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramTaskEXT
	 *            从客户端传过来的农事任务对象
	 * @param originalTaskEXT
	 *            从数据库中检索出来的农事任务对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editTaskcopy(final TaskEXT paramTaskEXT, final TaskEXT originalTaskEXT,
			final Map<String, Object> paramMap) throws Exception {

		if (null == paramTaskEXT.getTaskcopylist()) {
			paramTaskEXT.setTaskcopylist(new ArrayList<TaskcopyEXT>());
		}

		if (null == originalTaskEXT.getTaskcopylist()) {
			originalTaskEXT.setTaskcopylist(new ArrayList<TaskcopyEXT>());
		}

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的抄送人列表
		Iterator<TaskcopyEXT> paramTaskcopyEXTIterator = paramTaskEXT.getTaskcopylist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		TaskcopyEXT paramTaskcopyEXT = null;
		while (paramTaskcopyEXTIterator.hasNext()) {
			paramTaskcopyEXT = paramTaskcopyEXTIterator.next();

			// 设置农事任务的ID，在后续需要添加的时候农事任务的ID才不会为空
			paramTaskcopyEXT.setTaskid(paramTaskEXT.getId());

			for (TaskcopyEXT originalTaskcopyEXT : originalTaskEXT.getTaskcopylist()) {
				if (originalTaskcopyEXT.getUserid().equals(paramTaskcopyEXT.getUserid())) {

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("taskcopylist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramTaskcopyEXT, originalTaskcopyEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mTaskcopyService.update(originalTaskcopyEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Taskcopy is failure !");
					}

					// 该抄送人需要保留
					originalTaskEXT.getTaskcopylist().remove(originalTaskcopyEXT);

					// 当前抄送人原来就已经存在，把他移除掉，最终新增不存在的抄送人，删除原来已存在并且不需要保留的抄送人
					paramTaskcopyEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的抄送人
		if (originalTaskEXT.getTaskcopylist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (TaskcopyEXT obj : originalTaskEXT.getTaskcopylist()) {
				idList.add(obj.getId());// 需要刪除掉的抄送人
			}

			addOrUpdateRsult = mTaskcopyService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Taskcopy failure !");
			}
		}

		// 添加新的抄送人（历史的抄送人已更新）
		if (paramTaskEXT.getTaskcopylist().size() > 0) {
			addOrUpdateRsult = mTaskcopyService.batchAdd(paramTaskEXT.getTaskcopylist());

			if ((null == addOrUpdateRsult) || (paramTaskEXT.getTaskcopylist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Taskcopy failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑任务提醒
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramTaskEXT
	 *            从客户端传过来的农事任务对象
	 * @param originalTaskEXT
	 *            从数据库中检索出来的农事任务对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editTaskwarn(final TaskEXT paramTaskEXT, final TaskEXT originalTaskEXT,
			final Map<String, Object> paramMap) throws Exception {

		if (null == paramTaskEXT.getTaskwarnlist()) {
			paramTaskEXT.setTaskwarnlist(new ArrayList<TaskwarnEXT>());
		}

		if (null == originalTaskEXT.getTaskwarnlist()) {
			originalTaskEXT.setTaskwarnlist(new ArrayList<TaskwarnEXT>());
		}

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的任务提醒列表
		Iterator<TaskwarnEXT> paramTaskwarnEXTIterator = paramTaskEXT.getTaskwarnlist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		TaskwarnEXT paramTaskwarnEXT = null;
		while (paramTaskwarnEXTIterator.hasNext()) {
			paramTaskwarnEXT = paramTaskwarnEXTIterator.next();

			// 设置农事任务的ID，在后续需要添加的时候农事任务的ID才不会为空
			paramTaskwarnEXT.setTaskid(paramTaskEXT.getId());

			for (TaskwarnEXT originalTaskwarnEXT : originalTaskEXT.getTaskwarnlist()) {
				if (originalTaskwarnEXT.getTaskid().equals(paramTaskwarnEXT.getTaskid())) {

					// 设置提醒时间（提醒时间是由后台根据任务的结束时间和任务结束前多长时间计算得出的）
					originalTaskwarnEXT.setWarntime(paramTaskwarnEXT.getWarntime());

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("taskwarnlist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramTaskwarnEXT, originalTaskwarnEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mTaskwarnService.update(originalTaskwarnEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Taskwarn is failure !");
					}

					// 该任务提醒需要保留
					originalTaskEXT.getTaskwarnlist().remove(originalTaskwarnEXT);

					// 当前任务提醒原来就已经存在，把他移除掉，最终新增不存在的任务提醒，删除原来已存在并且不需要保留的任务提醒
					paramTaskwarnEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的任务提醒
		if (originalTaskEXT.getTaskwarnlist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (TaskwarnEXT obj : originalTaskEXT.getTaskwarnlist()) {
				idList.add(obj.getId());// 需要刪除掉的任务提醒
			}

			addOrUpdateRsult = mTaskwarnService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Taskwarn failure !");
			}
		}

		// 添加新的任务提醒（历史的任务提醒已更新）
		if (paramTaskEXT.getTaskwarnlist().size() > 0) {
			addOrUpdateRsult = mTaskwarnService.batchAdd(paramTaskEXT.getTaskwarnlist());

			if ((null == addOrUpdateRsult) || (paramTaskEXT.getTaskwarnlist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Taskwarn failure !");
			}
		}

		return true;
	}

	/**
	 * 设置农事任务的提醒时间
	 * 
	 * @author wangjunwen
	 * @date 2018年2月8日 下午5:20:22
	 *
	 * @param taskwarnEXT
	 *            农事任务提醒对象
	 * @param taskEXT
	 *            农事任务对象
	 */
	public void setTaskwarntime(final TaskwarnEXT taskwarnEXT, final TaskEXT taskEXT) {

		// 获取农事事务提醒的时间单位
		EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, taskwarnEXT.getTimeunit());

		long beforeTimes = 0;// 结束时间前多少毫秒

		if (EDict.farm_time_unit_minutes.equals(farmTimeUnit)) {// 结束时间钱多少分钟提醒

			beforeTimes = (taskwarnEXT.getWarntimes() * 60 * 1000);

		} else if (EDict.farm_time_unit_hour.equals(farmTimeUnit)) {// 结束时间钱多少小时提醒

			beforeTimes = (taskwarnEXT.getWarntimes() * 60 * 60 * 1000);

		} else if (EDict.farm_time_unit_day.equals(farmTimeUnit)) {// 结束时间钱多少天提醒

			beforeTimes = (taskwarnEXT.getWarntimes() * 24 * 60 * 60 * 1000);

		}

		// 最终提醒的时间（在任务结束前多长时间提醒）
		Date warntime = new Date((taskEXT.getEndtime().getTime() - beforeTimes));

		taskwarnEXT.setWarntime(warntime);// 设置提醒的时间
	}

	/**
	 * 完成任务
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:10:08
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer updateAsFinish(final TaskparticsEXT pTaskparticsEXT) throws Exception {

		// ================ 更新我参与的农事任务 =======================
		TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
		paramTaskparticsEXT.setUserid(pTaskparticsEXT.getUserid());
		paramTaskparticsEXT.setTaskid(pTaskparticsEXT.getTaskid());
		paramTaskparticsEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);

		List<TaskparticsEXT> taskparticsEXTList = mTaskparticsService.queryList(paramTaskparticsEXT);

		Integer resultUpdate = null;

		if ((null == taskparticsEXTList) || (taskparticsEXTList.isEmpty())) {

			ResultWrap resultWrap = new ResultWrap();

			resultWrap.setParamInvalid("taskid", "userid");

			// 农事任务的参与人不存在（taskid, userid 无效）
			throw new Exception(resultWrap.getMessage());

		} else {

			for (TaskparticsEXT finishTaskparticsEXT : taskparticsEXTList) {

				// 如果客户端确认已保存到农事记录，那么设置该参与人已保存到农事记录（但是至少已经保存过农事记录，或者当前客户端有农事记录的ID传入）
				if ((new Boolean(true).equals(pTaskparticsEXT.getIssavetorecord()))
						&& ((null != pTaskparticsEXT.getRecordeid())
								|| (null != finishTaskparticsEXT.getRecordeid()))) {

					finishTaskparticsEXT.setIssavedtorecord(CommonConstantsUtil.YES_OR_NO_YES);

				}

				// 我参与的任务已完成
				finishTaskparticsEXT.setTaskstatus(EDict.task_status_finish.getDict().getDictValue());
				// 设置农事记录的ID
				finishTaskparticsEXT.setRecordeid(pTaskparticsEXT.getRecordeid());

				resultUpdate = mTaskparticsService.update(finishTaskparticsEXT);

				if ((null == resultUpdate) || (1 != resultUpdate.intValue())) {
					throw new Exception("update Taskpartics.taskstatus failure !");
				}

				// 参与人完成任务时添加“已完成”任务动态
				this.addTaskcommentByFinishTask(finishTaskparticsEXT);
			}

		}

		// ================ 变更农事任务状态的业务处理 ================
		TaskEXT paramTaskEXT = new TaskEXT();
		paramTaskEXT.setId(pTaskparticsEXT.getTaskid());

		TaskEXT taskEXT = this.queryById(paramTaskEXT);

		if (null == taskEXT) {

			ResultWrap resultWrap = new ResultWrap();

			resultWrap.setParamInvalid("taskid");

			// 农事任务不存在（taskid 无效）
			throw new Exception(resultWrap.getMessage());

		} else {

			// 获取农事任务的类别
			final EDict taskTypeStatus = EDict.getEDictByType(EDictType.task_type, taskEXT.getType());

			// 农事任务参与人个人的农事任务状态
			EDict taskparticsTaskStatus = null;

			// 所有参与人的农事任务状态
			final Set<EDict> taskparticsTaskStatusSet = new HashSet<EDict>();

			for (TaskparticsEXT taskparticsEXT : taskEXT.getTaskparticslist()) {
				taskparticsTaskStatus = EDict.getEDictByType(EDictType.task_status, taskparticsEXT.getTaskstatus());

				taskparticsTaskStatusSet.add(taskparticsTaskStatus);
			}

			if (EDict.task_type_meeting_invitation.equals(taskTypeStatus)) {// 会议类的农事任务

				// 会议类的农事任务，只要有一个完成任务，那么整个农事任务就已经完成
				if (taskparticsTaskStatusSet.contains(EDict.task_status_finish)) {

					resultUpdate = this.update(taskEXT);

					if ((null == resultUpdate) || (1 != resultUpdate.intValue())) {
						throw new Exception("update Task.taskstatus failure !");
					}
				}
			} else {

				if ((taskparticsTaskStatusSet.contains(EDict.task_status_overdue))
						&& !(taskEXT.getTaskstatus().equals(EDict.task_status_overdue.getDict().getDictValue()))) {// 已逾期

					// 只要有参与者已逾期，那么整个农事任务的状态将变更为：已逾期
					taskEXT.setTaskstatus(EDict.task_status_overdue.getDict().getDictValue());

					resultUpdate = this.update(taskEXT);

					if ((null == resultUpdate) || (1 != resultUpdate.intValue())) {
						throw new Exception("update Task.taskstatus failure !");
					}

				} else {

					// 把已拒绝的状态排除掉
					taskparticsTaskStatusSet.remove(EDict.task_status_refuse);

					// 除了已拒绝以外，只要其他参与人都已经完成任务，那么整个任务的状态也将变更为：已完成
					if ((1 == taskparticsTaskStatusSet.size())
							&& (taskparticsTaskStatusSet.contains(EDict.task_status_finish))) {

						// 将农事任务的状态变更为：已完成
						taskEXT.setTaskstatus(EDict.task_status_finish.getDict().getDictValue());

						resultUpdate = this.update(taskEXT);

						if ((null == resultUpdate) || (1 != resultUpdate.intValue())) {
							throw new Exception("update Task.taskstatus failure !");
						}
					}
				}
			}

		}

		return resultUpdate;
	}

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:41:17
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @param operateid
	 *            操作人
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer deleteAllFromTrash(final TaskEXT pTaskEXT, final Integer operateid) throws Exception {

		Integer resultUpdate = null;

		// 清空我发布的在回收站中的农事任务
		if (null != pTaskEXT.getCreateuserid()) {

			resultUpdate = mTaskEXTMapper.deleteAllFromTrash(pTaskEXT);

		}

		// 清空我参与的在回收站中的农事任务
		if (null != pTaskEXT.getTaskparticsuserid()) {

			TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
			paramTaskparticsEXT.setUserid(pTaskEXT.getTaskparticsuserid());

			resultUpdate = mTaskparticsService.deleteAllFromTrash(paramTaskparticsEXT);

		}

		// 清空抄送我的在回收站中的农事任务
		if (null != pTaskEXT.getTaskcopyuserid()) {

			TaskcopyEXT paramTaskcopyEXT = new TaskcopyEXT();
			paramTaskcopyEXT.setUserid(pTaskEXT.getTaskcopyuserid());

			resultUpdate = mTaskcopyService.deleteAllFromTrash(paramTaskcopyEXT);

		}

		return resultUpdate;
	}

	/**
	 * 从回收站中恢复
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @param operateid
	 *            操作人
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer revertFromTrash(TaskEXT pTaskEXT, Integer operateid) throws Exception {
		Integer resultUpdate = mTaskEXTMapper.revertFromTrash(pTaskEXT);

		return resultUpdate;
	}

	/**
	 * 设置农事任务是否可保存到农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年3月15日 下午2:16:33
	 *
	 * @param pTaskEXT
	 *            农事任务
	 * @param pTaskparticsEXTList
	 *            农事任务的参与人列表
	 * @param pLoginUserInfo
	 *            农事任务的参与人
	 */
	public void setIscansavetorecordByTask(final TaskEXT pTaskEXT, final List<TaskparticsEXT> pTaskparticsEXTList,
			final LoginUserInfo pLoginUserInfo) {

		if ((null == pTaskEXT) || (null == pTaskparticsEXTList) || (pTaskparticsEXTList.isEmpty())
				|| (null == pLoginUserInfo)) {
			return;
		}

		for (TaskparticsEXT taskparticsEXT : pTaskparticsEXTList) {
			// 只有是自己参与农事任务才有权限保存到农事记录，并且已完成的农事任务才能保存到农事记录
			if (pTaskEXT.getId().equals(taskparticsEXT.getTaskid())
					&& pLoginUserInfo.getId().equals(taskparticsEXT.getUserid())) {

				// 农事任务参与人的农事任务状态
				EDict currentTaskparticsTaskStatus = EDict.getEDictByType(EDictType.task_status,
						taskparticsEXT.getTaskstatus());

				if ((new Integer(CommonConstantsUtil.YES_OR_NO_YES).equals(taskparticsEXT.getIssavedtorecord()))) {// 农事任务已经保存到农事记录就不能再执行保存到农事记录的操作了

					// 是否可存入农事记录：否
					pTaskEXT.setIscansavetorecord(false);

					// 是否已存至至农事记录：是
					pTaskEXT.setSavedrecordlabel(LanguageUtil.getLanguageByKey("fwk-farm-task-yesno-yes"));

				} else if ((EDict.task_status_finish.equals(currentTaskparticsTaskStatus))) {// 农事任务已完成，但是还未保存到农事记录的可以保存到

					// 是否可存入农事记录：是
					pTaskEXT.setIscansavetorecord(true);

					// 是否已存至至农事记录：否
					pTaskEXT.setSavedrecordlabel(LanguageUtil.getLanguageByKey("fwk-farm-task-yesno-no"));

					// 农事记录的ID
					pTaskEXT.setRecordeid(taskparticsEXT.getRecordeid());
				}

				break;
			}
		}

	}

	/**
	 * 在把农事任务的参与人添加到数据库之前的相关属性设置
	 * 
	 * @author wangjunwen
	 * @date 2018年3月16日 下午4:36:20
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @throws Exception
	 */
	public void setBeforeAddTaskparticslist(TaskEXT paramTaskEXT) throws Exception {

		if ((null == paramTaskEXT) || (null == paramTaskEXT.getTaskparticslist())
				|| (paramTaskEXT.getTaskparticslist().isEmpty())) {
			return;
		} else if ((null == paramTaskEXT.getId())) {
			ResultWrap resultWrap = new ResultWrap();

			resultWrap.setParamInvalid("Task.id");

			throw new Exception(resultWrap.getMessage());
		}

		for (TaskparticsEXT taskparticsEXT : paramTaskEXT.getTaskparticslist()) {

			taskparticsEXT.setTaskid(paramTaskEXT.getId());

			// 是否需要参与人确认的默认值设置
			if (new Integer(CommonConstantsUtil.YES_OR_NO_YES).equals(paramTaskEXT.getIsaffirmpartics())) {// 1：需要参与人确认

				// 需要参与人确认时，默认将参与人的农事任务参与状态置为：待确认
				taskparticsEXT.setTaskparticsstatus(EDict.task_partics_status_waitingconfirm.getDict().getDictValue());

			} else if (new Integer(CommonConstantsUtil.YES_OR_NO_NO).equals(paramTaskEXT.getIsaffirmpartics())) {// 0：不需要参与人确认

				// 不需要参与人确认时，默认将参与人的农事任务参与状态置为：参与
				taskparticsEXT.setTaskparticsstatus(EDict.task_partics_status_partake.getDict().getDictValue());

				// 农事任务参与人的农事任务状态
				taskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());
			} else {
				ResultWrap resultWrap = new ResultWrap();

				resultWrap.setParamInvalid("Task.isaffirmpartics");

				throw new Exception(resultWrap.getMessage());
			}
		}

	}

	/**
	 * 重置任务状态
	 * 
	 * @author wangjunwen
	 * @date 2018年3月28日 上午11:39:37
	 *
	 * @param taskEXTList
	 *            农事任务列表
	 * 
	 * @throws Exception
	 */
	public void resetTaskStatus(List<TaskEXT> taskEXTList) throws Exception {

		if ((null == taskEXTList) || (taskEXTList.isEmpty())) {
			return;
		}

		List<Integer> taskidList = new ArrayList<Integer>();

		for (TaskEXT taskEXT : taskEXTList) {
			taskidList.add(taskEXT.getId());// 农事任务的ID

			// 按客户端显示格式的要求重新设置参与人
			taskEXT.setTaskparticsusernames(
					ObjectUtil.toString(taskEXT.getTaskparticsusernames()).replaceAll(",", "、"));
		}

		// 需要更新状态的农事任务
		List<TaskEXT> updateTaskList = new ArrayList<TaskEXT>();

		// 需要更新状态的农事任务的参与人
		List<TaskparticsEXT> updateTaskparticsList = new ArrayList<TaskparticsEXT>();

		TaskparticsEXT paramTaskparticsEXT = new TaskparticsEXT();
		paramTaskparticsEXT.setTaskidlist(taskidList);

		List<TaskparticsEXT> taskparticsList = mTaskparticsService.queryList(paramTaskparticsEXT);

		// 当前时间
		Date currentDate = mCommonService.queryCurrentDate();
		// currentDate.setDate(currentDate.getDay()+1);
		EDict currentTaskStatus = null;// 农事任务的当前农事状态
		EDict currentTaskparticsTaskStatus = null;// 农事任务参与人的当前农事任务状态

		List<EDict> taskparticstaskstatusList = null;

		for (TaskEXT taskEXT : taskEXTList) {

			// 设置农事任务的参与人列表
			taskEXT.setTaskparticslist(new ArrayList<TaskparticsEXT>());

			taskparticstaskstatusList = new ArrayList<EDict>();

			currentTaskStatus = EDict.getEDictByType(EDictType.task_status, taskEXT.getTaskstatus());

			// 排除掉已拒绝的（证明所有参与人都已拒绝）
			if (EDict.task_status_refuse.equals(currentTaskStatus)) {

				// 设置参与人的确认参与农事任务的状态
				taskEXT.setConfirmtaskparticsstatus(EDict.task_partics_status_refuse.getDict().getDictValue());
				// 设置参与人的确认参与农事任务的状态名称
				taskEXT.setConfirmtaskparticsstatusname(EDict.task_partics_status_refuse.getDict().getDictName());

				continue;
			}

			for (TaskparticsEXT taskparticsEXT : taskparticsList) {

				// 设置该农事任务的参与人的农事状态
				if (!(taskEXT.getId().equals(taskparticsEXT.getTaskid()))) {
					continue;
				}

				// 农事农事任务的农事参与人列表
				taskEXT.getTaskparticslist().add(taskparticsEXT);

				// --------- 设置是否已逾期 -----------------------
				// 获取当前参与人的农事任务状态
				currentTaskparticsTaskStatus = EDict.getEDictByType(EDictType.task_status,
						taskparticsEXT.getTaskstatus());

				// 结束时间已经超过当前时间，并且参与人的任务状态不是“已完成”，并且不是“已逾期”，那么需要将该参与人的任务状更新为“已逾期”
				if ((taskEXT.getEndtime().before(currentDate))
						&& !(EDict.task_status_finish.equals(currentTaskparticsTaskStatus))
						&& !(EDict.task_status_overdue.equals(currentTaskparticsTaskStatus))) {

					// 如果农事参与人当前的农事状态为“进行中”，并且当前时间大于截止时间，那么该参与人的任务状态应该为“已逾期”
					taskparticstaskstatusList.add(EDict.task_status_overdue);

					// 设置农事任务参与人的农事状态为：已逾期
					taskparticsEXT.setTaskstatus(EDict.task_status_overdue.getDict().getDictValue());

					// 需要更新参与人的农事任务状态
					updateTaskparticsList.add(taskparticsEXT);

				} else if ((taskEXT.getEndtime().after(currentDate))
						&& (EDict.task_status_overdue.equals(currentTaskparticsTaskStatus))) {

					// 如果农事参与人当前的农事状态为“已逾期”，并且当前时间小于截止时间，那么该参与人的任务状态应该为“进行中”
					taskparticstaskstatusList.add(EDict.task_status_started);

					// 设置农事任务参与人的农事状态为：进行中
					taskparticsEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());

					// 需要更新参与人的农事任务状态
					updateTaskparticsList.add(taskparticsEXT);

				} else {

					taskparticstaskstatusList.add(currentTaskparticsTaskStatus);

				}

			}

			// 设置农事任务的状态（完成的状态在用户确认完成的时候自动设置，这儿只需关注是否逾期即可。“进行中”、“已拒绝”、“已完成”这些状态是通过响应的操作自动设置）
			// 新增：进行中
			// 确认时拒绝任务：已拒绝
			// 用户操作完成任务时：已完成
			final Set<EDict> eDictSet = new HashSet<EDict>();
			eDictSet.addAll(taskparticstaskstatusList);

			if ((1 == eDictSet.size()) && (eDictSet.contains(EDict.task_status_finish))
					&& !(EDict.task_status_finish.equals(currentTaskStatus))) {// 已完成

				taskEXT.setTaskstatus(EDict.task_status_finish.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_finish.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if ((1 == eDictSet.size()) && (eDictSet.contains(EDict.task_status_refuse))
					&& !(EDict.task_status_refuse.equals(currentTaskStatus))) {// 已拒绝

				taskEXT.setTaskstatus(EDict.task_status_refuse.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_refuse.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if ((1 == eDictSet.size()) && (eDictSet.contains(EDict.task_status_overdue))
					&& !(EDict.task_status_overdue.equals(currentTaskStatus))) {// 已逾期

				taskEXT.setTaskstatus(EDict.task_status_overdue.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_overdue.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if ((1 == eDictSet.size()) && (eDictSet.contains(EDict.task_status_started))
					&& !(EDict.task_status_started.equals(currentTaskStatus))) {// 进行中

				taskEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_started.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if ((1 == eDictSet.size()) && (eDictSet.contains(null)) && (null != currentTaskStatus)) {// 状态为空

				taskEXT.setTaskstatus(null);
				taskEXT.setTaskstatusname(null);

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if (eDictSet.contains(EDict.task_status_overdue)
					&& !(EDict.task_status_overdue.equals(currentTaskStatus))) {// 只要有一个人逾期任务，那么整个任务的状态为：已逾期

				taskEXT.setTaskstatus(EDict.task_status_overdue.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_overdue.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			} else if (eDictSet.contains(EDict.task_status_started)
					&& !(EDict.task_status_started.equals(currentTaskStatus))) {

				// 除了所有人都已完成或者已拒绝，或者有参与人已逾期，只要有参与人的任务状态包含进行中，那么整个任务的状态为：进行中
				taskEXT.setTaskstatus(EDict.task_status_started.getDict().getDictValue());
				taskEXT.setTaskstatusname(EDict.task_status_started.getDict().getDictName());

				// 需要更新农事事务的状态
				updateTaskList.add(taskEXT);

			}

		}

		if ((null != updateTaskList) && (updateTaskList.size() > 0)) {
			// 异步执行需要更新的农事任务
			this.asynchronizationUpdate(updateTaskList);
		}

		if ((null != updateTaskparticsList) && (updateTaskparticsList.size() > 0)) {
			// 异步执行需要更新的农事任务参与人
			mTaskparticsService.asynchronizationUpdate(updateTaskparticsList);
		}

	}

	/**
	 * 查询出在首页上显示的未读的任务数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:18:30
	 *
	 * @param loginUserInfo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCountNotReadTaskToHomePage(final LoginUserInfo loginUserInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// ------------------- 任务 ----------------------------------
		// 我参与的未读的任务
		TaskEXT myScheduleTaskEXT = new TaskEXT();
		myScheduleTaskEXT.setTaskparticsuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的任务
		myScheduleTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		myScheduleTaskEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		this.setQueryPermisions(myScheduleTaskEXT, loginUserInfo);// 设置数据的查询权限

		final Integer myScheduleTaskEXTCount = this.queryListTotalCount(myScheduleTaskEXT);

		// 抄送给我的未读的任务
		TaskEXT copyMeScheduleTaskEXT = new TaskEXT();
		copyMeScheduleTaskEXT.setTaskcopyuserids((ObjectUtil.toString(loginUserInfo.getId())));// 我参与的任务
		copyMeScheduleTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		copyMeScheduleTaskEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		this.setQueryPermisions(copyMeScheduleTaskEXT, loginUserInfo);// 设置数据的查询权限

		final Integer copyMeScheduleTaskEXTCount = this.queryListTotalCount(copyMeScheduleTaskEXT);

		// 统计出我的未读的任务的记录数量
		final Integer countNotRead = ((null == myScheduleTaskEXTCount ? 0 : myScheduleTaskEXTCount)
				+ (null == copyMeScheduleTaskEXTCount ? 0 : copyMeScheduleTaskEXTCount));

		// 有未读的任务，优先显示未读的任务的数量，否则显示今日未完成的数量
		if (0 == countNotRead.intValue()) {
			// 查询出今日有几个未完成的任务
			TaskEXT paramTaskEXT = new TaskEXT();
			// Date nowDate = mCommonService.queryCurrentDate();// 获取当前时间
			// String currentDate = DateTimeUtil.format(nowDate,
			// DateTimeUtil.DEFAULT_DATE_FORMAT);
			// paramTaskEXT.setBegintimestr(DateTimeUtil.resetDateRangeStart(currentDate));
			// paramTaskEXT.setBegintime(DateTimeUtil.parse(paramTaskEXT.getBegintimestr()));
			// paramTaskEXT.setEndtimestr(DateTimeUtil.resetDateRangeEnd(currentDate));
			paramTaskEXT.setTaskparticsuserid(loginUserInfo.getId());// 我参与的
			List<String> taskstatuslist = new ArrayList<String>();
			taskstatuslist.add(EDict.task_status_started.getDict().getDictValue());// 进行中
			taskstatuslist.add(EDict.task_status_overdue.getDict().getDictValue());// 已逾期
			paramTaskEXT.setTaskparticsusertaskstatuslist(taskstatuslist);
			paramTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
			this.setQueryPermisions(paramTaskEXT, loginUserInfo);// 设置数据的查询权限
			paramTaskEXT.setNulldatestate("yes");
			final Integer myNotFinishCount = this.queryListTotalCount(paramTaskEXT);

			map.put("totalcount", (null == myNotFinishCount ? 0 : myNotFinishCount.intValue()));// 今日有几个未完成的任务
		} else {
			map.put("totalcount", countNotRead);// 有几个未读的任务
		}

		map.put("hashnotread", (countNotRead > 0 ? true : false));// 是否有未读的

		return map;
	}

	/**
	 * 该农事任务的参与人已读记录
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午5:53:26
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @param loginUserInfo
	 *            农事任务的参与人
	 */
	public void setTaskparticsToReaded(final TaskEXT taskEXT, final LoginUserInfo loginUserInfo) {

		if ((null == taskEXT) || (null == taskEXT.getTaskparticslist()) || (taskEXT.getTaskparticslist().isEmpty())) {
			return;
		}

		final List<TaskparticsEXT> readedTaskparticsEXTList = new ArrayList<TaskparticsEXT>();

		for (TaskparticsEXT planparticsEXT : taskEXT.getTaskparticslist()) {
			if (ObjectUtil.toString(loginUserInfo.getId()).equals(ObjectUtil.toString(planparticsEXT.getUserid()))) {

				// 已读
				planparticsEXT.setHasreaded((byte) CommonConstantsUtil.YES_OR_NO_YES);
				readedTaskparticsEXTList.add(planparticsEXT);
			}
		}

		if (readedTaskparticsEXTList.size() > 0) {
			mTaskparticsService.asynchronizationUpdate(readedTaskparticsEXTList);
		}
	}

	/**
	 * 该农事任务的抄送人已读记录
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午5:53:26
	 *
	 * @param taskEXT
	 *            农事任务对象
	 * @param loginUserInfo
	 *            农事任务的参与人
	 */
	public void setTaskcopyToReaded(final TaskEXT taskEXT, final LoginUserInfo loginUserInfo) {

		if ((null == taskEXT) || (null == taskEXT.getTaskcopylist()) || (taskEXT.getTaskcopylist().isEmpty())) {
			return;
		}

		final List<TaskcopyEXT> readedTaskcopyEXTList = new ArrayList<TaskcopyEXT>();

		for (TaskcopyEXT planparticsEXT : taskEXT.getTaskcopylist()) {
			if (ObjectUtil.toString(loginUserInfo.getId()).equals(ObjectUtil.toString(planparticsEXT.getUserid()))) {

				// 已读
				planparticsEXT.setHasreaded((byte) CommonConstantsUtil.YES_OR_NO_YES);
				readedTaskcopyEXTList.add(planparticsEXT);
			}
		}

		if (readedTaskcopyEXTList.size() > 0) {
			mTaskcopyService.asynchronizationUpdate(readedTaskcopyEXTList);
		}
	}

	/**
	 * 参与人完成任务时添加“已完成”任务动态
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 上午10:01:28
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer addTaskcommentByFinishTask(final TaskparticsEXT pTaskparticsEXT) throws Exception {
		// ----- 添加农事任务的评论 -----------------------------------------
		TaskcommentEXT tempTaskcommentEXT = new TaskcommentEXT();
		tempTaskcommentEXT.setTaskid(pTaskparticsEXT.getTaskid());
		tempTaskcommentEXT.setUserid(pTaskparticsEXT.getUserid());
		tempTaskcommentEXT.setTitle(LanguageUtil.getLanguageByKey("fwk-farm-task-finish-task"));// 发布任务

		Integer resultAddOrUpdate = mTaskcommentService.add(tempTaskcommentEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Taskcomment failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 设置数据的查询权限
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 上午9:13:43
	 *
	 * @param paramTaskEXT
	 *            查询条件
	 * @param loginUserInfo
	 *            当前操作的用户
	 */
	public void setQueryPermisions(final TaskEXT paramTaskEXT, final LoginUserInfo loginUserInfo) {
		if ((null == paramTaskEXT) || (null == loginUserInfo)) {
			return;
		}

		// 数据的查询权限
		String queryPermisionsSQL = "";

		// 数据权限只能在当前登录的组织下
		queryPermisionsSQL = mFarmCommonService.getQueryPermisionsOnlyLoginOrg(loginUserInfo, " taskparticsuserids ");

		// 数据权限为当前用户所拥有的所有组织和部门
		// queryPermisionsSQL =
		// mFarmCommonService.qetQueryPermisionsAllOrgAndDepartment(loginUserInfo,
		// " taskparticsuserids ");

		// 设置数据的查询权限
		paramTaskEXT.setPermisionssqlwhere(queryPermisionsSQL);
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
	 *            农事任务列表
	 * @return
	 */
	public void asynchronizationUpdate(List<TaskEXT> updateTaskList) {

		// 使用线程执行需要更新的农事任务
		TaskThread taskThread = (this.new TaskThread(this, updateTaskList));

		Thread thread = new Thread(taskThread);
		thread.start();

	}

	/**
	 * 农事任务的线程执行类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 下午12:35:38
	 *
	 */
	private class TaskThread implements Runnable {

		public TaskThread(TaskService taskService, List<TaskEXT> updateTaskList) {
			super();
			this.taskService = taskService;
			this.updateTaskList = updateTaskList;
		}

		private TaskService taskService;

		// 需要更新状态的农事任务
		private List<TaskEXT> updateTaskList;

		@Override
		public void run() {

			if ((null != taskService) && (null != updateTaskList) || (updateTaskList.size() > 0)) {
				for (TaskEXT taskEXT : updateTaskList) {
					try {
						taskService.update(taskEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
