package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskresources;
import com.fwk.common.entity.TFtaskresourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskresourcesMapper {
    int countByExample(TFtaskresourcesExample example);

    int deleteByExample(TFtaskresourcesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskresources record);

    int insertSelective(TFtaskresources record);

    List<TFtaskresources> selectByExample(TFtaskresourcesExample example);

    TFtaskresources selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskresources record, @Param("example") TFtaskresourcesExample example);

    int updateByExample(@Param("record") TFtaskresources record, @Param("example") TFtaskresourcesExample example);

    int updateByPrimaryKeySelective(TFtaskresources record);

    int updateByPrimaryKey(TFtaskresources record);
}