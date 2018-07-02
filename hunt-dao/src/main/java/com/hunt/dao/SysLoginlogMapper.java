package com.hunt.dao;

import com.hunt.model.entity.SysLoginlog;
import com.hunt.model.entity.SysLoginlogParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLoginlogMapper {
    int countByExample(SysLoginlogParams example);

    int deleteByExample(SysLoginlogParams example);

    int insert(SysLoginlog record);

    int insertSelective(SysLoginlog record);

    List<SysLoginlog> selectByExample(SysLoginlogParams example);

    int updateByExampleSelective(@Param("record") SysLoginlog record, @Param("example") SysLoginlogParams example);

    int updateByExample(@Param("record") SysLoginlog record, @Param("example") SysLoginlogParams example);
}