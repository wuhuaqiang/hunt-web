package com.hunt.service;


import com.alibaba.fastjson.JSONObject;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysPermission;
import com.hunt.model.entity.SysPermissionGroup;

import java.util.List;


public interface SysPermissionService {
    boolean isExistName(Integer groupId, String name);

    boolean isExistCode(Integer groupId, String code);

    void insertPermission(SysPermission sysPermission);

    SysPermission selectById(Integer id);

    void update(SysPermission sysPermission);

    boolean isExistNameExcludeId(Integer id, Integer groupId, String name);

    boolean isExistCodeExcludeId(Integer id, Integer groupId, String code);

    PageInfo selectPage(int page, int rows,String name);

    boolean isExistGroupName(String name);

    Integer insertPermissionGroup(SysPermissionGroup sysPermissionGroup);

    List<SysPermissionGroup> selectGroup();
    public List<JSONObject> listAllPermission();
}
