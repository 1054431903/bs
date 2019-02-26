package com.fwk.common.dao;

import com.fwk.common.entity.TFplanwarn;
import com.fwk.common.entity.TFplanwarnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanwarnMapper {
    int countByExample(TFplanwarnExample example);

    int deleteByExample(TFplanwarnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanwarn record);

    int insertSelective(TFplanwarn record);

    List<TFplanwarn> selectByExample(TFplanwarnExample example);

    TFplanwarn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanwarn record, @Param("example") TFplanwarnExample example);

    int updateByExample(@Param("record") TFplanwarn record, @Param("example") TFplanwarnExample example);

    int updateByPrimaryKeySelective(TFplanwarn record);

    int updateByPrimaryKey(TFplanwarn record);
}