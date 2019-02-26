package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.PlanwarnEXTMapper;
import com.fwk.service.farm.entity.PlanwarnEXT;

/**
 * 农事管理模块 — 农事计划的提醒方式的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PlanwarnService {

	// 农事管理模块 — 农事计划的提醒方式的数据库处理类
	@Autowired
	private PlanwarnEXTMapper mPlanwarnEXTMapper;

	/**
	 * 新增农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PlanwarnEXT pPlanwarnEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mPlanwarnEXTMapper.add(pPlanwarnEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事计划的提醒方式对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<PlanwarnEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mPlanwarnEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordpartuser failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PlanwarnEXT pPlanwarnEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPlanwarnEXTMapper.update(pPlanwarnEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(PlanwarnEXT pPlanwarnEXT, final Map<String, Object> paramMap) throws Exception {

		final PlanwarnEXT originalPlanwarnEXT = this.queryById(pPlanwarnEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pPlanwarnEXT, originalPlanwarnEXT, paramMap);

		return this.update(originalPlanwarnEXT);

	}

	/**
	 * 删除指定ID的农事计划的提醒方式
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PlanwarnEXT pPlanwarnEXT) throws Exception {
		return mPlanwarnEXTMapper.deletebyid(pPlanwarnEXT);
	}

	/**
	 * 根据主键批量删除农事计划的提醒方式
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
		return mPlanwarnEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	public PlanwarnEXT queryById(PlanwarnEXT pPlanwarnEXT) throws Exception {
		PlanwarnEXT paramPlanwarnEXT = new PlanwarnEXT();
		paramPlanwarnEXT.setId(pPlanwarnEXT.getId());

		List<PlanwarnEXT> pPlanwarnEXTList = this.queryList(paramPlanwarnEXT);

		return ((null != pPlanwarnEXTList) && (pPlanwarnEXTList.size() > 0) ? pPlanwarnEXTList.get(0) : null);
	}

	/**
	 * 检索农事计划的提醒方式
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	public List<PlanwarnEXT> queryList(PlanwarnEXT pPlanwarnEXT) throws Exception {
		return mPlanwarnEXTMapper.queryList(pPlanwarnEXT);
	}

	/**
	 * 检索农事计划的提醒方式的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPlanwarnEXT
	 *            农事计划的提醒方式对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PlanwarnEXT pPlanwarnEXT) throws Exception {
		return mPlanwarnEXTMapper.queryListTotalCount(pPlanwarnEXT);
	}

}