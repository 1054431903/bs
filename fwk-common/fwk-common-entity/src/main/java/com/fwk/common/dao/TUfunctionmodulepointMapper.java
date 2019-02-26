package com.fwk.common.dao;

import com.fwk.common.entity.TUfunctionmodulepoint;
import com.fwk.common.entity.TUfunctionmodulepointExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUfunctionmodulepointMapper {
    int countByExample(TUfunctionmodulepointExample example);

    int deleteByExample(TUfunctionmodulepointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUfunctionmodulepoint record);

    int insertSelective(TUfunctionmodulepoint record);

    List<TUfunctionmodulepoint> selectByExample(TUfunctionmodulepointExample example);

    TUfunctionmodulepoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUfunctionmodulepoint record, @Param("example") TUfunctionmodulepointExample example);

    int updateByExample(@Param("record") TUfunctionmodulepoint record, @Param("example") TUfunctionmodulepointExample example);

    int updateByPrimaryKeySelective(TUfunctionmodulepoint record);

    int updateByPrimaryKey(TUfunctionmodulepoint record);
}