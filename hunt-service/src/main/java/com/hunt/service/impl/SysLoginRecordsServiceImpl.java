package com.hunt.service.impl;

import com.hunt.dao.SysLoginrecordsMapper;
import com.hunt.model.entity.SysLoginrecords;
import com.hunt.model.entity.SysLoginrecordsParams;
import com.hunt.service.SysLoginRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: whq
 * @Date: 2018/7/2 13:50
 * @Description:
 */
@Service
public class SysLoginRecordsServiceImpl implements SysLoginRecordsService {
    @Autowired
    private SysLoginrecordsMapper sysLoginrecordsMapper;

    @Override
    public int countByExample(SysLoginrecordsParams example) {
        return sysLoginrecordsMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(SysLoginrecordsParams example) {
        return sysLoginrecordsMapper.deleteByExample(example);
    }

    @Override
    public int insert(SysLoginrecords record) {
        return sysLoginrecordsMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLoginrecords record) {
        return sysLoginrecordsMapper.insertSelective(record);
    }

    @Override
    public List<SysLoginrecords> selectByExample(SysLoginrecordsParams example) {
        return sysLoginrecordsMapper.selectByExample(example);
    }

    @Override
    public List<SysLoginrecords> selectByAccountName(String accountName) {
        return sysLoginrecordsMapper.selectByAccountName(accountName);
    }

    @Override
    public int updateByExampleSelective(SysLoginrecords record, SysLoginrecordsParams example) {
        return sysLoginrecordsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(SysLoginrecords record, SysLoginrecordsParams example) {
        return sysLoginrecordsMapper.updateByExample(record, example);
    }
}
