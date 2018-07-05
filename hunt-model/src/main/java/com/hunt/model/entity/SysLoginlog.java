package com.hunt.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SysLoginlog implements Serializable {
    private String account;

    private Short id;

    private Date addtime;

    private Date updatetime;

    private String loginip;

    private Date logintime;

    private String issuccess;

    private String errormsg;

    private String logtype;

    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess == null ? null : issuccess.trim();
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg == null ? null : errormsg.trim();
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype == null ? null : logtype.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", account=").append(account);
        sb.append(", id=").append(id);
        sb.append(", addtime=").append(addtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", loginip=").append(loginip);
        sb.append(", logintime=").append(logintime);
        sb.append(", issuccess=").append(issuccess);
        sb.append(", errormsg=").append(errormsg);
        sb.append(", logtype=").append(logtype);
        sb.append("]");
        return sb.toString();
    }
}