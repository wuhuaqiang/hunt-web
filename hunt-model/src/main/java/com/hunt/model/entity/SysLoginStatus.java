package com.hunt.model.entity;

import java.util.Date;

/**
 * @Author: ouyangan
 * @Date: 2016-10-18 15:07
 * @Description:
 */
public class SysLoginStatus {

    // id :主键
    private Integer id;

    // sys_user_id :用户id
    private Integer sysUserId;

    // session_id :session id
    private String sessionId;

    // session_expires :session过期时间
    private java.util.Date sessionExpires;

    // sys_user_login_name :登录名
    private String sysUserLoginName;

    // sys_user_zh_name :中文名
    private String sysUserZhName;

    // last_login_time :上一次登录时间
    private java.util.Date lastLoginTime;

    // platform :登录平台 1:web 2:android 3:ios
    private Integer platform;

    // rank :排序
    private Integer rank;

    // create_time :创建时间
    private java.util.Date createTime;

    // update_time :更新时间
    private java.util.Date updateTime;

    // create_by :创建人
    private Integer createBy;

    // update_by :更热人
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getSessionExpires() {
        return sessionExpires;
    }

    public void setSessionExpires(Date sessionExpires) {
        this.sessionExpires = sessionExpires;
    }

    public String getSysUserLoginName() {
        return sysUserLoginName;
    }

    public void setSysUserLoginName(String sysUserLoginName) {
        this.sysUserLoginName = sysUserLoginName;
    }

    public String getSysUserZhName() {
        return sysUserZhName;
    }

    public void setSysUserZhName(String sysUserZhName) {
        this.sysUserZhName = sysUserZhName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
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
        return "SysLoginStatus{" +
                "id=" + id +
                ", sysUserId=" + sysUserId +
                ", sessionId='" + sessionId + '\'' +
                ", sessionExpires=" + sessionExpires +
                ", sysUserLoginName='" + sysUserLoginName + '\'' +
                ", sysUserZhName='" + sysUserZhName + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", platform=" + platform +
                ", rank=" + rank +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", status=" + status +
                '}';
    }
}
