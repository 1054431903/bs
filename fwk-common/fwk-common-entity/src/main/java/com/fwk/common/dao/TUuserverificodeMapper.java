package com.fwk.common.dao;

import com.fwk.common.entity.TUuserverificode;
import com.fwk.common.entity.TUuserverificodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUuserverificodeMapper {
    int countByExample(TUuserverificodeExample example);

    int deleteByExample(TUuserverificodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUuserverificode record);

    int insertSelective(TUuserverificode record);

    List<TUuserverificode> selectByExample(TUuserverificodeExample example);

    TUuserverificode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUuserverificode record, @Param("example") TUuserverificodeExample example);

    int updateByExample(@Param("record") TUuserverificode record, @Param("example") TUuserverificodeExample example);

    int updateByPrimaryKeySelective(TUuserverificode record);

    int updateByPrimaryKey(TUuserverificode record);
}