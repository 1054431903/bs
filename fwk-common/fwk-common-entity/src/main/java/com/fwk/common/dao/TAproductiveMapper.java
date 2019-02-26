package com.fwk.common.dao;

import com.fwk.common.entity.TAproductive;
import com.fwk.common.entity.TAproductiveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAproductiveMapper {
    int countByExample(TAproductiveExample example);

    int deleteByExample(TAproductiveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAproductive record);

    int insertSelective(TAproductive record);

    List<TAproductive> selectByExample(TAproductiveExample example);

    TAproductive selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAproductive record, @Param("example") TAproductiveExample example);

    int updateByExample(@Param("record") TAproductive record, @Param("example") TAproductiveExample example);

    int updateByPrimaryKeySelective(TAproductive record);

    int updateByPrimaryKey(TAproductive record);
}