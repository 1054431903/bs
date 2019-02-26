package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskplan;
import com.fwk.common.entity.TFtaskplanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskplanMapper {
    int countByExample(TFtaskplanExample example);

    int deleteByExample(TFtaskplanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskplan record);

    int insertSelective(TFtaskplan record);

    List<TFtaskplan> selectByExample(TFtaskplanExample example);

    TFtaskplan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskplan record, @Param("example") TFtaskplanExample example);

    int updateByExample(@Param("record") TFtaskplan record, @Param("example") TFtaskplanExample example);

    int updateByPrimaryKeySelective(TFtaskplan record);

    int updateByPrimaryKey(TFtaskplan record);
}