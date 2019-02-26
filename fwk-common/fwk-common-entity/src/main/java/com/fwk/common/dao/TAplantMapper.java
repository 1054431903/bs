package com.fwk.common.dao;

import com.fwk.common.entity.TAplant;
import com.fwk.common.entity.TAplantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAplantMapper {
    int countByExample(TAplantExample example);

    int deleteByExample(TAplantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAplant record);

    int insertSelective(TAplant record);

    List<TAplant> selectByExample(TAplantExample example);

    TAplant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAplant record, @Param("example") TAplantExample example);

    int updateByExample(@Param("record") TAplant record, @Param("example") TAplantExample example);

    int updateByPrimaryKeySelective(TAplant record);

    int updateByPrimaryKey(TAplant record);
}