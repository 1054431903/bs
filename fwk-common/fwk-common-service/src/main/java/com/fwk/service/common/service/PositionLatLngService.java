package com.fwk.service.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.common.dao.PositionLatLngDao;
import com.fwk.service.common.entity.PositionLatLng;


@Service
public class PositionLatLngService {
	
	@Autowired
	private PositionLatLngDao posLatLngDao;

	public List<PositionLatLng> queryPosLatLngByName(Map<String, Object> requestMap) {
		return posLatLngDao.queryPosLatLngByName(requestMap);
	}
	
}
