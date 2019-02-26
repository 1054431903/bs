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
import com.fwk.service.farm.dao.CommunioncommentreversionEXTMapper;
import com.fwk.service.farm.entity.CommunioncommentEXT;
import com.fwk.service.farm.entity.CommunioncommentreversionEXT;

/**
 * 农事管理模块 — 农事交流的评论的回复的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunioncommentreversionService {

	// 农事管理模块 — 农事交流的评论的回复的数据库处理类
	@Autowired
	private CommunioncommentreversionEXTMapper mCommunioncommentreversionEXTMapper;

	// 农事交流的评论的业务逻辑处理对象
	@Autowired
	private CommunioncommentService mCommunioncommentService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunioncommentreversionEXT pCommunioncommentreversionEXT) throws Exception {

		// 被回复的农事交流的评论的回复对象
		CommunioncommentreversionEXT parentCommunioncommentreversionEXT = null;

		if ((null != pCommunioncommentreversionEXT.getParentid())
				&& (pCommunioncommentreversionEXT.getParentid().intValue() > 0)) {

			parentCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
			parentCommunioncommentreversionEXT.setId(pCommunioncommentreversionEXT.getParentid());

			parentCommunioncommentreversionEXT = this.queryById(parentCommunioncommentreversionEXT);

			// 设置被回复人
			pCommunioncommentreversionEXT.setToreversionuserid(parentCommunioncommentreversionEXT.getReversionuserid());
		}

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncommentreversionEXTMapper.add(pCommunioncommentreversionEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Communioncommentreversion failure !");
		}

		if ((null == parentCommunioncommentreversionEXT)) {// 增加农事交流的评论的回复次数（回复的是农事交流的评论）

			CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
			paramCommunioncommentEXT.setId(pCommunioncommentreversionEXT.getCommunioncommentid());
			paramCommunioncommentEXT.setIncreaseordecreaseoperand(new Integer("+1"));

			resultAddOrUpdate = mCommunioncommentService.increaseOrDecreaseCommentcount(paramCommunioncommentEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("update Communioncomment.commentcount failure !");
			}

		} else {// 增加农事交流的评论的回复的回复次数（回复的是农事交流的评论的回复）

			parentCommunioncommentreversionEXT.setIncreaseordecreaseoperand(new Integer("+1"));

			resultAddOrUpdate = mCommunioncommentreversionEXTMapper
					.increaseOrDecreaseReversioncount(parentCommunioncommentreversionEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("update Communioncommentreversion.reversioncount failure !");
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            农事交流的评论的回复对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<CommunioncommentreversionEXT> list) throws Exception {
		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunioncommentreversionEXTMapper.batchAdd(list);

		if ((null == resultAddOrUpdate) || (list.size() != resultAddOrUpdate.intValue())) {
			throw new Exception("batch add Recordresource failure !");
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunioncommentreversionEXT pCommunioncommentreversionEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunioncommentreversionEXTMapper.update(pCommunioncommentreversionEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunioncommentreversionEXT pCommunioncommentreversionEXT, final Map<String, Object> paramMap)
			throws Exception {

		final CommunioncommentreversionEXT originalCommunioncommentreversionEXT = this
				.queryById(pCommunioncommentreversionEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunioncommentreversionEXT, originalCommunioncommentreversionEXT, paramMap);

		return this.update(originalCommunioncommentreversionEXT);

	}

	/**
	 * 删除指定ID的农事交流的评论的回复
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunioncommentreversionEXT pCommunioncommentreversionEXT) throws Exception {

		CommunioncommentreversionEXT paramCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
		paramCommunioncommentreversionEXT.setId(pCommunioncommentreversionEXT.getId());

		pCommunioncommentreversionEXT = this.queryById(paramCommunioncommentreversionEXT);

		// 被回复的农事交流的评论的回复对象
		CommunioncommentreversionEXT parentCommunioncommentreversionEXT = null;

		if ((null != pCommunioncommentreversionEXT.getParentid())
				&& (pCommunioncommentreversionEXT.getParentid().intValue() > 0)) {

			parentCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
			parentCommunioncommentreversionEXT.setId(pCommunioncommentreversionEXT.getParentid());

			parentCommunioncommentreversionEXT = this.queryById(parentCommunioncommentreversionEXT);

			// 设置被回复人
			pCommunioncommentreversionEXT
					.setToreversionuserid(parentCommunioncommentreversionEXT.getToreversionuserid());
		}

		Integer resultDelete = mCommunioncommentreversionEXTMapper.deletebyid(pCommunioncommentreversionEXT);

		if ((null == resultDelete) || (1 != resultDelete.intValue())) {
			throw new Exception("delete Communioncommentreversion failure !");
		}

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		if ((null == parentCommunioncommentreversionEXT)) {// 减少农事交流的评论的回复次数（删除是农事交流的评论）

			CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
			paramCommunioncommentEXT.setId(pCommunioncommentreversionEXT.getCommunioncommentid());
			paramCommunioncommentEXT.setIncreaseordecreaseoperand(new Integer("-1"));// 减少

			resultAddOrUpdate = mCommunioncommentService.increaseOrDecreaseCommentcount(paramCommunioncommentEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("update Communioncomment.commentcount failure !");
			}

		} else {// 增加农事交流的评论的回复的回复次数（删除是农事交流的评论的回复）

			parentCommunioncommentreversionEXT.setIncreaseordecreaseoperand(new Integer("-1"));

			resultAddOrUpdate = mCommunioncommentreversionEXTMapper
					.increaseOrDecreaseReversioncount(parentCommunioncommentreversionEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("update Communioncommentreversion.reversioncount failure !");
			}
		}

		return resultDelete;
	}

	/**
	 * 根据主键批量删除农事交流的评论的回复
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

		CommunioncommentreversionEXT paramCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
		paramCommunioncommentreversionEXT.setIdlist(idList);

		List<CommunioncommentreversionEXT> deleteCommunioncommentreversionEXTList = this
				.queryList(paramCommunioncommentreversionEXT);

		if ((null == deleteCommunioncommentreversionEXTList) || (deleteCommunioncommentreversionEXTList.isEmpty())) {
			return null;
		}

		// 农事交流的评论的ID
		final Map<Integer, AtomicInteger> communioncommentidMap = new TreeMap<Integer, AtomicInteger>();
		// 农事交流的评论的回复的ID
		final Map<Integer, AtomicInteger> parentidMap = new TreeMap<Integer, AtomicInteger>();

		for (CommunioncommentreversionEXT deleteObj : deleteCommunioncommentreversionEXTList) {

			if ((null != deleteObj.getParentid()) && (deleteObj.getParentid().intValue() > 0)) {// 删除的是农事评论的回复的回复
				if (parentidMap.containsKey(deleteObj.getParentid())) {
					parentidMap.get(deleteObj.getParentid()).decrementAndGet();
				} else {
					parentidMap.put(deleteObj.getParentid(), new AtomicInteger(-1));
				}
			} else {// 删除的是农事交流的评论
				if (communioncommentidMap.containsKey(deleteObj.getCommunioncommentid())) {
					communioncommentidMap.get(deleteObj.getCommunioncommentid()).decrementAndGet();
				} else {
					communioncommentidMap.put(deleteObj.getCommunioncommentid(), new AtomicInteger(-1));
				}
			}
		}

		// 删除农事交流的评论的回复
		final Integer resultDelete = mCommunioncommentreversionEXTMapper.batchdeletebyids(idList);

		if ((null == resultDelete) || (idList.size() != resultDelete.intValue())) {
			throw new Exception("delete Communioncommentreversion failure !");
		}

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		// -------------- 查询出农事交流的评论并更新 -------------------------
		if ((communioncommentidMap.size() > 0)) {
			CommunioncommentEXT paramCommunioncommentEXT = new CommunioncommentEXT();
			paramCommunioncommentEXT.setIdlist(new ArrayList<Integer>());

			for (Entry<Integer, AtomicInteger> entry : communioncommentidMap.entrySet()) {
				paramCommunioncommentEXT.getIdlist().add(entry.getKey());
			}

			List<CommunioncommentEXT> communioncommentEXTList = mCommunioncommentService
					.queryList(paramCommunioncommentEXT);

			if ((null != communioncommentEXTList) && (communioncommentEXTList.size() > 0)) {
				for (CommunioncommentEXT communioncommentEXT : communioncommentEXTList) {
					communioncommentEXT
							.setIncreaseordecreaseoperand(communioncommentidMap.get(communioncommentEXT.getId()).get());

					resultAddOrUpdate = mCommunioncommentService.increaseOrDecreaseCommentcount(communioncommentEXT);

					if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
						throw new Exception("update Communioncomment.commentcount failure !");
					}
				}

			}
		}

		// --------------- 查询出农事交流的评论的回复（回复别人的回复） -------------------------
		if ((parentidMap.size() > 0)) {
			CommunioncommentreversionEXT paramParentCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
			paramParentCommunioncommentreversionEXT.setIdlist(new ArrayList<Integer>());

			for (Entry<Integer, AtomicInteger> entry : parentidMap.entrySet()) {
				paramParentCommunioncommentreversionEXT.getIdlist().add(entry.getKey());
			}

			List<CommunioncommentreversionEXT> parentCommunioncommentreversionEXTList = this
					.queryList(paramParentCommunioncommentreversionEXT);

			if ((null != parentCommunioncommentreversionEXTList)
					&& (parentCommunioncommentreversionEXTList.size() > 0)) {

				for (CommunioncommentreversionEXT parentCommunioncommentreversionEXT : parentCommunioncommentreversionEXTList) {

					parentCommunioncommentreversionEXT.setIncreaseordecreaseoperand(
							parentidMap.get(parentCommunioncommentreversionEXT.getId()).get());

					resultAddOrUpdate = mCommunioncommentreversionEXTMapper
							.increaseOrDecreaseReversioncount(parentCommunioncommentreversionEXT);

					if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
						throw new Exception("update Communioncommentreversion.reversioncount failure !");
					}
				}

			}

		}

		return resultDelete;
	}

	/**
	 * 查询出指定ID的农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	public CommunioncommentreversionEXT queryById(CommunioncommentreversionEXT pCommunioncommentreversionEXT)
			throws Exception {
		CommunioncommentreversionEXT paramCommunioncommentreversionEXT = new CommunioncommentreversionEXT();
		paramCommunioncommentreversionEXT.setId(pCommunioncommentreversionEXT.getId());

		List<CommunioncommentreversionEXT> pCommunioncommentreversionEXTList = this
				.queryList(paramCommunioncommentreversionEXT);

		return ((null != pCommunioncommentreversionEXTList) && (pCommunioncommentreversionEXTList.size() > 0)
				? pCommunioncommentreversionEXTList.get(0) : null);
	}

	/**
	 * 检索农事交流的评论的回复
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunioncommentreversionEXT> queryList(CommunioncommentreversionEXT pCommunioncommentreversionEXT)
			throws Exception {
		List<CommunioncommentreversionEXT> list = mCommunioncommentreversionEXTMapper
				.queryList(pCommunioncommentreversionEXT);

		if ((null == list) || (list.isEmpty())) {
			return list;
		}

		List<Integer> userIdList = new ArrayList<Integer>();

		for (CommunioncommentreversionEXT communioncommentreversionEXT : list) {
			// 回复人的ID
			userIdList.add(communioncommentreversionEXT.getReversionuserid());
			// 被回复人的ID
			userIdList.add(communioncommentreversionEXT.getToreversionuserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (CommunioncommentreversionEXT communioncommentreversionEXT : list) {
			if (null == communioncommentreversionEXT) {
				continue;
			}

			// --------------- 设置农事交流的评论的回复人和被回复人的信息 ----------------------
			setCreateUserInfo(communioncommentreversionEXT, supportUserEXTMap);
		}

		return list;
	}

	/**
	 * 检索农事交流的评论的回复的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunioncommentreversionEXT pCommunioncommentreversionEXT) throws Exception {
		return mCommunioncommentreversionEXTMapper.queryListTotalCount(pCommunioncommentreversionEXT);
	}

	/**
	 * 增加或者减少农事交流的评论的回复的回复数量（正数为增加、负数为减少）
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:54:57
	 *
	 * @param pCommunioncommentreversionEXT
	 *            农事交流的评论的回复的对象
	 * @return
	 * @throws Exception
	 */
	public Integer increaseOrDecreaseReversioncount(CommunioncommentreversionEXT pCommunioncommentreversionEXT)
			throws Exception {
		return mCommunioncommentreversionEXTMapper.increaseOrDecreaseReversioncount(pCommunioncommentreversionEXT);
	}

	/**
	 * 设置农事交流的评论人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param communioncommentreversionEXT
	 *            农事交流的评论的回复对象
	 * @throws Exception
	 */
	private void setCreateUserInfo(final CommunioncommentreversionEXT communioncommentreversionEXT,
			final Map<String, SupportUserEXT> supportUserEXTMap) throws Exception {

		if ((null == communioncommentreversionEXT) || (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		// ----------- 回复人 ------------------
		SupportUserEXT tempSupportUserEXT = supportUserEXTMap
				.get(ObjectUtil.toString(communioncommentreversionEXT.getReversionuserid()));

		if ((null == tempSupportUserEXT)) {
			communioncommentreversionEXT.setReversionuseridname("");
		} else {
			communioncommentreversionEXT.setReversionuseridname(ObjectUtil.toString(tempSupportUserEXT.getName()));// 姓名
			communioncommentreversionEXT.setReversionuseridnickname(tempSupportUserEXT.getNickname());// 昵称
			communioncommentreversionEXT.setPhotourl(tempSupportUserEXT.getPhotourl());// 头像
		}

		// ----------- 被回复人 ------------------
		tempSupportUserEXT = supportUserEXTMap
				.get(ObjectUtil.toString(communioncommentreversionEXT.getToreversionuserid()));

		if ((null == tempSupportUserEXT)) {
			communioncommentreversionEXT.setToreversionuseridname("");
		} else {
			communioncommentreversionEXT.setToreversionuseridname(ObjectUtil.toString(tempSupportUserEXT.getName()));// 姓名
			communioncommentreversionEXT.setToreversionuseridnickname(tempSupportUserEXT.getNickname());// 昵称
			communioncommentreversionEXT.setToreversionuseridphotourl(tempSupportUserEXT.getPhotourl());// 头像
		}

	}

}
