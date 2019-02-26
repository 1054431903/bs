package com.fwk.service.system.dao;

import java.util.List;

import com.fwk.common.dao.TSinformationresourceMapper;
import com.fwk.service.system.entity.InformationResourceEXT;

public interface InformationResourceDao extends TSinformationresourceMapper{

	int saveInformationResource(InformationResourceEXT informationResourceEXT);
	
	int deleteInformationResource(InformationResourceEXT informationResourceEXT);
	
	int updateInformationResource(InformationResourceEXT informationResourceEXT);
	
	List<InformationResourceEXT> queryInformationResource(InformationResourceEXT informationResourceEXT);
}
