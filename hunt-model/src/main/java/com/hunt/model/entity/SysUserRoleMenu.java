package com.hunt.model.entity;

import java.io.Serializable;

/**
 * @Auther: cwx
 * @Date: 2018/5/30 15:49
 * @Description:用户的菜单实体类
 */
public class SysUserRoleMenu implements Serializable {
    /**
     * 菜单的id
     */
    private Integer id;
    /**
     * 菜单名字
     */
    private String menuName;
    /**
     * 是否可以修改 1可以2不可以
     */
    private Integer isFinal;
    /**
     * 菜单的父级id
     */
    private Integer parentId;

    /**
     * 菜单code唯一标示
     */
    private String menuCode;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysUserRoleMenu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", isFinal=" + isFinal +
                ", parentId=" + parentId +
                '}';
    }
}
