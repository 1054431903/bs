package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelcoord;
import com.fwk.common.entity.TPparcelcoordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelcoordMapper {
    int countByExample(TPparcelcoordExample example);

    int deleteByExample(TPparcelcoordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelcoord record);

    int insertSelective(TPparcelcoord record);

    List<TPparcelcoord> selectByExample(TPparcelcoordExample example);

    TPparcelcoord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelcoord record, @Param("example") TPparcelcoordExample example);

    int updateByExample(@Param("record") TPparcelcoord record, @Param("example") TPparcelcoordExample example);

    int updateByPrimaryKeySelective(TPparcelcoord record);

    int updateByPrimaryKey(TPparcelcoord record);
}