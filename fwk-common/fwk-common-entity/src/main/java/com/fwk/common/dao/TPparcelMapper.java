package com.fwk.common.dao;

import com.fwk.common.entity.TPparcel;
import com.fwk.common.entity.TPparcelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelMapper {
    int countByExample(TPparcelExample example);

    int deleteByExample(TPparcelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcel record);

    int insertSelective(TPparcel record);

    List<TPparcel> selectByExample(TPparcelExample example);

    TPparcel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcel record, @Param("example") TPparcelExample example);

    int updateByExample(@Param("record") TPparcel record, @Param("example") TPparcelExample example);

    int updateByPrimaryKeySelective(TPparcel record);

    int updateByPrimaryKey(TPparcel record);
}