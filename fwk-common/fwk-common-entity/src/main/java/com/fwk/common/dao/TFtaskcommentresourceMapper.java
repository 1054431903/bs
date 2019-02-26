package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskcommentresource;
import com.fwk.common.entity.TFtaskcommentresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskcommentresourceMapper {
    int countByExample(TFtaskcommentresourceExample example);

    int deleteByExample(TFtaskcommentresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskcommentresource record);

    int insertSelective(TFtaskcommentresource record);

    List<TFtaskcommentresource> selectByExample(TFtaskcommentresourceExample example);

    TFtaskcommentresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskcommentresource record, @Param("example") TFtaskcommentresourceExample example);

    int updateByExample(@Param("record") TFtaskcommentresource record, @Param("example") TFtaskcommentresourceExample example);

    int updateByPrimaryKeySelective(TFtaskcommentresource record);

    int updateByPrimaryKey(TFtaskcommentresource record);
}