package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.PermissionEXT;

public interface PermissionDao {


	List<PermissionEXT> queryPermissionList(Map<String, Object> requestMap);

	void deleteRolePermission(Map<String, Object> delRolepermission);

	int saveRolePermission(Map<String, Object> saveRolePermission);

	List<PermissionEXT> queryPermissionByLoginUser(Map<String, Object> requestMap);

}
