package com.fwk.common.dao;

import com.fwk.common.entity.TFpestcomment;
import com.fwk.common.entity.TFpestcommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFpestcommentMapper {
    int countByExample(TFpestcommentExample example);

    int deleteByExample(TFpestcommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFpestcomment record);

    int insertSelective(TFpestcomment record);

    List<TFpestcomment> selectByExample(TFpestcommentExample example);

    TFpestcomment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFpestcomment record, @Param("example") TFpestcommentExample example);

    int updateByExample(@Param("record") TFpestcomment record, @Param("example") TFpestcommentExample example);

    int updateByPrimaryKeySelective(TFpestcomment record);

    int updateByPrimaryKey(TFpestcomment record);
}