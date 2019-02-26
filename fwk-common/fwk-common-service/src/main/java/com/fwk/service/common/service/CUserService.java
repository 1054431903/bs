package com.fwk.service.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.common.dao.CUserEXTMapper;

@Service
public class CUserService {
	
	@Autowired
	private CUserEXTMapper userEXTMapper;
	
	public String getUserIdBySupportId(String supportid) {
		return userEXTMapper.getUserIdBySupportId(supportid);
	}
	
	public List<Integer> getUserIdBySupportIds(List<String> supportids){
		return userEXTMapper.getUserIdBySupportIds(supportids);
	}

}
