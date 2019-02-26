package com.fwk.common.dao;

import com.fwk.common.entity.TSdict;
import com.fwk.common.entity.TSdictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSdictMapper {
    int countByExample(TSdictExample example);

    int deleteByExample(TSdictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSdict record);

    int insertSelective(TSdict record);

    List<TSdict> selectByExample(TSdictExample example);

    TSdict selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSdict record, @Param("example") TSdictExample example);

    int updateByExample(@Param("record") TSdict record, @Param("example") TSdictExample example);

    int updateByPrimaryKeySelective(TSdict record);

    int updateByPrimaryKey(TSdict record);
}