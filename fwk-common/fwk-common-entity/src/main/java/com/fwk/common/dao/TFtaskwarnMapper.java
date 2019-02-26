package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskwarn;
import com.fwk.common.entity.TFtaskwarnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskwarnMapper {
    int countByExample(TFtaskwarnExample example);

    int deleteByExample(TFtaskwarnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskwarn record);

    int insertSelective(TFtaskwarn record);

    List<TFtaskwarn> selectByExample(TFtaskwarnExample example);

    TFtaskwarn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskwarn record, @Param("example") TFtaskwarnExample example);

    int updateByExample(@Param("record") TFtaskwarn record, @Param("example") TFtaskwarnExample example);

    int updateByPrimaryKeySelective(TFtaskwarn record);

    int updateByPrimaryKey(TFtaskwarn record);
}