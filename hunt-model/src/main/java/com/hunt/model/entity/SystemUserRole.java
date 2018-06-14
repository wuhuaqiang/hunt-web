package com.hunt.model.entity;

import java.io.Serializable;

/**
 * @Auther: cwx
 * @Date: 2018/6/4 13:31
 * @Description: 用戶的權限基礎類
 */
public class SystemUserRole implements Serializable {
    /**
     * 權限的名字
     */
    private String roleName;
    /**
     *權限的id
     */
    private String roleId;
    /**
     *權限對應的code
     */
    private String roleCode;
    /**
     *菜單對應權限的id
     */
    private String sysPermissionGroupId;
    /**
     *是否可以修改
     */
    private String isFinal;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getSysPermissionGroupId() {
        return sysPermissionGroupId;
    }

    public void setSysPermissionGroupId(String sysPermissionGroupId) {
        this.sysPermissionGroupId = sysPermissionGroupId;
    }

    public String getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(String isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        return "SystemUserRole{" +
                "roleName='" + roleName + '\'' +
                ", roleId='" + roleId + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", sysPermissionGroupId='" + sysPermissionGroupId + '\'' +
                ", isFinal='" + isFinal + '\'' +
                '}';
    }
}
