package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.service.farm.entity.RecordfarmtypeEXT;

/**
 * 农事管理模块 — 农事记录的农事类别的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface RecordfarmtypeEXTMapper {

	/**
	 * 新增农事记录的农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事类别对象
	 * @return
	 * @throws Exception
	 */
	Integer add(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception;

	/**
	 * 批量新增农事记录的农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的农事类别对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<RecordfarmtypeEXT> list) throws Exception;

	/**
	 * 更新农事记录的农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事类别对象
	 * @return
	 * @throws Exception
	 */
	Integer update(RecordfarmtypeEXT pRecordfarmtypeEXTT) throws Exception;

	/**
	 * 删除指定ID的农事记录的农事类别
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事类别对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(RecordfarmtypeEXT pRecordfarmtypeEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事记录的农事类别
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
	 * 检索农事记录的农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事类别对象
	 * @return
	 * @throws Exception
	 */
	List<RecordfarmtypeEXT> queryList(RecordfarmtypeEXT pRecordfarmtypeEXTT) throws Exception;

	/**
	 * 检索农事记录的农事类别的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事类别对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(RecordfarmtypeEXT pRecordfarmtypeEXTT) throws Exception;

}
