package com.hunt.dao;

import com.hunt.model.entity.SysUserRoleMenu;
import com.hunt.model.entity.SystemUserRole;

import java.util.List;

/**
 * @Auther: cwx
 * @Date: 2018/5/30 15:37
 * @Description: 获取用户的菜单接口
 */
public interface SelectUserRoleMenuMapper {
    /**
     * 根据用户的ID进行查询用户的菜单列
     * @param id
     * @return
     */
   public List<SysUserRoleMenu> selectUserRoleMenu(Long id);

    /**
     * 根据用户id查询用户的权限集
     * @param id
     * @return
     */
   public List<SystemUserRole> queryUserRoleList(Long id);


}
