package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunionparcel;
import com.fwk.common.entity.TFcommunionparcelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunionparcelMapper {
    int countByExample(TFcommunionparcelExample example);

    int deleteByExample(TFcommunionparcelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunionparcel record);

    int insertSelective(TFcommunionparcel record);

    List<TFcommunionparcel> selectByExample(TFcommunionparcelExample example);

    TFcommunionparcel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunionparcel record, @Param("example") TFcommunionparcelExample example);

    int updateByExample(@Param("record") TFcommunionparcel record, @Param("example") TFcommunionparcelExample example);

    int updateByPrimaryKeySelective(TFcommunionparcel record);

    int updateByPrimaryKey(TFcommunionparcel record);
}