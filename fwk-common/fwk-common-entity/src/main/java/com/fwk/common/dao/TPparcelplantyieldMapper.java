package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelplantyield;
import com.fwk.common.entity.TPparcelplantyieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelplantyieldMapper {
    int countByExample(TPparcelplantyieldExample example);

    int deleteByExample(TPparcelplantyieldExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelplantyield record);

    int insertSelective(TPparcelplantyield record);

    List<TPparcelplantyield> selectByExample(TPparcelplantyieldExample example);

    TPparcelplantyield selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelplantyield record, @Param("example") TPparcelplantyieldExample example);

    int updateByExample(@Param("record") TPparcelplantyield record, @Param("example") TPparcelplantyieldExample example);

    int updateByPrimaryKeySelective(TPparcelplantyield record);

    int updateByPrimaryKey(TPparcelplantyield record);
}