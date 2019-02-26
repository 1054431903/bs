package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.constants.ScheduleType;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.PlanEXTMapper;
import com.fwk.service.farm.entity.FWKSchedule;
import com.fwk.service.farm.entity.FWKScheduleEXT;
import com.fwk.service.farm.entity.PlanEXT;
import com.fwk.service.farm.entity.PlanparcelEXT;
import com.fwk.service.farm.entity.PlanparticsEXT;
import com.fwk.service.farm.entity.PlanuserpermisionsEXT;
import com.fwk.service.farm.entity.PlanwarnEXT;
import com.fwk.service.farm.entity.TaskEXT;

/**
 * 农事管理模块 — 农事计划的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PlanService {

	// 农事管理模块 — 农事计划的数据库处理类
	@Autowired
	private PlanEXTMapper mPlanEXTMapper;

	// 农事管理模块 — 农事计划的参与人的业务逻辑处理对象
	@Autowired
	private PlanparticsService mPlanparticsService;

	// 农事管理模块 — 农事计划的农事计划提醒的业务逻辑处理对象
	@Autowired
	private PlanwarnService mPlanwarnService;

	// 农事管理模块 — 农事计划的地块的业务逻辑处理对象
	@Autowired
	private PlanparcelService mPlanparcelService;

	// 农事记录的计划记录的数据权限的业务逻辑处理对象
	@Autowired
	private PlanuserpermisionsService mPlanuserpermisionsService;

	// 公共业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农事管理模块 — 农事任务管理的业务逻辑处理对象
	@Autowired
	private TaskService mTaskService;

	// 农事管理模块的公共业务逻辑处理对象
	@Autowired
	private FarmCommonService mFarmCommonService;

	/**
	 * 新增农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @param loginUserInfo
	 *            当前操作的用户对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PlanEXT pPlanEXT, final LoginUserInfo loginUserInfo) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		PlanEXT savedPlanEXT = null;// 已保存的地块
		final List<PlanEXT> savedPlanEXTList = new ArrayList<PlanEXT>();// 已保存到数据库的农事计划列表

		// 已保存到数据库的农事计划数量
		final AtomicInteger saveCounter = new AtomicInteger(0);

		// 需要保存的记录数量
		final int needSaveCount = 1;

		// 有几个地块就添加几条农事计划，如果没有地块那就只添加一条农事计划
		do {
			savedPlanEXT = new PlanEXT();

			BeanUtils.copyProperties(pPlanEXT, savedPlanEXT);

			// 将数据添加到数据库
			resultAddOrUpdate = mPlanEXTMapper.add(savedPlanEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Plan failure !");
			} else {
				// 添加到已保存到数据库的农事计划列表中
				savedPlanEXTList.add(savedPlanEXT);
				pPlanEXT.setId(savedPlanEXT.getId());
			}

		} while ((saveCounter.incrementAndGet() < needSaveCount));// do while 结束

		// 农事计划的数据权限对象
		PlanuserpermisionsEXT tempPlanuserpermisionsEXT = null;

		// ============== 保存与农事计划是多对一的关系的其他记录 =======================
		for (PlanEXT planEXT : savedPlanEXTList) {

			// -------- 农事计划的地块列表（农事计划和地块是一对一的关系，一个农事最多对应一个地块）-----------
			if ((null != pPlanEXT.getPlanparcellist()) && (pPlanEXT.getPlanparcellist().size() > 0)) {

				for (PlanparcelEXT planparcelEXT : pPlanEXT.getPlanparcellist()) {
					planparcelEXT.setPlanid(planEXT.getId());// 设置计划的ID
				}

				resultAddOrUpdate = mPlanparcelService.batchAdd(pPlanEXT.getPlanparcellist());

				if ((null == resultAddOrUpdate)
						|| (pPlanEXT.getPlanparcellist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Planparcel failure !");
				}
			} // 添加农事计划的地块结束

			// ----- 农事计划的参与人列表（农事计划与农事参与人是一对多的关系，一个农事可以有多个参与人）------------
			if ((null != pPlanEXT.getPlanparticslist()) && (pPlanEXT.getPlanparticslist().size() > 0)) {

				for (PlanparticsEXT planparticsEXT : pPlanEXT.getPlanparticslist()) {
					planparticsEXT.setPlanid(planEXT.getId());// 设置农事计划的ID
				}

				resultAddOrUpdate = mPlanparticsService.batchAdd(pPlanEXT.getPlanparticslist());

				if ((null == resultAddOrUpdate)
						|| (pPlanEXT.getPlanparticslist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Planpartics failure !");
				}
			} // 添加农事计划的参与人结束

			// ----- 农事计划的任务提醒列表（农事计划与农事计划提醒是一对多的关系，一个农事可以有多个任务提醒）------------
			if ((null != pPlanEXT.getPlanwarnlist()) && (pPlanEXT.getPlanwarnlist().size() > 0)) {

				for (PlanwarnEXT planwarnEXT : pPlanEXT.getPlanwarnlist()) {

					planwarnEXT.setPlanid(planEXT.getId());// 设置农事计划的ID

				}

				resultAddOrUpdate = mPlanwarnService.batchAdd(pPlanEXT.getPlanwarnlist());

				if ((null == resultAddOrUpdate)
						|| (pPlanEXT.getPlanwarnlist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Planwarn failure !");
				}
			} // 添加农事计划的任务提醒结束

			// ----- 添加农事计划的数据权限 ------------------------------
			if ((null == loginUserInfo.getDepartmentsupportidlist())
					|| (loginUserInfo.getDepartmentsupportidlist().isEmpty())) {

				tempPlanuserpermisionsEXT = new PlanuserpermisionsEXT();
				tempPlanuserpermisionsEXT.setPlanid(planEXT.getId());
				tempPlanuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
				tempPlanuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
				tempPlanuserpermisionsEXT.setCreateuserdepartmentid(null);// 部门为空

				resultAddOrUpdate = mPlanuserpermisionsService.add(tempPlanuserpermisionsEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("add Planuserpermisions failure !");
				}

			} else {

				List<PlanuserpermisionsEXT> planuserpermisionsEXTList = new ArrayList<PlanuserpermisionsEXT>();

				for (String departmentsupportid : loginUserInfo.getDepartmentsupportidlist()) {

					tempPlanuserpermisionsEXT = new PlanuserpermisionsEXT();
					tempPlanuserpermisionsEXT.setPlanid(planEXT.getId());
					tempPlanuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
					tempPlanuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
					tempPlanuserpermisionsEXT.setCreateuserdepartmentid(departmentsupportid);

					planuserpermisionsEXTList.add(tempPlanuserpermisionsEXT);
				}

				resultAddOrUpdate = mPlanuserpermisionsService.batchAdd(planuserpermisionsEXTList);

				if ((null == resultAddOrUpdate) || (planuserpermisionsEXTList.size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Planuserpermisions failure !");
				}
			}

		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事计划对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<PlanEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mPlanEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PlanEXT pPlanEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPlanEXTMapper.update(pPlanEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @param originalPlanEXT
	 *            原始的农事计划对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(PlanEXT pPlanEXT, final PlanEXT originalPlanEXT, final Map<String, Object> paramMap)
			throws Exception {

		/* ====== 原来已有的数据执行更新，原来不存在的数据执行添加，原来有的数据现在没有了就执行删除 ==== */

		// ---------- 编辑农事参数与人 ----------------
		this.editPlanpartics(pPlanEXT, originalPlanEXT, paramMap);

		// ---------- 编辑农事地块 ----------------
		this.editPlanparcel(pPlanEXT, originalPlanEXT, paramMap);

		// ---------- 编辑农事计划的提醒 ----------------
		this.editPlanwarn(pPlanEXT, originalPlanEXT, paramMap);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pPlanEXT, originalPlanEXT, paramMap);

		return this.update(originalPlanEXT);

	}

	/**
	 * 删除指定ID的农事计划
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PlanEXT pPlanEXT) throws Exception {
		return mPlanEXTMapper.deletebyid(pPlanEXT);
	}

	/**
	 * 根据主键批量删除农事计划
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
		return mPlanEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public PlanEXT queryById(PlanEXT pPlanEXT) throws Exception {
		PlanEXT paramPlanEXT = new PlanEXT();
		paramPlanEXT.setId(pPlanEXT.getId());

		List<PlanEXT> planEXTList = this.queryList(paramPlanEXT);

		if ((null == planEXTList) || (planEXTList.isEmpty())) {
			return null;
		} else {

			for (PlanEXT planEXT : planEXTList) {

				// --------------- 设置农事计划的地块 ----------------------
				PlanparcelEXT paramPlanparcelEXT = new PlanparcelEXT();
				paramPlanparcelEXT.setPlanid(planEXT.getId());

				List<PlanparcelEXT> planparcellist = mPlanparcelService.queryList(paramPlanparcelEXT);

				planEXT.setPlanparcellist(planparcellist);

				// --------------- 设置农事计划的参与人 ----------------------
				// this.setPlanparticsList(planEXT);// 已经在查询列表的设置过参与人了

				// --------------- 设置农事计划的任务提醒 ----------------------
				PlanwarnEXT paramPlanwarnEXT = new PlanwarnEXT();
				paramPlanwarnEXT.setPlanid(planEXT.getId());

				List<PlanwarnEXT> planwarnlist = mPlanwarnService.queryList(paramPlanwarnEXT);

				planEXT.setPlanwarnlist(planwarnlist);
				
				// 设置计划创建人(发布人)名称
				SupportUserEXT supportUserEXT = new SupportUserEXT();
				supportUserEXT.setId(planEXT.getCreateuserid());
				supportUserEXT = mCommonService.querySupportUserEXTByIdOrSupportid(supportUserEXT);
				if (supportUserEXT != null) {
					planEXT.setCreateuseridname(supportUserEXT.getName());
				}

				break;
			}

			return planEXTList.get(0);
		}

	}

	/**
	 * 设置农事计划的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param planEXT
	 *            农事计划对象
	 * @throws Exception
	 */
	public void setPlanparticsList(final PlanEXT planEXT) throws Exception {

		if (null == planEXT) {
			return;
		}

		PlanparticsEXT paramPlanparticsEXT = new PlanparticsEXT();
		paramPlanparticsEXT.setPlanid(planEXT.getId());

		List<PlanparticsEXT> recordpartuserlist = mPlanparticsService.queryList(paramPlanparticsEXT);

		if ((null == recordpartuserlist) || (recordpartuserlist.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;

		SupportUserEXT paramSupportUserEXT = null;
		for (PlanparticsEXT taskparticsEXT : recordpartuserlist) {
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

		planEXT.setPlanparticsusernames(usernameBuffer.toString());
		planEXT.setPlanparticsuserids(useridBuffer.toString());

		planEXT.setPlanparticslist(recordpartuserlist);

	}

	/**
	 * 设置农事计划的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param planEXT
	 *            农事计划对象
	 * @param supportUserEXTMap
	 *            用户信息
	 * @throws Exception
	 */
	public void setPlanparticsList(final PlanEXT planEXT, final Map<String, SupportUserEXT> supportUserEXTMap)
			throws Exception {

		if ((null == planEXT) || (null == planEXT.getPlanparticslist()) || (planEXT.getPlanparticslist().isEmpty())
				|| (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;
		for (PlanparticsEXT taskparticsEXT : planEXT.getPlanparticslist()) {

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

		planEXT.setPlanparticsusernames(usernameBuffer.toString());
		planEXT.setPlanparticsuserids(useridBuffer.toString());
	}

	/**
	 * 检索农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public List<PlanEXT> queryList(PlanEXT pPlanEXT) throws Exception {

		List<PlanEXT> list = mPlanEXTMapper.queryList(pPlanEXT);

		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		List<Integer> userIdList = new ArrayList<Integer>();
		List<Integer> planIdList = new ArrayList<Integer>();

		for (PlanEXT planEXT : list) {
			planIdList.add(planEXT.getId());
		}

		PlanparticsEXT paramPlanparticsEXT = new PlanparticsEXT();
		paramPlanparticsEXT.setPlanidlist(planIdList);

		List<PlanparticsEXT> planparticsEXTList = mPlanparticsService.queryList(paramPlanparticsEXT);

		for (PlanparticsEXT planparticsEXT : planparticsEXTList) {
			for (PlanEXT planEXT : list) {
				if (planEXT.getId().equals(planparticsEXT.getPlanid())) {
					if (null == planEXT.getPlanparticslist()) {
						planEXT.setPlanparticslist(new ArrayList<PlanparticsEXT>());
					}

					// 添加到农事记录的参与人列表中
					planEXT.getPlanparticslist().add(planparticsEXT);
					break;
				}
			}

			// 参与人的ID
			userIdList.add(planparticsEXT.getUserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (PlanEXT planEXT : list) {
			if (null == planEXT) {
				continue;
			}

			// --------------- 设置农事计划的参与人 ----------------------
			this.setPlanparticsList(planEXT, supportUserEXTMap);
		}

		return list;
	}

	/**
	 * 检索农事计划
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public List<PlanEXT> queryPlanList(PlanEXT pPlanEXT) throws Exception {

		List<PlanEXT> list = mPlanEXTMapper.queryList(pPlanEXT);

		return list;
	}

	/**
	 * 检索农事计划的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPlanEXT
	 *            农事计划对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PlanEXT pPlanEXT) throws Exception {
		return mPlanEXTMapper.queryListTotalCount(pPlanEXT);
	}

	/**
	 * 编辑参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramPlanEXT
	 *            从客户端传过来的农事计划对象
	 * @param originalPlanEXT
	 *            从数据库中检索出来的农事计划对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editPlanpartics(final PlanEXT paramPlanEXT, final PlanEXT originalPlanEXT,
			final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if (null == paramPlanEXT.getPlanparticslist()) {
			paramPlanEXT.setPlanparticslist(new ArrayList<PlanparticsEXT>());
		}

		if (null == originalPlanEXT.getPlanparticslist()) {
			originalPlanEXT.setPlanparticslist(new ArrayList<PlanparticsEXT>());
		}

		// 从客户端传过来的参与人列表
		Iterator<PlanparticsEXT> paramPlanparticsEXTIterator = paramPlanEXT.getPlanparticslist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		PlanparticsEXT paramPlanparticsEXT = null;
		while (paramPlanparticsEXTIterator.hasNext()) {
			paramPlanparticsEXT = paramPlanparticsEXTIterator.next();

			// 设置农事计划的ID，在后续需要添加的时候农事计划的ID才不会为空
			paramPlanparticsEXT.setPlanid(paramPlanEXT.getId());

			for (PlanparticsEXT originalPlanparticsEXT : originalPlanEXT.getPlanparticslist()) {
				if (originalPlanparticsEXT.getUserid().equals(paramPlanparticsEXT.getUserid())) {

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("planparticslist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramPlanparticsEXT, originalPlanparticsEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mPlanparticsService.update(originalPlanparticsEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Planpartics is failure !");
					}

					// 该参与人需要保留
					originalPlanEXT.getPlanparticslist().remove(originalPlanparticsEXT);

					// 当前参与人原来就已经存在，把他移除掉，最终新增不存在的参与人，删除原来已存在并且不需要保留的参与人
					paramPlanparticsEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的参与人
		if (originalPlanEXT.getPlanparticslist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (PlanparticsEXT obj : originalPlanEXT.getPlanparticslist()) {
				idList.add(obj.getId());// 需要刪除掉的参与人
			}

			addOrUpdateRsult = mPlanparticsService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Planpartics failure !");
			}
		}

		// 添加新的参与人（历史的参与人已更新）
		if (paramPlanEXT.getPlanparticslist().size() > 0) {
			addOrUpdateRsult = mPlanparticsService.batchAdd(paramPlanEXT.getPlanparticslist());

			if ((null == addOrUpdateRsult)
					|| (paramPlanEXT.getPlanparticslist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Planpartics failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑地块
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramPlanEXT
	 *            从客户端传过来的农事计划对象
	 * @param originalPlanEXT
	 *            从数据库中检索出来的农事计划对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editPlanparcel(final PlanEXT paramPlanEXT, final PlanEXT originalPlanEXT,
			final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if (null == paramPlanEXT.getPlanparcellist()) {
			paramPlanEXT.setPlanparcellist(new ArrayList<PlanparcelEXT>());
		}

		if (null == originalPlanEXT.getPlanparcellist()) {
			originalPlanEXT.setPlanparcellist(new ArrayList<PlanparcelEXT>());
		}

		// 从客户端传过来的地块列表
		Iterator<PlanparcelEXT> paramPlanparcelEXTIterator = paramPlanEXT.getPlanparcellist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		PlanparcelEXT paramPlanparcelEXT = null;
		while (paramPlanparcelEXTIterator.hasNext()) {
			paramPlanparcelEXT = paramPlanparcelEXTIterator.next();

			// 设置农事计划的ID，在后续需要添加的时候农事计划的ID才不会为空
			paramPlanparcelEXT.setPlanid(paramPlanEXT.getId());

			for (PlanparcelEXT originalPlanparcelEXT : originalPlanEXT.getPlanparcellist()) {
				if (originalPlanparcelEXT.getParcelid().equals(paramPlanparcelEXT.getParcelid())) {

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("planparcellist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramPlanparcelEXT, originalPlanparcelEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mPlanparcelService.update(originalPlanparcelEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Planparcel is failure !");
					}

					// 该地块需要保留
					originalPlanEXT.getPlanparcellist().remove(originalPlanparcelEXT);

					// 当前地块原来就已经存在，把他移除掉，最终新增不存在的地块，删除原来已存在并且不需要保留的地块
					paramPlanparcelEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的地块
		if (originalPlanEXT.getPlanparcellist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (PlanparcelEXT obj : originalPlanEXT.getPlanparcellist()) {
				idList.add(obj.getId());// 需要刪除掉的地块
			}

			addOrUpdateRsult = mPlanparcelService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Planparcel failure !");
			}
		}

		// 添加新的地块（历史的地块已更新）
		if (paramPlanEXT.getPlanparcellist().size() > 0) {
			addOrUpdateRsult = mPlanparcelService.batchAdd(paramPlanEXT.getPlanparcellist());

			if ((null == addOrUpdateRsult)
					|| (paramPlanEXT.getPlanparcellist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Planparcel failure !");
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
	 * @param paramPlanEXT
	 *            从客户端传过来的农事计划对象
	 * @param originalPlanEXT
	 *            从数据库中检索出来的农事计划对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editPlanwarn(final PlanEXT paramPlanEXT, final PlanEXT originalPlanEXT,
			final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if (null == paramPlanEXT.getPlanwarnlist()) {
			paramPlanEXT.setPlanwarnlist(new ArrayList<PlanwarnEXT>());
		}

		if (null == originalPlanEXT.getPlanwarnlist()) {
			originalPlanEXT.setPlanwarnlist(new ArrayList<PlanwarnEXT>());
		}

		// 从客户端传过来的任务提醒列表
		Iterator<PlanwarnEXT> paramPlanwarnEXTIterator = paramPlanEXT.getPlanwarnlist().iterator();

		final AtomicInteger index = new AtomicInteger(0);

		PlanwarnEXT paramPlanwarnEXT = null;
		while (paramPlanwarnEXTIterator.hasNext()) {
			paramPlanwarnEXT = paramPlanwarnEXTIterator.next();

			// 设置农事计划的ID，在后续需要添加的时候农事计划的ID才不会为空
			paramPlanwarnEXT.setPlanid(paramPlanEXT.getId());

			for (PlanwarnEXT originalPlanwarnEXT : originalPlanEXT.getPlanwarnlist()) {
				if (originalPlanwarnEXT.getPlanid().equals(paramPlanwarnEXT.getPlanid())) {

					// 设置提醒时间（提醒时间是由后台根据任务的结束时间和任务结束前多长时间计算得出的）
					originalPlanwarnEXT.setWarntime(paramPlanwarnEXT.getWarntime());

					JSONArray jsonArray = JSONArray.parseArray(paramMap.get("planwarnlist").toString());

					// 将客户端的值更新到数据库中的对象上
					HTMLUtil.updateObject(paramPlanwarnEXT, originalPlanwarnEXT,
							jsonArray.getJSONObject(index.getAndIncrement()));

					// 将数据更新到数据库
					addOrUpdateRsult = mPlanwarnService.update(originalPlanwarnEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new ELException("update Planwarn is failure !");
					}

					// 该任务提醒需要保留
					originalPlanEXT.getPlanwarnlist().remove(originalPlanwarnEXT);

					// 当前任务提醒原来就已经存在，把他移除掉，最终新增不存在的任务提醒，删除原来已存在并且不需要保留的任务提醒
					paramPlanwarnEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的任务提醒
		if (originalPlanEXT.getPlanwarnlist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (PlanwarnEXT obj : originalPlanEXT.getPlanwarnlist()) {
				idList.add(obj.getId());// 需要刪除掉的任务提醒
			}

			addOrUpdateRsult = mPlanwarnService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Planwarn failure !");
			}
		}

		// 添加新的任务提醒（历史的任务提醒已更新）
		if (paramPlanEXT.getPlanwarnlist().size() > 0) {
			addOrUpdateRsult = mPlanwarnService.batchAdd(paramPlanEXT.getPlanwarnlist());

			if ((null == addOrUpdateRsult) || (paramPlanEXT.getPlanwarnlist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Planwarn failure !");
			}
		}

		return true;
	}

	/**
	 * 设置农事计划的提醒时间
	 * 
	 * @author wangjunwen
	 * @date 2018年2月8日 下午5:20:22
	 *
	 * @param planwarnEXT
	 *            农事计划提醒对象
	 * @param planEXT
	 *            农事计划对象
	 */
	public void setPlanwarntime(final PlanwarnEXT planwarnEXT, final PlanEXT planEXT) {

		// 获取农事事务提醒的时间单位
		EDict farmTimeUnit = EDict.getEDictByType(EDictType.farm_time_unit, planwarnEXT.getTimeunit());

		long beforeTimes = 0;// 结束时间前多少毫秒

		if (EDict.farm_time_unit_minutes.equals(farmTimeUnit)) {// 结束时间钱多少分钟提醒

			beforeTimes = (planwarnEXT.getWarntimes() * 60 * 1000);

		} else if (EDict.farm_time_unit_hour.equals(farmTimeUnit)) {// 结束时间钱多少小时提醒

			beforeTimes = (planwarnEXT.getWarntimes() * 60 * 60 * 1000);

		} else if (EDict.farm_time_unit_day.equals(farmTimeUnit)) {// 结束时间钱多少天提醒

			beforeTimes = (planwarnEXT.getWarntimes() * 24 * 60 * 60 * 1000);

		}

		// 最终提醒的时间（在计划开始前多长时间提醒）
		Date warntime = new Date((planEXT.getBegintime().getTime() - beforeTimes));

		planwarnEXT.setWarntime(warntime);// 设置提醒的时间
	}

	/**
	 * 将农事计划转化为日程
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午4:35:54
	 *
	 * @param planEXTList
	 *            农事计划列表
	 * @return
	 */
	public List<FWKSchedule> convertToFWKScheduleFromPlanEXT(List<PlanEXT> planEXTList) {
		List<FWKSchedule> list = new ArrayList<FWKSchedule>();

		if ((null == planEXTList) || (planEXTList.isEmpty())) {
			return list;
		}

		FWKSchedule fwkSchedule = null;
		for (PlanEXT planEXT : planEXTList) {

			fwkSchedule = new FWKSchedule();
			fwkSchedule.setScheduletype(ScheduleType.plan.type);// 计划（日程）

			fwkSchedule.setId(planEXT.getId());// 农事任务的主键ID
			fwkSchedule.setContent(planEXT.getDescribe());// 任务内容
			fwkSchedule.setBegintime(planEXT.getBegintime());// 开始时间
			fwkSchedule.setEndtime(planEXT.getEndtime());// 截止时间
			fwkSchedule.setCreattime(planEXT.getCreattime());// 添加时间
			fwkSchedule.setDelstate(planEXT.getDelstate());// 删除状态
			fwkSchedule.setCreateuserid(planEXT.getCreateuserid());// 创建人
			fwkSchedule.setEdittime(planEXT.getEdittime());// 修改时间

			list.add(fwkSchedule);

		}

		return list;
	}

	/**
	 * 将农事任务转化为日程
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午4:38:14
	 *
	 * @param taskEXTList
	 *            农事任务列表
	 * @return
	 */
	public List<FWKSchedule> convertToFWKScheduleFromTaskEXT(List<TaskEXT> taskEXTList) {
		List<FWKSchedule> list = new ArrayList<FWKSchedule>();

		if ((null == taskEXTList) || (taskEXTList.isEmpty())) {
			return list;
		}

		EDict taskType = null;

		FWKSchedule fwkSchedule = null;
		for (TaskEXT taskEXT : taskEXTList) {

			fwkSchedule = new FWKSchedule();

			// 获取任务类别
			taskType = EDict.getEDictByType(EDictType.task_type, taskEXT.getType());

			if (EDict.task_type_agricultural_task.equals(taskType)) {

				fwkSchedule.setScheduletype(ScheduleType.task.type);// 任务（日程）

			} else if (EDict.task_type_meeting_invitation.equals(taskType)) {

				fwkSchedule.setScheduletype(ScheduleType.metting.type);// 约会（日程）

			}

			if (null == fwkSchedule.getScheduletype()) {
				continue;
			}

			fwkSchedule.setId(taskEXT.getId());// 农事任务的主键ID
			fwkSchedule.setContent(taskEXT.getDescribe());// 任务内容
			fwkSchedule.setBegintime(taskEXT.getBegintime());// 开始时间
			fwkSchedule.setEndtime(taskEXT.getEndtime());// 截止时间
			fwkSchedule.setCreattime(taskEXT.getCreattime());// 添加时间
			fwkSchedule.setDelstate(taskEXT.getDelstate());// 删除状态
			fwkSchedule.setCreateuserid(taskEXT.getCreateuserid());// 创建人
			fwkSchedule.setEdittime(taskEXT.getEdittime());// 修改时间

			list.add(fwkSchedule);

		}

		return list;
	}

	/**
	 * 查询日程
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午5:13:56
	 *
	 * @param paramPlanEXT
	 * @param loginUserInfo
	 * @return
	 * @throws Exception
	 */
	public FWKScheduleEXT queryFWKScheduleEXT(final PlanEXT paramPlanEXT, final LoginUserInfo loginUserInfo)
			throws Exception {

		FWKScheduleEXT fwkScheduleEXT = new FWKScheduleEXT();

		// ============= 设置我参与的日程 ===================================
		// ------------------- 计划 ----------------------------------
		PlanEXT mySchedulePlanEXT = new PlanEXT();
		BeanUtils.copyProperties(paramPlanEXT, mySchedulePlanEXT);
		mySchedulePlanEXT.setPlanparticsuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的计划
		this.setQueryPermisions(paramPlanEXT, loginUserInfo);// 设置数据的查询权限
		mySchedulePlanEXT.setOrderBySQL(" endtime ASC ");// 按截止时间升序排序

		mySchedulePlanEXT.setUserpermisionscreateuserorgid(loginUserInfo.getLoginorgid());

		List<PlanEXT> mySchedulePlanEXTList = this.queryPlanList(mySchedulePlanEXT);
		List<FWKSchedule> mySchedulePlanList = this.convertToFWKScheduleFromPlanEXT(mySchedulePlanEXTList);
		fwkScheduleEXT.getMyschedulelist().addAll(mySchedulePlanList);

		// ------------------- 任务 ----------------------------------
		TaskEXT myScheduleTaskEXT = new TaskEXT();
		BeanUtils.copyProperties(paramPlanEXT, myScheduleTaskEXT);
		// 我参与的任务
		myScheduleTaskEXT.setTaskparticsuserids(ObjectUtil.toString(loginUserInfo.getId()));
		// 设置数据的查询权限
		mTaskService.setQueryPermisions(myScheduleTaskEXT, loginUserInfo);
		// 首先按任务状态升序排序，再按截止时间升序排序
		myScheduleTaskEXT.setOrderBySQL(" defaultsortno ASC, endtime ASC ");

		List<TaskEXT> myScheduleTaskEXTList = mTaskService.queryTaskList(myScheduleTaskEXT);
		List<FWKSchedule> myScheduleTaskList = this.convertToFWKScheduleFromTaskEXT(myScheduleTaskEXTList);
		fwkScheduleEXT.getMyschedulelist().addAll(myScheduleTaskList);

		// 对我参与的日程排序
		// Collections.sort(fwkScheduleEXT.getMyschedulelist());//计划在前，任务在后，无需排序

		// ============= 设置我给别人安排的日程 ===================================
		// ------------------- 计划 ----------------------------------
		PlanEXT scheduleForOthersPlanEXT = new PlanEXT();
		BeanUtils.copyProperties(paramPlanEXT, scheduleForOthersPlanEXT);
		scheduleForOthersPlanEXT.setUserpermisionscreateuserid(ObjectUtil.toString(loginUserInfo.getId()));// 我给别人安排的计划
		scheduleForOthersPlanEXT.setCreateuserid(loginUserInfo.getId());// 我给别人安排的计划(即我创建的计划)
		this.setQueryPermisions(paramPlanEXT, loginUserInfo);// 设置数据的查询权限
		scheduleForOthersPlanEXT.setOrderBySQL(" endtime ASC ");// 按截止时间升序排序

		List<PlanEXT> scheduleForOthersPlanEXTList = this.queryPlanList(scheduleForOthersPlanEXT);
		List<FWKSchedule> scheduleForOthersPlanList = this
				.convertToFWKScheduleFromPlanEXT(scheduleForOthersPlanEXTList);
		fwkScheduleEXT.getScheduleforotherslist().addAll(scheduleForOthersPlanList);

		// ------------------- 任务 ----------------------------------
		TaskEXT scheduleForOthersTaskEXT = new TaskEXT();
		BeanUtils.copyProperties(paramPlanEXT, scheduleForOthersTaskEXT);
		// 我给别人安排的任务
		scheduleForOthersTaskEXT.setUserpermisionscreateuserid(ObjectUtil.toString(loginUserInfo.getId()));
		// 我给别人安排的任务(即我创建的任务)
		scheduleForOthersPlanEXT.setCreateuserid(loginUserInfo.getId());
		// 设置数据的查询权限
		mTaskService.setQueryPermisions(scheduleForOthersTaskEXT, loginUserInfo);
		// 首先按任务状态升序排序，再按截止时间升序排序
		scheduleForOthersTaskEXT.setOrderBySQL(" defaultsortno ASC, endtime ASC ");

		List<TaskEXT> scheduleForOthersTaskEXTList = mTaskService.queryTaskList(scheduleForOthersTaskEXT);
		List<FWKSchedule> scheduleForOthersTaskList = this
				.convertToFWKScheduleFromTaskEXT(scheduleForOthersTaskEXTList);
		fwkScheduleEXT.getScheduleforotherslist().addAll(scheduleForOthersTaskList);

		// 对我给别人安排的日程排序
		Collections.sort(fwkScheduleEXT.getScheduleforotherslist());

		return fwkScheduleEXT;
	}

	/**
	 * 查询出在首页上显示的未读的日程数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:18:30
	 *
	 * @param loginUserInfo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCountNotReadScheduleToHomePage(final LoginUserInfo loginUserInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// ------------------- 计划 ----------------------------------
		// 我参与的未读的计划
		PlanEXT mySchedulePlanEXT = new PlanEXT();
		mySchedulePlanEXT.setPlanparticsuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的计划
		mySchedulePlanEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		mySchedulePlanEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		this.setQueryPermisions(mySchedulePlanEXT, loginUserInfo);// 设置数据的查询权限

		final Integer mySchedulePlanEXTCount = this.queryListTotalCount(mySchedulePlanEXT);

		// ------------------- 任务 ----------------------------------
		// 我参与的未读的任务
		TaskEXT myScheduleTaskEXT = new TaskEXT();
		myScheduleTaskEXT.setTaskparticsuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的任务
		myScheduleTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		myScheduleTaskEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		mTaskService.setQueryPermisions(myScheduleTaskEXT, loginUserInfo);// 设置数据的查询权限

		final Integer myScheduleTaskEXTCount = mTaskService.queryListTotalCount(myScheduleTaskEXT);

		// 抄送给我的未读的任务
		TaskEXT copyMeScheduleTaskEXT = new TaskEXT();
		copyMeScheduleTaskEXT.setTaskcopyuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的任务
		copyMeScheduleTaskEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		copyMeScheduleTaskEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		mTaskService.setQueryPermisions(copyMeScheduleTaskEXT, loginUserInfo);// 设置数据的查询权限

		final Integer copyMeScheduleTaskEXTCount = mTaskService.queryListTotalCount(copyMeScheduleTaskEXT);

		// 统计出我的未读的日程的记录数量
		final Integer countNotRead = ((null == mySchedulePlanEXTCount ? 0 : mySchedulePlanEXTCount)
				+ (null == myScheduleTaskEXTCount ? 0 : myScheduleTaskEXTCount)
				+ (null == copyMeScheduleTaskEXTCount ? 0 : copyMeScheduleTaskEXTCount));

		// 查询出今日有几个日程
		PlanEXT paramPlanEXT = new PlanEXT();
		Date nowDate = mCommonService.queryCurrentDate();// 获取当前时间
		String currentDate = DateTimeUtil.format(nowDate, DateTimeUtil.DEFAULT_DATE_FORMAT);
		paramPlanEXT.setBegintimestr(DateTimeUtil.resetDateRangeStart(currentDate));
		paramPlanEXT.setBegintime(DateTimeUtil.parse(paramPlanEXT.getBegintimestr()));
		paramPlanEXT.setEndtimestr(DateTimeUtil.resetDateRangeEnd(currentDate));
		paramPlanEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);

		// 查询出今天的日程
		FWKScheduleEXT fwkScheduleEXT = this.queryFWKScheduleEXT(paramPlanEXT, loginUserInfo);

		int todayCount = 0;

		if ((null != fwkScheduleEXT)) {
			// 我的日程数量
			todayCount += fwkScheduleEXT.getMyschedulelist().size();
			// 我给被人安排的日程数量
			// todayCount += fwkScheduleEXT.getScheduleforotherslist().size();
		}

		map.put("totalcount", todayCount);// 今天又几个日程
		map.put("hashnotread", (countNotRead > 0 ? true : false));// 是否有未读的

		return map;
	}

	/**
	 * 该农事计划的参与人已读计划
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午5:53:26
	 *
	 * @param planEXT
	 *            农事计划对象
	 * @param loginUserInfo
	 *            农事计划的参与人
	 */
	public void setPlanparticsToReaded(final PlanEXT planEXT, final LoginUserInfo loginUserInfo) {

		if ((null == planEXT) || (null == planEXT.getPlanparticslist()) || (planEXT.getPlanparticslist().isEmpty())) {
			return;
		}

		final List<PlanparticsEXT> readedPlanparticsEXTList = new ArrayList<PlanparticsEXT>();

		for (PlanparticsEXT planparticsEXT : planEXT.getPlanparticslist()) {
			if (ObjectUtil.toString(loginUserInfo.getId()).equals(ObjectUtil.toString(planparticsEXT.getUserid()))) {

				// 已读
				planparticsEXT.setHasreaded((byte) CommonConstantsUtil.YES_OR_NO_YES);
				readedPlanparticsEXTList.add(planparticsEXT);
			}
		}

		if (readedPlanparticsEXTList.size() > 0) {
			mPlanparticsService.asynchronizationUpdate(readedPlanparticsEXTList);
		}
	}

	/**
	 * 设置数据的查询权限
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 上午9:13:43
	 *
	 * @param paramPlanEXT
	 *            查询条件
	 * @param loginUserInfo
	 *            当前操作的用户
	 */
	public void setQueryPermisions(final PlanEXT paramPlanEXT, final LoginUserInfo loginUserInfo) {

		if ((null == paramPlanEXT) || (null == loginUserInfo)) {
			return;
		}

		// 数据的查询权限
		String queryPermisionsSQL = "";

		// 数据权限只能在当前登录的组织下
		queryPermisionsSQL = mFarmCommonService.getQueryPermisionsOnlyLoginOrg(loginUserInfo, " planparticsuserids ");

		// 数据权限为当前用户所拥有的所有组织和部门
		// queryPermisionsSQL =
		// mFarmCommonService.qetQueryPermisionsAllOrgAndDepartment(loginUserInfo,
		// " planparticsuserids ");

		// 设置数据的查询权限
		paramPlanEXT.setPermisionssqlwhere(queryPermisionsSQL);

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
	 *            农事计划列表
	 * @return
	 */
	public void asynchronizationUpdate(List<PlanEXT> updatePlanList) {

		// 使用线程执行需要更新的农事计划
		PlanThread planThread = (this.new PlanThread(this, updatePlanList));

		Thread thread = new Thread(planThread);
		thread.start();

	}

	/**
	 * 农事计划的线程执行类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 下午12:35:38
	 *
	 */
	private class PlanThread implements Runnable {

		public PlanThread(PlanService planService, List<PlanEXT> updatePlanList) {
			super();
			this.planService = planService;
			this.updatePlanList = updatePlanList;
		}

		private PlanService planService;

		// 需要更新状态的农事计划
		private List<PlanEXT> updatePlanList;

		@Override
		public void run() {

			if ((null != planService) && (null != updatePlanList) || (updatePlanList.size() > 0)) {
				for (PlanEXT planEXT : updatePlanList) {
					try {
						planService.update(planEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
