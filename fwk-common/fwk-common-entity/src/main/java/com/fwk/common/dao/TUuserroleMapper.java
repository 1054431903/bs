package com.fwk.common.dao;

import com.fwk.common.entity.TUuserrole;
import com.fwk.common.entity.TUuserroleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUuserroleMapper {
    int countByExample(TUuserroleExample example);

    int deleteByExample(TUuserroleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUuserrole record);

    int insertSelective(TUuserrole record);

    List<TUuserrole> selectByExample(TUuserroleExample example);

    TUuserrole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUuserrole record, @Param("example") TUuserroleExample example);

    int updateByExample(@Param("record") TUuserrole record, @Param("example") TUuserroleExample example);

    int updateByPrimaryKeySelective(TUuserrole record);

    int updateByPrimaryKey(TUuserrole record);
}