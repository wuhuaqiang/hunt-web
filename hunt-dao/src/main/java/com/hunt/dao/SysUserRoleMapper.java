package com.hunt.dao;

import com.hunt.model.entity.SysUserRole;
import com.hunt.model.entity.SysUserRoleOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    //新增
    public Integer insert(SysUserRole SysUserRole);

    //更新
    public void update(SysUserRole SysUserRole);

    //通过对象进行查询
    public SysUserRoleOrganization select(SysUserRole SysUserRole);

    //通过id进行查询
    public SysUserRoleOrganization selectById(@Param("id") Integer id);

    //查询全部
    public List<SysUserRoleOrganization> selectAll();

    //查询数量
    public int selectCounts();

    void deleteUserId(@Param("userId") Integer userId);

    List<SysUserRole> selectByUserId(@Param("userId") Integer userId);

    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}