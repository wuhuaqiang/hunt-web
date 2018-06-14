package com.hunt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hunt.dao.SysPermissionGroupMapper;
import com.hunt.dao.SysUserRoleMapper;
import com.hunt.dao.SysUserRoleOrganizationMapper;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysPermissionGroup;
import com.hunt.model.entity.SysUserRole;
import com.hunt.model.entity.SysUserRoleOrganization;
import com.hunt.service.UserRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: cwx
 * @Date: 2018/6/11 16:53
 * @Description:用户的权限操数据业务操作
 */
@Service
@Transactional
public class UserRoleDataServerImpl  implements UserRoleDataService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysPermissionGroupMapper sysPermissionGroupMapper;

    /**
     * set用户角色信息
     * @param
     * @return
     */
    @Override
    public Integer insertUserRoleOrganization(SysUserRole sysUserRole) {
        sysUserRole.setCreateTime(new Date());
        sysUserRoleMapper.deleteUserId(sysUserRole.getSysUserId());
        return sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    public boolean booleanMeanCode(String menuCode) {
        return sysPermissionGroupMapper.booleanMeanCode(menuCode);
    }

    @Override
    public Integer insert(SysPermissionGroup SysPermissionGroup) {
        SysPermissionGroup.setCreateTime(new Date());
        return sysPermissionGroupMapper.insert(SysPermissionGroup);
    }

    @Override
    public void update(SysPermissionGroup SysPermissionGroup) {
        SysPermissionGroup.setUpdateTime(new Date());
        sysPermissionGroupMapper.update(SysPermissionGroup);
    }

    @Override
    public SysPermissionGroup select(SysPermissionGroup SysPermissionGroup) {
        return sysPermissionGroupMapper.select(SysPermissionGroup);
    }

    @Override
    public SysPermissionGroup selectById(Integer id) {
        return sysPermissionGroupMapper.selectById(id);
    }

    @Override
    public PageInfo selectAll( int page,int rows,String name ) {
      int count= sysPermissionGroupMapper.selectCounts(name);
        PageHelper.startPage(page, rows);
       List<SysPermissionGroup> list=  sysPermissionGroupMapper.selectAll(name);
        return new PageInfo(count, list);

    }

    @Override
    public List selectAll(String name) {
        return sysPermissionGroupMapper.selectAll(name);
    }

    @Override
    public int selectCounts(String name) {
        return sysPermissionGroupMapper.selectCounts(name);
    }

    @Override
    public boolean isExistGroupName(String name) {
        return sysPermissionGroupMapper.isExistGroupName(name);
    }
}
