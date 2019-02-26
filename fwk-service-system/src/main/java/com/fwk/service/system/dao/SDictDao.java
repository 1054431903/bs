package com.fwk.service.system.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.system.entity.SDictEXT;

public interface SDictDao {

	List<SDictEXT> queryTSdict(Map<String, Object> requestMap);

	int queryTSdictCount(Map<String, Object> requestMap);

	int saveTSdict(Map<String, Object> requestMap);

	int queryTSdictNotExist(Map<String, Object> queryMap);

	int updateTSdict(Map<String, Object> requestMap);

}
