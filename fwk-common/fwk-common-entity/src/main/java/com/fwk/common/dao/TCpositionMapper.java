package com.fwk.common.dao;

import com.fwk.common.entity.TCposition;
import com.fwk.common.entity.TCpositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCpositionMapper {
    int countByExample(TCpositionExample example);

    int deleteByExample(TCpositionExample example);

    int deleteByPrimaryKey(String code);

    int insert(TCposition record);

    int insertSelective(TCposition record);

    List<TCposition> selectByExample(TCpositionExample example);

    TCposition selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") TCposition record, @Param("example") TCpositionExample example);

    int updateByExample(@Param("record") TCposition record, @Param("example") TCpositionExample example);

    int updateByPrimaryKeySelective(TCposition record);

    int updateByPrimaryKey(TCposition record);
}