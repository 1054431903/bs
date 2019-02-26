package com.fwk.service.archives.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TAagricnameMapper;
import com.fwk.service.archives.entity.AgricnameEXT;

/**
 * 档案管理模块 — 农资名称管理的数据库处理对类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface AgricnameEXTMapper extends TAagricnameMapper {

	/**
	 * 新增农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	Integer add(AgricnameEXT pAgricnameEXT) throws Exception;

	/**
	 * 更新农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	Integer update(AgricnameEXT pAgricnameEXTT) throws Exception;

	/**
	 * 删除指定ID的农资名称
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(AgricnameEXT pAgricnameEXTT) throws Exception;

	/**
	 * 根据主键批量删除农资名称
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
	 * 检索农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	List<AgricnameEXT> queryList(AgricnameEXT pAgricnameEXTT) throws Exception;

	/**
	 * 检索农资名称的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(AgricnameEXT pAgricnameEXTT) throws Exception;

}
