package com.fwk.common.dao;

import com.fwk.common.entity.TFrecord;
import com.fwk.common.entity.TFrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordMapper {
    int countByExample(TFrecordExample example);

    int deleteByExample(TFrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecord record);

    int insertSelective(TFrecord record);

    List<TFrecord> selectByExampleWithBLOBs(TFrecordExample example);

    List<TFrecord> selectByExample(TFrecordExample example);

    TFrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecord record, @Param("example") TFrecordExample example);

    int updateByExampleWithBLOBs(@Param("record") TFrecord record, @Param("example") TFrecordExample example);

    int updateByExample(@Param("record") TFrecord record, @Param("example") TFrecordExample example);

    int updateByPrimaryKeySelective(TFrecord record);

    int updateByPrimaryKeyWithBLOBs(TFrecord record);

    int updateByPrimaryKey(TFrecord record);
}