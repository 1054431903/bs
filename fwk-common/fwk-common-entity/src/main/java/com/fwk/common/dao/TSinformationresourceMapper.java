package com.fwk.common.dao;

import com.fwk.common.entity.TSinformationresource;
import com.fwk.common.entity.TSinformationresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSinformationresourceMapper {
    int countByExample(TSinformationresourceExample example);

    int deleteByExample(TSinformationresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSinformationresource record);

    int insertSelective(TSinformationresource record);

    List<TSinformationresource> selectByExample(TSinformationresourceExample example);

    TSinformationresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSinformationresource record, @Param("example") TSinformationresourceExample example);

    int updateByExample(@Param("record") TSinformationresource record, @Param("example") TSinformationresourceExample example);

    int updateByPrimaryKeySelective(TSinformationresource record);

    int updateByPrimaryKey(TSinformationresource record);
}