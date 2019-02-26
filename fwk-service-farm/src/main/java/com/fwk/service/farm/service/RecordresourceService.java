package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecordresourceEXTMapper;
import com.fwk.service.farm.entity.RecordresourceEXT;

/**
 * 农事管理模块 — 农事记录的附件资源文件的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordresourceService {

	// 农事管理模块 — 农事记录的参与人的数据库处理类
	@Autowired
	private RecordresourceEXTMapper mRecordresourceEXTMapper;

	/**
	 * 新增农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordresourceEXT pRecordresourceEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordresourceEXTMapper.add(pRecordresourceEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的附件资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的附件资源文件对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<RecordresourceEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordresourceEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordresourceEXT pRecordresourceEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordresourceEXTMapper.update(pRecordresourceEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordresourceEXT pRecordresourceEXT, final Map<String, Object> paramMap) throws Exception {

		final RecordresourceEXT originalRecordresourceEXT = this.queryById(pRecordresourceEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordresourceEXT, originalRecordresourceEXT, paramMap);

		return this.update(originalRecordresourceEXT);

	}

	/**
	 * 删除指定ID的农事记录的参与人
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordresourceEXT pRecordresourceEXT) throws Exception {
		return mRecordresourceEXTMapper.deletebyid(pRecordresourceEXT);
	}

	/**
	 * 根据主键批量删除农事记录的参与人
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
		return mRecordresourceEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public RecordresourceEXT queryById(RecordresourceEXT pRecordresourceEXT) throws Exception {
		RecordresourceEXT paramRecordresourceEXT = new RecordresourceEXT();
		paramRecordresourceEXT.setId(pRecordresourceEXT.getId());

		List<RecordresourceEXT> pRecordresourceEXTList = this.queryList(paramRecordresourceEXT);

		return ((null != pRecordresourceEXTList) && (pRecordresourceEXTList.size() > 0) ? pRecordresourceEXTList.get(0)
				: null);
	}

	/**
	 * 检索农事记录的参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordresourceEXT> queryList(RecordresourceEXT pRecordresourceEXT) throws Exception {
		return mRecordresourceEXTMapper.queryList(pRecordresourceEXT);
	}

	/**
	 * 检索农事记录的参与人的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordresourceEXT
	 *            农事记录的参与人对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordresourceEXT pRecordresourceEXT) throws Exception {
		return mRecordresourceEXTMapper.queryListTotalCount(pRecordresourceEXT);
	}

}
