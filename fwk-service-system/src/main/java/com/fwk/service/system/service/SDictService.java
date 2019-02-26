package com.fwk.service.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.system.dao.SDictDao;
import com.fwk.service.system.entity.SDictEXT;

@Service
public class SDictService {
	
	@Autowired
	private SDictDao dictDao;

	public List<SDictEXT> queryTSdict(Map<String, Object> requestMap) {
		return dictDao.queryTSdict(requestMap);
	}

	public int queryTSdictCount(Map<String, Object> requestMap) {
		return dictDao.queryTSdictCount(requestMap);
	}

	public int saveTSdict(Map<String, Object> requestMap) {
		return dictDao.saveTSdict(requestMap);
	}

	public int queryTSdictNotExist(Map<String, Object> queryMap) {
		return dictDao.queryTSdictNotExist(queryMap);
	}

	public int updateTSdict(Map<String, Object> requestMap) {
		return dictDao.updateTSdict(requestMap);
	}

}
