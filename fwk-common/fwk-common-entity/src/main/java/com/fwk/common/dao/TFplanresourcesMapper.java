package com.fwk.common.dao;

import com.fwk.common.entity.TFplanresources;
import com.fwk.common.entity.TFplanresourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanresourcesMapper {
    int countByExample(TFplanresourcesExample example);

    int deleteByExample(TFplanresourcesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanresources record);

    int insertSelective(TFplanresources record);

    List<TFplanresources> selectByExample(TFplanresourcesExample example);

    TFplanresources selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanresources record, @Param("example") TFplanresourcesExample example);

    int updateByExample(@Param("record") TFplanresources record, @Param("example") TFplanresourcesExample example);

    int updateByPrimaryKeySelective(TFplanresources record);

    int updateByPrimaryKey(TFplanresources record);
}