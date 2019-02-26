package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFtaskMapper;
import com.fwk.service.farm.entity.TaskEXT;

/**
 * 农事管理模块 — 农事任务的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface TaskEXTMapper extends TFtaskMapper {

	/**
	 * 新增农事任务
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	Integer add(TaskEXT pTaskEXT) throws Exception;

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
	Integer batchAdd(List<TaskEXT> list) throws Exception;

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
	Integer update(TaskEXT pTaskEXT) throws Exception;

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
	Integer deletebyid(TaskEXT pTaskEXT) throws Exception;

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
	Integer batchdeletebyids(@Param(value = "idList") List<Integer> idList) throws Exception;

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
	List<TaskEXT> queryList(TaskEXT pTaskEXT) throws Exception;

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
	Integer queryListTotalCount(TaskEXT pTaskEXT) throws Exception;

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	Integer deleteAllFromTrash(TaskEXT pTaskEXT) throws Exception;

	/**
	 * 从回收站中恢复
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskEXT
	 *            农事任务对象
	 * @return
	 * @throws Exception
	 */
	Integer revertFromTrash(TaskEXT pTaskEXT) throws Exception;

}
