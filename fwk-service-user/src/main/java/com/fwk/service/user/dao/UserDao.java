package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.entity.UserOrgDepartEXT;

public interface UserDao {

	UserEXT login(Map<String, Object> requestMap);

	int registe(Map<String, Object> requestMap);

	int queryCheckUserExist(Map<String, Object> requestMap);

	List<UserEXT> queryUsersByOrgId(Map<String, Object> queryUserMap);

	int updateUser(Map<String, Object> queryUserMap);

	List<UserEXT> queryUser(Map<String, Object> checkUserPassword);

	List<UserEXT> queryUserBuOrgDepartment(Map<String, Object> requestMap);

	int queryUserBuOrgDepartmentCount(Map<String, Object> requestMap);

	List<UserEXT> queryUserDetail(Map<String, Object> requestMap);
	
	List<UserEXT> queryUserDetailBySupportId(String supportId);

	int insertUser(Map<String, Object> requestMap);

	List<UserOrgDepartEXT> queryUserOrgDepart(Map<String, Object> requestMap);

	int updateUserOrgDepart(Map<String, Object> saveUserOrgDepartMap);

	int insertUserOrgDepart(Map<String, Object> saveUserOrgDepartMap);

	int removeUserFromOrg(Map<String, Object> requestMap);

	List<UserEXT> getUserByDepartment(Map<String, Object> queryUserByDepart);

	List<OrgDepartUserTreeDefine> getUserTree(Map<String, Object> userMap);

	int updatePasswordByPhone(Map<String, Object> requestMap);

	int insertUserSupport(UserEXT userEXT2);

	int updateUserSupportid(UserEXT userEXT);

}
