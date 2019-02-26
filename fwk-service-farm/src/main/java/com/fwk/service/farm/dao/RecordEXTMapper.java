package com.fwk.service.farm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fwk.common.dao.TFrecordMapper;
import com.fwk.service.farm.entity.RecordEXT;

/**
 * 农事管理模块 — 农事记录的数据库处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:25:39
 *
 */
public interface RecordEXTMapper extends TFrecordMapper {

	/**
	 * 新增农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pRecordEXT
	 *            农事记录对象
	 * @return
	 * @throws Exception
	 */
	Integer add(RecordEXT pRecordEXT) throws Exception;

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
	Integer update(RecordEXT pRecordEXTT) throws Exception;

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
	Integer deletebyid(RecordEXT pRecordEXTT) throws Exception;

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
	Integer batchdeletebyids(@Param(value = "idList") List<Integer> idList) throws Exception;

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
	List<RecordEXT> queryList(RecordEXT pRecordEXTT) throws Exception;

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
	Integer queryListTotalCount(RecordEXT pRecordEXTT) throws Exception;

//	List<String> queryExistPlanTaskEachDate(Map<String, Object> map);
	List<Map<String, Object>> queryExistPlanTaskEachDate(Map<String, Object> map);

	Integer queryOutPlanCntByToday(Map<String, Object> map);

	Integer checkIsOwenOperateAuthor(Map<String, Object> map);

}
