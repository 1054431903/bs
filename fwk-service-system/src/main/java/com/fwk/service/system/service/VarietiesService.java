package com.fwk.service.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.common.entity.TSvarietiesExample;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.system.dao.VarietiesDao;
import com.fwk.service.system.entity.VarietiesEXT;

@Service
public class VarietiesService {
	
	@Autowired
	private VarietiesDao varietiesDao;

	public int saveVarieties(VarietiesEXT varietiesEXT) {
		return varietiesDao.insertSelective(varietiesEXT);
	}
	
	public int deleteVarieties(Integer id) {
		VarietiesEXT varietiesEXT = new VarietiesEXT();
		varietiesEXT.setDelstate(CommonConstantsUtil.IS_DELETE_TRUE);
		varietiesEXT.setEdittime(new Date());
		varietiesEXT.setId(id);
		return varietiesDao.updateByPrimaryKeySelective(varietiesEXT);
	}
	
	public int deleteVarietiesByOrgId(String orgId) {
		VarietiesEXT varietiesEXT = new VarietiesEXT();
		varietiesEXT.setEdittime(new Date());
		varietiesEXT.setDelstate(CommonConstantsUtil.IS_DELETE_TRUE);
		TSvarietiesExample example = new TSvarietiesExample();
		example.createCriteria().andOrgidEqualTo(orgId);
		return varietiesDao.updateByExampleSelective(varietiesEXT, example);
	}
	
	public int batchDeleteVarieties(List<Integer> ids) {
		VarietiesEXT varietiesEXT = new VarietiesEXT();
		varietiesEXT.setDelstate(CommonConstantsUtil.IS_DELETE_TRUE);
		varietiesEXT.setEdittime(new Date());
		TSvarietiesExample example = new TSvarietiesExample();
		example.createCriteria().andIdIn(ids);
		return varietiesDao.updateByExampleSelective(varietiesEXT, example);
	}
	
	public int updateVarieties(VarietiesEXT varietiesEXT) {
		varietiesEXT.setEdittime(new Date());
		return varietiesDao.updateByPrimaryKeySelective(varietiesEXT);
	}
	
	public List<VarietiesEXT> queryVarieties(VarietiesEXT varietiesEXT){
		return varietiesDao.queryVarieties(varietiesEXT);
	}
	
	public int queryVarietiesCount(VarietiesEXT varietiesEXT) {
		return varietiesDao.queryVarietiesCount(varietiesEXT);
	}
}
