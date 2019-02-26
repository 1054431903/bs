package com.fwk.common.dao;

import com.fwk.common.entity.TUrolepermission;
import com.fwk.common.entity.TUrolepermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUrolepermissionMapper {
    int countByExample(TUrolepermissionExample example);

    int deleteByExample(TUrolepermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUrolepermission record);

    int insertSelective(TUrolepermission record);

    List<TUrolepermission> selectByExample(TUrolepermissionExample example);

    TUrolepermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUrolepermission record, @Param("example") TUrolepermissionExample example);

    int updateByExample(@Param("record") TUrolepermission record, @Param("example") TUrolepermissionExample example);

    int updateByPrimaryKeySelective(TUrolepermission record);

    int updateByPrimaryKey(TUrolepermission record);
}