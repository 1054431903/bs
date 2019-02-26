package com.fwk.service.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.user.dao.DepartmentDao;
import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.entity.OrgDepartUserTreeDefine;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	public List<DepartmentEXT> queryDepartment(Map<String, Object> requestMap) {
		return departmentDao.queryDepartment(requestMap);
	}

	public int saveDepartment(Map<String, Object> requestMap) {
		return departmentDao.saveDepartment(requestMap);
	}

	public int updateDepartment(Map<String, Object> requestMap) {
		return departmentDao.updateDepartment(requestMap);
	}

	public List<OrgDepartUserTreeDefine> getDepartmentTree(Map<String, Object> departmentMap) {
		return departmentDao.getDepartmentTree(departmentMap);
	}

	public DepartmentEXT queryDepartmentDetail(Map<String, Object> queryDepartmentDetailMap) {
		return departmentDao.queryDepartmentDetail(queryDepartmentDetailMap);
	}

	public DepartmentEXT queryOrgBySupportId(Object supportid) {
		if (ObjectUtil.isBlank(supportid)) {
			return null;
		}
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("supportid", supportid);
		orgMap.put("delstate", CommonConstantsUtil.IS_DELETE_FALSE);
		List<DepartmentEXT> deptList = queryDepartment(orgMap);
		if (!CollectionUtils.isEmpty(deptList)) {
			return deptList.get(0);
		}
		return null;
	}

	public boolean isExistSupportidRec(Map<String, Object> requestMap) {
		if (!requestMap.containsKey("supportid") || ObjectUtil.isBlank(requestMap.get("supportid"))) {
			return false;
		}
		List<DepartmentEXT> deptList = queryDepartment(requestMap);
		if (CollectionUtils.isEmpty(deptList)) {
			return false;
		}
		return true;
	}

	public int correspondSupportRecord(Map<String, Object> requestMap) {
		if (!isExistSupportidRec(requestMap)) {
			if (requestMap.containsKey("supportid") && ObjectUtil.isNotBlank(requestMap.get("supportid"))) {
				return saveDepartment(requestMap);
			}
		}
		return 0;
	}
}
