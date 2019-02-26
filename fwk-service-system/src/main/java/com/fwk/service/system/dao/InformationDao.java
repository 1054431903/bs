package com.fwk.service.system.dao;

import java.util.List;

import com.fwk.common.dao.TSinformationMapper;
import com.fwk.service.system.entity.InformationEXT;

public interface InformationDao extends TSinformationMapper{

	
	int saveInformation(InformationEXT informationEXT);
	
	List<InformationEXT> queryInformation(InformationEXT informationEXT);
	
	int updateInformation(InformationEXT informationEXT);
	
	int deleteInformation(InformationEXT informationEXT);
	
	int queryInformationCount(InformationEXT informationEXT);
	
	int publishInformation(InformationEXT informationEXT);
	
	int copyInformation(InformationEXT informationEXT);
}
