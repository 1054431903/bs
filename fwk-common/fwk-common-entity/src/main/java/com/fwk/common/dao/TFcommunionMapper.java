package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunion;
import com.fwk.common.entity.TFcommunionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunionMapper {
    int countByExample(TFcommunionExample example);

    int deleteByExample(TFcommunionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunion record);

    int insertSelective(TFcommunion record);

    List<TFcommunion> selectByExampleWithBLOBs(TFcommunionExample example);

    List<TFcommunion> selectByExample(TFcommunionExample example);

    TFcommunion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunion record, @Param("example") TFcommunionExample example);

    int updateByExampleWithBLOBs(@Param("record") TFcommunion record, @Param("example") TFcommunionExample example);

    int updateByExample(@Param("record") TFcommunion record, @Param("example") TFcommunionExample example);

    int updateByPrimaryKeySelective(TFcommunion record);

    int updateByPrimaryKeyWithBLOBs(TFcommunion record);

    int updateByPrimaryKey(TFcommunion record);
}