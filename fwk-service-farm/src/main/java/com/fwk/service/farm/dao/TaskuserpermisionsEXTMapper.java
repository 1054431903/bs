package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFplanparcelMapper;
import com.fwk.service.farm.entity.TaskuserpermisionsEXT;

/**
 * 农事管理模块 — 农事任务的数据权限的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface TaskuserpermisionsEXTMapper extends TFplanparcelMapper {

	/**
	 * 新增农事任务的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskuserpermisionsEXT
	 *            农事任务的数据权限对象
	 * @return
	 * @throws Exception
	 */
	Integer add(TaskuserpermisionsEXT pTaskuserpermisionsEXT) throws Exception;

	/**
	 * 批量新增农事任务的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的数据权限对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<TaskuserpermisionsEXT> list) throws Exception;

	/**
	 * 更新农事任务的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskuserpermisionsEXT
	 *            农事任务的数据权限对象
	 * @return
	 * @throws Exception
	 */
	Integer update(TaskuserpermisionsEXT pTaskuserpermisionsEXTT) throws Exception;

	/**
	 * 删除指定ID的农事任务的数据权限
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskuserpermisionsEXT
	 *            农事任务的数据权限对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(TaskuserpermisionsEXT pTaskuserpermisionsEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事任务的数据权限
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
	 * 检索农事任务的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskuserpermisionsEXT
	 *            农事任务的数据权限对象
	 * @return
	 * @throws Exception
	 */
	List<TaskuserpermisionsEXT> queryList(TaskuserpermisionsEXT pTaskuserpermisionsEXTT) throws Exception;

	/**
	 * 检索农事任务的数据权限的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskuserpermisionsEXT
	 *            农事任务的数据权限对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(TaskuserpermisionsEXT pTaskuserpermisionsEXTT) throws Exception;

}
