package com.fwk.common.dao;

import com.fwk.common.entity.TFpest;
import com.fwk.common.entity.TFpestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFpestMapper {
    int countByExample(TFpestExample example);

    int deleteByExample(TFpestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFpest record);

    int insertSelective(TFpest record);

    List<TFpest> selectByExampleWithBLOBs(TFpestExample example);

    List<TFpest> selectByExample(TFpestExample example);

    TFpest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFpest record, @Param("example") TFpestExample example);

    int updateByExampleWithBLOBs(@Param("record") TFpest record, @Param("example") TFpestExample example);

    int updateByExample(@Param("record") TFpest record, @Param("example") TFpestExample example);

    int updateByPrimaryKeySelective(TFpest record);

    int updateByPrimaryKeyWithBLOBs(TFpest record);

    int updateByPrimaryKey(TFpest record);
}