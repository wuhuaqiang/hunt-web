package com.hunt.service;


import com.hunt.model.dto.PageInfo;
import com.hunt.model.dto.SysRoleOrganizationTree;
import com.hunt.model.entity.SysRoleOrganization;

import java.util.List;


public interface SysRoleOrganizationService {
    boolean isExistName(String name, Integer parentId);

    Integer insertRoleOrganization(SysRoleOrganization roleOrganization);

    boolean isExistNameExcludeId(Integer id, String name, Integer parentId);

    void updateRoleOrganization(SysRoleOrganization roleOrganization);

    SysRoleOrganization selectRoleOrganizationById(Integer id);

    PageInfo selectPage(int page, int rows, Integer id);

    public SysRoleOrganizationTree selectSysRoleOrganizationTree(Integer id);

    public List<SysRoleOrganizationTree> selectSysRoleOrganizationTreeChildrenList(Integer id);
}
