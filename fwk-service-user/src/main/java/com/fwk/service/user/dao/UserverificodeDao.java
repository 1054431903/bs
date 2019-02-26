package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.UserverificodeEXT;

public interface UserverificodeDao {

	int insertVerificode(Map<String, Object> saveMap);

	List<UserverificodeEXT> queryVerificode(Map<String, Object> requestMap);

	int updateVerificode(Map<String, Object> saveMap);

}
