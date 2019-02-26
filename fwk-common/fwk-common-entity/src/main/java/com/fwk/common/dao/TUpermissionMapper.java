package com.fwk.common.dao;

import com.fwk.common.entity.TUpermission;
import com.fwk.common.entity.TUpermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUpermissionMapper {
    int countByExample(TUpermissionExample example);

    int deleteByExample(TUpermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUpermission record);

    int insertSelective(TUpermission record);

    List<TUpermission> selectByExample(TUpermissionExample example);

    TUpermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUpermission record, @Param("example") TUpermissionExample example);

    int updateByExample(@Param("record") TUpermission record, @Param("example") TUpermissionExample example);

    int updateByPrimaryKeySelective(TUpermission record);

    int updateByPrimaryKey(TUpermission record);
}