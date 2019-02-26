package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskparcel;
import com.fwk.common.entity.TFtaskparcelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskparcelMapper {
    int countByExample(TFtaskparcelExample example);

    int deleteByExample(TFtaskparcelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskparcel record);

    int insertSelective(TFtaskparcel record);

    List<TFtaskparcel> selectByExample(TFtaskparcelExample example);

    TFtaskparcel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskparcel record, @Param("example") TFtaskparcelExample example);

    int updateByExample(@Param("record") TFtaskparcel record, @Param("example") TFtaskparcelExample example);

    int updateByPrimaryKeySelective(TFtaskparcel record);

    int updateByPrimaryKey(TFtaskparcel record);
}