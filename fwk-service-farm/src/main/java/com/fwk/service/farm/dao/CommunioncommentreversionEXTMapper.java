package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.service.farm.entity.CommunioncommentreversionEXT;

/**
 * 农事管理模块 — 农事交流的评论的回复的的回复的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface CommunioncommentreversionEXTMapper {

	/**
	 * 新增农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	Integer add(CommunioncommentreversionEXT pCommunioncommentreversionEXT) throws Exception;

	/**
	 * 批量新增农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的评论的回复的对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<CommunioncommentreversionEXT> list) throws Exception;

	/**
	 * 更新农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	Integer update(CommunioncommentreversionEXT pCommunioncommentreversionEXTT) throws Exception;

	/**
	 * 删除指定ID的农事交流的评论的回复
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(CommunioncommentreversionEXT pCommunioncommentreversionEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事交流的评论的回复
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
	 * 检索农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	List<CommunioncommentreversionEXT> queryList(CommunioncommentreversionEXT pCommunioncommentreversionEXTT)
			throws Exception;

	/**
	 * 检索农事交流的评论的回复的的回复的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(CommunioncommentreversionEXT pCommunioncommentreversionEXTT) throws Exception;

	/**
	 * 增加或者减少农事交流的评论的回复的回复数量（正数为增加、负数为减少）
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:54:57
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	Integer increaseOrDecreaseReversioncount(CommunioncommentreversionEXT pCommunioncommentreversionEXT)
			throws Exception;

}
