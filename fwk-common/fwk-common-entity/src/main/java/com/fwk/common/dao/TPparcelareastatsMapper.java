package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelareastats;
import com.fwk.common.entity.TPparcelareastatsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelareastatsMapper {
    int countByExample(TPparcelareastatsExample example);

    int deleteByExample(TPparcelareastatsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelareastats record);

    int insertSelective(TPparcelareastats record);

    List<TPparcelareastats> selectByExample(TPparcelareastatsExample example);

    TPparcelareastats selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelareastats record, @Param("example") TPparcelareastatsExample example);

    int updateByExample(@Param("record") TPparcelareastats record, @Param("example") TPparcelareastatsExample example);

    int updateByPrimaryKeySelective(TPparcelareastats record);

    int updateByPrimaryKey(TPparcelareastats record);
}