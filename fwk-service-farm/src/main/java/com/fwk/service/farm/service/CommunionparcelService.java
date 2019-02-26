package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.CommunionparcelEXTMapper;
import com.fwk.service.farm.entity.CommunionparcelEXT;

/**
 * 农事管理模块 — 农事交流的地块的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunionparcelService {

	// 农事管理模块 — 农事交流的地块的数据库处理类
	@Autowired
	private CommunionparcelEXTMapper mCommunionparcelEXTMapper;

	/**
	 * 新增农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunionparcelEXT pCommunionparcelEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionparcelEXTMapper.add(pCommunionparcelEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的地块对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<CommunionparcelEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionparcelEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunionparcelEXT pCommunionparcelEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunionparcelEXTMapper.update(pCommunionparcelEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunionparcelEXT pCommunionparcelEXT, final Map<String, Object> paramMap) throws Exception {

		final CommunionparcelEXT originalCommunionparcelEXT = this.queryById(pCommunionparcelEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunionparcelEXT, originalCommunionparcelEXT, paramMap);

		return this.update(originalCommunionparcelEXT);

	}

	/**
	 * 删除指定ID的农事交流的地块
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunionparcelEXT pCommunionparcelEXT) throws Exception {
		return mCommunionparcelEXTMapper.deletebyid(pCommunionparcelEXT);
	}

	/**
	 * 根据主键批量删除农事交流的地块
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
		return mCommunionparcelEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	public CommunionparcelEXT queryById(CommunionparcelEXT pCommunionparcelEXT) throws Exception {
		CommunionparcelEXT paramCommunionparcelEXT = new CommunionparcelEXT();
		paramCommunionparcelEXT.setId(pCommunionparcelEXT.getId());

		List<CommunionparcelEXT> pCommunionparcelEXTList = this.queryList(paramCommunionparcelEXT);

		return ((null != pCommunionparcelEXTList) && (pCommunionparcelEXTList.size() > 0)
				? pCommunionparcelEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunionparcelEXT> queryList(CommunionparcelEXT pCommunionparcelEXT) throws Exception {
		return mCommunionparcelEXTMapper.queryList(pCommunionparcelEXT);
	}

	/**
	 * 检索农事交流的地块的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunionparcelEXT
	 *            农事交流的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunionparcelEXT pCommunionparcelEXT) throws Exception {
		return mCommunionparcelEXTMapper.queryListTotalCount(pCommunionparcelEXT);
	}

}
