package com.fwk.common.dao;

import com.fwk.common.entity.TPparcelmeassoil;
import com.fwk.common.entity.TPparcelmeassoilExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPparcelmeassoilMapper {
    int countByExample(TPparcelmeassoilExample example);

    int deleteByExample(TPparcelmeassoilExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPparcelmeassoil record);

    int insertSelective(TPparcelmeassoil record);

    List<TPparcelmeassoil> selectByExample(TPparcelmeassoilExample example);

    TPparcelmeassoil selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPparcelmeassoil record, @Param("example") TPparcelmeassoilExample example);

    int updateByExample(@Param("record") TPparcelmeassoil record, @Param("example") TPparcelmeassoilExample example);

    int updateByPrimaryKeySelective(TPparcelmeassoil record);

    int updateByPrimaryKey(TPparcelmeassoil record);
}