package com.fwk.service.farm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.service.farm.dao.RecordfarmtypeEXTMapper;
import com.fwk.service.farm.entity.RecordfarmtypeEXT;

/**
 * 农事管理模块 — 农事记录的农事种类的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordfarmtypeService {

	// 农事管理模块 — 农事记录的农事种类的数据库处理类
	@Autowired
	private RecordfarmtypeEXTMapper mRecordfarmtypeEXTMapper;

	/**
	 * 新增农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordfarmtypeEXTMapper.add(pRecordfarmtypeEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordparcel failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事记录的农事种类对象列表
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer batchAdd(List<RecordfarmtypeEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mRecordfarmtypeEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordparcel failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordfarmtypeEXTMapper.update(pRecordfarmtypeEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordfarmtypeEXT pRecordfarmtypeEXT, final Map<String, Object> paramMap) throws Exception {

		final RecordfarmtypeEXT originalRecordfarmtypeEXT = this.queryById(pRecordfarmtypeEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordfarmtypeEXT, originalRecordfarmtypeEXT, paramMap);

		return this.update(originalRecordfarmtypeEXT);

	}

	/**
	 * 删除指定ID的农事记录的农事种类
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {
		return mRecordfarmtypeEXTMapper.deletebyid(pRecordfarmtypeEXT);
	}

	/**
	 * 根据主键批量删除农事记录的农事种类
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
		return mRecordfarmtypeEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	public RecordfarmtypeEXT queryById(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {
		RecordfarmtypeEXT paramRecordfarmtypeEXT = new RecordfarmtypeEXT();
		paramRecordfarmtypeEXT.setId(pRecordfarmtypeEXT.getId());

		List<RecordfarmtypeEXT> pRecordfarmtypeEXTList = this.queryList(paramRecordfarmtypeEXT);

		return ((null != pRecordfarmtypeEXTList) && (pRecordfarmtypeEXTList.size() > 0) ? pRecordfarmtypeEXTList.get(0)
				: null);
	}

	/**
	 * 检索农事记录的农事种类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordfarmtypeEXT> queryList(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {
		return mRecordfarmtypeEXTMapper.queryList(pRecordfarmtypeEXT);
	}

	/**
	 * 检索农事记录的农事种类的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordfarmtypeEXT
	 *            农事记录的农事种类对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordfarmtypeEXT pRecordfarmtypeEXT) throws Exception {
		return mRecordfarmtypeEXTMapper.queryListTotalCount(pRecordfarmtypeEXT);
	}

}
