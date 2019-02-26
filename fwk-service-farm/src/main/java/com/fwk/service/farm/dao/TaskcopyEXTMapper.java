package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFtaskcopyMapper;
import com.fwk.service.farm.entity.TaskcopyEXT;

/**
 * 农事管理模块 — 农事任务的抄送人的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface TaskcopyEXTMapper extends TFtaskcopyMapper {

	/**
	 * 新增农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	Integer add(TaskcopyEXT pTaskcopyEXT) throws Exception;

	/**
	 * 批量新增农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务的抄送人对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<TaskcopyEXT> list) throws Exception;

	/**
	 * 更新农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	Integer update(TaskcopyEXT pTaskcopyEXT) throws Exception;

	/**
	 * 删除指定ID的农事任务的抄送人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(TaskcopyEXT pTaskcopyEXT) throws Exception;

	/**
	 * 根据主键批量删除农事任务的抄送人
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
	 * 检索农事任务的抄送人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	List<TaskcopyEXT> queryList(TaskcopyEXT pTaskcopyEXT) throws Exception;

	/**
	 * 检索农事任务的抄送人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(TaskcopyEXT pTaskcopyEXT) throws Exception;

	/**
	 * 清空回收站
	 * 
	 * @author wangjunwen
	 * @date 2018年3月5日 下午4:42:52
	 *
	 * @param pTaskcopyEXT
	 *            农事任务的抄送人对象
	 * @return
	 * @throws Exception
	 */
	Integer deleteAllFromTrash(TaskcopyEXT pTaskcopyEXT) throws Exception;

}
