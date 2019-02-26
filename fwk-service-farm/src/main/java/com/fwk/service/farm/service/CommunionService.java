package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.IPNetworkInfo;
import com.fwk.common.util.IPUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EResourceType;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.entity.SupportOrgEXT;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.CommunionEXTMapper;
import com.fwk.service.farm.entity.CommunionEXT;
import com.fwk.service.farm.entity.CommunioncategoryEXT;
import com.fwk.service.farm.entity.CommunionparcelEXT;
import com.fwk.service.farm.entity.CommunionresourceEXT;
import com.fwk.service.farm.entity.CommunionuserpermisionsEXT;

/**
 * 农事管理模块 — 农事交流的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class CommunionService {

	// 农事管理模块 — 农事交流的数据库处理类
	@Autowired
	private CommunionEXTMapper mCommunionEXTMapper;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农事交流的地块的业务逻辑处理对象
	@Autowired
	private CommunionparcelService mCommunionparcelService;

	// 农事交流的附件资源文件的业务逻辑处理对象
	@Autowired
	private CommunionresourceService mCommunionresourceService;

	// 农事交流的品类的业务逻辑处理对象
	@Autowired
	private CommunioncategoryService mCommunioncategoryService;

	// 农事交流的数据权限的业务逻辑处理对象
	@Autowired
	private CommunionuserpermisionsService mCommunionuserpermisionsService;

	/**
	 * 新增农事交流
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @param loginUserInfo
	 *            当前操作的用户对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(CommunionEXT pCommunionEXT, final LoginUserInfo loginUserInfo) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = mCommunionEXTMapper.add(pCommunionEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Communion failure !");
		}

		// -------- 农事交流的地块列表-----------
		if ((null != pCommunionEXT.getCommunionparcellist()) && (pCommunionEXT.getCommunionparcellist().size() > 0)) {

			for (CommunionparcelEXT communionparcelEXT : pCommunionEXT.getCommunionparcellist()) {
				communionparcelEXT.setCommunionid(pCommunionEXT.getId());
			}

			resultAddOrUpdate = mCommunionparcelService.batchAdd(pCommunionEXT.getCommunionparcellist());

			if ((null == resultAddOrUpdate)
					|| (pCommunionEXT.getCommunionparcellist().size() != resultAddOrUpdate.intValue())) {
				throw new Exception("batch add Communionparcel failure !");
			}
		} // 添加农事交流的地块结束

		// ------ 农事交流的附件列表------------
		if ((null != pCommunionEXT.getResourcehistorylist()) && !(pCommunionEXT.getResourcehistorylist().isEmpty())) {

			CommunionresourceEXT recordresourceEXT = null;

			// 获取资源的上传历史
			ResourcehistoryEXT resourcehistoryEXT = null;

			for (int i = 0; i < pCommunionEXT.getResourcehistorylist().size(); i++) {
				resourcehistoryEXT = pCommunionEXT.getResourcehistorylist().get(i);

				resourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
						resourcehistoryEXT.getResourcename(), i);

				if ((null != resourcehistoryEXT) && (null != resourcehistoryEXT.getId())) {

					recordresourceEXT = new CommunionresourceEXT();
					recordresourceEXT.setCommunionid(pCommunionEXT.getId());
					recordresourceEXT.setResourceid(resourcehistoryEXT.getId());

					// 保存农事交流的附件资源
					resultAddOrUpdate = mCommunionresourceService.add(recordresourceEXT);

					if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
						throw new Exception("add Communionresource failure !");
					}
				}
			}

		} // 添加农事的附件结束

		// ------ 农事交流的品类列表------------
		if ((null != pCommunionEXT.getCommunioncategorylist())
				&& (pCommunionEXT.getCommunioncategorylist().size() > 0)) {

			for (CommunioncategoryEXT communioncategoryEXT : pCommunionEXT.getCommunioncategorylist()) {
				communioncategoryEXT.setCommunionid(pCommunionEXT.getId());// 设置农事交流的ID
			}

			resultAddOrUpdate = mCommunioncategoryService.batchAdd(pCommunionEXT.getCommunioncategorylist());

			if ((null == resultAddOrUpdate)
					|| (pCommunionEXT.getCommunioncategorylist().size() != resultAddOrUpdate.intValue())) {
				throw new Exception("batch add Communioncategory failure !");
			}
		} // 添加农事交流的品类结束

		// ----- 添加农事任务的数据权限 -----------------------------------------
		CommunionuserpermisionsEXT tempCommunionuserpermisionsEXT = null;
		if ((null == loginUserInfo.getDepartmentsupportidlist())
				|| (loginUserInfo.getDepartmentsupportidlist().isEmpty())) {

			tempCommunionuserpermisionsEXT = new CommunionuserpermisionsEXT();
			tempCommunionuserpermisionsEXT.setCommunionid(pCommunionEXT.getId());
			tempCommunionuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
			tempCommunionuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
			tempCommunionuserpermisionsEXT.setCreateuserdepartmentid(null);

			resultAddOrUpdate = mCommunionuserpermisionsService.add(tempCommunionuserpermisionsEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Communionuserpermisions failure !");
			}

		} else {

			List<CommunionuserpermisionsEXT> taskuserpermisionsEXTList = new ArrayList<CommunionuserpermisionsEXT>();

			for (String departmentsupportid : loginUserInfo.getDepartmentsupportidlist()) {

				tempCommunionuserpermisionsEXT = new CommunionuserpermisionsEXT();
				tempCommunionuserpermisionsEXT.setCommunionid(pCommunionEXT.getId());
				tempCommunionuserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
				tempCommunionuserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
				tempCommunionuserpermisionsEXT.setCreateuserdepartmentid(departmentsupportid);

				taskuserpermisionsEXTList.add(tempCommunionuserpermisionsEXT);
			}

			resultAddOrUpdate = mCommunionuserpermisionsService.batchAdd(taskuserpermisionsEXTList);

			if ((null == resultAddOrUpdate) || (taskuserpermisionsEXTList.size() != resultAddOrUpdate.intValue())) {
				throw new Exception("batch add Communionuserpermisions failure !");
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事交流
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(CommunionEXT pCommunionEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mCommunionEXTMapper.update(pCommunionEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Communion failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事交流
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @param originalCommunionEXT
	 *            原始的农事交流对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(CommunionEXT pCommunionEXT, final CommunionEXT originalCommunionEXT,
			final Map<String, Object> paramMap) throws Exception {

		/* ====== 原来已有的数据执行更新，原来不存在的数据执行添加，原来有的数据现在没有了就执行删除 ==== */

		// ---------- 编辑品类 ----------------
		this.editCommunioncategory(pCommunionEXT, originalCommunionEXT);

		// ---------- 编辑地块 ----------------
		this.editCommunionparcel(pCommunionEXT, originalCommunionEXT);

		// ---------- 编辑农事的资源文件 ----------------
		this.editCommunionresourceEXT(pCommunionEXT, originalCommunionEXT, paramMap);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pCommunionEXT, originalCommunionEXT, paramMap);

		return this.update(originalCommunionEXT);

	}

	/**
	 * 删除指定ID的农事交流
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(CommunionEXT pCommunionEXT) throws Exception {
		return mCommunionEXTMapper.deletebyid(pCommunionEXT);
	}

	/**
	 * 根据主键批量删除农事交流
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
		return mCommunionEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事交流
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public CommunionEXT queryById(CommunionEXT pCommunionEXT) throws Exception {
		CommunionEXT paramCommunionEXT = new CommunionEXT();
		paramCommunionEXT.setId(pCommunionEXT.getId());

		List<CommunionEXT> list = this.queryList(paramCommunionEXT);

		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		List<CommunionEXT> finalList = new ArrayList<CommunionEXT>();
		finalList.add(list.get(0));

		this.setDetail(finalList);

		return finalList.get(0);
	}

	/**
	 * 设置详情
	 * 
	 * @author wangjunwen
	 * @date 2018年4月18日 下午4:33:52
	 *
	 * @param communionEXTList
	 *            农事交流对象列表
	 * @throws Exception
	 */
	public void setDetail(final List<CommunionEXT> communionEXTList) throws Exception {
		if ((null == communionEXTList) || (communionEXTList.isEmpty())) {
			return;
		}

		for (CommunionEXT communionEXT : communionEXTList) {

			// ========== 农事交流的地块列表 =============================
			CommunionparcelEXT paramCommunionparcelEXT = new CommunionparcelEXT();
			paramCommunionparcelEXT.setCommunionid(communionEXT.getId());

			List<CommunionparcelEXT> recordparcelEXTList = mCommunionparcelService.queryList(paramCommunionparcelEXT);
			communionEXT.setCommunionparcellist(recordparcelEXTList);

			if ((null != recordparcelEXTList) && (recordparcelEXTList.size() > 0)) {
				for (CommunionparcelEXT recordparcelEXT : recordparcelEXTList) {

					// 设置在客户端显示用的地块信息
					recordparcelEXT.setParcelshowinfo((recordparcelEXT.getParcelidname()));
				}
			}

			// ========== 农事交流的附件列表 =============================
			CommunionresourceEXT paramCommunionresourceEXT = new CommunionresourceEXT();
			paramCommunionresourceEXT.setCommunionid(communionEXT.getId());

			List<CommunionresourceEXT> recordresourceEXTList = mCommunionresourceService
					.queryList(paramCommunionresourceEXT);

			communionEXT.setCommunionresourcelist(recordresourceEXTList);// 设置农事交流的资源文件列表

			if ((null != recordresourceEXTList) && (recordresourceEXTList.size() > 0)) {
				List<ResourcehistoryEXT> resourcehistorylist = new ArrayList<ResourcehistoryEXT>();

				ResourcehistoryEXT tempResourcehistoryEXT = null;
				for (CommunionresourceEXT recordresourceEXT : recordresourceEXTList) {
					tempResourcehistoryEXT = new ResourcehistoryEXT();
					tempResourcehistoryEXT.setResourcepath(recordresourceEXT.getResourcepath());// 资源的访问地址
					tempResourcehistoryEXT.setResourcename(recordresourceEXT.getResourcename());// 资源名称
					tempResourcehistoryEXT.setTimelength(recordresourceEXT.getTimelength());// 音、视频的时长

					resourcehistorylist.add(tempResourcehistoryEXT);
				}

				// 设置农事交流的资源文件
				communionEXT.setResourcehistorylist(resourcehistorylist);

				// 农事交流的音频文件列表
				List<ResourcehistoryEXT> audioResourcehistorylist = new ArrayList<ResourcehistoryEXT>();
				// 农事交流的图片文件列表
				List<ResourcehistoryEXT> imageResourcehistorylist = new ArrayList<ResourcehistoryEXT>();
				// 农事交流的其他未明确分类的文件列表
				List<ResourcehistoryEXT> otherResourcehistorylist = new ArrayList<ResourcehistoryEXT>();

				// 资源文件
				Iterator<ResourcehistoryEXT> resourceIterator = resourcehistorylist.iterator();

				ResourcehistoryEXT resourcehistoryEXT = null;// 音频资源
				EResourceType eResourceType = null;// 文件类型

				while (resourceIterator.hasNext()) {
					resourcehistoryEXT = resourceIterator.next();

					// 获取文件类型
					eResourceType = EResourceType
							.getResourceTypeByFileExtentionName(resourcehistoryEXT.getResourcepath());

					if (EResourceType.AUDIO.equals(eResourceType)) {// 音频文件

						audioResourcehistorylist.add(resourcehistoryEXT);// 添加到音频资源列表中

						// 其他资源和音频资源要分开显示，所以将音频资源移除掉
						resourceIterator.remove();

					} else if (EResourceType.PICTURE.equals(eResourceType)) {// 图片文件

						imageResourcehistorylist.add(resourcehistoryEXT);// 添加到农事交流的图片文件列表

					} else {// 其他文件

						otherResourcehistorylist.add(resourcehistoryEXT);// 添加到农事交流的其他未明确分类的文件列表

					}
				}

				// 设置农事交流的音频资源文件
				communionEXT.setAudioResourcehistorylist(audioResourcehistorylist);

				// 设置农事交流的图片文件列表
				communionEXT.setImageResourcehistorylist(imageResourcehistorylist);

				// 设置农事交流的其他未明确分类的文件列表
				communionEXT.setOtherResourcehistorylist(otherResourcehistorylist);
			}

			// ========== 品类列表 ================================
			CommunioncategoryEXT paramCommunioncategoryEXT = new CommunioncategoryEXT();
			paramCommunioncategoryEXT.setCommunionid(communionEXT.getId());

			List<CommunioncategoryEXT> CommunioncategoryEXTList = mCommunioncategoryService
					.queryList(paramCommunioncategoryEXT);

			communionEXT.setCommunioncategorylist(CommunioncategoryEXTList);
		}
	}

	/**
	 * 设置农事交流的发布人的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param communionEXT
	 *            农事交流对象
	 * @throws Exception
	 */
	private void setCreateUserInfo(final CommunionEXT communionEXT, final Map<String, SupportUserEXT> supportUserEXTMap)
			throws Exception {

		if ((null == communionEXT) || (null == supportUserEXTMap) || (supportUserEXTMap.isEmpty())) {
			return;
		}

		SupportUserEXT tempSupportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(communionEXT.getCreateuserid()));

		if ((null == tempSupportUserEXT)) {
			communionEXT.setCreateuseridname("");
		} else {
			communionEXT.setCreateuseridname(ObjectUtil.toString(tempSupportUserEXT.getName()));// 姓名
			communionEXT.setPhotourl(tempSupportUserEXT.getPhotourl());// 头像
		}

	}

	/**
	 * 设置农事交流的发布人的组织信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 下午1:20:08
	 *
	 * @param communionEXT
	 *            农事交流对象
	 * @param supportOrgEXTMap
	 *            组织信息
	 * @throws Exception
	 */
	private void setCreateUserOrgInfo(final CommunionEXT communionEXT,
			final Map<String, SupportOrgEXT> supportOrgEXTMap) throws Exception {

		if ((null == communionEXT) || (null == supportOrgEXTMap) || (supportOrgEXTMap.isEmpty())) {
			return;
		}

		SupportOrgEXT tempSupportOrgEXT = supportOrgEXTMap.get(ObjectUtil.toString(communionEXT.getUserloginorgid()));

		if ((null == tempSupportOrgEXT)) {
			communionEXT.setCreateuseridname("");
		} else {
			communionEXT.setOrgname(tempSupportOrgEXT.getName());
		}

	}

	/**
	 * 检索农事交流
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public List<CommunionEXT> queryList(CommunionEXT pCommunionEXT) throws Exception {
		final List<CommunionEXT> list = mCommunionEXTMapper.queryList(pCommunionEXT);

		if ((null == list) || (list.isEmpty())) {
			return list;
		}

		final List<Integer> userIdList = new ArrayList<Integer>();
		final List<String> userLoginOrgIdList = new ArrayList<String>();

		for (CommunionEXT communionEXT : list) {
			// 创建人的ID
			userIdList.add(communionEXT.getCreateuserid());

			// 用户添加农事交流时所登录的组织
			userLoginOrgIdList.add(communionEXT.getUserloginorgid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		// 获取发布人的组织信息（发布农事交流时所登录的组织）
		Map<String, SupportOrgEXT> supportOrgEXTMap = mCommonService.getOrgOrDepartmentList(userLoginOrgIdList);

		for (CommunionEXT communionEXT : list) {
			if (null == communionEXT) {
				continue;
			}

			// --------------- 设置农事交流的发布人的信息 ----------------------
			this.setCreateUserInfo(communionEXT, supportUserEXTMap);

			// --------------- 设置农事交流的发布人的组织信息 ----------------------
			this.setCreateUserOrgInfo(communionEXT, supportOrgEXTMap);
		}

		return list;
	}

	/**
	 * 检索农事交流的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(CommunionEXT pCommunionEXT) throws Exception {
		return mCommunionEXTMapper.queryListTotalCount(pCommunionEXT);
	}

	/**
	 * 编辑品类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramCommunionEXT
	 *            从客户端传过来的农事交流对象
	 * @param originalCommunionEXT
	 *            从数据库中检索出来的农事交流对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editCommunioncategory(final CommunionEXT paramCommunionEXT, final CommunionEXT originalCommunionEXT)
			throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的品类列表
		Iterator<CommunioncategoryEXT> paramCommunioncategoryEXTIterator = paramCommunionEXT.getCommunioncategorylist()
				.iterator();

		CommunioncategoryEXT paramCommunioncategoryEXT = null;
		while (paramCommunioncategoryEXTIterator.hasNext()) {
			paramCommunioncategoryEXT = paramCommunioncategoryEXTIterator.next();

			// 设置农事交流的ID，在后续需要添加的时候农事交流的ID才不会为空
			paramCommunioncategoryEXT.setCommunionid(paramCommunionEXT.getId());

			for (CommunioncategoryEXT originalCommunionpartuserEX : originalCommunionEXT.getCommunioncategorylist()) {
				if (originalCommunionpartuserEX.getCategoryid().equals(paramCommunioncategoryEXT.getCategoryid())) {
					// 该品类需要保留
					originalCommunionEXT.getCommunioncategorylist().remove(originalCommunionpartuserEX);

					// 当前品类原来就已经存在，把他移除掉，最终新增不存在的品类，删除原来已存在并且不需要保留的品类
					paramCommunioncategoryEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的品类
		if (originalCommunionEXT.getCommunioncategorylist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (CommunioncategoryEXT obj : originalCommunionEXT.getCommunioncategorylist()) {
				idList.add(obj.getId());// 需要刪除掉的品类
			}

			addOrUpdateRsult = mCommunioncategoryService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Communioncategory failure !");
			}
		}

		// 添加新的品类（历史的品类已更新）
		if (paramCommunionEXT.getCommunioncategorylist().size() > 0) {
			addOrUpdateRsult = mCommunioncategoryService.batchAdd(paramCommunionEXT.getCommunioncategorylist());

			if ((null == addOrUpdateRsult)
					|| (paramCommunionEXT.getCommunioncategorylist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Communioncategory failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑地块
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramCommunionEXT
	 *            从客户端传过来的农事交流对象
	 * @param originalCommunionEXT
	 *            从数据库中检索出来的农事交流对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editCommunionparcel(final CommunionEXT paramCommunionEXT, final CommunionEXT originalCommunionEXT)
			throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的地块列表
		Iterator<CommunionparcelEXT> paramCommunionparcelEXTIterator = paramCommunionEXT.getCommunionparcellist()
				.iterator();

		CommunionparcelEXT paramCommunionparcelEXT = null;
		while (paramCommunionparcelEXTIterator.hasNext()) {
			paramCommunionparcelEXT = paramCommunionparcelEXTIterator.next();

			// 设置农事交流的ID，在后续需要添加的时候农事交流的ID才不会为空
			paramCommunionparcelEXT.setCommunionid(paramCommunionEXT.getId());

			for (CommunionparcelEXT originalCommunionpartuserEX : originalCommunionEXT.getCommunionparcellist()) {
				if (originalCommunionpartuserEX.getParcelid().equals(paramCommunionparcelEXT.getParcelid())) {
					// 该地块需要保留
					originalCommunionEXT.getCommunionparcellist().remove(originalCommunionpartuserEX);

					// 当前地块原来就已经存在，把他移除掉，最终新增不存在的地块，删除原来已存在并且不需要保留的地块
					paramCommunionparcelEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的地块
		if (originalCommunionEXT.getCommunionparcellist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (CommunionparcelEXT obj : originalCommunionEXT.getCommunionparcellist()) {
				idList.add(obj.getId());// 需要刪除掉的地块
			}

			addOrUpdateRsult = mCommunionparcelService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Communionparcel failure !");
			}
		}

		// 添加新的地块（历史的地块已更新）
		if (paramCommunionEXT.getCommunionparcellist().size() > 0) {
			addOrUpdateRsult = mCommunionparcelService.batchAdd(paramCommunionEXT.getCommunionparcellist());

			if ((null == addOrUpdateRsult)
					|| (paramCommunionEXT.getCommunionparcellist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Communionparcel failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑农事交流的资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramCommunionEXT
	 *            从客户端传过来的农事交流对象
	 * @param originalCommunionEXT
	 *            从数据库中检索出来的农事交流对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editCommunionresourceEXT(final CommunionEXT paramCommunionEXT,
			final CommunionEXT originalCommunionEXT, final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的资源文件列表
		Iterator<ResourcehistoryEXT> paramResourcehistoryEXTIterator = paramCommunionEXT.getResourcehistorylist()
				.iterator();

		// 资源文件的排序号
		final AtomicInteger resourceOrderNo = new AtomicInteger(CommonConstantsUtil.DEFAULT_RESOURCE_ORDER_NO);

		ResourcehistoryEXT savedResourcehistoryEXT = null;
		CommunionresourceEXT addCommunionresourceEXT = null;

		ResourcehistoryEXT paramResourcehistoryEXT = null;
		while (paramResourcehistoryEXTIterator.hasNext()) {
			paramResourcehistoryEXT = paramResourcehistoryEXTIterator.next();

			for (CommunionresourceEXT originalCommunionresourceEXT : originalCommunionEXT.getCommunionresourcelist()) {

				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(
						originalCommunionresourceEXT.getResourceid(), paramResourcehistoryEXT.getResourcepath(),
						paramResourcehistoryEXT.getResourcename(), resourceOrderNo.getAndIncrement());

				if (savedResourcehistoryEXT.getId().equals(originalCommunionresourceEXT.getResourceid())) {

					// 当前上传的资源与原来的资源一致，所以需要保留
					originalCommunionEXT.getCommunionresourcelist().remove(originalCommunionresourceEXT);

				} else {

					// 当前上传的资源没有在原来的农事交流资源引用中，那么新增农事交流的资源引用
					addCommunionresourceEXT = new CommunionresourceEXT();
					addCommunionresourceEXT.setCommunionid(originalCommunionEXT.getId());// 设置农事交流的ID
					addCommunionresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置农事交流资源上传历史的ID

					addOrUpdateRsult = mCommunionresourceService.add(addCommunionresourceEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new Exception("update Communionresource failure !");
					}

				}

				// 当前投入品原来就已经存在，把他移除掉，最终新增不存在的投入品，删除原来已存在并且不需要保留的投入品
				paramResourcehistoryEXTIterator.remove();

				break;
			}
		}

		// 删除不需要保留的农事交流资源（历史的农事交流资源已更新）
		if (originalCommunionEXT.getCommunionresourcelist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (CommunionresourceEXT obj : originalCommunionEXT.getCommunionresourcelist()) {
				idList.add(obj.getId());// 需要刪除掉的农事交流资源
			}

			addOrUpdateRsult = mCommunionresourceService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Communionresource failure !");
			}

		}

		// 当前的资源记录数量大于农事交流的历史资源数量时添加新的农事交流资源
		if (paramCommunionEXT.getResourcehistorylist().size() > 0) {

			for (ResourcehistoryEXT resourcehistoryEXT : paramCommunionEXT.getResourcehistorylist()) {
				// 添加资源到历史资源中
				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
						resourcehistoryEXT.getResourcename(), resourceOrderNo.incrementAndGet());

				// 添加农事交流资源
				addCommunionresourceEXT = new CommunionresourceEXT();
				addCommunionresourceEXT.setCommunionid(originalCommunionEXT.getId());// 设置农事交流的ID
				addCommunionresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置农事交流资源上传历史的ID

				addOrUpdateRsult = mCommunionresourceService.add(addCommunionresourceEXT);

				if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
					throw new Exception("update Communionresource failure !");
				}
			}

		}

		return true;
	}

	/**
	 * 增加或者减少农事交流的评论数量（正数为增加、负数为减少）
	 * 
	 * @author wangjunwen
	 * @date 2018年3月1日 下午1:54:57
	 *
	 * @param pCommunionEXT
	 *            农事交流对象
	 * @return
	 * @throws Exception
	 */
	public Integer increaseOrDecreaseCommentcount(CommunionEXT pCommunionEXT) throws Exception {
		return mCommunionEXTMapper.increaseOrDecreaseCommentcount(pCommunionEXT);
	}

	/**
	 * 设置用户所在的地理位置
	 * 
	 * @author wangjunwen
	 * @date 2018年3月2日 下午1:31:35
	 *
	 * @param request
	 *            HttpServletRequest对象
	 * @param pCommunionEXT
	 *            CommunionEXT对象
	 */
	public void setLocation(HttpServletRequest request, CommunionEXT pCommunionEXT) {
		// 已经设置过地理位置了，那么就不再重新设置
		if ((null == pCommunionEXT) || (ObjectUtil.isNotBlank(pCommunionEXT.getLocation()))) {
			return;
		}

		try {
			IPNetworkInfo ipNetworkInfo = IPUtil.getNetworkInfo(request);

			if ((null != ipNetworkInfo)) {
				StringBuffer locationBuffer = new StringBuffer();

				locationBuffer.append(ipNetworkInfo.getArea()).append(" ").append(ipNetworkInfo.getCity());

				// 设置用户所在的地理位置
				pCommunionEXT.setLocation(locationBuffer.toString());
			}
		} catch (Exception e) {
			// 获取不到IP的网络信息时直接忽略掉
		}
	}

}
