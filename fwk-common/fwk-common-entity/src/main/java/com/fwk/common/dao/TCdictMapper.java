package com.fwk.common.dao;

import com.fwk.common.entity.TCdict;
import com.fwk.common.entity.TCdictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCdictMapper {
    int countByExample(TCdictExample example);

    int deleteByExample(TCdictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCdict record);

    int insertSelective(TCdict record);

    List<TCdict> selectByExample(TCdictExample example);

    TCdict selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCdict record, @Param("example") TCdictExample example);

    int updateByExample(@Param("record") TCdict record, @Param("example") TCdictExample example);

    int updateByPrimaryKeySelective(TCdict record);

    int updateByPrimaryKey(TCdict record);
}