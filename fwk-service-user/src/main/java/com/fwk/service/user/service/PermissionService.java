package com.fwk.service.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.user.dao.PermissionDao;
import com.fwk.service.user.entity.PermissionEXT;

/**
 * 权限
 * */
@Service
public class PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	public List<PermissionEXT> queryPermissionList(Map<String, Object> requestMap) {
		return permissionDao.queryPermissionList(requestMap);
	}

	public void deleteRolePermission(Map<String, Object> delRolepermission) {
		permissionDao.deleteRolePermission(delRolepermission);
	}

	public int saveRolePermission(Map<String, Object> saveRolePermission) {
		return permissionDao.saveRolePermission(saveRolePermission);
	}

	public List<PermissionEXT> queryPermissionByLoginUser(Map<String, Object> requestMap) {
		return permissionDao.queryPermissionByLoginUser(requestMap);
	}

}
