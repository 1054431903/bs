package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.CommunioncategoryEXTMapper;
import com.fwk.service.farm.entity.CommunioncategoryEXT;

/**
 * 农事管理模块 — 农事交流的品类的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunioncategoryService {

	// 农事管理模块 — 农事交流的品类的数据库处理类
	@Autowired
	private CommunioncategoryEXTMapper mCommunioncategoryEXTMapper;

	/**
	 * 新增农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncategoryEXTMapper.add(pCommunioncategoryEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的品类对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<CommunioncategoryEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncategoryEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunioncategoryEXTMapper.update(pCommunioncategoryEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunioncategoryEXT pCommunioncategoryEXT, final Map<String, Object> paramMap)
			throws Exception {

		final CommunioncategoryEXT originalCommunioncategoryEXT = this.queryById(pCommunioncategoryEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunioncategoryEXT, originalCommunioncategoryEXT, paramMap);

		return this.update(originalCommunioncategoryEXT);

	}

	/**
	 * 删除指定ID的农事交流的品类
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {
		return mCommunioncategoryEXTMapper.deletebyid(pCommunioncategoryEXT);
	}

	/**
	 * 根据主键批量删除农事交流的品类
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
		return mCommunioncategoryEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	public CommunioncategoryEXT queryById(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {
		CommunioncategoryEXT paramCommunioncategoryEXT = new CommunioncategoryEXT();
		paramCommunioncategoryEXT.setId(pCommunioncategoryEXT.getId());

		List<CommunioncategoryEXT> pCommunioncategoryEXTList = this.queryList(paramCommunioncategoryEXT);

		return ((null != pCommunioncategoryEXTList) && (pCommunioncategoryEXTList.size() > 0)
				? pCommunioncategoryEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的品类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunioncategoryEXT> queryList(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {
		return mCommunioncategoryEXTMapper.queryList(pCommunioncategoryEXT);
	}

	/**
	 * 检索农事交流的品类的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncategoryEXT
	 *            农事交流的品类对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunioncategoryEXT pCommunioncategoryEXT) throws Exception {
		return mCommunioncategoryEXTMapper.queryListTotalCount(pCommunioncategoryEXT);
	}

}
