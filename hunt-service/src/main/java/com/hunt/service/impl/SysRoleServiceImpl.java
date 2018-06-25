package com.hunt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hunt.dao.SysPermissionMapper;
import com.hunt.dao.SysRoleMapper;
import com.hunt.dao.SysRolePermissionMapper;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.dto.SysRoleDto;
import com.hunt.model.entity.SysPermission;
import com.hunt.model.entity.SysRole;
import com.hunt.model.entity.SysRolePermission;
import com.hunt.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author ouyangan
 * @Date 2016/10/14/14:53
 * @Description
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {
    private static Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public boolean isExsitRoleName(String name) {
        return sysRoleMapper.isExsitName(name);
    }

    @Override
    public void insertRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.insert(sysRole);
        sysRole.setId(sysRoleMapper.selectMaxId());
        String[] permissionIdsArray = permissionIds.split(",");
        for (int i = 0; i < permissionIdsArray.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Integer.valueOf(permissionIdsArray[i]));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
    }

    @Override
    public boolean isExsitRoleNameExcludeId(Integer id, String name) {
        return sysRoleMapper.isExsitNameExcludeId(id, name);
    }

    @Override
    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public PageInfo selectPage(int page, int row) {
        int counts = sysRoleMapper.selectCounts(null);
        List<SysRole> sysRoles = sysRoleMapper.selectAll();

        List<SysRoleDto> sysRoleDtoList = new ArrayList<>();
        for (int i = 0; i < sysRoles.size(); i++) {
            SysRoleDto sysRoleDto = new SysRoleDto();
            BeanUtils.copyProperties(sysRoles.get(i), sysRoleDto);
            List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectByRoleId(sysRoles.get(i).getId());

            List<SysPermission> sysPermissionList = new ArrayList<>();
            for (int j = 0; j < sysRolePermissionList.size(); j++) {
                SysPermission sysPermission = sysPermissionMapper.selectById(sysRolePermissionList.get(j).getSysPermissionId());
                sysPermissionList.add(sysPermission);
            }
            sysRoleDto.setSysPermissions(sysPermissionList);
            sysRoleDtoList.add(sysRoleDto);
        }
        PageInfo pageInfo = new PageInfo(counts, sysRoleDtoList);
        return pageInfo;
    }

    @Override
    public PageInfo listRole(int page, int row ,String roleName) {
        int counts = sysRoleMapper.selectCounts(roleName);
       /* PageHelper.startPage(page, row);*/
        if((page-1)*row+1>counts){
            return null;
        }
        List<JSONObject> jsonObjects = sysRoleMapper.listRole(roleName);
        List<JSONObject> result =new  ArrayList<JSONObject>();
        if((page*row)>counts){
            for ( int i= (page-1)*row;i<counts;i++){
                result.add(jsonObjects.get(i));
            }
        }else{
            for ( int i= (page-1)*row;i<page*row;i++){
                result.add(jsonObjects.get(i));
            }
        }


        PageInfo pageInfo = new PageInfo(counts, result);
        return pageInfo;
    }

    @Override
    public void deleteRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
        sysRolePermissionMapper.deleteByRoleId(sysRole.getId());
    }

    @Override
    public void updateRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.update(sysRole);
        sysRolePermissionMapper.deleteByRoleId(sysRole.getId());
        String[] pIds = permissionIds.split(",");
        for (int i = 0; i < pIds.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Integer.valueOf(pIds[i]));
            sysRolePermission.setUpdateTime(new Date());
            sysRolePermissionMapper.insert(sysRolePermission);
        }
    }
}
