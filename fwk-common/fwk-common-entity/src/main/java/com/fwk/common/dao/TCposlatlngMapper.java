package com.fwk.common.dao;

import com.fwk.common.entity.TCposlatlng;
import com.fwk.common.entity.TCposlatlngExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCposlatlngMapper {
    int countByExample(TCposlatlngExample example);

    int deleteByExample(TCposlatlngExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCposlatlng record);

    int insertSelective(TCposlatlng record);

    List<TCposlatlng> selectByExample(TCposlatlngExample example);

    TCposlatlng selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCposlatlng record, @Param("example") TCposlatlngExample example);

    int updateByExample(@Param("record") TCposlatlng record, @Param("example") TCposlatlngExample example);

    int updateByPrimaryKeySelective(TCposlatlng record);

    int updateByPrimaryKey(TCposlatlng record);
}