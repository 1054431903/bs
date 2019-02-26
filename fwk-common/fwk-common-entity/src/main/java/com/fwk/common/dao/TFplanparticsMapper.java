package com.fwk.common.dao;

import com.fwk.common.entity.TFplanpartics;
import com.fwk.common.entity.TFplanparticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanparticsMapper {
    int countByExample(TFplanparticsExample example);

    int deleteByExample(TFplanparticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanpartics record);

    int insertSelective(TFplanpartics record);

    List<TFplanpartics> selectByExample(TFplanparticsExample example);

    TFplanpartics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanpartics record, @Param("example") TFplanparticsExample example);

    int updateByExample(@Param("record") TFplanpartics record, @Param("example") TFplanparticsExample example);

    int updateByPrimaryKeySelective(TFplanpartics record);

    int updateByPrimaryKey(TFplanpartics record);
}