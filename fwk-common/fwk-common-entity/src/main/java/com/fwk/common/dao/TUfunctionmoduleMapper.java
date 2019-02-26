package com.fwk.common.dao;

import com.fwk.common.entity.TUfunctionmodule;
import com.fwk.common.entity.TUfunctionmoduleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUfunctionmoduleMapper {
    int countByExample(TUfunctionmoduleExample example);

    int deleteByExample(TUfunctionmoduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUfunctionmodule record);

    int insertSelective(TUfunctionmodule record);

    List<TUfunctionmodule> selectByExample(TUfunctionmoduleExample example);

    TUfunctionmodule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUfunctionmodule record, @Param("example") TUfunctionmoduleExample example);

    int updateByExample(@Param("record") TUfunctionmodule record, @Param("example") TUfunctionmoduleExample example);

    int updateByPrimaryKeySelective(TUfunctionmodule record);

    int updateByPrimaryKey(TUfunctionmodule record);
}