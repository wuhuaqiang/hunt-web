package com.hunt.model.entity;

import java.util.Date;

/**
 * @Author:
 * @Date: 2016-10-12 14:21
 * @Description:
 */
public class SysUserPermission {

    // id :
    private Integer id;

    // sys_user_id :
    private Integer sysUserId;

    // sys_permission_id :
    private Integer sysPermissionId;

    // is_final :
    private Integer isFinal;

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

    public Integer getSysPermissionId() {
        return sysPermissionId;
    }

    public void setSysPermissionId(Integer sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysUserPermission{" +
                "id=" + id +
                ", sysUserId=" + sysUserId +
                ", sysPermissionId=" + sysPermissionId +
                ", isFinal=" + isFinal +
                ", rank=" + rank +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", status=" + status +
                '}';
    }
}
