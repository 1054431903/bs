package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecorduserpermisionsEXTMapper;
import com.fwk.service.farm.entity.RecorduserpermisionsEXT;

/**
 * 农事管理模块 — 农事记录的数据权限的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecorduserpermisionsService {

	// 农事管理模块 — 农事记录的数据权限的数据库处理类
	@Autowired
	private RecorduserpermisionsEXTMapper mRecorduserpermisionsEXTMapper;

	/**
	 * 新增农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecorduserpermisionsEXTMapper.add(pRecorduserpermisionsEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recorduserpermisions failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的数据权限对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<RecorduserpermisionsEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecorduserpermisionsEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recorduserpermisions failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecorduserpermisionsEXTMapper.update(pRecorduserpermisionsEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecorduserpermisionsEXT pRecorduserpermisionsEXT, final Map<String, Object> paramMap)
			throws Exception {

		final RecorduserpermisionsEXT originalRecorduserpermisionsEXT = this.queryById(pRecorduserpermisionsEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecorduserpermisionsEXT, originalRecorduserpermisionsEXT, paramMap);

		return this.update(originalRecorduserpermisionsEXT);

	}

	/**
	 * 删除指定ID的农事记录的数据权限
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {
		return mRecorduserpermisionsEXTMapper.deletebyid(pRecorduserpermisionsEXT);
	}

	/**
	 * 根据主键批量删除农事记录的数据权限
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
		return mRecorduserpermisionsEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public RecorduserpermisionsEXT queryById(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {
		RecorduserpermisionsEXT paramRecorduserpermisionsEXT = new RecorduserpermisionsEXT();
		paramRecorduserpermisionsEXT.setId(pRecorduserpermisionsEXT.getId());

		List<RecorduserpermisionsEXT> pRecorduserpermisionsEXTList = this.queryList(paramRecorduserpermisionsEXT);

		return ((null != pRecorduserpermisionsEXTList) && (pRecorduserpermisionsEXTList.size() > 0)
				? pRecorduserpermisionsEXTList.get(0) : null);
	}

	/**
	 * 检索农事记录的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public List<RecorduserpermisionsEXT> queryList(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {
		return mRecorduserpermisionsEXTMapper.queryList(pRecorduserpermisionsEXT);
	}

	/**
	 * 检索农事记录的数据权限的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecorduserpermisionsEXT
	 *            农事记录的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecorduserpermisionsEXT pRecorduserpermisionsEXT) throws Exception {
		return mRecorduserpermisionsEXTMapper.queryListTotalCount(pRecorduserpermisionsEXT);
	}

}
