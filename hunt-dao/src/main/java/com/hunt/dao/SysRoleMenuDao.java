package com.hunt.dao;

import com.hunt.model.entity.SysRoleMenu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈文旭
 * @since 2018-05-29
 */
public interface SysRoleMenuDao {
    /**
     * 添加权限对应的菜单id
     * @param sysrolemenu
     */
    public void insert(SysRoleMenu sysrolemenu);

}
