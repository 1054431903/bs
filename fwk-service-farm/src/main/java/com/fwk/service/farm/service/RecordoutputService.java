package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecordoutputEXTMapper;
import com.fwk.service.farm.entity.RecordoutputEXT;

/**
 * 农事管理模块 — 农事记录的投入品引用的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordoutputService {

	// 农事管理模块 — 农事记录的投入品引用的数据库处理类
	@Autowired
	private RecordoutputEXTMapper mRecordoutputEXTMapper;

	/**
	 * 新增农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordoutputEXT pRecordoutputEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordoutputEXTMapper.add(pRecordoutputEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordoutput failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的投入品引用对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<RecordoutputEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordoutputEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordoutput failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordoutputEXT pRecordoutputEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordoutputEXTMapper.update(pRecordoutputEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordoutputEXT pRecordoutputEXT, final Map<String, Object> paramMap) throws Exception {

		final RecordoutputEXT originalRecordoutputEXT = this.queryById(pRecordoutputEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordoutputEXT, originalRecordoutputEXT, paramMap);

		return this.update(originalRecordoutputEXT);

	}

	/**
	 * 删除指定ID的农事记录的投入品引用
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordoutputEXT pRecordoutputEXT) throws Exception {
		return mRecordoutputEXTMapper.deletebyid(pRecordoutputEXT);
	}

	/**
	 * 根据主键批量删除农事记录的投入品引用
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
		return mRecordoutputEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	public RecordoutputEXT queryById(RecordoutputEXT pRecordoutputEXT) throws Exception {
		RecordoutputEXT paramRecordoutputEXT = new RecordoutputEXT();
		paramRecordoutputEXT.setId(pRecordoutputEXT.getId());

		List<RecordoutputEXT> pRecordoutputEXTList = this.queryList(paramRecordoutputEXT);

		return ((null != pRecordoutputEXTList) && (pRecordoutputEXTList.size() > 0) ? pRecordoutputEXTList.get(0)
				: null);
	}

	/**
	 * 检索农事记录的投入品引用
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordoutputEXT> queryList(RecordoutputEXT pRecordoutputEXT) throws Exception {
		return mRecordoutputEXTMapper.queryList(pRecordoutputEXT);
	}

	/**
	 * 检索农事记录的投入品引用的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordoutputEXT
	 *            农事记录的投入品引用对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordoutputEXT pRecordoutputEXT) throws Exception {
		return mRecordoutputEXTMapper.queryListTotalCount(pRecordoutputEXT);
	}

}
