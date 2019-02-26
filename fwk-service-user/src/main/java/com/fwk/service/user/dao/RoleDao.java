package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.RoleEXT;

public interface RoleDao {

	int updateRole(Map<String, Object> requestMap);

	int insertRole(Map<String, Object> requestMap);

	List<RoleEXT> queryRole(Map<String, Object> checkRoleExistMap);

	int deleteUserRole(Map<String, Object> roleMap);

	void insertUserRole(Map<String, Object> saveRoleMap);

	List<OrgDepartUserTreeDefine> queryRoleTree(Map<String, Object> roleTreeMap);

	int queryRoleCount(Map<String, Object> requestMap);

}
