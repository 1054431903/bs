package com.fwk.common.dao;

import com.fwk.common.entity.TAagricname;
import com.fwk.common.entity.TAagricnameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAagricnameMapper {
    int countByExample(TAagricnameExample example);

    int deleteByExample(TAagricnameExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAagricname record);

    int insertSelective(TAagricname record);

    List<TAagricname> selectByExample(TAagricnameExample example);

    TAagricname selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAagricname record, @Param("example") TAagricnameExample example);

    int updateByExample(@Param("record") TAagricname record, @Param("example") TAagricnameExample example);

    int updateByPrimaryKeySelective(TAagricname record);

    int updateByPrimaryKey(TAagricname record);
}