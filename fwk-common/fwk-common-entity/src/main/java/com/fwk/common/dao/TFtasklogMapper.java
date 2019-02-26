package com.fwk.common.dao;

import com.fwk.common.entity.TFtasklog;
import com.fwk.common.entity.TFtasklogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtasklogMapper {
    int countByExample(TFtasklogExample example);

    int deleteByExample(TFtasklogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtasklog record);

    int insertSelective(TFtasklog record);

    List<TFtasklog> selectByExample(TFtasklogExample example);

    TFtasklog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtasklog record, @Param("example") TFtasklogExample example);

    int updateByExample(@Param("record") TFtasklog record, @Param("example") TFtasklogExample example);

    int updateByPrimaryKeySelective(TFtasklog record);

    int updateByPrimaryKey(TFtasklog record);
}