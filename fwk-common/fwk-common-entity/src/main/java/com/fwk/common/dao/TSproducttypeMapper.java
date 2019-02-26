package com.fwk.common.dao;

import com.fwk.common.entity.TSproducttype;
import com.fwk.common.entity.TSproducttypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSproducttypeMapper {
    int countByExample(TSproducttypeExample example);

    int deleteByExample(TSproducttypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSproducttype record);

    int insertSelective(TSproducttype record);

    List<TSproducttype> selectByExample(TSproducttypeExample example);

    TSproducttype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSproducttype record, @Param("example") TSproducttypeExample example);

    int updateByExample(@Param("record") TSproducttype record, @Param("example") TSproducttypeExample example);

    int updateByPrimaryKeySelective(TSproducttype record);

    int updateByPrimaryKey(TSproducttype record);
}