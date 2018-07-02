package com.hunt.service;

import com.hunt.model.entity.SysLoginrecords;
import com.hunt.model.entity.SysLoginrecordsParams;

import java.util.List;

/**
 * @Auther: whq
 * @Date: 2018/7/2 13:50
 * @Description:
 */
public interface SysLoginRecordsService {
    int countByExample(SysLoginrecordsParams example);

    int deleteByExample(SysLoginrecordsParams example);

    int insert(SysLoginrecords record);

    int insertSelective(SysLoginrecords record);

    List<SysLoginrecords> selectByExample(SysLoginrecordsParams example);

    List<SysLoginrecords> selectByAccountName(String accountName);

    int updateByExampleSelective(SysLoginrecords record, SysLoginrecordsParams example);

    int updateByExample(SysLoginrecords record, SysLoginrecordsParams example);
}
