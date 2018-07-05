package com.hunt.controller;

import com.hunt.model.dto.LoginInfo;
import com.hunt.model.entity.SysPermissionGroup;
import com.hunt.model.entity.SysUserRole;
import com.hunt.service.UserRoleDataService;
import com.hunt.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Auther: cwx
 * @Date: 2018/6/11 16:29
 * @Description: 用戶的角色出來
 */
@RestController
@RequestMapping("/userRoleData")
public class UserRoleDataController extends BaseController {
    /**
     * 对用户的角色进行操作
     */
    @Resource
    private UserRoleDataService userRoleDataService;

    /**
     * 设置用户角色
     *
     * @param
     * @return
     */
    @ApiOperation(value = "设置用户角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("userMsg:EditRole")
    @RequestMapping(value = "/setUserRole", method = RequestMethod.POST)
    public Result setUserRole(@RequestBody Map<String, Object> map) {
        Result result = null;
        try {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setSysUserId(Integer.valueOf(map.get("uId").toString()));
            sysUserRole.setSysRoleId(Integer.valueOf(map.get("roleId").toString()));
            Integer i = userRoleDataService.insertUserRoleOrganization(sysUserRole);
            result = Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 添加角色信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "添加角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("menuMsg:Add")
    @RequestMapping(value = "/addSysPermissionGroup", method = RequestMethod.POST)
    public Result addSysPermissionGroup(@RequestBody Map<String, Object> map) {
        Result result = null;
        try {
            String menuCode = (String) map.get("menuCode");
            String permissionGroupName = (String) map.get("permissionGroupName");
            String description = (String) map.get("description");
            Object parentId = map.get("parentId");
            String status = map.get("status").toString();
            boolean faleg = userRoleDataService.booleanMeanCode(menuCode);
            boolean f = userRoleDataService.isExistGroupName(permissionGroupName);
            if (f) {
                result = Result.error("角色名字重复");
                return result;

            }
            if (!faleg) {
                SysPermissionGroup sysPermissionGroup = new SysPermissionGroup();
                sysPermissionGroup.setMenuCode(menuCode);
                sysPermissionGroup.setName(permissionGroupName);
                sysPermissionGroup.setDescription(description);
                if (parentId == null) {
                    sysPermissionGroup.setParentId(null);
                } else {
                    sysPermissionGroup.setParentId((Integer) parentId);
                }
                sysPermissionGroup.setStatus(Integer.parseInt(status));
                Subject subject = SecurityUtils.getSubject();
                LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
                Integer userId = loginInfo.getId();
                sysPermissionGroup.setCreateBy(userId);
                userRoleDataService.insert(sysPermissionGroup);
                result = Result.success();
                return result;
            }
            result = Result.error("权限code已存在");
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 查询角色信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("menuMsg:Query")
    @RequestMapping(value = "/selectAll", method = RequestMethod.POST)
    public Result selectAll() {
        Result result = null;
        try {
            result = Result.success(userRoleDataService.selectAll(null));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 查询角色分页信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("menuMsg:Query")
    @RequestMapping(value = "/selectPage", method = RequestMethod.POST)
    public Result selectPage(@RequestBody Map<String, Object> map) {
        Result result = null;
        try {
            int page = Integer.valueOf(map.get("page").toString());
            int rows = Integer.valueOf(map.get("rows").toString());
            String name = (String) map.get("name");
            result = Result.success(userRoleDataService.selectAll(page, rows, name));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 更新角色信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "更新角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("menuMsg:Edit")
    @RequestMapping(value = "/updateSysPermissionGroup", method = RequestMethod.POST)
    public Result updateSysPermissionGroup(@RequestBody SysPermissionGroup sysPermissionGroup) {
        Result result = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
            Integer userId = loginInfo.getId();
            sysPermissionGroup.setUpdateBy(userId);
            userRoleDataService.update(sysPermissionGroup);
            result = Result.success();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

    /**
     * 删除角色信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "删除角色信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("menuMsg:Remove")
    @RequestMapping(value = "/deleteSysPermissionGroup", method = RequestMethod.POST)
    public Result deleteSysPermissionGroup(@RequestBody Map<String, Object> map) {
        Result result = null;
        try {
            Integer id = Integer.valueOf(map.get("id").toString());
            SysPermissionGroup sysPermissionGroup = userRoleDataService.selectById(id);
            sysPermissionGroup.setStatus(2);
            userRoleDataService.update(sysPermissionGroup);
            result = Result.success();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error();
        }
        return result;
    }

}
