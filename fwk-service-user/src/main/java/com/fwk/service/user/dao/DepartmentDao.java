package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.entity.OrgDepartUserTreeDefine;

public interface DepartmentDao {

	List<DepartmentEXT> queryDepartment(Map<String, Object> requestMap);

	int saveDepartment(Map<String, Object> requestMap);

	int updateDepartment(Map<String, Object> requestMap);

	List<OrgDepartUserTreeDefine> getDepartmentTree(Map<String, Object> departmentMap);

	DepartmentEXT queryDepartmentDetail(Map<String, Object> queryDepartmentDetailMap);

}
