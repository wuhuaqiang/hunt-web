package com.hunt.service;

import com.hunt.model.entity.SysLoginlog;
import com.hunt.model.entity.SysLoginlogParams;

import java.util.List;

/**
 * @Auther: whq
 * @Date: 2018/7/2 10:31
 * @Description:
 */
public interface SysLoginlogService {
    int countByExample(SysLoginlogParams example);

    int deleteByExample(SysLoginlogParams example);

    int insert(SysLoginlog record);

    int insertSelective(SysLoginlog record);

    List<SysLoginlog> selectByExample(SysLoginlogParams example);

    int updateByExampleSelective(SysLoginlog record, SysLoginlogParams example);

    int updateByExample(SysLoginlog record, SysLoginlogParams example);
}
