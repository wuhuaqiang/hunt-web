package com.hunt.controller;

import com.hunt.model.dto.LoginInfo;
import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysPermission;
import com.hunt.service.SysPermissionService;
import com.hunt.util.ResponseCode;
import com.hunt.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: cwx
 * @Date: 2018/6/13 09:31
 * @Description: 用户的权限数据请求类
 */
@Api(value = "用户菜单")
@RestController
@RequestMapping(value = "userjurisdictiondata", method = RequestMethod.POST, produces = "application/json")
public class UserJurisdictionDataController extends BaseController {
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 查询角色权限
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @RequiresPermissions("permissionMsg:Query")
    public Result PermissionPage(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            int page = Integer.parseInt(params.get("page").toString());
            int rows = Integer.parseInt(params.get("rows").toString());
            String name = (String) params.get("name");
            PageInfo pageInfo = sysPermissionService.selectPage(page, rows, name);
            result = Result.success(pageInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 新增权限
     * groupId     权限组id
     * permissionName 名称
     * permissionCode 编码
     * permissionDescription 描述
     * isFinal 是否可修改 1表示可以修改
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "新增权限", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("permissionMsg:Add")
    @RequestMapping(value = "insertPermission", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params) {
        Integer groupId = Integer.parseInt(params.get("groupId").toString());
        String permissionName = params.get("permissionName").toString();
        String permissionCode = params.get("permissionCode").toString();
        String permissionDescription = params.get("permissionDescription").toString();
        Integer isFinal = Integer.parseInt(params.get("isFinal").toString());
        String requestUrl = params.get("requestUrl").toString();
        boolean isExistName = sysPermissionService.isExistName(groupId, permissionName);
        boolean isExistCode = sysPermissionService.isExistCode(groupId, permissionCode);
        System.out.println(isExistName);
        System.out.println(isExistCode);
        if (isExistName) {
            return Result.error("该分组下名称已存在");
        }
        if (isExistCode) {
            return Result.error("该权限分组下编码已存在已存在");
        }
        SysPermission sysPermission = new SysPermission();
        sysPermission.setName(permissionName);
        sysPermission.setCode(permissionCode);
        sysPermission.setDescription(permissionDescription);
        sysPermission.setIsFinal(isFinal);
        sysPermission.setCreateTime(new Date());
        sysPermission.setRequestUrl(requestUrl);
        sysPermission.setSysPermissionGroupId(groupId);
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        Integer userId = loginInfo.getId();
        sysPermission.setCreateBy(userId);
        sysPermissionService.insertPermission(sysPermission);
        return Result.success();
    }

    /**
     * 更新权限
     *
     * @param params 参数
     * @return
     */
    @ApiOperation(value = "更新权限", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("permissionMsg:Edit")
    @RequestMapping(value = "updatePermission", method = RequestMethod.POST)
    public Result update(@RequestBody Map<String, Object> params) {
        Integer id = Integer.parseInt(params.get("id").toString());
        Integer groupId = Integer.parseInt(params.get("groupId").toString());
        String permissionName = params.get("permissionName").toString();
        String permissionCode = params.get("permissionCode").toString();
        String permissionDescription = params.get("permissionDescription").toString();
        String requestUrl = params.get("requestUrl").toString();
        SysPermission sysPermission = sysPermissionService.selectById(id);
        if (sysPermission == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysPermission.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        boolean isExistName = sysPermissionService.isExistNameExcludeId(id, groupId, permissionName);
        if (isExistName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        boolean isExistCode = sysPermissionService.isExistCodeExcludeId(id, groupId, permissionCode);
        if (isExistCode) {
            return Result.error(ResponseCode.code_already_exist.getMsg());
        }
        sysPermission.setName(permissionName);
        sysPermission.setCode(permissionCode);
        sysPermission.setDescription(permissionDescription);
        sysPermission.setSysPermissionGroupId(groupId);
        sysPermission.setUpdateTime(new Date());
        sysPermission.setRequestUrl(requestUrl);
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        Integer userId = loginInfo.getId();
        sysPermission.setUpdateBy(userId);
        sysPermissionService.update(sysPermission);
        return Result.success();
    }


    /**
     * 删除权限
     * id
     *
     * @return
     */
    @ApiOperation(value = "删除权限", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("permissionMsg:Remove")
    @RequestMapping(value = "deletePermission", method = RequestMethod.POST)
    public Result deletePermission(@RequestBody Map<String, Object> params) {
        Integer id = Integer.parseInt(params.get("id").toString());
        SysPermission sysPermission = sysPermissionService.selectById(id);
        if (sysPermission == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysPermission.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysPermission.setStatus(2);
        // 级联删除关联表 ,角色权限,用户权限
        sysPermissionService.update(sysPermission);
        return Result.success();
    }


}
