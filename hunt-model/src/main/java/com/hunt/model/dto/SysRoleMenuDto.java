package com.hunt.model.dto;

import java.io.Serializable;

/**
 * <p>
 * 对应的权限和菜单绑定
 * </p>
 *
 * @author 陈文旭
 * @since 2018-05-29
 */
public class SysRoleMenuDto {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 权限id
     */
    private Double roleId;
    /**
     * 菜单的id
     */
    private Double menuId;
    /**
     * 菜单状态 0表示启动，1表示未启动
     */
    private Double menuState;
    /**
     * 创建时间
     */
    private String menuCreateDate;
    /**
     * 更新时间
     */
    private String menuUpdataDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRoleId() {
        return roleId;
    }

    public void setRoleId(Double roleId) {
        this.roleId = roleId;
    }

    public Double getMenuId() {
        return menuId;
    }

    public void setMenuId(Double menuId) {
        this.menuId = menuId;
    }

    public Double getMenuState() {
        return menuState;
    }

    public void setMenuState(Double menuState) {
        this.menuState = menuState;
    }

    public String getMenuCreateDate() {
        return menuCreateDate;
    }

    public void setMenuCreateDate(String menuCreateDate) {
        this.menuCreateDate = menuCreateDate;
    }

    public String getMenuUpdataDate() {
        return menuUpdataDate;
    }

    public void setMenuUpdataDate(String menuUpdataDate) {
        this.menuUpdataDate = menuUpdataDate;
    }

    @Override
    public String toString() {
        return "SysRoleMenu{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", menuId=" + menuId +
        ", menuState=" + menuState +
        ", menuCreateDate=" + menuCreateDate +
        ", menuUpdataDate=" + menuUpdataDate +
        "}";
    }
}
