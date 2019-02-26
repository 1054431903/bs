package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelmeassoilresource;
import com.fwk.common.entity.TPparcelmeassoilresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelmeassoilresourceMapper {
    int countByExample(TPparcelmeassoilresourceExample example);

    int deleteByExample(TPparcelmeassoilresourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelmeassoilresource record);

    int insertSelective(TPparcelmeassoilresource record);

    List<TPparcelmeassoilresource> selectByExample(TPparcelmeassoilresourceExample example);

    TPparcelmeassoilresource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelmeassoilresource record, @Param("example") TPparcelmeassoilresourceExample example);

    int updateByExample(@Param("record") TPparcelmeassoilresource record, @Param("example") TPparcelmeassoilresourceExample example);

    int updateByPrimaryKeySelective(TPparcelmeassoilresource record);

    int updateByPrimaryKey(TPparcelmeassoilresource record);
}