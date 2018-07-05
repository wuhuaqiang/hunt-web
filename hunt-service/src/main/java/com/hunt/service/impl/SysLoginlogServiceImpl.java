package com.hunt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hunt.dao.SysLoginlogMapper;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysLoginlog;
import com.hunt.model.entity.SysLoginlogParams;
import com.hunt.service.SysLoginlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: whq
 * @Date: 2018/7/2 10:34
 * @Description:
 */
@Service
@Transactional
public class SysLoginlogServiceImpl implements SysLoginlogService {
    @Autowired
    private SysLoginlogMapper sysLoginlogMapper;

    @Override
    public int countByExample(SysLoginlogParams example) {
        return sysLoginlogMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(SysLoginlogParams example) {
        return sysLoginlogMapper.deleteByExample(example);
    }

    @Override
    public int insert(SysLoginlog record) {
        return sysLoginlogMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLoginlog record) {
        return sysLoginlogMapper.insertSelective(record);
    }

    @Override
    public PageInfo selectByExample(SysLoginlogParams example, Integer page, Integer rows) {
        int counts = sysLoginlogMapper.countByExample(example);
        PageHelper.startPage(page, rows);
        List<SysLoginlog> list = sysLoginlogMapper.selectByExample(example);
        return new PageInfo(counts, list);
    }

    @Override
    public int updateByExampleSelective(SysLoginlog record, SysLoginlogParams example) {
        return sysLoginlogMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(SysLoginlog record, SysLoginlogParams example) {
        return sysLoginlogMapper.updateByExample(record, example);
    }
}
