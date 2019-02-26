package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFrecordpartuserMapper;
import com.fwk.service.farm.entity.RecordresourceEXT;

/**
 * 农事管理模块 — 农事记录的附件资源文件的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface RecordresourceEXTMapper extends TFrecordpartuserMapper {

	/**
	 * 新增农事记录的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	Integer add(RecordresourceEXT pRecordresourceEXT) throws Exception;

	/**
	 * 批量新增农事记录的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的附件资源文件对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<RecordresourceEXT> list) throws Exception;

	/**
	 * 更新农事记录的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	Integer update(RecordresourceEXT pRecordresourceEXTT) throws Exception;

	/**
	 * 删除指定ID的农事记录的附件资源文件
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(RecordresourceEXT pRecordresourceEXTT) throws Exception;

	/**
	 * 根据主键批量删除农事记录的附件资源文件
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
	 * 检索农事记录的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	List<RecordresourceEXT> queryList(RecordresourceEXT pRecordresourceEXTT) throws Exception;

	/**
	 * 检索农事记录的附件资源文件的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(RecordresourceEXT pRecordresourceEXTT) throws Exception;

}
