package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFplanparticsMapper;
import com.fwk.service.farm.entity.PlanparticsEXT;

/**
 * 农事管理模块 — 农事计划的参与人的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface PlanparticsEXTMapper extends TFplanparticsMapper {

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
	Integer add(PlanparticsEXT pPlanparticsEXT) throws Exception;

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
	Integer batchAdd(List<PlanparticsEXT> list) throws Exception;

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
	Integer update(PlanparticsEXT pPlanparticsEXTT) throws Exception;

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
	Integer deletebyid(PlanparticsEXT pPlanparticsEXTT) throws Exception;

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
	Integer batchdeletebyids(@Param(value = "idList") List<Integer> idList) throws Exception;

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
	List<PlanparticsEXT> queryList(PlanparticsEXT pPlanparticsEXTT) throws Exception;

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
	Integer queryListTotalCount(PlanparticsEXT pPlanparticsEXTT) throws Exception;

}
