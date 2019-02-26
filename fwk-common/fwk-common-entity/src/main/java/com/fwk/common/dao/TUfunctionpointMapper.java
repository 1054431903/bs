package com.fwk.common.dao;

import com.fwk.common.entity.TUfunctionpoint;
import com.fwk.common.entity.TUfunctionpointExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUfunctionpointMapper {
    int countByExample(TUfunctionpointExample example);

    int deleteByExample(TUfunctionpointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUfunctionpoint record);

    int insertSelective(TUfunctionpoint record);

    List<TUfunctionpoint> selectByExample(TUfunctionpointExample example);

    TUfunctionpoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUfunctionpoint record, @Param("example") TUfunctionpointExample example);

    int updateByExample(@Param("record") TUfunctionpoint record, @Param("example") TUfunctionpointExample example);

    int updateByPrimaryKeySelective(TUfunctionpoint record);

    int updateByPrimaryKey(TUfunctionpoint record);
}