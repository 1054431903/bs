package com.fwk.service.archives.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.service.archives.dao.PeasantprotocolresourceEXTMapper;
import com.fwk.service.archives.entity.PeasantprotocolresourceEXT;

/**
 * 档案管理模块 — 农户合作协议资源管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PeasantprotocolresourceService {

	// 档案管理模块 — 农户合作协议资源管理的数据库处理类
	@Autowired
	private PeasantprotocolresourceEXTMapper mPeasantprotocolresourceEXTMapper;

	/**
	 * 新增农户合作协议资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		// 将数据添加到数据库
		resultAddOrUpdate = mPeasantprotocolresourceEXTMapper.add(pPeasantprotocolresourceEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Peasantprotocolresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农户合作协议资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPeasantprotocolresourceEXTMapper.update(pPeasantprotocolresourceEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Peasantprotocolresource failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 删除指定ID的农户合作协议资源
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT) throws Exception {
		return mPeasantprotocolresourceEXTMapper.deletebyid(pPeasantprotocolresourceEXT);
	}

	/**
	 * 根据主键批量删除农户合作协议资源
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
		return mPeasantprotocolresourceEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农户合作协议资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	public PeasantprotocolresourceEXT queryById(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT)
			throws Exception {
		PeasantprotocolresourceEXT paramPeasantprotocolresourceEXT = new PeasantprotocolresourceEXT();
		paramPeasantprotocolresourceEXT.setId(pPeasantprotocolresourceEXT.getId());

		List<PeasantprotocolresourceEXT> pPeasantprotocolresourceEXTList = this
				.queryList(paramPeasantprotocolresourceEXT);

		return ((null != pPeasantprotocolresourceEXTList) && (pPeasantprotocolresourceEXTList.size() > 0)
				? pPeasantprotocolresourceEXTList.get(0) : null);
	}

	/**
	 * 检索农户合作协议资源
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	public List<PeasantprotocolresourceEXT> queryList(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT)
			throws Exception {
		return mPeasantprotocolresourceEXTMapper.queryList(pPeasantprotocolresourceEXT);
	}

	/**
	 * 检索农户合作协议资源的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPeasantprotocolresourceEXT
	 *            农户合作协议资源对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PeasantprotocolresourceEXT pPeasantprotocolresourceEXT) throws Exception {
		return mPeasantprotocolresourceEXTMapper.queryListTotalCount(pPeasantprotocolresourceEXT);
	}

}
