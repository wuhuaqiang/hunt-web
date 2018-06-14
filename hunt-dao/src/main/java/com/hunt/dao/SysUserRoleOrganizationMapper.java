package com.hunt.dao;

import com.hunt.model.entity.SysUserRoleOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleOrganizationMapper {
    //新增
    public Integer insert(SysUserRoleOrganization SysUserRoleOrganization);

    //更新
    public void update(SysUserRoleOrganization SysUserRoleOrganization);

    //通过对象进行查询
    public SysUserRoleOrganization select(SysUserRoleOrganization SysUserRoleOrganization);

    //通过id进行查询
    public SysUserRoleOrganization selectById(@Param("id") Integer id);

    //查询全部
    public List<SysUserRoleOrganization> selectAll();

    //查询数量
    public int selectCounts();

    void deleteUserId(@Param("userId") Integer userId);

    List<SysUserRoleOrganization> selectByUserId(@Param("userId") Integer userId);

    List<Integer> selectByRoleOrganizationId(@Param("roleOrganizationId") Integer roleOrganizationId);
}