package com.hunt.model.entity;

import java.io.Serializable;

/**
 * @Author: ouyangan
 * @Date: 2016-10-17 16:52
 * @Description:
 */
public class SysUserRole implements Serializable {

    // id :
    private Integer id;

    // sys_user_id :
    private Integer sysUserId;

    // sys_role_organization_id :
    private Integer sysRoleId;

    // rank :排序
    private Integer rank;

    // create_time :创建时间
    private java.util.Date createTime;

    // update_time :更新时间
    private java.util.Date updateTime;

    // create_by :创建人id
    private Integer createBy;

    // update_by :更新人id
    private Integer updateBy;

    // status :数据状态,1:正常,2:删除
    private Integer status;

    // is_final :是否能修改
    private Integer isFinal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * get 创建时间
     *
     * @return java.util.Date
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * set 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get 更新时间
     *
     * @return java.util.Date
     */
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    /**
     * set 更新时间
     *
     * @param updateTime
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * get 创建人id
     *
     * @return Integer
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * set 创建人id
     *
     * @param createBy
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * get 更新人id
     *
     * @return Integer
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * set 更新人id
     *
     * @param updateBy
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * get 数据状态,1:正常,2:删除
     *
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * set 数据状态,1:正常,2:删除
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * get 是否能修改
     *
     * @return Integer
     */
    public Integer getIsFinal() {
        return isFinal;
    }

    /**
     * set 是否能修改
     *
     * @param isFinal
     */
    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        return "SysUserRoleOrganization{" +
                "id='" + id + '\'' +
                ", sysUserId='" + sysUserId + '\'' +
                ", sysRoleId='" + sysRoleId + '\'' +
                ", rank='" + rank + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", status='" + status + '\'' +
                ", isFinal=" + isFinal +
                '}';
    }
}
