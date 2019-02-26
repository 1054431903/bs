package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.CommunioncommentEXTMapper;
import com.fwk.service.farm.entity.CommunionEXT;
import com.fwk.service.farm.entity.CommunioncommentEXT;

/**
 * 农事管理模块 — 农事交流的评论的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunioncommentService {

	// 农事管理模块 — 农事交流的评论的数据库处理类
	@Autowired
	private CommunioncommentEXTMapper mCommunioncommentEXTMapper;

	// 农事管理模块 — 农事任务管理的业务逻辑处理对象
	@Autowired
	private CommunionService mCommunionService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunioncommentEXT pCommunioncommentEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncommentEXTMapper.add(pCommunioncommentEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Recordresource failure !");
		}

		// ----------- 增加农事交流的评论次数 ----------------------
		CommunionEXT paramCommunionEXT = new CommunionEXT();
		paramCommunionEXT.setId(pCommunioncommentEXT.getCommunionid());
		paramCommunionEXT.setIncreaseordecreaseoperand(new Integer("+1"));// 增加

		resultAddOrUpdate = mCommunionService.increaseOrDecreaseCommentcount(paramCommunionEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("update Communion.commentcount failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的评论对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<CommunioncommentEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncommentEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunioncommentEXT pCommunioncommentEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunioncommentEXTMapper.update(pCommunioncommentEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunioncommentEXT pCommunioncommentEXT, final Map<String, Object> paramMap) throws Exception {

		final CommunioncommentEXT originalCommunioncommentEXT = this.queryById(pCommunioncommentEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunioncommentEXT, originalCommunioncommentEXT, paramMap);

		return this.update(originalCommunioncommentEXT);

	}

	/**
	 * 删除指定ID的农事交流的评论
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunioncommentEXT pCommunioncommentEXT) throws Exception {

		final CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
		paramCommunioncommentEXT.setId(pCommunioncommentEXT.getId());

		pCommunioncommentEXT = this.queryById(paramCommunioncommentEXT);

		Integer resultAddOrUpdate = mCommunioncommentEXTMapper.deletebyid(pCommunioncommentEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("delete Communioncomment failure !");
		}

		// ----------- 减少农事交流的评论次数 ----------------------
		CommunionEXT paramCommunionEXT = new CommunionEXT();
		paramCommunionEXT.setId(pCommunioncommentEXT.getCommunionid());
		paramCommunionEXT.setIncreaseordecreaseoperand(new Integer("-1"));// 减少

		resultAddOrUpdate = mCommunionService.increaseOrDecreaseCommentcount(paramCommunionEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("update Communion.commentcount failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 根据主键批量删除农事交流的评论
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

		final CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
		paramCommunioncommentEXT.setIdlist(idList);
		List<CommunioncommentEXT> communioncommentEXTList = this.queryList(paramCommunioncommentEXT);

		Integer resultDelete = mCommunioncommentEXTMapper.batchdeletebyids(idList);

		if ((null == resultDelete) || (idList.size() != resultDelete.intValue())) {
			throw new Exception("batch delete Communioncomment failure !");
		}

		// ----------- 减少农事交流的评论次数 ----------------------
		Integer resultAddOrUpdate = null;

		Map<Integer, AtomicInteger> communionidMap = new TreeMap<Integer, AtomicInteger>();

		for (CommunioncommentEXT communioncommentEXT : communioncommentEXTList) {

			if (communionidMap.containsKey(communioncommentEXT.getCommunionid())) {
				communionidMap.get(communioncommentEXT.getCommunionid()).decrementAndGet();
			} else {
				communionidMap.put(communioncommentEXT.getCommunionid(), new AtomicInteger(-1));
			}
		}

		CommunionEXT paramCommunionEXT = null;// 临时变量

		for (Entry<Integer, AtomicInteger> entry : communionidMap.entrySet()) {
			paramCommunionEXT = new CommunionEXT();
			paramCommunionEXT.setId(entry.getKey());
			paramCommunionEXT.setIncreaseordecreaseoperand(entry.getValue().get());// 减少

			resultAddOrUpdate = mCommunionService.increaseOrDecreaseCommentcount(paramCommunionEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("update Communion.commentcount failure !");
			}
		}

		return resultDelete;

	}

	/**
	 * 查询出指定ID的农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public CommunioncommentEXT queryById(CommunioncommentEXT pCommunioncommentEXT) throws Exception {
		CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
		paramCommunioncommentEXT.setId(pCommunioncommentEXT.getId());

		List<CommunioncommentEXT> pCommunioncommentEXTList = this.queryList(paramCommunioncommentEXT);

		return ((null != pCommunioncommentEXTList) && (pCommunioncommentEXTList.size() > 0)
				? pCommunioncommentEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的评论
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunioncommentEXT> queryList(CommunioncommentEXT pCommunioncommentEXT) throws Exception {
		List<CommunioncommentEXT> list = mCommunioncommentEXTMapper.queryList(pCommunioncommentEXT);

		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		List<Integer> userIdList = new ArrayList<Integer>();

		for (CommunioncommentEXT communioncommentEXT : list) {
			// 创建人的ID
			userIdList.add(communioncommentEXT.getCommentuserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (CommunioncommentEXT communioncommentEXT : list) {
			if (null == communioncommentEXT) {
				continue;
			}

			// --------------- 设置农事交流的发布人的信息 ----------------------
			this.setCreateUserInfo(communioncommentEXT, supportUserEXTMap);
		}

		return list;
	}

	/**
	 * 设置农事交流的评论人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param CommunioncommentEXT
	 *            农事交流对象
	 * @throws Exception
	 */
	private void setCreateUserInfo(final CommunioncommentEXT communioncommentEXT,
			final Map<String, SupportUserEXT> supportUserEXTMap) throws Exception {

		if ((null == communioncommentEXT) || (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		SupportUserEXT tempSupportUserEXT = supportUserEXTMap
				.get(ObjectUtil.toString(communioncommentEXT.getCommentuserid()));

		if ((null == tempSupportUserEXT)) {
			communioncommentEXT.setCommentuseridname("");
		} else {
			communioncommentEXT.setCommentuseridname(ObjectUtil.toString(tempSupportUserEXT.getName()));// 姓名
			communioncommentEXT.setCommentuseridnickname(tempSupportUserEXT.getNickname());
			communioncommentEXT.setPhotourl(tempSupportUserEXT.getPhotourl());// 头像
		}

	}

	/**
	 * 检索农事交流的评论的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunioncommentEXT pCommunioncommentEXT) throws Exception {
		return mCommunioncommentEXTMapper.queryListTotalCount(pCommunioncommentEXT);
	}

	/**
	 * 增加或者减少农事交流的评论的评论数量（正数为增加、负数为减少）
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:54:57
	 *
	 * @param pCommunioncommentEXT
	 *            农事交流的评论对象
	 * @return
	 * @throws Exception
	 */
	public Integer increaseOrDecreaseCommentcount(CommunioncommentEXT pCommunioncommentEXT) throws Exception {
		return mCommunioncommentEXTMapper.increaseOrDecreaseCommentcount(pCommunioncommentEXT);
	}

}
