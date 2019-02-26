package com.fwk.service.archives.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TApeasantprotocolMapper;
import com.fwk.service.archives.entity.PeasantprotocolresourceEXT;

/**
 * 档案管理模块 — 农户合作协议管理的数据库处理对类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface PeasantprotocolresourceEXTMapper extends TApeasantprotocolMapper {

	/**
	 * 新增农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	Integer add(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT) throws Exception;

	/**
	 * 更新农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	Integer update(PeasantprotocolresourceEXT pPeasantprotocolresourceEXTT) throws Exception;

	/**
	 * 删除指定ID的农户合作协议
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(PeasantprotocolresourceEXT pPeasantprotocolresourceEXTT) throws Exception;

	/**
	 * 根据主键批量删除农户合作协议
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
	 * 检索农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	List<PeasantprotocolresourceEXT> queryList(PeasantprotocolresourceEXT pPeasantprotocolresourceEXTT)
			throws Exception;

	/**
	 * 检索农户合作协议的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(PeasantprotocolresourceEXT pPeasantprotocolresourceEXTT) throws Exception;

}
