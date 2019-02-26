package com.fwk.service.user.dao;

import java.util.List;
import java.util.Map;

import com.fwk.service.user.entity.OrgDepartUserTreeDefine;
import com.fwk.service.user.entity.OrgEXT;

public interface OrgDao {

	List<OrgEXT> queryOrg(Map<String, Object> requestMap);

	List<OrgEXT> queryOrgByCond(Map<String, Object> requestMap);

	int updateOrg(Map<String, Object> requestMap);

	int saveOrg(Map<String, Object> requestMap);

	List<OrgEXT> queryOrgByOperateid(Map<String, Object> requestMap);

	int deleteOrg(Map<String, Object> requestMap);

	List<OrgEXT> queryOrgAllByOperateid(Map<String, Object> requestMap);

	List<OrgDepartUserTreeDefine> getOrgToTree(Map<String, Object> orgMap);

	int saveOrgByEXT(OrgEXT orgEXT);

}
