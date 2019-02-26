package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelplant;
import com.fwk.common.entity.TPparcelplantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelplantMapper {
    int countByExample(TPparcelplantExample example);

    int deleteByExample(TPparcelplantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelplant record);

    int insertSelective(TPparcelplant record);

    List<TPparcelplant> selectByExample(TPparcelplantExample example);

    TPparcelplant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelplant record, @Param("example") TPparcelplantExample example);

    int updateByExample(@Param("record") TPparcelplant record, @Param("example") TPparcelplantExample example);

    int updateByPrimaryKeySelective(TPparcelplant record);

    int updateByPrimaryKey(TPparcelplant record);
}