package com.fwk.common.dao;

import com.fwk.common.entity.TUrole;
import com.fwk.common.entity.TUroleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUroleMapper {
    int countByExample(TUroleExample example);

    int deleteByExample(TUroleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUrole record);

    int insertSelective(TUrole record);

    List<TUrole> selectByExample(TUroleExample example);

    TUrole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUrole record, @Param("example") TUroleExample example);

    int updateByExample(@Param("record") TUrole record, @Param("example") TUroleExample example);

    int updateByPrimaryKeySelective(TUrole record);

    int updateByPrimaryKey(TUrole record);
}