package com.fwk.common.dao;

import com.fwk.common.entity.TFrecordparcel;
import com.fwk.common.entity.TFrecordparcelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordparcelMapper {
    int countByExample(TFrecordparcelExample example);

    int deleteByExample(TFrecordparcelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecordparcel record);

    int insertSelective(TFrecordparcel record);

    List<TFrecordparcel> selectByExample(TFrecordparcelExample example);

    TFrecordparcel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecordparcel record, @Param("example") TFrecordparcelExample example);

    int updateByExample(@Param("record") TFrecordparcel record, @Param("example") TFrecordparcelExample example);

    int updateByPrimaryKeySelective(TFrecordparcel record);

    int updateByPrimaryKey(TFrecordparcel record);
}