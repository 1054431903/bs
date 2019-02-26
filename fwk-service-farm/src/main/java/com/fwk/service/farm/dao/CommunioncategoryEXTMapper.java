package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.service.farm.entity.CommunioncategoryEXT;

/**
 * 农事管理模块 — 农事交流的品类的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface CommunioncategoryEXTMapper {

	/**
	 * 新增农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	Integer add(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception;

	/**
	 * 批量新增农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的品类对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<CommunioncategoryEXT> list) throws Exception;

	/**
	 * 更新农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	Integer update(CommunioncategoryEXT pCommunioncategoryEXTT) throws Exception;

	/**
	 * 删除指定ID的农事交流的品类
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(CommunioncategoryEXT pCommunioncategoryEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事交流的品类
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
	 * 检索农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	List<CommunioncategoryEXT> queryList(CommunioncategoryEXT pCommunioncategoryEXTT) throws Exception;

	/**
	 * 检索农事交流的品类的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(CommunioncategoryEXT pCommunioncategoryEXTT) throws Exception;

}
