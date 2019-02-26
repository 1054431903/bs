package com.fwk.service.system.dao;

import java.util.List;

import com.fwk.common.dao.TSvarietiesMapper;
import com.fwk.service.system.entity.VarietiesEXT;

public interface VarietiesDao extends TSvarietiesMapper {

	List<VarietiesEXT> queryVarieties(VarietiesEXT varietiesEXT);
	
	int queryVarietiesCount(VarietiesEXT varietiesEXT);
}
