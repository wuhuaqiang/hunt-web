package com.hunt.service.impl;

import com.hunt.dao.SelectUserRoleMenuMapper;
import com.hunt.dao.SysRoleMapper;
import com.hunt.dao.SysUserMapper;
import com.hunt.model.dto.SysUserRoleMenuDto;
import com.hunt.model.dto.SystemUserRoleDto;
import com.hunt.model.entity.SysRole;
import com.hunt.model.entity.SysUser;
import com.hunt.model.entity.SysUserRoleMenu;
import com.hunt.model.entity.SystemUserRole;
import com.hunt.service.SelectUserRoleMenuService;
import com.hunt.service.tools.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: cwx
 * @Date: 2018/5/30 16:12
 * @Description: 获取用户的菜单Service业务处理接口
 */
@Service
@Transactional
public class SelectUserRoleMenuSerivelmpl implements SelectUserRoleMenuService {
    /**
     * 查詢系統權限
     */
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 用户添加
     */
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 根據查詢系統用戶的權限
     */
    @Autowired
    private SelectUserRoleMenuMapper  selectUserRoleMenuMapper;

    /**
     * 获取用户的菜单结果list
     * @param id
     * @return
     */
    @Override
    public List<SysUserRoleMenuDto> selectUserRoleMenu(Long id) {
        return MyBeanUtils.copyPropertiesList(selectUserRoleMenuMapper.selectUserRoleMenu(id),SysUserRoleMenuDto.class);
    }
    /**
     * 根据用户id查询用户的权限集
     * @param id
     * @return
     */
    @Override
    public List<SystemUserRoleDto> queryUserRoleList(Long id) {
        return MyBeanUtils.copyPropertiesList(selectUserRoleMenuMapper.queryUserRoleList(id),SystemUserRoleDto.class);
    }
    /**
     * 獲取所有的權限
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return sysRoleMapper.selectAll();
    }

    /**
     * 添加用户信息
     * @param sysUser
     */
    @Override
    public void userInsert(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUser.setPasswordUpdateTime(new Date());
        sysUserMapper.insert(sysUser);
     }
}
