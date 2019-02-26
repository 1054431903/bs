package com.fwk.service.archives.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TAmedicinesendMapper;
import com.fwk.service.archives.entity.MedicinesendEXT;

/**
 * 档案管理模块 — 农资发放管理的数据库处理对类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface MedicinesendEXTMapper extends TAmedicinesendMapper {

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
	Integer add(MedicinesendEXT pMedicinesendEXT) throws Exception;

	/**
	 * 更新农资发放记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	Integer update(MedicinesendEXT pMedicinesendEXT) throws Exception;

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
	Integer deletebyid(MedicinesendEXT pMedicinesendEXT) throws Exception;

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
	Integer batchdeletebyids(@Param(value = "idList") List<Integer> idList) throws Exception;

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
	List<MedicinesendEXT> queryList(MedicinesendEXT pMedicinesendEXT) throws Exception;

	/**
	 * 检索农资发放记录的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pMedicinesendEXT
	 *            农资发放记录对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(MedicinesendEXT pMedicinesendEXT) throws Exception;

}
