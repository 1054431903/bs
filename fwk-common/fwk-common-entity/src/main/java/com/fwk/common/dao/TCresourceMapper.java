package com.fwk.common.dao;

import com.fwk.common.entity.TCresource;
import com.fwk.common.entity.TCresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCresourceMapper {
    int countByExample(TCresourceExample example);

    int deleteByExample(TCresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCresource record);

    int insertSelective(TCresource record);

    List<TCresource> selectByExample(TCresourceExample example);

    TCresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCresource record, @Param("example") TCresourceExample example);

    int updateByExample(@Param("record") TCresource record, @Param("example") TCresourceExample example);

    int updateByPrimaryKeySelective(TCresource record);

    int updateByPrimaryKey(TCresource record);
}