package com.fwk.common.dao;

import com.fwk.common.entity.TFrecordpartuser;
import com.fwk.common.entity.TFrecordpartuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFrecordpartuserMapper {
    int countByExample(TFrecordpartuserExample example);

    int deleteByExample(TFrecordpartuserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFrecordpartuser record);

    int insertSelective(TFrecordpartuser record);

    List<TFrecordpartuser> selectByExample(TFrecordpartuserExample example);

    TFrecordpartuser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFrecordpartuser record, @Param("example") TFrecordpartuserExample example);

    int updateByExample(@Param("record") TFrecordpartuser record, @Param("example") TFrecordpartuserExample example);

    int updateByPrimaryKeySelective(TFrecordpartuser record);

    int updateByPrimaryKey(TFrecordpartuser record);
}