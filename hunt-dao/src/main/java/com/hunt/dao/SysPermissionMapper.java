package com.hunt.dao;

import com.alibaba.fastjson.JSONObject;
import com.hunt.model.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMapper {
    //新增
    public Integer insert(SysPermission SysPermission);

    //更新
    public void update(SysPermission SysPermission);
    
    //查询所有的权限
    public List<JSONObject> listAllPermission();

    //通过对象进行查询
    public SysPermission select(SysPermission SysPermission);

    //通过id进行查询
    public SysPermission selectById(@Param("id") Integer id);

    //查询全部
    public List<SysPermission> selectAll(@Param("name") String name);

    //查询数量
    public int selectCounts(@Param("name") String name);

    boolean isExistName(@Param("groupId") Integer groupId, @Param("name") String name);

    boolean isExistCode(@Param("groupId") Integer groupId, @Param("code") String code);

    boolean isExistNameExcludeId(@Param("id") Integer id, @Param("groupId") Integer groupId, @Param("name") String name);

    boolean isExistCodeExcludeId(@Param("id") Integer id, @Param("groupId") Integer groupId, @Param("code") String code);
}