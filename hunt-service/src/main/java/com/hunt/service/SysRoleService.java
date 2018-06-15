package com.hunt.service;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysRole;


public interface SysRoleService {
    boolean isExsitRoleName(String name);

    void insertRole(SysRole sysRole, String permissionIds);

    void updateRole(SysRole sysRole, String permissionIds);

    boolean isExsitRoleNameExcludeId(Integer id, String name);

    SysRole selectById(Integer id);

    PageInfo selectPage(int page, int row);

    PageInfo listRole(int page, int row, String roleName);

    void deleteRole(SysRole sysRole);
}
