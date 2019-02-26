package com.fwk.service.system.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.system.entity.SDictTypeEXT;

public interface SDictTypeDao {

	List<SDictTypeEXT> queryTSdictType(Map<String, Object> requestMap);

	int saveTSdictType(Map<String, Object> requestMap);

	int queryTSdictTypeCount(Map<String, Object> requestMap);

	int queryTSdictTypeNotExist(Map<String, Object> queryMap);

	int updateTSdictType(Map<String, Object> requestMap);

}
