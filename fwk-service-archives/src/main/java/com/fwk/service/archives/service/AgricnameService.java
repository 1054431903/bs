package com.fwk.service.archives.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.archives.dao.AgricnameEXTMapper;
import com.fwk.service.archives.entity.AgricnameEXT;

/**
 * 档案管理模块 — 农资名称管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class AgricnameService {

	// 档案管理模块 — 农资名称管理的数据库处理类
	@Autowired
	private AgricnameEXTMapper mAgricnameEXTMapper;

	/**
	 * 新增农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public Integer add(AgricnameEXT pAgricnameEXT) throws Exception {
		return mAgricnameEXTMapper.add(pAgricnameEXT);
	}

	/**
	 * 更新农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(AgricnameEXT pAgricnameEXT) throws Exception {
		return mAgricnameEXTMapper.update(pAgricnameEXT);
	}

	/**
	 * 删除指定ID的农资名称
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(AgricnameEXT pAgricnameEXT) throws Exception {
		return mAgricnameEXTMapper.deletebyid(pAgricnameEXT);
	}

	/**
	 * 根据主键批量删除农资名称
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
		return mAgricnameEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public AgricnameEXT queryById(AgricnameEXT pAgricnameEXT) throws Exception {
		AgricnameEXT paramAgricnameEXT = new AgricnameEXT();
		paramAgricnameEXT.setId(pAgricnameEXT.getId());

		List<AgricnameEXT> pAgricnameEXTList = this.queryList(paramAgricnameEXT);

		return ((null != pAgricnameEXTList) && (pAgricnameEXTList.size() > 0) ? pAgricnameEXTList.get(0) : null);
	}

	/**
	 * 检索农资名称
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public List<AgricnameEXT> queryList(AgricnameEXT pAgricnameEXT) throws Exception {
		return mAgricnameEXTMapper.queryList(pAgricnameEXT);
	}

	/**
	 * 检索农资名称的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pAgricnameEXT
	 *            农资名称对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(AgricnameEXT pAgricnameEXT) throws Exception {
		return mAgricnameEXTMapper.queryListTotalCount(pAgricnameEXT);
	}

}
