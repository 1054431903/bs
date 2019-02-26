package com.fwk.common.dao;

import com.fwk.common.entity.TFrecordoutput;
import com.fwk.common.entity.TFrecordoutputExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordoutputMapper {
    int countByExample(TFrecordoutputExample example);

    int deleteByExample(TFrecordoutputExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecordoutput record);

    int insertSelective(TFrecordoutput record);

    List<TFrecordoutput> selectByExample(TFrecordoutputExample example);

    TFrecordoutput selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecordoutput record, @Param("example") TFrecordoutputExample example);

    int updateByExample(@Param("record") TFrecordoutput record, @Param("example") TFrecordoutputExample example);

    int updateByPrimaryKeySelective(TFrecordoutput record);

    int updateByPrimaryKey(TFrecordoutput record);
}