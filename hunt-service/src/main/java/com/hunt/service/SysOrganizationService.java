package com.hunt.service;



import com.hunt.model.dto.PageInfo;
import com.hunt.model.dto.SysOrganizationTree;
import com.hunt.model.entity.SysOrganization;

import java.util.List;


public interface SysOrganizationService {
    Integer insertOrganization(SysOrganization sysOrganization);

    int deleteOrganization(Integer id);

    void updateOrganization(SysOrganization organization);

    PageInfo selectPage(int page, int row, Integer id);

    public SysOrganizationTree selectSysOrganizationTree(Integer id);

    public List<SysOrganizationTree> selectChildrenTreeList(Integer id);

    boolean isExistFullName(String fullName);

    SysOrganization selectOrganization(Integer id);

    boolean isExistFullNameExcludeId(Integer id, String fullName);

}
