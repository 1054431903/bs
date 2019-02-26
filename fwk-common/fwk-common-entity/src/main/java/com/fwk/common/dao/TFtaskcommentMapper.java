package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskcomment;
import com.fwk.common.entity.TFtaskcommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskcommentMapper {
    int countByExample(TFtaskcommentExample example);

    int deleteByExample(TFtaskcommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskcomment record);

    int insertSelective(TFtaskcomment record);

    List<TFtaskcomment> selectByExample(TFtaskcommentExample example);

    TFtaskcomment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskcomment record, @Param("example") TFtaskcommentExample example);

    int updateByExample(@Param("record") TFtaskcomment record, @Param("example") TFtaskcommentExample example);

    int updateByPrimaryKeySelective(TFtaskcomment record);

    int updateByPrimaryKey(TFtaskcomment record);
}