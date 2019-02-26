package com.fwk.common.dao;

import com.fwk.common.entity.TPparceltype;
import com.fwk.common.entity.TPparceltypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparceltypeMapper {
    int countByExample(TPparceltypeExample example);

    int deleteByExample(TPparceltypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparceltype record);

    int insertSelective(TPparceltype record);

    List<TPparceltype> selectByExample(TPparceltypeExample example);

    TPparceltype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparceltype record, @Param("example") TPparceltypeExample example);

    int updateByExample(@Param("record") TPparceltype record, @Param("example") TPparceltypeExample example);

    int updateByPrimaryKeySelective(TPparceltype record);

    int updateByPrimaryKey(TPparceltype record);
}