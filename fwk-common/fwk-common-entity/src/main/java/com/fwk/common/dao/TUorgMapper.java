package com.fwk.common.dao;

import com.fwk.common.entity.TUorg;
import com.fwk.common.entity.TUorgExample;
import com.fwk.common.entity.TUorgWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUorgMapper {
    int countByExample(TUorgExample example);

    int deleteByExample(TUorgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUorgWithBLOBs record);

    int insertSelective(TUorgWithBLOBs record);

    List<TUorgWithBLOBs> selectByExampleWithBLOBs(TUorgExample example);

    List<TUorg> selectByExample(TUorgExample example);

    TUorgWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUorgWithBLOBs record, @Param("example") TUorgExample example);

    int updateByExampleWithBLOBs(@Param("record") TUorgWithBLOBs record, @Param("example") TUorgExample example);

    int updateByExample(@Param("record") TUorg record, @Param("example") TUorgExample example);

    int updateByPrimaryKeySelective(TUorgWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TUorgWithBLOBs record);

    int updateByPrimaryKey(TUorg record);
}