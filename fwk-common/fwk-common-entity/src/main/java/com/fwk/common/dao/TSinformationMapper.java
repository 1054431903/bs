package com.fwk.common.dao;

import com.fwk.common.entity.TSinformation;
import com.fwk.common.entity.TSinformationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSinformationMapper {
    int countByExample(TSinformationExample example);

    int deleteByExample(TSinformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSinformation record);

    int insertSelective(TSinformation record);

    List<TSinformation> selectByExample(TSinformationExample example);

    TSinformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSinformation record, @Param("example") TSinformationExample example);

    int updateByExample(@Param("record") TSinformation record, @Param("example") TSinformationExample example);

    int updateByPrimaryKeySelective(TSinformation record);

    int updateByPrimaryKey(TSinformation record);
}