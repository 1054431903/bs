package com.fwk.service.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.user.dao.OrgDao;
import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.OrgEXT;

@Service
public class OrgService {

	@Autowired
	private OrgDao orgDao;

	public List<OrgEXT> queryOrg(Map<String, Object> requestMap) {
		return orgDao.queryOrg(requestMap);
	}

	List<OrgEXT> queryOrgByCond(Map<String, Object> requestMap){
		return orgDao.queryOrgByCond(requestMap);
	}
	public int updateOrg(Map<String, Object> requestMap) {
		return orgDao.updateOrg(requestMap);
	}

	public int saveOrg(Map<String, Object> requestMap) {
		return orgDao.saveOrg(requestMap);
	}

	/**
	 * 获取当前登录用户的org信息,operateid不能为空
	 */
	public List<OrgEXT> queryOrgByOperateid(Map<String, Object> requestMap) {
		return orgDao.queryOrgByOperateid(requestMap);
	}

	public int deleteOrg(Map<String, Object> requestMap) {
		return orgDao.deleteOrg(requestMap);
	}

	public List<OrgEXT> queryOrgAllByOperateid(Map<String, Object> requestMap) {
		return orgDao.queryOrgAllByOperateid(requestMap);
	}

	public List<OrgDepartUserTreeDefine> getOrgToTree(Map<String, Object> orgMap) {
		return orgDao.getOrgToTree(orgMap);
	}

	public int saveOrgByEXT(OrgEXT orgEXT) {
		return orgDao.saveOrgByEXT(orgEXT);
	}

	public OrgEXT queryOrgBySupportId(Object supportid) {
		if (ObjectUtil.isBlank(supportid)) {
			return null;
		}
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("supportid", supportid);
		orgMap.put("delstate", CommonConstantsUtil.IS_DELETE_FALSE);
		List<OrgEXT> orgList = queryOrgByCond(orgMap);
		if (!CollectionUtils.isEmpty(orgList)) {
			return orgList.get(0);
		}
		return null;
	}

	public boolean isExistSupportidRec(Map<String, Object> requestMap) {
		if (!requestMap.containsKey("supportid") || ObjectUtil.isBlank(requestMap.get("supportid"))) {
			return false;
		}
		List<OrgEXT> orgList = queryOrg(requestMap);
		if (CollectionUtils.isEmpty(orgList)) {
			return false;
		}
		return true;
	}

	public int correspondSupportRecord(Map<String, Object> requestMap) {
		if (!isExistSupportidRec(requestMap)) {
			if (requestMap.containsKey("supportid") && ObjectUtil.isNotBlank(requestMap.get("supportid"))) {
				return saveOrg(requestMap);
			}
		}
		return 0;
	}
}
