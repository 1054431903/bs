package com.fwk.common.dao;

import com.fwk.common.entity.TApeasantprotocol;
import com.fwk.common.entity.TApeasantprotocolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TApeasantprotocolMapper {
    int countByExample(TApeasantprotocolExample example);

    int deleteByExample(TApeasantprotocolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TApeasantprotocol record);

    int insertSelective(TApeasantprotocol record);

    List<TApeasantprotocol> selectByExample(TApeasantprotocolExample example);

    TApeasantprotocol selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TApeasantprotocol record, @Param("example") TApeasantprotocolExample example);

    int updateByExample(@Param("record") TApeasantprotocol record, @Param("example") TApeasantprotocolExample example);

    int updateByPrimaryKeySelective(TApeasantprotocol record);

    int updateByPrimaryKey(TApeasantprotocol record);
}