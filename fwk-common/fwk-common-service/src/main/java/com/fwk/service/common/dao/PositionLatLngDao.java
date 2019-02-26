package com.fwk.service.common.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.common.entity.PositionLatLng;


public interface PositionLatLngDao {

	List<PositionLatLng> queryPosLatLngByName(Map<String, Object> requestMap);

}
