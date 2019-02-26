package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunioncategory;
import com.fwk.common.entity.TFcommunioncategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunioncategoryMapper {
    int countByExample(TFcommunioncategoryExample example);

    int deleteByExample(TFcommunioncategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunioncategory record);

    int insertSelective(TFcommunioncategory record);

    List<TFcommunioncategory> selectByExample(TFcommunioncategoryExample example);

    TFcommunioncategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunioncategory record, @Param("example") TFcommunioncategoryExample example);

    int updateByExample(@Param("record") TFcommunioncategory record, @Param("example") TFcommunioncategoryExample example);

    int updateByPrimaryKeySelective(TFcommunioncategory record);

    int updateByPrimaryKey(TFcommunioncategory record);
}