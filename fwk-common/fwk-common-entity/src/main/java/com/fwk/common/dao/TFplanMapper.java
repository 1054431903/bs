package com.fwk.common.dao;

import com.fwk.common.entity.TFplan;
import com.fwk.common.entity.TFplanExample;
import com.fwk.common.entity.TFplanWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFplanMapper {
    int countByExample(TFplanExample example);

    int deleteByExample(TFplanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFplanWithBLOBs record);

    int insertSelective(TFplanWithBLOBs record);

    List<TFplanWithBLOBs> selectByExampleWithBLOBs(TFplanExample example);

    List<TFplan> selectByExample(TFplanExample example);

    TFplanWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFplanWithBLOBs record, @Param("example") TFplanExample example);

    int updateByExampleWithBLOBs(@Param("record") TFplanWithBLOBs record, @Param("example") TFplanExample example);

    int updateByExample(@Param("record") TFplan record, @Param("example") TFplanExample example);

    int updateByPrimaryKeySelective(TFplanWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TFplanWithBLOBs record);

    int updateByPrimaryKey(TFplan record);
}