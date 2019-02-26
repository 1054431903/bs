package com.fwk.service.system.dao;

import java.util.List;

import com.fwk.common.dao.TSinformationcontentMapper;
import com.fwk.service.system.entity.InformationContentEXT;

public interface InformationContentDao extends TSinformationcontentMapper {
	
	int saveInformationContent(InformationContentEXT informationContentEXT);
	
	int deleteInformationContent(InformationContentEXT informationContentEXT);
	
	int updateInformationContent(InformationContentEXT informationContentEXT);
	
	List<InformationContentEXT> queryInformationContent(InformationContentEXT informationContentEXT);
	
	int queryInformationContentCount(InformationContentEXT informationContentEXT);
}
