package com.fwk.common.dao;

import com.fwk.common.entity.TSinformationcontent;
import com.fwk.common.entity.TSinformationcontentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSinformationcontentMapper {
    int countByExample(TSinformationcontentExample example);

    int deleteByExample(TSinformationcontentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSinformationcontent record);

    int insertSelective(TSinformationcontent record);

    List<TSinformationcontent> selectByExampleWithBLOBs(TSinformationcontentExample example);

    List<TSinformationcontent> selectByExample(TSinformationcontentExample example);

    TSinformationcontent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSinformationcontent record, @Param("example") TSinformationcontentExample example);

    int updateByExampleWithBLOBs(@Param("record") TSinformationcontent record, @Param("example") TSinformationcontentExample example);

    int updateByExample(@Param("record") TSinformationcontent record, @Param("example") TSinformationcontentExample example);

    int updateByPrimaryKeySelective(TSinformationcontent record);

    int updateByPrimaryKeyWithBLOBs(TSinformationcontent record);

    int updateByPrimaryKey(TSinformationcontent record);
}