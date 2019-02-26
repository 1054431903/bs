package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunionresource;
import com.fwk.common.entity.TFcommunionresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunionresourceMapper {
    int countByExample(TFcommunionresourceExample example);

    int deleteByExample(TFcommunionresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunionresource record);

    int insertSelective(TFcommunionresource record);

    List<TFcommunionresource> selectByExample(TFcommunionresourceExample example);

    TFcommunionresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunionresource record, @Param("example") TFcommunionresourceExample example);

    int updateByExample(@Param("record") TFcommunionresource record, @Param("example") TFcommunionresourceExample example);

    int updateByPrimaryKeySelective(TFcommunionresource record);

    int updateByPrimaryKey(TFcommunionresource record);
}