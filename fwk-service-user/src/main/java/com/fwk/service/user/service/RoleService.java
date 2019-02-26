package com.fwk.service.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.user.dao.RoleDao;
import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.RoleEXT;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;

	public int updateRole(Map<String, Object> requestMap) {
		return roleDao.updateRole(requestMap);
	}

	public int insertRole(Map<String, Object> requestMap) {
		return roleDao.insertRole(requestMap);
	}

	public List<RoleEXT> queryRole(Map<String, Object> checkRoleExistMap) {
		return roleDao.queryRole(checkRoleExistMap);
	}

	public int deleteUserRole(Map<String, Object> roleMap) {
		return roleDao.deleteUserRole(roleMap);
	}

	public void insertUserRole(Map<String, Object> saveRoleMap) {
		roleDao.insertUserRole(saveRoleMap);
	}

	public List<OrgDepartUserTreeDefine> queryRoleTree(Map<String, Object> roleTreeMap) {
		return roleDao.queryRoleTree(roleTreeMap);
	}

	public int queryRoleCount(Map<String, Object> requestMap) {
		return roleDao.queryRoleCount(requestMap);
	}

}
