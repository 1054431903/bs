package com.fwk.common.dao;

import com.fwk.common.entity.TFplanparcel;
import com.fwk.common.entity.TFplanparcelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanparcelMapper {
    int countByExample(TFplanparcelExample example);

    int deleteByExample(TFplanparcelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanparcel record);

    int insertSelective(TFplanparcel record);

    List<TFplanparcel> selectByExample(TFplanparcelExample example);

    TFplanparcel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanparcel record, @Param("example") TFplanparcelExample example);

    int updateByExample(@Param("record") TFplanparcel record, @Param("example") TFplanparcelExample example);

    int updateByPrimaryKeySelective(TFplanparcel record);

    int updateByPrimaryKey(TFplanparcel record);
}