package com.fwk.service.common.dao;

import java.util.List;

public interface CUserEXTMapper {

	public String getUserIdBySupportId(String supportid);
	
	public List<Integer> getUserIdBySupportIds(List<String> supportids);
	
}
