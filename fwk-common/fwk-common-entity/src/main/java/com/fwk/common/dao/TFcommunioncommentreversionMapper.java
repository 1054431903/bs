package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunioncommentreversion;
import com.fwk.common.entity.TFcommunioncommentreversionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunioncommentreversionMapper {
    int countByExample(TFcommunioncommentreversionExample example);

    int deleteByExample(TFcommunioncommentreversionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunioncommentreversion record);

    int insertSelective(TFcommunioncommentreversion record);

    List<TFcommunioncommentreversion> selectByExampleWithBLOBs(TFcommunioncommentreversionExample example);

    List<TFcommunioncommentreversion> selectByExample(TFcommunioncommentreversionExample example);

    TFcommunioncommentreversion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunioncommentreversion record, @Param("example") TFcommunioncommentreversionExample example);

    int updateByExampleWithBLOBs(@Param("record") TFcommunioncommentreversion record, @Param("example") TFcommunioncommentreversionExample example);

    int updateByExample(@Param("record") TFcommunioncommentreversion record, @Param("example") TFcommunioncommentreversionExample example);

    int updateByPrimaryKeySelective(TFcommunioncommentreversion record);

    int updateByPrimaryKeyWithBLOBs(TFcommunioncommentreversion record);

    int updateByPrimaryKey(TFcommunioncommentreversion record);
}