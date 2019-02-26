package com.fwk.service.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.system.dao.InformationResourceDao;
import com.fwk.service.system.entity.InformationResourceEXT;

@Service
public class InformationResourceService {

	@Autowired
	private InformationResourceDao informationResourceDao;
	
	public int saveInformationResource(InformationResourceEXT informationResourceEXT){
		return informationResourceDao.saveInformationResource(informationResourceEXT);
	}
	
	public int deleteInformationResource(InformationResourceEXT informationResourceEXT){
		return informationResourceDao.deleteInformationResource(informationResourceEXT);
	}
	
	public int updateInformationResource(InformationResourceEXT informationResourceEXT){
		return informationResourceDao.updateInformationResource(informationResourceEXT);
	}
	
	public List<InformationResourceEXT> queryInformationResource(InformationResourceEXT informationResourceEXT){
		return informationResourceDao.queryInformationResource(informationResourceEXT);
	}
	
	public int copyInformationResource(int sourceInformationId, int targetInformationId){
		InformationResourceEXT informationResourceEXT = new InformationResourceEXT();
		informationResourceEXT.setInformationid(sourceInformationId);
		List<InformationResourceEXT> list = informationResourceDao.queryInformationResource(informationResourceEXT);
		int result = 0;
		for (InformationResourceEXT infoResource : list) {
			infoResource.setInformationid(targetInformationId);
			result = informationResourceDao.saveInformationResource(infoResource);
		}
		return result;
	}
}
