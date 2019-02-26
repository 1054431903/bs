package com.fwk.common.dao;

import com.fwk.common.entity.TUusers;
import com.fwk.common.entity.TUusersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUusersMapper {
    int countByExample(TUusersExample example);

    int deleteByExample(TUusersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUusers record);

    int insertSelective(TUusers record);

    List<TUusers> selectByExample(TUusersExample example);

    TUusers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUusers record, @Param("example") TUusersExample example);

    int updateByExample(@Param("record") TUusers record, @Param("example") TUusersExample example);

    int updateByPrimaryKeySelective(TUusers record);

    int updateByPrimaryKey(TUusers record);
}