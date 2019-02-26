package com.fwk.common.dao;

import com.fwk.common.entity.TFtaskexecute;
import com.fwk.common.entity.TFtaskexecuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskexecuteMapper {
    int countByExample(TFtaskexecuteExample example);

    int deleteByExample(TFtaskexecuteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskexecute record);

    int insertSelective(TFtaskexecute record);

    List<TFtaskexecute> selectByExample(TFtaskexecuteExample example);

    TFtaskexecute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskexecute record, @Param("example") TFtaskexecuteExample example);

    int updateByExample(@Param("record") TFtaskexecute record, @Param("example") TFtaskexecuteExample example);

    int updateByPrimaryKeySelective(TFtaskexecute record);

    int updateByPrimaryKey(TFtaskexecute record);
}