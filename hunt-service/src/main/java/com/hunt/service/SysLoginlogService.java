package com.hunt.service;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysLoginlog;
import com.hunt.model.entity.SysLoginlogParams;

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

    PageInfo selectByExample(SysLoginlogParams example, Integer page, Integer rows);

    int updateByExampleSelective(SysLoginlog record, SysLoginlogParams example);

    int updateByExample(SysLoginlog record, SysLoginlogParams example);
}
