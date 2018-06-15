package com.hunt.dao;

import com.alibaba.fastjson.JSONObject;
import com.hunt.model.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    //新增
    public Integer insert(SysRole SysRole);

    //更新
    public void update(SysRole SysRole);

    //通过对象进行查询
    public SysRole select(SysRole SysRole);

    //查询最新角色ID
    public int selectMaxId();

    //通过id进行查询
    public SysRole selectById(@Param("id") Integer id);

    //通过userId进行查询
    public List<SysRole> selectByUserId(@Param("userId") Integer userId);

    //查询角色相关信息列表
    public List<JSONObject> listRole(@Param("roleName") String roleName);

    //查询全部
    public List<SysRole> selectAll();

    //查询数量
    public int selectCounts(@Param("roleName") String roleName);

    boolean isExsitName(@Param("name") String name);

    boolean isExsitNameExcludeId(@Param("id") Integer id, @Param("name") String name);
}