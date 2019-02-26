package com.fwk.common.dao;

import com.fwk.common.entity.TSvarieties;
import com.fwk.common.entity.TSvarietiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSvarietiesMapper {
    int countByExample(TSvarietiesExample example);

    int deleteByExample(TSvarietiesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSvarieties record);

    int insertSelective(TSvarieties record);

    List<TSvarieties> selectByExample(TSvarietiesExample example);

    TSvarieties selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSvarieties record, @Param("example") TSvarietiesExample example);

    int updateByExample(@Param("record") TSvarieties record, @Param("example") TSvarietiesExample example);

    int updateByPrimaryKeySelective(TSvarieties record);

    int updateByPrimaryKey(TSvarieties record);
}