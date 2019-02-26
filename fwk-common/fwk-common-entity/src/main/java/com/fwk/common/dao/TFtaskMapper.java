package com.fwk.common.dao;

import com.fwk.common.entity.TFtask;
import com.fwk.common.entity.TFtaskExample;
import com.fwk.common.entity.TFtaskWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFtaskMapper {
    int countByExample(TFtaskExample example);

    int deleteByExample(TFtaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFtaskWithBLOBs record);

    int insertSelective(TFtaskWithBLOBs record);

    List<TFtaskWithBLOBs> selectByExampleWithBLOBs(TFtaskExample example);

    List<TFtask> selectByExample(TFtaskExample example);

    TFtaskWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFtaskWithBLOBs record, @Param("example") TFtaskExample example);

    int updateByExampleWithBLOBs(@Param("record") TFtaskWithBLOBs record, @Param("example") TFtaskExample example);

    int updateByExample(@Param("record") TFtask record, @Param("example") TFtaskExample example);

    int updateByPrimaryKeySelective(TFtaskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TFtaskWithBLOBs record);

    int updateByPrimaryKey(TFtask record);
}