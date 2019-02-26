package com.fwk.service.farm.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EResourceType;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.farm.dao.RecordEXTMapper;
import com.fwk.service.farm.entity.OutputEXT;
import com.fwk.service.farm.entity.RecordEXT;
import com.fwk.service.farm.entity.RecordfarmtypeEXT;
import com.fwk.service.farm.entity.RecordoutputEXT;
import com.fwk.service.farm.entity.RecordparcelEXT;
import com.fwk.service.farm.entity.RecordpartuserEXT;
import com.fwk.service.farm.entity.RecordresourceEXT;
import com.fwk.service.farm.entity.RecorduserpermisionsEXT;

/**
 * 农事管理模块 — 农事记录的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class RecordService {

	/** 地块的编号格式 */
	private static final DecimalFormat parcel_id_format = new DecimalFormat("0000");

	// 农事管理模块 — 农事记录的数据库处理类
	@Autowired
	private RecordEXTMapper mRecordEXTMapper;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农事记录的地块的业务逻辑处理对象
	@Autowired
	private RecordparcelService mRecordparcelService;

	// 农事记录的地块的业务逻辑处理对象
	@Autowired
	private RecordpartuserService mRecordpartuserService;

	// 农事记录的投入品的业务逻辑处理对象
	@Autowired
	private OutputService mOutputService;

	// 农事记录的投入品引用的业务逻辑处理对象
	@Autowired
	private RecordoutputService mRecordoutputService;

	// 农事记录的附件资源文件的业务逻辑处理对象
	@Autowired
	private RecordresourceService mRecordresourceService;

	// 农事记录的农事种类的业务逻辑处理对象
	@Autowired
	private RecordfarmtypeService mRecordfarmtypeService;

	// 农事记录的农事记录的数据权限的业务逻辑处理对象
	@Autowired
	private RecorduserpermisionsService mRecorduserpermisionsService;

	// 农事管理模块的公共业务逻辑处理对象
	@Autowired
	private FarmCommonService mFarmCommonService;

	/**
	 * 新增农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @param loginUserInfo
	 *            当前操作的用户对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(RecordEXT pRecordEXT, final LoginUserInfo loginUserInfo) throws Exception {

		// 将添加成功的所有ID返回去
		pRecordEXT.setIdlist(new ArrayList<Integer>());

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		RecordEXT savedRecordEXT = null;// 已保存的地块
		final List<RecordEXT> savedRecordEXTList = new ArrayList<RecordEXT>();// 已保存到数据库的农事记录列表

		// 已保存到数据库的农事记录数量
		final AtomicInteger saveCounter = new AtomicInteger(0);

		// 需要保存的记录数量
		final int needSaveCount = ((null == pRecordEXT.getRecordparcellist())
				|| (pRecordEXT.getRecordparcellist().isEmpty()) ? 1 : pRecordEXT.getRecordparcellist().size());

		// 有几个地块就添加几条农事记录，如果没有地块那就只添加一条农事记录
		do {
			savedRecordEXT = new RecordEXT();

			BeanUtils.copyProperties(pRecordEXT, savedRecordEXT);

			// 将数据添加到数据库
			resultAddOrUpdate = mRecordEXTMapper.add(savedRecordEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Record failure !");
			} else {
				// 添加到已保存到数据库的农事记录列表中
				savedRecordEXTList.add(savedRecordEXT);

				// 设置要返回去的所有ID
				pRecordEXT.getIdlist().add(savedRecordEXT.getId());
			}

			// 农事记录的地块列表（农事记录和地块是一对一的关系）
			if ((null != pRecordEXT.getRecordparcellist()) && (pRecordEXT.getRecordparcellist().size() > 0)) {
				// 设置农事记录的ID
				pRecordEXT.getRecordparcellist().get((saveCounter.get())).setRecordid(savedRecordEXT.getId());
			}

		} while ((saveCounter.incrementAndGet() < needSaveCount));// do while 结束

		// -------- 农事记录的地块列表（农事记录和地块是一对一的关系，一个农事最多对应一个地块）-----------
		if ((null != pRecordEXT.getRecordparcellist()) && (pRecordEXT.getRecordparcellist().size() > 0)) {
			resultAddOrUpdate = mRecordparcelService.batchAdd(pRecordEXT.getRecordparcellist());

			if ((null == resultAddOrUpdate)
					|| (pRecordEXT.getRecordparcellist().size() != resultAddOrUpdate.intValue())) {
				throw new Exception("batch add Recordparcel failure !");
			}
		} // 添加农事记录的地块结束

		// 农事记录的数据权限对象
		RecorduserpermisionsEXT tempRecorduserpermisionsEXT = null;

		// ============== 保存与农事记录是多对一的关系的其他记录 =======================
		for (RecordEXT recordEXT : savedRecordEXTList) {

			// ----- 农事记录的参人列表（农事记录与农事参与人是一对多的关系，一个农事可以有多个参与人）------------
			if ((null != pRecordEXT.getRecordpartuserlist()) && (pRecordEXT.getRecordpartuserlist().size() > 0)) {

				for (RecordpartuserEXT recordpartuserEXT : pRecordEXT.getRecordpartuserlist()) {
					recordpartuserEXT.setRecordid(recordEXT.getId());// 设置农事记录的ID
				}

				resultAddOrUpdate = mRecordpartuserService.batchAdd(pRecordEXT.getRecordpartuserlist());

				if ((null == resultAddOrUpdate)
						|| (pRecordEXT.getRecordpartuserlist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Recordparcel failure !");
				}
			} // 添加农事记录的参人结束

			// ------ 农事记录的投入品列表（农事记录与投入品是一对多的关系，一个农事可以有多个投入品）-----------
			if ((null != pRecordEXT.getOutputlist()) && (pRecordEXT.getOutputlist().size() > 0)) {

				resultAddOrUpdate = mOutputService.batchAdd(pRecordEXT.getOutputlist());

				if ((null == resultAddOrUpdate)
						|| (pRecordEXT.getOutputlist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Output failure !");
				}

				// 农事记录投入品引用对象列表
				List<RecordoutputEXT> recordoutputEXTList = new ArrayList<RecordoutputEXT>();

				RecordoutputEXT tempRecordoutputEXT = null;
				for (OutputEXT outputEXT : pRecordEXT.getOutputlist()) {
					tempRecordoutputEXT = new RecordoutputEXT();
					tempRecordoutputEXT.setRecordid(recordEXT.getId());// 设置农事记录的ID
					tempRecordoutputEXT.setOutputid(outputEXT.getId());// 设置农事投入品的ID

					recordoutputEXTList.add(tempRecordoutputEXT);
				}

				resultAddOrUpdate = mRecordoutputService.batchAdd(recordoutputEXTList);

				if ((null == resultAddOrUpdate)
						|| (pRecordEXT.getOutputlist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Recordoutput failure !");
				}

			} // 添加农事记录的投入品结束

			// ------ 农事记录的附件列表（农事记录与附件是一对多的关系，一个农事可以有多个附件）------------
			if ((null != pRecordEXT.getResourcehistorylist()) && !(pRecordEXT.getResourcehistorylist().isEmpty())) {

				RecordresourceEXT recordresourceEXT = null;

				// 获取资源的上传历史
				ResourcehistoryEXT resourcehistoryEXT = null;

				for (int i = 0; i < pRecordEXT.getResourcehistorylist().size(); i++) {
					resourcehistoryEXT = pRecordEXT.getResourcehistorylist().get(i);

					resourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
							resourcehistoryEXT.getResourcename(), i, resourcehistoryEXT.getTimelength());

					if ((null != resourcehistoryEXT) && (null != resourcehistoryEXT.getId())) {

						recordresourceEXT = new RecordresourceEXT();
						recordresourceEXT.setRecordid(recordEXT.getId());
						recordresourceEXT.setResourceid(resourcehistoryEXT.getId());

						// 保存农事记录的附件资源
						resultAddOrUpdate = mRecordresourceService.add(recordresourceEXT);

						if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
							throw new Exception("add Recordresource failure !");
						}
					}
				}

			} // 添加农事的附件结束

			// ------ 农事记录的农事种类列表（农事记录与农事种类是一对多的关系，一个农事可以有多个农事种类）------------
			if ((null != pRecordEXT.getFarmtypelist()) && (pRecordEXT.getFarmtypelist().size() > 0)) {

				for (RecordfarmtypeEXT recordfarmtypeEXT : pRecordEXT.getFarmtypelist()) {
					recordfarmtypeEXT.setRecordid(recordEXT.getId());// 设置农事记录的ID
				}

				resultAddOrUpdate = mRecordfarmtypeService.batchAdd(pRecordEXT.getFarmtypelist());

				if ((null == resultAddOrUpdate)
						|| (pRecordEXT.getFarmtypelist().size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Recordfarmtype failure !");
				}
			} // 添加农事记录的农事种类结束

			// ----- 添加农事记录的数据权限 -----------------------------------------
			if ((null == loginUserInfo.getDepartmentsupportidlist())
					|| (loginUserInfo.getDepartmentsupportidlist().isEmpty())) {

				tempRecorduserpermisionsEXT = new RecorduserpermisionsEXT();
				tempRecorduserpermisionsEXT.setRecordid(recordEXT.getId());
				tempRecorduserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
				tempRecorduserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
				tempRecorduserpermisionsEXT.setCreateuserdepartmentid(null);

				resultAddOrUpdate = mRecorduserpermisionsService.add(tempRecorduserpermisionsEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("add Recorduserpermisions failure !");
				}

			} else {

				List<RecorduserpermisionsEXT> recorduserpermisionsEXTList = new ArrayList<RecorduserpermisionsEXT>();

				for (String departmentsupportid : loginUserInfo.getDepartmentsupportidlist()) {
					tempRecorduserpermisionsEXT = new RecorduserpermisionsEXT();
					tempRecorduserpermisionsEXT.setRecordid(recordEXT.getId());
					tempRecorduserpermisionsEXT.setCreateuserid(loginUserInfo.getId());
					tempRecorduserpermisionsEXT.setCreateuserorgid(loginUserInfo.getLoginorgid());
					tempRecorduserpermisionsEXT.setCreateuserdepartmentid(departmentsupportid);

					recorduserpermisionsEXTList.add(tempRecorduserpermisionsEXT);
				}

				resultAddOrUpdate = mRecorduserpermisionsService.batchAdd(recorduserpermisionsEXTList);

				if ((null == resultAddOrUpdate)
						|| (recorduserpermisionsEXTList.size() != resultAddOrUpdate.intValue())) {
					throw new Exception("batch add Recorduserpermisions failure !");
				}
			}

		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(RecordEXT pRecordEXT) throws Exception {
		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mRecordEXTMapper.update(pRecordEXT);
		if (null == addOrUpdateRsult) {
			throw new Exception("update Record failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @param originalRecordEXT
	 *            原始的农事记录对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(RecordEXT pRecordEXT, final RecordEXT originalRecordEXT, final Map<String, Object> paramMap)
			throws Exception {

		/* ====== 原来已有的数据执行更新，原来不存在的数据执行添加，原来有的数据现在没有了就执行删除 ==== */

		
		
		// ---------- 编辑农事类别 ----------------
		this.editFarmtype(pRecordEXT, originalRecordEXT);

		// ---------- 编辑农事参数与人 ----------------
		this.editRecordpartuser(pRecordEXT, originalRecordEXT);

		// ---------- 编辑农事投入品 ----------------
		this.editOutputEXT(pRecordEXT, originalRecordEXT, paramMap);

		// ---------- 编辑农事的资源文件 ----------------
		this.editRecordresourceEXT(pRecordEXT, originalRecordEXT, paramMap);
		
		//xx ---------- 编辑农事的地块 ----------------
		this.editRecordParcellist(pRecordEXT);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pRecordEXT, originalRecordEXT, paramMap);

		return this.update(originalRecordEXT);

	}

	//xx:添加农事记录的地块信息
	private void editRecordParcellist(RecordEXT pRecordEXT) {
		// TODO Auto-generated method stub
		if(ObjectUtil.isNotBlank(pRecordEXT) && ObjectUtil.isNotBlank(pRecordEXT.getId()) && ObjectUtil.isNotBlank(pRecordEXT.getRecordparcellist()) && pRecordEXT.getRecordparcellist().size() >= 1) {
			//删除该记录对应的地块数据 t_f_record_parcel
			mRecordparcelService.deletebyrecordid(pRecordEXT.getId());
			
			for (RecordparcelEXT recordparcelEXT : pRecordEXT.getRecordparcellist()) {
				mRecordparcelService.addByRecordParce(pRecordEXT.getId(),recordparcelEXT.getParcelid());
			}
		}
	}

	/**
	 * 删除指定ID的农事记录
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(RecordEXT pRecordEXT) throws Exception {
		return mRecordEXTMapper.deletebyid(pRecordEXT);
	}

	/**
	 * 根据主键批量删除农事记录
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
		return mRecordEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	public RecordEXT queryById(RecordEXT pRecordEXT) throws Exception {
		RecordEXT paramRecordEXT = new RecordEXT();
		paramRecordEXT.setId(pRecordEXT.getId());

		List<RecordEXT> list = this.queryList(paramRecordEXT);

		if ((null != list) && (list.size() > 0)) {

//			for (RecordEXT recordEXT : list) {
				RecordEXT recordEXT = list.get(0);
				// ========== 农事记录的地块列表 =============================
				RecordparcelEXT paramRecordparcelEXT = new RecordparcelEXT();
				paramRecordparcelEXT.setRecordid(recordEXT.getId());

				List<RecordparcelEXT> recordparcelEXTList = mRecordparcelService.queryList(paramRecordparcelEXT);
				recordEXT.setRecordparcellist(recordparcelEXTList);

				if ((null != recordparcelEXTList) && (recordparcelEXTList.size() > 0)) {
					for (RecordparcelEXT recordparcelEXT : recordparcelEXTList) {

						// 设置在客户端显示用的地块信息
						recordparcelEXT.setParcelshowinfo((recordparcelEXT.getParcelname() + "（"
								+ ObjectUtil.toString(recordparcelEXT.getAddresscode()) + "-"
								+ parcel_id_format.format(recordparcelEXT.getParcelid()) + "）"));
					}
				}

				// ========== 农事记录的参人列表 =============================
				// this.setRecordpartuserList(recordEXT);// 已经在查询列表时就设置过了参与人

				// ========== 农事记录的投入品列表 ============================
				OutputEXT paramOutputEXT = new OutputEXT();
				paramOutputEXT.setRecordoutputrecordid(recordEXT.getId());

				List<OutputEXT> outputlist = mOutputService.queryList(paramOutputEXT);
				recordEXT.setOutputlist(outputlist);

				// ========== 农事记录的附件列表 =============================
				RecordresourceEXT paramRecordresourceEXT = new RecordresourceEXT();
				paramRecordresourceEXT.setRecordid(recordEXT.getId());

				List<RecordresourceEXT> recordresourceEXTList = mRecordresourceService
						.queryList(paramRecordresourceEXT);

				recordEXT.setRecordresourceEXTList(recordresourceEXTList);// 设置农事记录的资源文件列表

				if ((null != recordresourceEXTList) && (recordresourceEXTList.size() > 0)) {
					List<ResourcehistoryEXT> resourcehistorylist = new ArrayList<ResourcehistoryEXT>();

					ResourcehistoryEXT tempResourcehistoryEXT = null;
					for (RecordresourceEXT recordresourceEXT : recordresourceEXTList) {
						tempResourcehistoryEXT = new ResourcehistoryEXT();
						tempResourcehistoryEXT.setResourcepath(recordresourceEXT.getResourcepath());// 资源的访问地址
						tempResourcehistoryEXT.setResourcename(recordresourceEXT.getResourcename());// 资源名称
						tempResourcehistoryEXT.setTimelength(recordresourceEXT.getTimelength());// 音、视频的时长

						resourcehistorylist.add(tempResourcehistoryEXT);
					}

					// 设置农事记录的资源文件
					recordEXT.setResourcehistorylist(resourcehistorylist);

					// 农事记录的音频文件列表
					List<ResourcehistoryEXT> audioResourcehistorylist = new ArrayList<ResourcehistoryEXT>();
					// 农事记录的图片文件列表
					List<ResourcehistoryEXT> imageResourcehistorylist = new ArrayList<ResourcehistoryEXT>();
					// 农事记录的其他未明确分类的文件列表
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

							imageResourcehistorylist.add(resourcehistoryEXT);// 添加到农事记录的图片文件列表

						} else {// 其他文件

							otherResourcehistorylist.add(resourcehistoryEXT);// 添加到农事记录的其他未明确分类的文件列表

						}
					}

					// 设置农事记录的音频资源文件
					recordEXT.setAudioResourcehistorylist(audioResourcehistorylist);

					// 设置农事记录的图片文件列表
					recordEXT.setImageResourcehistorylist(imageResourcehistorylist);

					// 设置农事记录的其他未明确分类的文件列表
					recordEXT.setOtherResourcehistorylist(otherResourcehistorylist);
				}

				// ========== 农事类别列表 ================================
				RecordfarmtypeEXT paramRecordfarmtypeEXT = new RecordfarmtypeEXT();
				paramRecordfarmtypeEXT.setRecordid(recordEXT.getId());

				List<RecordfarmtypeEXT> farmtypelist = mRecordfarmtypeService.queryList(paramRecordfarmtypeEXT);

				recordEXT.setFarmtypelist(farmtypelist);

//			}

			return list.get(0);
		}

		return null;
	}

	/**
	 * 设置农事记录的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param recordEXT
	 *            农事记录对象
	 * @throws Exception
	 */
	public void setRecordpartuserList(final RecordEXT recordEXT) throws Exception {

		if (null == recordEXT) {
			return;
		}

		RecordpartuserEXT paramRecordpartuserEXT = new RecordpartuserEXT();
		paramRecordpartuserEXT.setRecordid(recordEXT.getId());

		List<RecordpartuserEXT> recordpartuserlist = mRecordpartuserService.queryList(paramRecordpartuserEXT);

		if ((null == recordpartuserlist) || (recordpartuserlist.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;

		SupportUserEXT paramSupportUserEXT = null;
		for (RecordpartuserEXT recordpartuserEXT : recordpartuserlist) {
			paramSupportUserEXT = new SupportUserEXT();
			paramSupportUserEXT.setId(recordpartuserEXT.getUserid());
			paramSupportUserEXT.setSupportid(recordpartuserEXT.getUsersupportid());

			supportUserEXT = mCommonService.querySupportUserEXTByIdOrSupportid(paramSupportUserEXT);

			if (null != supportUserEXT) {
				recordpartuserEXT.setUsername(supportUserEXT.getName());
				recordpartuserEXT.setUsernickname(supportUserEXT.getNickname());
				recordpartuserEXT.setUserphotourl(supportUserEXT.getPhotourl());

				if (usernameBuffer.length() > 0) {
					usernameBuffer.append("、").append(recordpartuserEXT.getUsername());
				} else {
					usernameBuffer.append(recordpartuserEXT.getUsername());
				}

				if (useridBuffer.length() > 0) {
					useridBuffer.append(",").append(recordpartuserEXT.getUserid());
				} else {
					useridBuffer.append(recordpartuserEXT.getUserid());
				}

			}
		}

		recordEXT.setRecordpartusernames(usernameBuffer.toString());
		recordEXT.setRecordpartuserids(useridBuffer.toString());

		recordEXT.setRecordpartuserlist(recordpartuserlist);
	}

	/**
	 * 设置农事记录的参与人列表
	 * 
	 * @author wangjunwen
	 * @date 2018年3月30日 下午4:50:56
	 *
	 * @param recordEXT
	 *            农事记录对象
	 * @param supportUserEXTMap
	 *            用户信息
	 * @throws Exception
	 */
	public void setRecordpartuserList(final RecordEXT recordEXT, final Map<String, SupportUserEXT> supportUserEXTMap)
			throws Exception {

		if ((null == recordEXT) || (null == recordEXT.getRecordpartuserlist())
				|| (recordEXT.getRecordpartuserlist().isEmpty()) || (null == supportUserEXTMap)
				|| (supportUserEXTMap.isEmpty())) {
			return;
		}

		StringBuffer usernameBuffer = new StringBuffer();// 参与人姓名
		StringBuffer useridBuffer = new StringBuffer();// 参与人的ID

		SupportUserEXT supportUserEXT = null;
		for (RecordpartuserEXT recordpartuserEXT : recordEXT.getRecordpartuserlist()) {

			supportUserEXT = supportUserEXTMap.get(ObjectUtil.toString(recordpartuserEXT.getUserid()));

			if (null != supportUserEXT) {
				recordpartuserEXT.setUsername(supportUserEXT.getName());
				recordpartuserEXT.setUsernickname(supportUserEXT.getNickname());
				recordpartuserEXT.setUserphotourl(supportUserEXT.getPhotourl());

				if (usernameBuffer.length() > 0) {
					usernameBuffer.append("、").append(recordpartuserEXT.getUsername());
				} else {
					usernameBuffer.append(recordpartuserEXT.getUsername());
				}

				if (useridBuffer.length() > 0) {
					useridBuffer.append(",").append(recordpartuserEXT.getUserid());
				} else {
					useridBuffer.append(recordpartuserEXT.getUserid());
				}

			}
		}

		recordEXT.setRecordpartusernames(usernameBuffer.toString());
		recordEXT.setRecordpartuserids(useridBuffer.toString());
	}

	/**
	 * 检索农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	public List<RecordEXT> queryList(RecordEXT pRecordEXT) throws Exception {
		final List<RecordEXT> list = mRecordEXTMapper.queryList(pRecordEXT);

		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		List<Integer> userIdList = new ArrayList<Integer>();
		List<Integer> recordIdList = new ArrayList<Integer>();

		for (RecordEXT recordEXT : list) {
			recordIdList.add(recordEXT.getId());
		}

		RecordpartuserEXT paramRecordpartuserEXT = new RecordpartuserEXT();
		paramRecordpartuserEXT.setRecordidlist(recordIdList);

		List<RecordpartuserEXT> recordpartuserEXTList = mRecordpartuserService.queryList(paramRecordpartuserEXT);

		for (RecordpartuserEXT recordpartuserEXT : recordpartuserEXTList) {
			for (RecordEXT recordEXT : list) {
				if (recordEXT.getId().equals(recordpartuserEXT.getRecordid())) {
					if (null == recordEXT.getRecordpartuserlist()) {
						recordEXT.setRecordpartuserlist(new ArrayList<RecordpartuserEXT>());
					}

					// 添加到农事记录的参与人列表中
					recordEXT.getRecordpartuserlist().add(recordpartuserEXT);
					break;
				}
			}

			// 参与人的ID
			userIdList.add(recordpartuserEXT.getUserid());
		}

		// 获取用户信息
		Map<String, SupportUserEXT> supportUserEXTMap = mCommonService.querySupportUserEXTByUseridList(userIdList);

		for (RecordEXT recordEXT : list) {
			if (null == recordEXT) {
				continue;
			}

			// ========== 农事记录的参人列表 =============================
			this.setRecordpartuserList(recordEXT, supportUserEXTMap);

		}

		return list;
	}

	/**
	 * 检索农事记录的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(RecordEXT pRecordEXT) throws Exception {
		return mRecordEXTMapper.queryListTotalCount(pRecordEXT);
	}

	/**
	 * 编辑农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramRecordEXT
	 *            从客户端传过来的农事记录对象
	 * @param originalRecordEXT
	 *            从数据库中检索出来的农事记录对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editFarmtype(final RecordEXT paramRecordEXT, final RecordEXT originalRecordEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if ((null == paramRecordEXT.getFarmtypelist())) {
			paramRecordEXT.setFarmtypelist(new ArrayList<RecordfarmtypeEXT>());
		}

		if ((null == originalRecordEXT.getFarmtypelist())) {
			originalRecordEXT.setFarmtypelist(new ArrayList<RecordfarmtypeEXT>());
		}

		// 从客户端传过来的农事类别列表
		Iterator<RecordfarmtypeEXT> paramRecordfarmtypeEXTIterator = paramRecordEXT.getFarmtypelist().iterator();

		RecordfarmtypeEXT paramRecordfarmtypeEXT = null;
		while (paramRecordfarmtypeEXTIterator.hasNext()) {
			paramRecordfarmtypeEXT = paramRecordfarmtypeEXTIterator.next();

			// 设置农事记录的ID，在后续需要添加的时候农事记录的ID才不会为空
			
			if(ObjectUtil.isNotBlank(paramRecordEXT) && ObjectUtil.isNotBlank(paramRecordEXT.getId())) {
				paramRecordfarmtypeEXT.setRecordid(paramRecordEXT.getId());
			}else if(ObjectUtil.isNotBlank(paramRecordEXT) && ObjectUtil.isNotBlank(paramRecordEXT.getIdlist()) && paramRecordEXT.getIdlist().size() >= 1) {
				paramRecordfarmtypeEXT.setRecordid(paramRecordEXT.getIdlist().get(0));
			}

			//xx,这里删除后保存的类型数据只剩一条，所以注释代码
//			for (RecordfarmtypeEXT originalRecordpartuserEX : originalRecordEXT.getFarmtypelist()) {
//				if (originalRecordpartuserEX.getFarmtype().equals(paramRecordfarmtypeEXT.getFarmtype())) {
//					// 该农事类别需要保留
//					originalRecordEXT.getFarmtypelist().remove(originalRecordpartuserEX);
//
//					// 当前农事类别原来就已经存在，把他移除掉，最终新增不存在的农事类别，删除原来已存在并且不需要保留的农事类别
//					paramRecordfarmtypeEXTIterator.remove();
//
//					break;
//				}
//			}
		}

		// 删除不需要保留的农事类别
		if (originalRecordEXT.getFarmtypelist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (RecordfarmtypeEXT obj : originalRecordEXT.getFarmtypelist()) {
				idList.add(obj.getId());// 需要刪除掉的农事类别
			}

			addOrUpdateRsult = mRecordfarmtypeService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Recordfarmtype failure !");
			}
		}

		// 添加新的农事类别（历史的农事类别已更新）
		if (paramRecordEXT.getFarmtypelist().size() > 0) {
			addOrUpdateRsult = mRecordfarmtypeService.batchAdd(paramRecordEXT.getFarmtypelist());

			if ((null == addOrUpdateRsult)
					|| (paramRecordEXT.getFarmtypelist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Recordfarmtype failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑参与人
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramRecordEXT
	 *            从客户端传过来的农事记录对象
	 * @param originalRecordEXT
	 *            从数据库中检索出来的农事记录对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editRecordpartuser(final RecordEXT paramRecordEXT, final RecordEXT originalRecordEXT)
			throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if ((null == paramRecordEXT.getRecordpartuserlist())) {
			paramRecordEXT.setRecordpartuserlist(new ArrayList<RecordpartuserEXT>());
		}

		if ((null == originalRecordEXT.getRecordpartuserlist())) {
			originalRecordEXT.setRecordpartuserlist(new ArrayList<RecordpartuserEXT>());
		}

		// 从客户端传过来的参与人列表
		Iterator<RecordpartuserEXT> paramRecordpartuserEXTIterator = paramRecordEXT.getRecordpartuserlist().iterator();

		RecordpartuserEXT paramRecordpartuserEXT = null;
		while (paramRecordpartuserEXTIterator.hasNext()) {
			paramRecordpartuserEXT = paramRecordpartuserEXTIterator.next();

			// 设置农事记录的ID，在后续需要添加的时候农事记录的ID才不会为空
			paramRecordpartuserEXT.setRecordid(paramRecordEXT.getId());

			for (RecordpartuserEXT originalRecordpartuserEX : originalRecordEXT.getRecordpartuserlist()) {
				if (originalRecordpartuserEX.getUserid().equals(paramRecordpartuserEXT.getUserid())) {
					// 该参与人需要保留
					originalRecordEXT.getRecordpartuserlist().remove(originalRecordpartuserEX);

					// 当前参与人原来就已经存在，把他移除掉，最终新增不存在的参与人，删除原来已存在并且不需要保留的参与人
					paramRecordpartuserEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的参与人
		if (originalRecordEXT.getRecordpartuserlist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (RecordpartuserEXT obj : originalRecordEXT.getRecordpartuserlist()) {
				idList.add(obj.getId());// 需要刪除掉的参与人
			}

			addOrUpdateRsult = mRecordpartuserService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Recordpartuser failure !");
			}
		}

		// 添加新的参与人（历史的参与人已更新）
		if (paramRecordEXT.getRecordpartuserlist().size() > 0) {
			addOrUpdateRsult = mRecordpartuserService.batchAdd(paramRecordEXT.getRecordpartuserlist());

			if ((null == addOrUpdateRsult)
					|| (paramRecordEXT.getRecordpartuserlist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Recordpartuser failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑投入品
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramRecordEXT
	 *            从客户端传过来的农事记录对象
	 * @param originalRecordEXT
	 *            从数据库中检索出来的农事记录对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editOutputEXT(final RecordEXT paramRecordEXT, final RecordEXT originalRecordEXT,
			final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if ((null == paramRecordEXT.getOutputlist())) {
			paramRecordEXT.setOutputlist(new ArrayList<OutputEXT>());
		}

		if ((null == originalRecordEXT.getOutputlist())) {
			originalRecordEXT.setOutputlist(new ArrayList<OutputEXT>());
		}

		// 从客户端传过来的投入品列表
		Iterator<OutputEXT> paramOutputEXTIterator = paramRecordEXT.getOutputlist().iterator();

		OutputEXT paramOutputEXT = null;
		while (paramOutputEXTIterator.hasNext()) {
			paramOutputEXT = paramOutputEXTIterator.next();

			for (OutputEXT originalOutputEXT : originalRecordEXT.getOutputlist()) {
				if (originalOutputEXT.getId().equals(paramOutputEXT.getId())) {

					// 把客户端的值更新到数据库中查询到的对象上
					HTMLUtil.updateObject(paramOutputEXT, originalOutputEXT,
							(JSONObject) JSONObject.toJSON(paramOutputEXT));

					addOrUpdateRsult = mOutputService.update(originalOutputEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new Exception("update Output failure !");
					}

					// 需要保留的农事记录投入品
					originalRecordEXT.getOutputlist().remove(originalOutputEXT);

					// 当前投入品原来就已经存在，把他移除掉，最终新增不存在的投入品，删除原来已存在并且不需要保留的投入品
					paramOutputEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的投入品
		if (originalRecordEXT.getOutputlist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (OutputEXT obj : originalRecordEXT.getOutputlist()) {
				idList.add(obj.getId());// 需要刪除掉的投入品
			}

			addOrUpdateRsult = mOutputService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Output failure !");
			}

			// 同时需要查询出投入品的引用，并删除掉投入品的引用
			final List<RecordoutputEXT> deleteRecordoutputEXTList = new ArrayList<RecordoutputEXT>();

			RecordoutputEXT paramRecordoutputEXT = null;
			List<RecordoutputEXT> originalRecordoutputEXTList = null;

			for (Integer outputid : idList) {
				paramRecordoutputEXT = new RecordoutputEXT();
				paramRecordoutputEXT.setRecordid(paramRecordEXT.getId());// 农事记录的ID
				paramRecordoutputEXT.setOutputid(outputid);

				originalRecordoutputEXTList = mRecordoutputService.queryList(paramRecordoutputEXT);

				if ((null != originalRecordoutputEXTList) && (originalRecordoutputEXTList.size() > 0)) {
					deleteRecordoutputEXTList.addAll(originalRecordoutputEXTList);
				}
			}

			if (deleteRecordoutputEXTList.size() > 0) {
				idList = new ArrayList<Integer>();
				for (RecordoutputEXT obj : deleteRecordoutputEXTList) {
					idList.add(obj.getId());// 需要刪除掉的投入品引用
				}

				addOrUpdateRsult = mRecordoutputService.batchdeletebyids(idList);

				if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
					throw new Exception("batch delete Recordoutput failure !");
				}
			}

		}

		// 当前的投入品数量大于历史的投入品数量时新增投入品（历史的投入品记录已更新）
		if (paramRecordEXT.getOutputlist().size() > 0) {
			addOrUpdateRsult = mOutputService.batchAdd(paramRecordEXT.getOutputlist());

			if ((null == addOrUpdateRsult) || (paramRecordEXT.getOutputlist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Output failure !");
			}

			// 需要新增的投入品引用
			final List<RecordoutputEXT> addRecordoutputEXTList = new ArrayList<RecordoutputEXT>();
			RecordoutputEXT tempRecordoutputEXT = null;

			for (OutputEXT outputEXT : paramRecordEXT.getOutputlist()) {
				tempRecordoutputEXT = new RecordoutputEXT();
				tempRecordoutputEXT.setRecordid(paramRecordEXT.getId());// 农事记录的ID
				tempRecordoutputEXT.setOutputid(outputEXT.getId());// 投入品的ID

				// 需要新增的农事投入品的引用
				addRecordoutputEXTList.add(tempRecordoutputEXT);
			}

			addOrUpdateRsult = mRecordoutputService.batchAdd(addRecordoutputEXTList);

			if ((null == addOrUpdateRsult) || (addRecordoutputEXTList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Recordoutput failure !");
			}
		}

		return true;
	}

	/**
	 * 编辑农事记录的资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramRecordEXT
	 *            从客户端传过来的农事记录对象
	 * @param originalRecordEXT
	 *            从数据库中检索出来的农事记录对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editRecordresourceEXT(final RecordEXT paramRecordEXT, final RecordEXT originalRecordEXT,
			final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if ((null == paramRecordEXT.getResourcehistorylist())) {
			paramRecordEXT.setResourcehistorylist(new ArrayList<ResourcehistoryEXT>());
		}

		if (null == originalRecordEXT.getRecordresourceEXTList()) {
			originalRecordEXT.setRecordresourceEXTList(new ArrayList<RecordresourceEXT>());
		}

		// 从客户端传过来的资源文件列表
		Iterator<ResourcehistoryEXT> paramResourcehistoryEXTIterator = paramRecordEXT.getResourcehistorylist()
				.iterator();

		// 资源文件的排序号
		final AtomicInteger resourceOrderNo = new AtomicInteger(CommonConstantsUtil.DEFAULT_RESOURCE_ORDER_NO);

		ResourcehistoryEXT savedResourcehistoryEXT = null;
		RecordresourceEXT addRecordresourceEXT = null;

		ResourcehistoryEXT paramResourcehistoryEXT = null;
		while (paramResourcehistoryEXTIterator.hasNext()) {
			paramResourcehistoryEXT = paramResourcehistoryEXTIterator.next();

			for (RecordresourceEXT originalRecordresourceEXT : originalRecordEXT.getRecordresourceEXTList()) {

				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(originalRecordresourceEXT.getResourceid(),
						paramResourcehistoryEXT.getResourcepath(), paramResourcehistoryEXT.getResourcename(),
						resourceOrderNo.getAndIncrement(), paramResourcehistoryEXT.getTimelength());

				if (savedResourcehistoryEXT.getId().equals(originalRecordresourceEXT.getResourceid())) {

					// 当前上传的资源与原来的资源一致，所以需要保留
					originalRecordEXT.getRecordresourceEXTList().remove(originalRecordresourceEXT);

				} else {

					// 当前上传的资源没有在原来的农事记录资源引用中，那么新增农事记录的资源引用
					addRecordresourceEXT = new RecordresourceEXT();
					addRecordresourceEXT.setRecordid(originalRecordEXT.getId());// 设置农事记录的ID
					addRecordresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置农事记录资源上传历史的ID

					addOrUpdateRsult = mRecordresourceService.add(addRecordresourceEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new Exception("update Recordresource failure !");
					}

				}

				// 当前投入品原来就已经存在，把他移除掉，最终新增不存在的投入品，删除原来已存在并且不需要保留的投入品
				paramResourcehistoryEXTIterator.remove();

				break;
			}
		}

		// 删除不需要保留的农事记录资源（历史的农事记录资源已更新）
		if (originalRecordEXT.getRecordresourceEXTList().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (RecordresourceEXT obj : originalRecordEXT.getRecordresourceEXTList()) {
				idList.add(obj.getId());// 需要刪除掉的农事记录资源
			}

			addOrUpdateRsult = mRecordresourceService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Recordresource failure !");
			}

		}

		// 当前的资源记录数量大于农事记录的历史资源数量时添加新的农事记录资源
		if (paramRecordEXT.getResourcehistorylist().size() > 0) {

			for (ResourcehistoryEXT resourcehistoryEXT : paramRecordEXT.getResourcehistorylist()) {
				// 添加资源到历史资源中
				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
						resourcehistoryEXT.getResourcename(), resourceOrderNo.incrementAndGet(),
						resourcehistoryEXT.getTimelength());

				// 添加农事记录资源
				addRecordresourceEXT = new RecordresourceEXT();
				addRecordresourceEXT.setRecordid(originalRecordEXT.getId());// 设置农事记录的ID
				addRecordresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置农事记录资源上传历史的ID

				addOrUpdateRsult = mRecordresourceService.add(addRecordresourceEXT);

				if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
					throw new Exception("update Recordresource failure !");
				}
			}

		}

		return true;
	}

	/**
	 * 编辑农事类别
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramRecordEXT
	 *            从客户端传过来的农事记录对象
	 * @param originalRecordEXT
	 *            从数据库中检索出来的农事记录对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editRecordfarmtype(final RecordEXT paramRecordEXT, final RecordEXT originalRecordEXT)
			throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		// 从客户端传过来的农事类别列表
		Iterator<RecordfarmtypeEXT> paramRecordfarmtypeEXTIterator = paramRecordEXT.getFarmtypelist().iterator();

		RecordfarmtypeEXT paramRecordfarmtypeEXT = null;
		while (paramRecordfarmtypeEXTIterator.hasNext()) {
			paramRecordfarmtypeEXT = paramRecordfarmtypeEXTIterator.next();

			// 设置农事记录的ID，在后续需要添加的时候农事记录的ID才不会为空
			paramRecordfarmtypeEXT.setRecordid(paramRecordEXT.getId());

			for (RecordfarmtypeEXT originalrecordfarmtypeEXT : originalRecordEXT.getFarmtypelist()) {
				if (originalrecordfarmtypeEXT.getFarmtype().equals(paramRecordfarmtypeEXT.getFarmtype())) {
					// 该农事类别需要保留
					originalRecordEXT.getRecordpartuserlist().remove(originalrecordfarmtypeEXT);

					// 当前农事类别原来就已经存在，把他移除掉，最终新增不存在的农事类别，删除原来已存在并且不需要保留的农事类别
					paramRecordfarmtypeEXTIterator.remove();

					break;
				}
			}
		}

		// 删除不需要保留的农事类别
		if (originalRecordEXT.getFarmtypelist().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (RecordfarmtypeEXT obj : originalRecordEXT.getFarmtypelist()) {
				idList.add(obj.getId());// 需要刪除掉的农事类别
			}

			addOrUpdateRsult = mRecordfarmtypeService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Recordfarmtype failure !");
			}
		}

		// 添加新的农事类别（历史的农事类别已更新）
		if (paramRecordEXT.getFarmtypelist().size() > 0) {
			addOrUpdateRsult = mRecordfarmtypeService.batchAdd(paramRecordEXT.getFarmtypelist());

			if ((null == addOrUpdateRsult)
					|| (paramRecordEXT.getFarmtypelist().size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch add Recordfarmtype failure !");
			}
		}

		return true;
	}

	/**
	 * 查询出在首页上显示的未读的农事记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:18:30
	 *
	 * @param loginUserInfo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCountNotReadRecordToHomePage(final LoginUserInfo loginUserInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 我参与的未读的农事记录
		RecordEXT myScheduleRecordEXT = new RecordEXT();
		myScheduleRecordEXT.setRecordpartuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的农事
		myScheduleRecordEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		myScheduleRecordEXT.setHasreaded(CommonConstantsUtil.YES_OR_NO_NO);
		this.setQueryPermisions(myScheduleRecordEXT, loginUserInfo);// 设置数据的查询权限

		final Integer myScheduleRecordEXTCount = this.queryListTotalCount(myScheduleRecordEXT);

		// 有未读的记录就直接返回
		if ((null != myScheduleRecordEXTCount) && (myScheduleRecordEXTCount.intValue() > 0)) {

			map.put("totalcount", myScheduleRecordEXTCount);// 未读的数量
			map.put("hashnotread", true);// 是否有未读的

			return map;
		}else {
			map.put("noread", false);
		}

		// 所有我参与的记录数量
		RecordEXT allMyPartakeScheduleRecordEXT = new RecordEXT();
		allMyPartakeScheduleRecordEXT.setRecordpartuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的农事
		allMyPartakeScheduleRecordEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		this.setQueryPermisions(allMyPartakeScheduleRecordEXT, loginUserInfo);// 设置数据的查询权限

		final Integer allMyPartakeScheduleRecordEXTCount = this.queryListTotalCount(allMyPartakeScheduleRecordEXT);

		// 所有我创建的记录数量
		RecordEXT allMyCreateScheduleRecordEXT = new RecordEXT();
		allMyCreateScheduleRecordEXT.setUserpermisionscreateuserid(ObjectUtil.toString(loginUserInfo.getId()));// 我创建的农事
		allMyCreateScheduleRecordEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		this.setQueryPermisions(allMyCreateScheduleRecordEXT, loginUserInfo);// 设置数据的查询权限

		final Integer allMyCreateScheduleRecordEXTCount = this.queryListTotalCount(allMyCreateScheduleRecordEXT);

		// 查询出我既是创建人，又是参与人的记录
		RecordEXT allMyCreateAndPartakeRecordEXT = new RecordEXT();
		allMyCreateAndPartakeRecordEXT.setRecordpartuserids(ObjectUtil.toString(loginUserInfo.getId()));// 我参与的农事
		allMyCreateAndPartakeRecordEXT.setUserpermisionscreateuserid(ObjectUtil.toString(loginUserInfo.getId()));// 我创建的农事
		allMyCreateAndPartakeRecordEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
		this.setQueryPermisions(allMyCreateAndPartakeRecordEXT, loginUserInfo);// 设置数据的查询权限

		final Integer allMyCreateAndPartakeRecordEXTCount = this.queryListTotalCount(allMyCreateAndPartakeRecordEXT);

		// 统计出所有我参与的和我创建的记录数量，再减去我既是创建人又是参与人的记录数量，否则就会出现重复计算了总数
		final Integer countNotRead = (((null == allMyPartakeScheduleRecordEXTCount ? 0
				: allMyPartakeScheduleRecordEXTCount)
				+ (null == allMyCreateScheduleRecordEXTCount ? 0 : allMyCreateScheduleRecordEXTCount) + 0)
				- (null == allMyCreateAndPartakeRecordEXTCount ? 0 : allMyCreateAndPartakeRecordEXTCount));

		map.put("totalcount", countNotRead);// 未读的数量
		map.put("hashnotread", false);// 是否有未读的

		return map;
	}

	/**
	 * 该农事记录的参与人已读记录
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午5:53:26
	 *
	 * @param recordEXT
	 *            农事记录对象
	 * @param loginUserInfo
	 *            农事记录的参与人
	 */
	public void setRecordpartuserToReaded(final RecordEXT recordEXT, final LoginUserInfo loginUserInfo) {

		if ((null == recordEXT) || (null == recordEXT.getRecordpartuserlist())
				|| (recordEXT.getRecordpartuserlist().isEmpty())) {
			return;
		}

		final List<RecordpartuserEXT> readedRecordpartuserEXTList = new ArrayList<RecordpartuserEXT>();

		for (RecordpartuserEXT planparticsEXT : recordEXT.getRecordpartuserlist()) {
			if (ObjectUtil.toString(loginUserInfo.getId()).equals(ObjectUtil.toString(planparticsEXT.getUserid()))) {

				// 已读
				planparticsEXT.setHasreaded((byte) CommonConstantsUtil.YES_OR_NO_YES);
				readedRecordpartuserEXTList.add(planparticsEXT);
			}
		}

		if (readedRecordpartuserEXTList.size() > 0) {
			mRecordpartuserService.asynchronizationUpdate(readedRecordpartuserEXTList);
		}
	}

	/**
	 * 设置数据的查询权限
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 上午9:20:42
	 *
	 * @param paramRecordEXT
	 *            查询条件
	 * @param loginUserInfo
	 *            当前操作的用户
	 */
	public void setQueryPermisions(final RecordEXT paramRecordEXT, final LoginUserInfo loginUserInfo) {
		if ((null == paramRecordEXT) || (null == loginUserInfo)) {
			return;
		}

		// 数据的查询权限
		String queryPermisionsSQL = "";

		// 数据权限只能在当前登录的组织下
		queryPermisionsSQL = mFarmCommonService.getQueryPermisionsOnlyLoginOrg(loginUserInfo, " recordpartuserids ");

		// 数据权限为当前用户所拥有的所有组织和部门
		// queryPermisionsSQL =
		// mFarmCommonService.qetQueryPermisionsAllOrgAndDepartment(loginUserInfo,
		// " recordpartuserids ");

		// 设置数据的查询权限
		paramRecordEXT.setPermisionssqlwhere(queryPermisionsSQL);
	}

//	public List<String> queryExistPlanTaskEachDate(Map<String, Object> map) {
//		return mRecordEXTMapper.queryExistPlanTaskEachDate(map);
//	}
	
	public List<Map<String, Object>> queryExistPlanTaskEachDate(Map<String, Object> map) {
		return mRecordEXTMapper.queryExistPlanTaskEachDate(map);
	}

	public Integer queryOutPlanCntByToday(Map<String, Object> map) {
		return mRecordEXTMapper.queryOutPlanCntByToday(map);
	}

	public Integer checkIsOwenOperateAuthor(Map<String, Object> map) {
		return mRecordEXTMapper.checkIsOwenOperateAuthor(map);
		
	}

}
