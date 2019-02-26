package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFtaskparticsMapper;
import com.fwk.service.farm.entity.TaskparticsEXT;

/**
 * 农事管理模块 — 农事任务的参与人的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface TaskparticsEXTMapper extends TFtaskparticsMapper {

	/**
	 * 新增农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer add(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 批量新增农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的参与人对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<TaskparticsEXT> list) throws Exception;

	/**
	 * 更新农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer update(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 删除指定ID的农事任务的参与人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 根据主键批量删除农事任务的参与人
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
	 * 检索农事任务的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	List<TaskparticsEXT> queryList(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 检索农事任务的参与人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer deleteAllFromTrash(TaskparticsEXT pTaskparticsEXT) throws Exception;

	/**
	 * 保存到农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年3月6日 下午3:18:09
	 *
	 * @param pTaskparticsEXT
	 *            农事任务的参与人对象
	 * @return
	 * @throws Exception
	 */
	Integer saveToRecord(TaskparticsEXT pTaskparticsEXT) throws Exception;

}