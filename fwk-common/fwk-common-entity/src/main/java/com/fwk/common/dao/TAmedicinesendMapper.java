package com.fwk.common.dao;

import com.fwk.common.entity.TAmedicinesend;
import com.fwk.common.entity.TAmedicinesendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAmedicinesendMapper {
    int countByExample(TAmedicinesendExample example);

    int deleteByExample(TAmedicinesendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAmedicinesend record);

    int insertSelective(TAmedicinesend record);

    List<TAmedicinesend> selectByExample(TAmedicinesendExample example);

    TAmedicinesend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAmedicinesend record, @Param("example") TAmedicinesendExample example);

    int updateByExample(@Param("record") TAmedicinesend record, @Param("example") TAmedicinesendExample example);

    int updateByPrimaryKeySelective(TAmedicinesend record);

    int updateByPrimaryKey(TAmedicinesend record);
}