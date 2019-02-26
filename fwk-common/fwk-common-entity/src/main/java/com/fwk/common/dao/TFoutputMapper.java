package com.fwk.common.dao;

import com.fwk.common.entity.TFoutput;
import com.fwk.common.entity.TFoutputExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFoutputMapper {
    int countByExample(TFoutputExample example);

    int deleteByExample(TFoutputExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFoutput record);

    int insertSelective(TFoutput record);

    List<TFoutput> selectByExample(TFoutputExample example);

    TFoutput selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFoutput record, @Param("example") TFoutputExample example);

    int updateByExample(@Param("record") TFoutput record, @Param("example") TFoutputExample example);

    int updateByPrimaryKeySelective(TFoutput record);

    int updateByPrimaryKey(TFoutput record);
}