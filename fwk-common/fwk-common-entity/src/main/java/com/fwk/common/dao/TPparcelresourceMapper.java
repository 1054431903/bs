package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelresource;
import com.fwk.common.entity.TPparcelresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelresourceMapper {
    int countByExample(TPparcelresourceExample example);

    int deleteByExample(TPparcelresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelresource record);

    int insertSelective(TPparcelresource record);

    List<TPparcelresource> selectByExample(TPparcelresourceExample example);

    TPparcelresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelresource record, @Param("example") TPparcelresourceExample example);

    int updateByExample(@Param("record") TPparcelresource record, @Param("example") TPparcelresourceExample example);

    int updateByPrimaryKeySelective(TPparcelresource record);

    int updateByPrimaryKey(TPparcelresource record);
}