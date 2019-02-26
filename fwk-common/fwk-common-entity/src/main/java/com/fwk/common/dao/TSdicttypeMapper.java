package com.fwk.common.dao;

import com.fwk.common.entity.TSdicttype;
import com.fwk.common.entity.TSdicttypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSdicttypeMapper {
    int countByExample(TSdicttypeExample example);

    int deleteByExample(TSdicttypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSdicttype record);

    int insertSelective(TSdicttype record);

    List<TSdicttype> selectByExample(TSdicttypeExample example);

    TSdicttype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSdicttype record, @Param("example") TSdicttypeExample example);

    int updateByExample(@Param("record") TSdicttype record, @Param("example") TSdicttypeExample example);

    int updateByPrimaryKeySelective(TSdicttype record);

    int updateByPrimaryKey(TSdicttype record);
}