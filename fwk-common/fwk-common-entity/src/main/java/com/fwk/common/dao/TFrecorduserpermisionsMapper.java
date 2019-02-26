package com.fwk.common.dao;

import com.fwk.common.entity.TFrecorduserpermisions;
import com.fwk.common.entity.TFrecorduserpermisionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecorduserpermisionsMapper {
    int countByExample(TFrecorduserpermisionsExample example);

    int deleteByExample(TFrecorduserpermisionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecorduserpermisions record);

    int insertSelective(TFrecorduserpermisions record);

    List<TFrecorduserpermisions> selectByExample(TFrecorduserpermisionsExample example);

    TFrecorduserpermisions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecorduserpermisions record, @Param("example") TFrecorduserpermisionsExample example);

    int updateByExample(@Param("record") TFrecorduserpermisions record, @Param("example") TFrecorduserpermisionsExample example);

    int updateByPrimaryKeySelective(TFrecorduserpermisions record);

    int updateByPrimaryKey(TFrecorduserpermisions record);
}