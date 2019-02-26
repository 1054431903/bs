package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelplantresource;
import com.fwk.common.entity.TPparcelplantresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelplantresourceMapper {
    int countByExample(TPparcelplantresourceExample example);

    int deleteByExample(TPparcelplantresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelplantresource record);

    int insertSelective(TPparcelplantresource record);

    List<TPparcelplantresource> selectByExample(TPparcelplantresourceExample example);

    TPparcelplantresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelplantresource record, @Param("example") TPparcelplantresourceExample example);

    int updateByExample(@Param("record") TPparcelplantresource record, @Param("example") TPparcelplantresourceExample example);

    int updateByPrimaryKeySelective(TPparcelplantresource record);

    int updateByPrimaryKey(TPparcelplantresource record);
}