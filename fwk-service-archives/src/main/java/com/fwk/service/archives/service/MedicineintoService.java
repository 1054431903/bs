package com.fwk.service.archives.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.archives.dao.MedicineintoEXTMapper;
import com.fwk.service.archives.entity.MedicineintoEXT;

/**
 * 档案管理模块 — 农资投入管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class MedicineintoService {

	/** 农资投入记录的编号的格式化 */
	private static final DecimalFormat medicineinto_miid_format = new DecimalFormat("NZTR0000");

	// 档案管理模块 — 农资投入管理的数据库处理类
	@Autowired
	private MedicineintoEXTMapper mMedicineintoEXTMapper;

	/**
	 * 新增农资投入记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer add(MedicineintoEXT pMedicineintoEXT) throws Exception {
		// 添加或者更新的结果
		Integer resultAddOrUpdate = null;

		resultAddOrUpdate = mMedicineintoEXTMapper.add(pMedicineintoEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Medicinesend failure !");
		} else {
			MedicineintoEXT paramMedicineintoEXT = new MedicineintoEXT();
			paramMedicineintoEXT.setId(pMedicineintoEXT.getId());

			pMedicineintoEXT = this.queryById(paramMedicineintoEXT);

			// 设置农资投入记录的编号
			if (this.setMedicinesendMsid(pMedicineintoEXT)) {
				resultAddOrUpdate = this.update(pMedicineintoEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Medicinesend failure !");
				}
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 更新新增农资投入记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(MedicineintoEXT pMedicineintoEXT) throws Exception {
		return mMedicineintoEXTMapper.update(pMedicineintoEXT);
	}

	/**
	 * 删除指定ID的农资投入记录
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(MedicineintoEXT pMedicineintoEXT) throws Exception {
		return mMedicineintoEXTMapper.deletebyid(pMedicineintoEXT);
	}

	/**
	 * 根据主键批量删除农资投入记录
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
		return mMedicineintoEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农资投入记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 * @return
	 * @throws Exception
	 */
	public MedicineintoEXT queryById(MedicineintoEXT pMedicineintoEXT) throws Exception {
		MedicineintoEXT paramMedicineintoEXT = new MedicineintoEXT();
		paramMedicineintoEXT.setId(pMedicineintoEXT.getId());
		
		List<MedicineintoEXT> mMedicineintoEXTList = this.queryList(paramMedicineintoEXT);

		return ((null != mMedicineintoEXTList) && (mMedicineintoEXTList.size() > 0) ? mMedicineintoEXTList.get(0)
				: null);
	}

	/**
	 * 检索农资投入记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 * @return
	 * @throws Exception
	 */
	public List<MedicineintoEXT> queryList(MedicineintoEXT pMedicineintoEXT) throws Exception {
		return mMedicineintoEXTMapper.queryList(pMedicineintoEXT);
	}

	/**
	 * 检索农资投入记录的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pMedicineintoEXT
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(MedicineintoEXT pMedicineintoEXT) throws Exception {
		return mMedicineintoEXTMapper.queryListTotalCount(pMedicineintoEXT);
	}

	/**
	 * 设置农资投入记录的编号
	 * 
	 * @author wangjunwen
	 * @date 2018年1月22日 下午5:04:12
	 *
	 * @param pMedicineintoEXT
	 *            农资投入记录对象
	 */
	public boolean setMedicinesendMsid(MedicineintoEXT pMedicineintoEXT) {
		if ((null != pMedicineintoEXT.getMiid()) && (null != pMedicineintoEXT.getMiid())) {
			pMedicineintoEXT.setMiid(medicineinto_miid_format.format(pMedicineintoEXT.getMiid()));
			return true;
		}

		return false;
	}

}
