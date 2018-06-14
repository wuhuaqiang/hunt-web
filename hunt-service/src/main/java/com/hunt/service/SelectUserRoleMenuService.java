package com.hunt.service;

import com.hunt.model.dto.SysUserRoleMenuDto;
import com.hunt.model.dto.SystemUserRoleDto;
import com.hunt.model.entity.SysRole;
import com.hunt.model.entity.SysUser;
import com.hunt.model.entity.SystemUserRole;


import java.util.List;

/**
 * @Auther: cwx
 * @Date: 2018/5/30 16:10
 * @Description: 获取用户的菜单Service业务处理接口
 */
public interface SelectUserRoleMenuService {

    /**
     * 根据用户的ID进行查询用户的菜单列
     * @param id
     * @return
     */
    public List<SysUserRoleMenuDto> selectUserRoleMenu(Long id);

    /**
     * 根据用户id查询用户的权限集
     * @param id
     * @return
     */
    public List<SystemUserRoleDto> queryUserRoleList(Long id);

    /**
     * 獲取所有的權限
     */
    public  List<SysRole> selectRoleAll();

    /**
     * 添加用户信息
     * @param sysUser
     */
    public void userInsert(SysUser sysUser);
}
