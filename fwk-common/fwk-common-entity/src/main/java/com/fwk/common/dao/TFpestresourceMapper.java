package com.fwk.common.dao;

import com.fwk.common.entity.TFpestresource;
import com.fwk.common.entity.TFpestresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFpestresourceMapper {
    int countByExample(TFpestresourceExample example);

    int deleteByExample(TFpestresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFpestresource record);

    int insertSelective(TFpestresource record);

    List<TFpestresource> selectByExample(TFpestresourceExample example);

    TFpestresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFpestresource record, @Param("example") TFpestresourceExample example);

    int updateByExample(@Param("record") TFpestresource record, @Param("example") TFpestresourceExample example);

    int updateByPrimaryKeySelective(TFpestresource record);

    int updateByPrimaryKey(TFpestresource record);
}