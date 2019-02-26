package com.fwk.service.archives.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TAagricnameMapper;
import com.fwk.service.archives.entity.PeasantEXT;

/**
 * 档案管理模块 — 合作农户档案管理的数据库处理对类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface PeasantEXTMapper extends TAagricnameMapper {

	/**
	 * 新增合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	Integer add(PeasantEXT pPeasantEXT) throws Exception;

	/**
	 * 批量新增合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            合作农户档案对象列表
	 * @return
	 * @throws Exception
	 */
	Integer batchAdd(List<PeasantEXT> list) throws Exception;

	/**
	 * 更新合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	Integer update(PeasantEXT pPeasantEXTT) throws Exception;

	/**
	 * 删除指定ID的合作农户档案
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	Integer deletebyid(PeasantEXT pPeasantEXTT) throws Exception;

	/**
	 * 根据主键批量删除合作农户档案
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
	 * 检索合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	List<PeasantEXT> queryList(PeasantEXT pPeasantEXTT) throws Exception;

	/**
	 * 检索合作农户档案的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	Integer queryListTotalCount(PeasantEXT pPeasantEXTT) throws Exception;

}
