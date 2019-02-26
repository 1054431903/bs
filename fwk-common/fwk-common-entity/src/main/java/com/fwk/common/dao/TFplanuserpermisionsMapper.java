package com.fwk.common.dao;

import com.fwk.common.entity.TFplanuserpermisions;
import com.fwk.common.entity.TFplanuserpermisionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanuserpermisionsMapper {
    int countByExample(TFplanuserpermisionsExample example);

    int deleteByExample(TFplanuserpermisionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanuserpermisions record);

    int insertSelective(TFplanuserpermisions record);

    List<TFplanuserpermisions> selectByExample(TFplanuserpermisionsExample example);

    TFplanuserpermisions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanuserpermisions record, @Param("example") TFplanuserpermisionsExample example);

    int updateByExample(@Param("record") TFplanuserpermisions record, @Param("example") TFplanuserpermisionsExample example);

    int updateByPrimaryKeySelective(TFplanuserpermisions record);

    int updateByPrimaryKey(TFplanuserpermisions record);
}