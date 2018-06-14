package com.hunt.service;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysPermissionGroup;
import com.hunt.model.entity.SysUserRole;

import java.util.List;

/**
 * @Auther: cwx
 * @Date: 2018/6/11 16:49
 * @Description: 用户的权限操数据接口
 */
public interface UserRoleDataService {
    /**
     * 添加用户角色
     * @param sysUserRole
     * @return
     */
    public Integer insertUserRoleOrganization(SysUserRole sysUserRole);


    /**
     * menuCode是否已经存在
     * @param menuCode
     * @return
     */
    public boolean booleanMeanCode(String menuCode);
    //新增
    public Integer insert(SysPermissionGroup SysPermissionGroup);

    //更新
    public void update(SysPermissionGroup SysPermissionGroup);

    //通过对象进行查询
    public SysPermissionGroup select(SysPermissionGroup SysPermissionGroup);

    //通过id进行查询
    public SysPermissionGroup selectById( Integer id);

    //查询分页
    public PageInfo selectAll(int page,int rows,String name);
    //查询全部
    public List selectAll(String name);

    //查询数量
    public int selectCounts(String name);

    boolean isExistGroupName( String name);
}
