package com.fwk.service.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.system.dao.SDictTypeDao;
import com.fwk.service.system.entity.SDictTypeEXT;

@Service
public class SDictTypeService {
	
	@Autowired
	private SDictTypeDao dictTypeDao;

	public List<SDictTypeEXT> queryTSdictType(Map<String, Object> requestMap) {
		return dictTypeDao.queryTSdictType(requestMap);
	}

	public int saveTSdictType(Map<String, Object> requestMap) {
		return dictTypeDao.saveTSdictType(requestMap);
	}

	public int queryTSdictTypeCount(Map<String, Object> requestMap) {
		return dictTypeDao.queryTSdictTypeCount(requestMap);
	}

	public int queryTSdictTypeNotExist(Map<String, Object> queryMap) {
		return dictTypeDao.queryTSdictTypeNotExist(queryMap);
	}

	public int updateTSdictType(Map<String, Object> requestMap) {
		return dictTypeDao.updateTSdictType(requestMap);
	}

}
