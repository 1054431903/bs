package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunionuserpermisions;
import com.fwk.common.entity.TFcommunionuserpermisionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunionuserpermisionsMapper {
    int countByExample(TFcommunionuserpermisionsExample example);

    int deleteByExample(TFcommunionuserpermisionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunionuserpermisions record);

    int insertSelective(TFcommunionuserpermisions record);

    List<TFcommunionuserpermisions> selectByExample(TFcommunionuserpermisionsExample example);

    TFcommunionuserpermisions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunionuserpermisions record, @Param("example") TFcommunionuserpermisionsExample example);

    int updateByExample(@Param("record") TFcommunionuserpermisions record, @Param("example") TFcommunionuserpermisionsExample example);

    int updateByPrimaryKeySelective(TFcommunionuserpermisions record);

    int updateByPrimaryKey(TFcommunionuserpermisions record);
}