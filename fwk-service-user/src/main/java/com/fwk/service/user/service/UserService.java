package com.fwk.service.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.user.dao.UserDao;
import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.entity.UserOrgDepartEXT;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public UserEXT login(Map<String, Object> requestMap) {
		return userDao.login(requestMap);
	}

	public int registe(Map<String, Object> requestMap) {
		return userDao.registe(requestMap);
	}

	public int queryCheckUserExist(Map<String, Object> requestMap) {
		return userDao.queryCheckUserExist(requestMap);
	}

	public List<UserEXT> queryUsersByOrgId(Map<String, Object> queryUserMap) {
		return userDao.queryUsersByOrgId(queryUserMap);
	}
	
	public int updateUser(Map<String, Object> queryUserMap){
		return userDao.updateUser(queryUserMap);
	}

	public List<UserEXT> queryUser(Map<String, Object> checkUserPassword) {
		return userDao.queryUser(checkUserPassword);
	}
	
	public List<UserEXT> queryUserBuOrgDepartment(Map<String, Object> requestMap) {
		return userDao.queryUserBuOrgDepartment(requestMap);
	}

	public int queryUserBuOrgDepartmentCount(Map<String, Object> requestMap) {
		return userDao.queryUserBuOrgDepartmentCount(requestMap);
	}

	public List<UserEXT> queryUserDetail(Map<String, Object> requestMap) {
		return userDao.queryUserDetail(requestMap);
	}
	
	public List<UserEXT> queryUserDetailBySupportId(String supportId){
		return userDao.queryUserDetailBySupportId(supportId);
	}

	public int insertUser(Map<String, Object> requestMap) {
		return userDao.insertUser(requestMap);
	}

	public List<UserOrgDepartEXT> queryUserOrgDepart(
			Map<String, Object> requestMap) {
		return userDao.queryUserOrgDepart(requestMap);
	}

	public int updateUserOrgDepart(Map<String, Object> saveUserOrgDepartMap) {
		return userDao.updateUserOrgDepart(saveUserOrgDepartMap);
	}

	public int insertUserOrgDepart(Map<String, Object> saveUserOrgDepartMap) {
		return userDao.insertUserOrgDepart(saveUserOrgDepartMap);
	}

	public int removeUserFromOrg(Map<String, Object> requestMap) {
		return userDao.removeUserFromOrg(requestMap);
	}

	public List<UserEXT> getUserByDepartment(Map<String, Object> queryUserByDepart) {
		return userDao.getUserByDepartment(queryUserByDepart);
	}

	public List<OrgDepartUserTreeDefine> getUserTree(Map<String, Object> userMap) {
		return userDao.getUserTree(userMap);
	}

	public int updatePasswordByPhone(Map<String, Object> requestMap) {
		return userDao.updatePasswordByPhone(requestMap);
	}

	public int insertUserSupport(UserEXT userEXT2) {
		return userDao.insertUserSupport(userEXT2);
	}

	public int updateUserSupportid(UserEXT userEXT) {
		return userDao.updateUserSupportid(userEXT);
	}
}
