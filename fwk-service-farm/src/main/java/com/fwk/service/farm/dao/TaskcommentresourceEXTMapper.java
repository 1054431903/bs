package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFtaskcommentresourceMapper;
import com.fwk.service.farm.entity.TaskcommentresourceEXT;

/**
 * 农事管理模块 — 农事任务评论的资源的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface TaskcommentresourceEXTMapper extends TFtaskcommentresourceMapper {

	/**
	 * 新增农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	Integer add(TaskcommentresourceEXT pTaskcommentresourceEXT) throws Exception;

	/**
	 * 批量新增农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事任务评论的资源对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<TaskcommentresourceEXT> list) throws Exception;

	/**
	 * 更新农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	Integer update(TaskcommentresourceEXT pTaskcommentresourceEXTT) throws Exception;

	/**
	 * 删除指定ID的农事任务评论的资源
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(TaskcommentresourceEXT pTaskcommentresourceEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事任务评论的资源
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
	 * 检索农事任务评论的资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	List<TaskcommentresourceEXT> queryList(TaskcommentresourceEXT pTaskcommentresourceEXTT) throws Exception;

	/**
	 * 检索农事任务评论的资源的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pTaskcommentresourceEXT
	 *            农事任务评论的资源对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(TaskcommentresourceEXT pTaskcommentresourceEXTT) throws Exception;

}
