package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskuserpermisions;
import com.fwk.common.entity.TFtaskuserpermisionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskuserpermisionsMapper {
    int countByExample(TFtaskuserpermisionsExample example);

    int deleteByExample(TFtaskuserpermisionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskuserpermisions record);

    int insertSelective(TFtaskuserpermisions record);

    List<TFtaskuserpermisions> selectByExample(TFtaskuserpermisionsExample example);

    TFtaskuserpermisions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskuserpermisions record, @Param("example") TFtaskuserpermisionsExample example);

    int updateByExample(@Param("record") TFtaskuserpermisions record, @Param("example") TFtaskuserpermisionsExample example);

    int updateByPrimaryKeySelective(TFtaskuserpermisions record);

    int updateByPrimaryKey(TFtaskuserpermisions record);
}