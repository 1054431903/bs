package com.fwk.service.farm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecordparcelEXTMapper;
import com.fwk.service.farm.entity.RecordparcelEXT;

/**
 * 农事管理模块 — 农事记录的地块的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordparcelService {

	// 农事管理模块 — 农事记录的地块的数据库处理类
	@Autowired
	private RecordparcelEXTMapper mRecordparcelEXTMapper;

	/**
	 * 新增农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordparcelEXT pRecordparcelEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordparcelEXTMapper.add(pRecordparcelEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordparcel failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的地块对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<RecordparcelEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordparcelEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordparcel failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordparcelEXT pRecordparcelEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordparcelEXTMapper.update(pRecordparcelEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordparcelEXT pRecordparcelEXT, final Map<String, Object> paramMap) throws Exception {

		final RecordparcelEXT originalRecordparcelEXT = this.queryById(pRecordparcelEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordparcelEXT, originalRecordparcelEXT, paramMap);

		return this.update(originalRecordparcelEXT);

	}

	/**
	 * 删除指定ID的农事记录的地块
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordparcelEXT pRecordparcelEXT) throws Exception {
		return mRecordparcelEXTMapper.deletebyid(pRecordparcelEXT);
	}

	/**
	 * 根据主键批量删除农事记录的地块
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
		return mRecordparcelEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	public RecordparcelEXT queryById(RecordparcelEXT pRecordparcelEXT) throws Exception {
		RecordparcelEXT paramRecordparcelEXT = new RecordparcelEXT();
		paramRecordparcelEXT.setId(pRecordparcelEXT.getId());

		List<RecordparcelEXT> pRecordparcelEXTList = this.queryList(paramRecordparcelEXT);

		return ((null != pRecordparcelEXTList) && (pRecordparcelEXTList.size() > 0) ? pRecordparcelEXTList.get(0)
				: null);
	}

	/**
	 * 检索农事记录的地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordparcelEXT> queryList(RecordparcelEXT pRecordparcelEXT) throws Exception {
		return mRecordparcelEXTMapper.queryList(pRecordparcelEXT);
	}

	/**
	 * 检索农事记录的地块的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordparcelEXT
	 *            农事记录的地块对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordparcelEXT pRecordparcelEXT) throws Exception {
		return mRecordparcelEXTMapper.queryListTotalCount(pRecordparcelEXT);
	}

	public void deletebyrecordid(Integer recordid) {
		mRecordparcelEXTMapper.deletebyrecordid(recordid);
	}

	public void addByRecordParce(Integer recordid, Integer parcelid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recordid", recordid);
		map.put("parcelid", parcelid);
		mRecordparcelEXTMapper.addByRecordParce(map);
	}

}
