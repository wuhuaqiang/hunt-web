package com.hunt.service;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysDataGroup;
import com.hunt.model.entity.SysDataItem;
import com.hunt.model.entity.SysIpForbidden;
import com.hunt.model.entity.SysLog;

import java.util.List;


public interface SystemService {
    void forceLogout(Integer userId);

    void clearAuthorizationInfoCacheByUserId(Integer userId);

    void clearAuthorizationInfoALL();

    void clearAuthorizationInfoByRoleId(Integer roleId);

    PageInfo selectLogStatus(int page, int rows);

    PageInfo selectLog(int page, int rows, String s, String order, String method, String url, String param, String result);

    void insertSysControllerLog(SysLog runningLog);

    Integer insertSysDataGroup(SysDataGroup sysDataGroup);

    boolean isExistDataGroupName(String name);

    List<SysDataGroup> selectDataGroupList();

    Integer insertSysDataItem(SysDataItem sysDataItem);

    boolean isExistDataItemKeyName(String key, Integer groupId);

    void deleteDataItemById(Integer id);

    void updateDateItem(SysDataItem sysDataItem);

    boolean isExistDataItemNameExcludeId(Integer id, String key, Integer groupId);

    PageInfo selectDataItemPage(int page, int rows);

    SysDataItem selectDataItemById(Integer id);

    String selectDataItemByKey(String key, Integer groupId);

    Integer insertIp(SysIpForbidden sysIpForbidden);

    void deleteIp(Integer id);

    void updateIp(SysIpForbidden sysIpForbidden);

    PageInfo selectIp(int page, int rows);

    boolean isExistIp(String ip);

    boolean isExistIpExcludeId(String ip, Integer id);

    boolean isForbiddenIp(String remoteAddr);

    void openIpIntercept();

    void closeIpIntercept();

    boolean selectIPForbiddenStatus();
}
