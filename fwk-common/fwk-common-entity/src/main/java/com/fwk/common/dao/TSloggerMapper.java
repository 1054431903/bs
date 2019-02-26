package com.fwk.common.dao;

import com.fwk.common.entity.TSlogger;
import com.fwk.common.entity.TSloggerExample;
import com.fwk.common.entity.TSloggerWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSloggerMapper {
    int countByExample(TSloggerExample example);

    int deleteByExample(TSloggerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSloggerWithBLOBs record);

    int insertSelective(TSloggerWithBLOBs record);

    List<TSloggerWithBLOBs> selectByExampleWithBLOBs(TSloggerExample example);

    List<TSlogger> selectByExample(TSloggerExample example);

    TSloggerWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSloggerWithBLOBs record, @Param("example") TSloggerExample example);

    int updateByExampleWithBLOBs(@Param("record") TSloggerWithBLOBs record, @Param("example") TSloggerExample example);

    int updateByExample(@Param("record") TSlogger record, @Param("example") TSloggerExample example);

    int updateByPrimaryKeySelective(TSloggerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TSloggerWithBLOBs record);

    int updateByPrimaryKey(TSlogger record);
}