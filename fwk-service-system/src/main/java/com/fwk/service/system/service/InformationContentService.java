package com.fwk.service.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.system.dao.InformationContentDao;
import com.fwk.service.system.entity.InformationContentEXT;

@Service
public class InformationContentService {
	
	@Autowired
	private InformationContentDao informationContentDao;
	
	public int saveInformationContent(InformationContentEXT informationContentEXT){
		return informationContentDao.saveInformationContent(informationContentEXT);
	}
	
	public int deleteInformationContent(InformationContentEXT informationContentEXT){
		return informationContentDao.deleteInformationContent(informationContentEXT);
	}
	
	public int updateInformationContent(InformationContentEXT informationContentEXT){
		return informationContentDao.updateInformationContent(informationContentEXT);
	}
	
	public List<InformationContentEXT> queryInformationContent(InformationContentEXT informationContentEXT){
		return informationContentDao.queryInformationContent(informationContentEXT);
	}
	
	public int queryInformationContentCount(InformationContentEXT informationContentEXT){
		return informationContentDao.queryInformationContentCount(informationContentEXT);
	}

	public int copyInformationContent(int sourceInfomationId, int targetInformationId){
		InformationContentEXT informationContentEXT = new InformationContentEXT();
		informationContentEXT.setInformationid(sourceInfomationId);
		List<InformationContentEXT> list = informationContentDao.queryInformationContent(informationContentEXT);
		int result = 0;
		for (InformationContentEXT infoContent : list) {
			infoContent.setInformationid(targetInformationId);
			result = informationContentDao.saveInformationContent(infoContent);
		}
		return result;
	}
	
	public int updateSort(InformationContentEXT sourceContent, InformationContentEXT targetContent){
		int result = informationContentDao.updateInformationContent(sourceContent);
		result += informationContentDao.updateInformationContent(targetContent);
		return result;
	}
}
