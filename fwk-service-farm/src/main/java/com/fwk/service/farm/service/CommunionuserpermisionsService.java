package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.CommunionuserpermisionsEXTMapper;
import com.fwk.service.farm.entity.CommunionuserpermisionsEXT;

/**
 * 农事管理模块 — 农事交流的数据权限的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunionuserpermisionsService {

	// 农事管理模块 — 农事交流的数据权限的数据库处理类
	@Autowired
	private CommunionuserpermisionsEXTMapper mCommunionuserpermisionsEXTMapper;

	/**
	 * 新增农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionuserpermisionsEXTMapper.add(pCommunionuserpermisionsEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Communionuserpermisions failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的数据权限对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<CommunionuserpermisionsEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionuserpermisionsEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Communionuserpermisions failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunionuserpermisionsEXTMapper.update(pCommunionuserpermisionsEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT, final Map<String, Object> paramMap)
			throws Exception {

		final CommunionuserpermisionsEXT originalCommunionuserpermisionsEXT = this
				.queryById(pCommunionuserpermisionsEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunionuserpermisionsEXT, originalCommunionuserpermisionsEXT, paramMap);

		return this.update(originalCommunionuserpermisionsEXT);

	}

	/**
	 * 删除指定ID的农事交流的数据权限
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT) throws Exception {
		return mCommunionuserpermisionsEXTMapper.deletebyid(pCommunionuserpermisionsEXT);
	}

	/**
	 * 根据主键批量删除农事交流的数据权限
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
		return mCommunionuserpermisionsEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public CommunionuserpermisionsEXT queryById(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT)
			throws Exception {
		CommunionuserpermisionsEXT paramCommunionuserpermisionsEXT = new CommunionuserpermisionsEXT();
		paramCommunionuserpermisionsEXT.setId(pCommunionuserpermisionsEXT.getId());

		List<CommunionuserpermisionsEXT> pCommunionuserpermisionsEXTList = this
				.queryList(paramCommunionuserpermisionsEXT);

		return ((null != pCommunionuserpermisionsEXTList) && (pCommunionuserpermisionsEXTList.size() > 0)
				? pCommunionuserpermisionsEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的数据权限
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunionuserpermisionsEXT> queryList(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT)
			throws Exception {
		return mCommunionuserpermisionsEXTMapper.queryList(pCommunionuserpermisionsEXT);
	}

	/**
	 * 检索农事交流的数据权限的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunionuserpermisionsEXT
	 *            农事交流的数据权限对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunionuserpermisionsEXT pCommunionuserpermisionsEXT) throws Exception {
		return mCommunionuserpermisionsEXTMapper.queryListTotalCount(pCommunionuserpermisionsEXT);
	}

}
