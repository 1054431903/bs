package com.fwk.service.archives.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.common.util.ObjectUtil;
import com.fwk.service.archives.dao.MedicinesendEXTMapper;
import com.fwk.service.archives.entity.MedicinesendEXT;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.service.CommonService;

/**
 * 档案管理模块 — 农资发放管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class MedicinesendService {

	/** 农资发放记录的编号的格式化 */
	private static final DecimalFormat medicinesend_msid_format = new DecimalFormat("NZFF0000");

	// 档案管理模块 — 农资发放管理的数据库处理类
	@Autowired
	private MedicinesendEXTMapper mMedicinesendEXTMapper;

	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增农资发放记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer add(MedicinesendEXT pMedicinesendEXT) throws Exception {
		// 添加或者更新的结果
		Integer resultAddOrUpdate = null;

		resultAddOrUpdate = mMedicinesendEXTMapper.add(pMedicinesendEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Medicinesend failure !");
		} else {
			MedicinesendEXT paramMedicinesendEXT = new MedicinesendEXT();
			paramMedicinesendEXT.setId(pMedicinesendEXT.getId());

			pMedicinesendEXT = this.queryById(paramMedicinesendEXT);

			// 设置农资发放记录的编号
			if (this.setMedicinesendMsid(pMedicinesendEXT)) {
				resultAddOrUpdate = this.update(pMedicinesendEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Medicinesend failure !");
				}
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新新增农资发放记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(MedicinesendEXT pMedicinesendEXT) throws Exception {
		return mMedicinesendEXTMapper.update(pMedicinesendEXT);
	}

	/**
	 * 删除指定ID的农资发放记录
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(MedicinesendEXT pMedicinesendEXT) throws Exception {
		return mMedicinesendEXTMapper.deletebyid(pMedicinesendEXT);
	}

	/**
	 * 根据主键批量删除农资发放记录
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
		return mMedicinesendEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农资发放记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	public MedicinesendEXT queryById(MedicinesendEXT pMedicinesendEXT) throws Exception {
		MedicinesendEXT paramMedicinesendEXT = new MedicinesendEXT();
		paramMedicinesendEXT.setId(pMedicinesendEXT.getId());

		List<MedicinesendEXT> mMedicinesendEXTList = this.queryList(paramMedicinesendEXT);

		return ((null != mMedicinesendEXTList) && (mMedicinesendEXTList.size() > 0) ? mMedicinesendEXTList.get(0)
				: null);
	}

	/**
	 * 检索农资发放记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	public List<MedicinesendEXT> queryList(MedicinesendEXT pMedicinesendEXT) throws Exception {
		List<MedicinesendEXT> medicinesendEXTList = mMedicinesendEXTMapper.queryList(pMedicinesendEXT);

		// 设置农资发放的地址
		this.setAddress(medicinesendEXTList);

		return medicinesendEXTList;
	}

	/**
	 * 检索农资发放记录的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pMedicinesendEXT
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(MedicinesendEXT pMedicinesendEXT) throws Exception {
		return mMedicinesendEXTMapper.queryListTotalCount(pMedicinesendEXT);
	}

	/**
	 * 设置农资发放记录的编号
	 * 
	 * @author wangjunwen
	 * @date 2018年1月22日 下午5:04:12
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 */
	public boolean setMedicinesendMsid(MedicinesendEXT pMedicinesendEXT) {
		if ((null != pMedicinesendEXT.getMsid()) && (null != pMedicinesendEXT.getMsid())) {
			pMedicinesendEXT.setMsid(medicinesend_msid_format.format(pMedicinesendEXT.getMsid()));
			return true;
		}

		return false;
	}

	/**
	 * 设置农资发放的的地址
	 * 
	 * @author wangjunwen
	 * @date 2018年3月7日 下午3:48:24
	 *
	 * @param medicinesendEXTList
	 *            农资发放的列表
	 * @throws Exception
	 */
	public void setAddress(final List<MedicinesendEXT> medicinesendEXTList) throws Exception {

		if ((null == medicinesendEXTList) || (medicinesendEXTList.isEmpty())) {
			return;
		}

		final Set<String> addresscodeSet = new HashSet<String>();

		for (MedicinesendEXT medicinesendEXT : medicinesendEXTList) {
			if (null != medicinesendEXT.getDrowaddresscode()) {
				addresscodeSet.add(medicinesendEXT.getDrowaddresscode());
			}
		}

		final List<PositionEXT> positionEXTList = new ArrayList<PositionEXT>();

		if (addresscodeSet.size() > 0) {
			PositionEXT tempPositionEXT = null;

			for (String adresscode : addresscodeSet) {
				tempPositionEXT = mCommonService.queryPositionByCode(adresscode);

				if (null != tempPositionEXT) {
					positionEXTList.add(tempPositionEXT);
				}
			}
		}

		for (MedicinesendEXT medicinesendEXT : medicinesendEXTList) {
			if (null == medicinesendEXT.getDrowaddresscode()) {
				continue;
			}

			for (PositionEXT positionEXT : positionEXTList) {
				if (positionEXT.getCode().equals(medicinesendEXT.getDrowaddresscode())) {
					medicinesendEXT.setDrowaddress((ObjectUtil.toString(positionEXT.getAllname())
							+ ObjectUtil.toString(medicinesendEXT.getDrowaddressdetail())));

					break;
				}
			}
		}

	}

}
