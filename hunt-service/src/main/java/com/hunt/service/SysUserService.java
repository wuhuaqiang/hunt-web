package com.hunt.service;

import com.hunt.model.dto.LoginInfo;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysUser;

import java.io.Serializable;


public interface SysUserService {

    Integer insertUser(SysUser user, String jobIds, String permissionIds);

    boolean isExistLoginName(String loginName);

    void deleteById(Integer id);

    SysUser selectById(Integer id);

    boolean isExistLoginNameExcludeId(Integer id, String loginName);

    void updateUser(SysUser user, String jobIds, String permissionIds);

    PageInfo selectPage(int page, int rows, String sort, String order, String loginName, String zhName, String email, String phone, String address);

    void updateUser(SysUser user);

    SysUser selectByLoginName(String loginName);

    LoginInfo login(SysUser user, Serializable id, int platform);


}
