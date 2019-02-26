package com.fwk.common.dao;

import com.fwk.common.entity.TSinformationcontentresource;
import com.fwk.common.entity.TSinformationcontentresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSinformationcontentresourceMapper {
    int countByExample(TSinformationcontentresourceExample example);

    int deleteByExample(TSinformationcontentresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSinformationcontentresource record);

    int insertSelective(TSinformationcontentresource record);

    List<TSinformationcontentresource> selectByExample(TSinformationcontentresourceExample example);

    TSinformationcontentresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSinformationcontentresource record, @Param("example") TSinformationcontentresourceExample example);

    int updateByExample(@Param("record") TSinformationcontentresource record, @Param("example") TSinformationcontentresourceExample example);

    int updateByPrimaryKeySelective(TSinformationcontentresource record);

    int updateByPrimaryKey(TSinformationcontentresource record);
}