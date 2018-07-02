package com.hunt.dao;

import com.hunt.model.entity.SysLoginrecords;
import com.hunt.model.entity.SysLoginrecordsParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLoginrecordsMapper {
    int countByExample(SysLoginrecordsParams example);

    int deleteByExample(SysLoginrecordsParams example);

    int insert(SysLoginrecords record);

    int insertSelective(SysLoginrecords record);

    List<SysLoginrecords> selectByExample(SysLoginrecordsParams example);

    List<SysLoginrecords> selectByAccountName(String accountName);

    int updateByExampleSelective(@Param("record") SysLoginrecords record, @Param("example") SysLoginrecordsParams example);

    int updateByExample(@Param("record") SysLoginrecords record, @Param("example") SysLoginrecordsParams example);
}