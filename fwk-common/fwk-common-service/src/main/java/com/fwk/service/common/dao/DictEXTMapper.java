package com.fwk.service.common.dao;

import java.util.List;

import com.fwk.common.dao.TCdictMapper;
import com.fwk.service.common.entity.DictEXT;

/**
 * 数据字典的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月20日 上午9:53:40
 *
 */
public interface DictEXTMapper extends TCdictMapper {

	/**
	 * 查询出数据字典的列表
	 * 
	 * @author wangjunwen
	 * @date 2018年1月20日 上午9:56:38
	 *
	 * @param pDictEXT
	 *            数据字典对象
	 * @return
	 * @throws Exception
	 */
	List<DictEXT> queryList(DictEXT pDictEXT) throws Exception;

	/**
	 * 查询出数据字典的列表的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月20日 上午9:59:32
	 *
	 * @param pDictEXT
	 *            数据字典对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(DictEXT pDictEXT) throws Exception;

}
