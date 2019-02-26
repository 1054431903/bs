package com.fwk.service.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.service.system.dao.InformationDao;
import com.fwk.service.system.entity.InformationEXT;

@Service
public class InformationService {

	@Autowired
	private InformationDao informationDao;
	
	public int saveInformation(InformationEXT informationEXT){
		return informationDao.saveInformation(informationEXT);
	}
	
	public List<InformationEXT> queryInformation(InformationEXT informationEXT){
		return informationDao.queryInformation(informationEXT);
	}
	
	public int deleteInformation(InformationEXT informationEXT){
		return informationDao.deleteInformation(informationEXT);
	}
	
	public int updateInformation(InformationEXT informationEXT){
		return informationDao.updateInformation(informationEXT);
	}
	
	public int queryInformationCount(InformationEXT informationEXT){
		return informationDao.queryInformationCount(informationEXT);
	}
	
	public int publishInformation(InformationEXT informationEXT){
		return informationDao.publishInformation(informationEXT);
	}
	
	public int copyInformation(InformationEXT informationEXT){
		return informationDao.copyInformation(informationEXT);
	}
}
