package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskcopy;
import com.fwk.common.entity.TFtaskcopyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskcopyMapper {
    int countByExample(TFtaskcopyExample example);

    int deleteByExample(TFtaskcopyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskcopy record);

    int insertSelective(TFtaskcopy record);

    List<TFtaskcopy> selectByExample(TFtaskcopyExample example);

    TFtaskcopy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskcopy record, @Param("example") TFtaskcopyExample example);

    int updateByExample(@Param("record") TFtaskcopy record, @Param("example") TFtaskcopyExample example);

    int updateByPrimaryKeySelective(TFtaskcopy record);

    int updateByPrimaryKey(TFtaskcopy record);
}