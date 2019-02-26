package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.CommunionresourceEXTMapper;
import com.fwk.service.farm.entity.CommunionresourceEXT;

/**
 * 农事管理模块 — 农事交流的附件资源文件的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunionresourceService {

	// 农事管理模块 — 农事交流的附件资源文件的数据库处理类
	@Autowired
	private CommunionresourceEXTMapper mCommunionresourceEXTMapper;

	/**
	 * 新增农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunionresourceEXT pCommunionresourceEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionresourceEXTMapper.add(pCommunionresourceEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的附件资源文件对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<CommunionresourceEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionresourceEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunionresourceEXT pCommunionresourceEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunionresourceEXTMapper.update(pCommunionresourceEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunionresourceEXT pCommunionresourceEXT, final Map<String, Object> paramMap)
			throws Exception {

		final CommunionresourceEXT originalCommunionresourceEXT = this.queryById(pCommunionresourceEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunionresourceEXT, originalCommunionresourceEXT, paramMap);

		return this.update(originalCommunionresourceEXT);

	}

	/**
	 * 删除指定ID的农事交流的附件资源文件
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunionresourceEXT pCommunionresourceEXT) throws Exception {
		return mCommunionresourceEXTMapper.deletebyid(pCommunionresourceEXT);
	}

	/**
	 * 根据主键批量删除农事交流的附件资源文件
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
		return mCommunionresourceEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	public CommunionresourceEXT queryById(CommunionresourceEXT pCommunionresourceEXT) throws Exception {
		CommunionresourceEXT paramCommunionresourceEXT = new CommunionresourceEXT();
		paramCommunionresourceEXT.setId(pCommunionresourceEXT.getId());

		List<CommunionresourceEXT> pCommunionresourceEXTList = this.queryList(paramCommunionresourceEXT);

		return ((null != pCommunionresourceEXTList) && (pCommunionresourceEXTList.size() > 0)
				? pCommunionresourceEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunionresourceEXT> queryList(CommunionresourceEXT pCommunionresourceEXT) throws Exception {
		return mCommunionresourceEXTMapper.queryList(pCommunionresourceEXT);
	}

	/**
	 * 检索农事交流的附件资源文件的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunionresourceEXT
	 *            农事交流的附件资源文件对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunionresourceEXT pCommunionresourceEXT) throws Exception {
		return mCommunionresourceEXTMapper.queryListTotalCount(pCommunionresourceEXT);
	}

}
