package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFplanparcelMapper;
import com.fwk.service.farm.entity.PlanparcelEXT;

/**
 * 农事管理模块 — 农事计划的地块的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface PlanparcelEXTMapper extends TFplanparcelMapper {

	/**
	 * 新增农事计划的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPlanparcelEXT
	 *            农事计划的地块对象
	 * @return
	 * @throws Exception
	 */
	Integer add(PlanparcelEXT pPlanparcelEXT) throws Exception;

	/**
	 * 批量新增农事计划的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事计划的地块对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<PlanparcelEXT> list) throws Exception;

	/**
	 * 更新农事计划的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPlanparcelEXT
	 *            农事计划的地块对象
	 * @return
	 * @throws Exception
	 */
	Integer update(PlanparcelEXT pPlanparcelEXTT) throws Exception;

	/**
	 * 删除指定ID的农事计划的地块
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPlanparcelEXT
	 *            农事计划的地块对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(PlanparcelEXT pPlanparcelEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事计划的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 下午3:34:49
	 *
	 * @param idList
	 *            业务主键列表
	 * @return
	 * @throws Exception
	 */
	Integer batchdeletebyids(@Param(value = "idList") List<Integer> idList) throws Exception;

	/**
	 * 检索农事计划的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPlanparcelEXT
	 *            农事计划的地块对象
	 * @return
	 * @throws Exception
	 */
	List<PlanparcelEXT> queryList(PlanparcelEXT pPlanparcelEXTT) throws Exception;

	/**
	 * 检索农事计划的地块的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPlanparcelEXT
	 *            农事计划的地块对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(PlanparcelEXT pPlanparcelEXTT) throws Exception;

}
