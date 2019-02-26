package com.fwk.common.dao;

import com.fwk.common.entity.TFrecordresource;
import com.fwk.common.entity.TFrecordresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordresourceMapper {
    int countByExample(TFrecordresourceExample example);

    int deleteByExample(TFrecordresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecordresource record);

    int insertSelective(TFrecordresource record);

    List<TFrecordresource> selectByExample(TFrecordresourceExample example);

    TFrecordresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecordresource record, @Param("example") TFrecordresourceExample example);

    int updateByExample(@Param("record") TFrecordresource record, @Param("example") TFrecordresourceExample example);

    int updateByPrimaryKeySelective(TFrecordresource record);

    int updateByPrimaryKey(TFrecordresource record);
}