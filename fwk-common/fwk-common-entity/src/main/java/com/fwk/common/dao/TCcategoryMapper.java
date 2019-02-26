package com.fwk.common.dao;

import com.fwk.common.entity.TCcategory;
import com.fwk.common.entity.TCcategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCcategoryMapper {
    int countByExample(TCcategoryExample example);

    int deleteByExample(TCcategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCcategory record);

    int insertSelective(TCcategory record);

    List<TCcategory> selectByExample(TCcategoryExample example);

    TCcategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCcategory record, @Param("example") TCcategoryExample example);

    int updateByExample(@Param("record") TCcategory record, @Param("example") TCcategoryExample example);

    int updateByPrimaryKeySelective(TCcategory record);

    int updateByPrimaryKey(TCcategory record);
}