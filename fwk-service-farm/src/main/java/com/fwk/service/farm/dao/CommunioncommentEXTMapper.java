package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.service.farm.entity.CommunioncommentEXT;

/**
 * 农事管理模块 — 农事交流的评论的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface CommunioncommentEXTMapper {

	/**
	 * 新增农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	Integer add(CommunioncommentEXT pCommunioncommentEXT) throws Exception;

	/**
	 * 批量新增农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的评论对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<CommunioncommentEXT> list) throws Exception;

	/**
	 * 更新农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	Integer update(CommunioncommentEXT pCommunioncommentEXTT) throws Exception;

	/**
	 * 删除指定ID的农事交流的评论
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(CommunioncommentEXT pCommunioncommentEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事交流的评论
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
	 * 检索农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	List<CommunioncommentEXT> queryList(CommunioncommentEXT pCommunioncommentEXTT) throws Exception;

	/**
	 * 检索农事交流的评论的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(CommunioncommentEXT pCommunioncommentEXTT) throws Exception;

	/**
	 * 增加或者减少农事交流的评论的评论数量（正数为增加、负数为减少）
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:54:57
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	Integer increaseOrDecreaseCommentcount(CommunioncommentEXT pCommunioncommentEXT) throws Exception;

}
