package com.fwk.common.dao;

import com.fwk.common.entity.TAmedicineinto;
import com.fwk.common.entity.TAmedicineintoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAmedicineintoMapper {
    int countByExample(TAmedicineintoExample example);

    int deleteByExample(TAmedicineintoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAmedicineinto record);

    int insertSelective(TAmedicineinto record);

    List<TAmedicineinto> selectByExample(TAmedicineintoExample example);

    TAmedicineinto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAmedicineinto record, @Param("example") TAmedicineintoExample example);

    int updateByExample(@Param("record") TAmedicineinto record, @Param("example") TAmedicineintoExample example);

    int updateByPrimaryKeySelective(TAmedicineinto record);

    int updateByPrimaryKey(TAmedicineinto record);
}