package com.fwk.service.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.common.dao.DictEXTMapper;
import com.fwk.service.common.entity.DictEXT;

/**
 * 数据字典的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月20日 上午10:21:01
 *
 */
@Service
public class DictService {

	@Autowired
	private DictEXTMapper mDictEXTMapper;

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
	public List<DictEXT> queryList(DictEXT pDictEXT) throws Exception {
		return mDictEXTMapper.queryList(pDictEXT);
	}

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
	public Integer queryListTotalCount(DictEXT pDictEXT) throws Exception {
		return mDictEXTMapper.queryListTotalCount(pDictEXT);
	}
}
