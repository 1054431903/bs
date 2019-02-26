package com.fwk.common.dao;

import com.fwk.common.entity.TClog;
import com.fwk.common.entity.TClogExample;
import com.fwk.common.entity.TClogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClogMapper {
    int countByExample(TClogExample example);

    int deleteByExample(TClogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TClogWithBLOBs record);

    int insertSelective(TClogWithBLOBs record);

    List<TClogWithBLOBs> selectByExampleWithBLOBs(TClogExample example);

    List<TClog> selectByExample(TClogExample example);

    TClogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TClogWithBLOBs record, @Param("example") TClogExample example);

    int updateByExampleWithBLOBs(@Param("record") TClogWithBLOBs record, @Param("example") TClogExample example);

    int updateByExample(@Param("record") TClog record, @Param("example") TClogExample example);

    int updateByPrimaryKeySelective(TClogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TClogWithBLOBs record);

    int updateByPrimaryKey(TClog record);
}