package com.fwk.common.dao;

import com.fwk.common.entity.TPparceluser;
import com.fwk.common.entity.TPparceluserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparceluserMapper {
    int countByExample(TPparceluserExample example);

    int deleteByExample(TPparceluserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparceluser record);

    int insertSelective(TPparceluser record);

    List<TPparceluser> selectByExample(TPparceluserExample example);

    TPparceluser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparceluser record, @Param("example") TPparceluserExample example);

    int updateByExample(@Param("record") TPparceluser record, @Param("example") TPparceluserExample example);

    int updateByPrimaryKeySelective(TPparceluser record);

    int updateByPrimaryKey(TPparceluser record);
}