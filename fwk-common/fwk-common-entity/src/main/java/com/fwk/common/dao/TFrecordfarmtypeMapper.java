package com.fwk.common.dao;

import com.fwk.common.entity.TFrecordfarmtype;
import com.fwk.common.entity.TFrecordfarmtypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordfarmtypeMapper {
    int countByExample(TFrecordfarmtypeExample example);

    int deleteByExample(TFrecordfarmtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecordfarmtype record);

    int insertSelective(TFrecordfarmtype record);

    List<TFrecordfarmtype> selectByExample(TFrecordfarmtypeExample example);

    TFrecordfarmtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecordfarmtype record, @Param("example") TFrecordfarmtypeExample example);

    int updateByExample(@Param("record") TFrecordfarmtype record, @Param("example") TFrecordfarmtypeExample example);

    int updateByPrimaryKeySelective(TFrecordfarmtype record);

    int updateByPrimaryKey(TFrecordfarmtype record);
}