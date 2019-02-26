package com.fwk.common.dao;

import com.fwk.common.entity.TApeasant;
import com.fwk.common.entity.TApeasantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TApeasantMapper {
    int countByExample(TApeasantExample example);

    int deleteByExample(TApeasantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TApeasant record);

    int insertSelective(TApeasant record);

    List<TApeasant> selectByExample(TApeasantExample example);

    TApeasant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TApeasant record, @Param("example") TApeasantExample example);

    int updateByExample(@Param("record") TApeasant record, @Param("example") TApeasantExample example);

    int updateByPrimaryKeySelective(TApeasant record);

    int updateByPrimaryKey(TApeasant record);
}