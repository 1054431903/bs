package com.fwk.common.dao;

import com.fwk.common.entity.TFcommunioncomment;
import com.fwk.common.entity.TFcommunioncommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFcommunioncommentMapper {
    int countByExample(TFcommunioncommentExample example);

    int deleteByExample(TFcommunioncommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFcommunioncomment record);

    int insertSelective(TFcommunioncomment record);

    List<TFcommunioncomment> selectByExampleWithBLOBs(TFcommunioncommentExample example);

    List<TFcommunioncomment> selectByExample(TFcommunioncommentExample example);

    TFcommunioncomment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFcommunioncomment record, @Param("example") TFcommunioncommentExample example);

    int updateByExampleWithBLOBs(@Param("record") TFcommunioncomment record, @Param("example") TFcommunioncommentExample example);

    int updateByExample(@Param("record") TFcommunioncomment record, @Param("example") TFcommunioncommentExample example);

    int updateByPrimaryKeySelective(TFcommunioncomment record);

    int updateByPrimaryKeyWithBLOBs(TFcommunioncomment record);

    int updateByPrimaryKey(TFcommunioncomment record);
}