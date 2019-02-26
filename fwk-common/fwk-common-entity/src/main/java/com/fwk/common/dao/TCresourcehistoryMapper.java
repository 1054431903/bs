package com.fwk.common.dao;

import com.fwk.common.entity.TCresourcehistory;
import com.fwk.common.entity.TCresourcehistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCresourcehistoryMapper {
    int countByExample(TCresourcehistoryExample example);

    int deleteByExample(TCresourcehistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCresourcehistory record);

    int insertSelective(TCresourcehistory record);

    List<TCresourcehistory> selectByExample(TCresourcehistoryExample example);

    TCresourcehistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCresourcehistory record, @Param("example") TCresourcehistoryExample example);

    int updateByExample(@Param("record") TCresourcehistory record, @Param("example") TCresourcehistoryExample example);

    int updateByPrimaryKeySelective(TCresourcehistory record);

    int updateByPrimaryKey(TCresourcehistory record);
}