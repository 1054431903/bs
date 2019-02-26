package com.fwk.service.common.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.common.entity.PositionEXT;

public interface PositionDao {

	List<PositionEXT> queryPositionByParentCode(Map<String, Object> requestMap);

	PositionEXT getPositionDetail(Map<String, Object> requestMap);

	int savePosition(Map<String, Object> saveMap);

	int saveSupportPosition(Map<String, Object> saveMap);

	void saveSupportPositionVal(String valsql);

}
