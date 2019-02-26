package com.fwk.service.farm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFoutputMapper;
import com.fwk.service.farm.entity.OutputEXT;

/**
 * 农事管理模块 — 农事记录的投入品的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface OutputEXTMapper extends TFoutputMapper {

	/**
	 * 新增投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	Integer add(OutputEXT pOutputEXT) throws Exception;
	
	/**
	 * 批量新增投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            投入品对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<OutputEXT> list) throws Exception;

	/**
	 * 更新投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	Integer update(OutputEXT pOutputEXTT) throws Exception;

	/**
	 * 删除指定ID的投入品
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(OutputEXT pOutputEXTT) throws Exception;

	/**
	 * 根据主键批量删除投入品
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
	 * 检索投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	List<OutputEXT> queryList(OutputEXT pOutputEXTT) throws Exception;

	/**
	 * 检索投入品的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(OutputEXT pOutputEXTT) throws Exception;

}
