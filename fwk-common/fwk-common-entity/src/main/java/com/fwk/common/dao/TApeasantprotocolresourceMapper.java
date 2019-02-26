package com.fwk.common.dao;

import com.fwk.common.entity.TApeasantprotocolresource;
import com.fwk.common.entity.TApeasantprotocolresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TApeasantprotocolresourceMapper {
    int countByExample(TApeasantprotocolresourceExample example);

    int deleteByExample(TApeasantprotocolresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TApeasantprotocolresource record);

    int insertSelective(TApeasantprotocolresource record);

    List<TApeasantprotocolresource> selectByExample(TApeasantprotocolresourceExample example);

    TApeasantprotocolresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TApeasantprotocolresource record, @Param("example") TApeasantprotocolresourceExample example);

    int updateByExample(@Param("record") TApeasantprotocolresource record, @Param("example") TApeasantprotocolresourceExample example);

    int updateByPrimaryKeySelective(TApeasantprotocolresource record);

    int updateByPrimaryKey(TApeasantprotocolresource record);
}