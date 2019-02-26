package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.OutputEXTMapper;
import com.fwk.service.farm.entity.OutputEXT;

/**
 * 农事管理模块 — 农事记录的投入品的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class OutputService {

	// 农事管理模块 — 农事记录的投入品的数据库处理类
	@Autowired
	private OutputEXTMapper mOutputEXTMapper;

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
	@Transactional // 使用事务控制
	public Integer add(OutputEXT pOutputEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mOutputEXTMapper.add(pOutputEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Output failure !");
		}

		return resultAddOrUpdate;
	}

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
	public Integer batchAdd(List<OutputEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mOutputEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Output failure !");
		}

		return resultAddOrUpdate;
	}

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
	public Integer update(OutputEXT pOutputEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mOutputEXTMapper.update(pOutputEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Peasant failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(OutputEXT pOutputEXT, final Map<String, Object> paramMap) throws Exception {

		final OutputEXT originalOutputEXT = this.queryById(pOutputEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pOutputEXT, originalOutputEXT, paramMap);

		return this.update(originalOutputEXT);

	}

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
	public Integer deletebyid(OutputEXT pOutputEXT) throws Exception {
		return mOutputEXTMapper.deletebyid(pOutputEXT);
	}

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
	public Integer batchdeletebyids(List<Integer> idList) throws Exception {
		return mOutputEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pOutputEXT
	 *            投入品对象
	 * @return
	 * @throws Exception
	 */
	public OutputEXT queryById(OutputEXT pOutputEXT) throws Exception {
		OutputEXT paramOutputEXT = new OutputEXT();
		paramOutputEXT.setId(pOutputEXT.getId());

		List<OutputEXT> pOutputEXTList = this.queryList(paramOutputEXT);

		return ((null != pOutputEXTList) && (pOutputEXTList.size() > 0) ? pOutputEXTList.get(0) : null);
	}

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
	public List<OutputEXT> queryList(OutputEXT pOutputEXT) throws Exception {
		return mOutputEXTMapper.queryList(pOutputEXT);
	}

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
	public Integer queryListTotalCount(OutputEXT pOutputEXT) throws Exception {
		return mOutputEXTMapper.queryListTotalCount(pOutputEXT);
	}

}
