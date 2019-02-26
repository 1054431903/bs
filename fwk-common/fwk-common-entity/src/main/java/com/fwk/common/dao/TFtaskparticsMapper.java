package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskpartics;
import com.fwk.common.entity.TFtaskparticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskparticsMapper {
    int countByExample(TFtaskparticsExample example);

    int deleteByExample(TFtaskparticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskpartics record);

    int insertSelective(TFtaskpartics record);

    List<TFtaskpartics> selectByExampleWithBLOBs(TFtaskparticsExample example);

    List<TFtaskpartics> selectByExample(TFtaskparticsExample example);

    TFtaskpartics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskpartics record, @Param("example") TFtaskparticsExample example);

    int updateByExampleWithBLOBs(@Param("record") TFtaskpartics record, @Param("example") TFtaskparticsExample example);

    int updateByExample(@Param("record") TFtaskpartics record, @Param("example") TFtaskparticsExample example);

    int updateByPrimaryKeySelective(TFtaskpartics record);

    int updateByPrimaryKeyWithBLOBs(TFtaskpartics record);

    int updateByPrimaryKey(TFtaskpartics record);
}