package com.fwk.common.dao;

import com.fwk.common.entity.TUdepartment;
import com.fwk.common.entity.TUdepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUdepartmentMapper {
    int countByExample(TUdepartmentExample example);

    int deleteByExample(TUdepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUdepartment record);

    int insertSelective(TUdepartment record);

    List<TUdepartment> selectByExample(TUdepartmentExample example);

    TUdepartment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUdepartment record, @Param("example") TUdepartmentExample example);

    int updateByExample(@Param("record") TUdepartment record, @Param("example") TUdepartmentExample example);

    int updateByPrimaryKeySelective(TUdepartment record);

    int updateByPrimaryKey(TUdepartment record);
}