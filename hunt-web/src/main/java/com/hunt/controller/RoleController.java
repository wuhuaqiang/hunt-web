package com.hunt.controller;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysRole;
import com.hunt.service.SysRoleService;
import com.hunt.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.hunt.util.ResponseCode;
import com.hunt.util.Result;

import java.util.Map;

/**
 * @Author ouyangan
 * @Date 2016/10/14/14:46
 * @Description
 */
@Api("角色模块")
@RequestMapping("role")
@Controller
public class RoleController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "跳转至角色模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("role:list")
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public String role() {
        return "system/role";
    }

    /**
     * 新增角色
     *
     * @param params 参数
     * @return
     */
    @ApiOperation(value = "新增角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("roleMsg:Add")
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params) {
        String name = params.get("roleName").toString();
        String permissionIds = params.get("rolePermissionIds").toString();
        String description = params.get("roleDescription").toString();
        int isFinal = Integer.parseInt(params.get("isFinal").toString());
        boolean isExsitRoleName = sysRoleService.isExsitRoleName(name);
        if (isExsitRoleName) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        SysRole sysRole = new SysRole();
        sysRole.setName(name);
        sysRole.setDescription(description);
        sysRole.setIsFinal(isFinal);
        sysRoleService.insertRole(sysRole, permissionIds);
        return Result.success();
    }

    /**
     * 更新角色
     *
     * @param params 参数
     * @return
     */
    @ApiOperation(value = "更新角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("roleMsg:Edit")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestBody Map<String, Object> params) {
        Integer id = Integer.valueOf(params.get("roleId").toString());
        String name = params.get("roleName").toString();
        String permissionIds = params.get("permissionIds").toString();
        String description = null;
        System.out.println("id = [" + id + "], name = [" + name + "], description = [" + description + "], permissionIds = [" + permissionIds + "]");
        boolean isExsitRoleNameExcludeId = sysRoleService.isExsitRoleNameExcludeId(id, name);
        if (isExsitRoleNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysRole sysRole = sysRoleService.selectById(id);
        if (sysRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysRole.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysRole.setId(id);
        sysRole.setName(name);
        sysRole.setDescription(description);
        sysRoleService.updateRole(sysRole, permissionIds);
        systemService.clearAuthorizationInfoByRoleId(id);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param params 角色参数
     * @return
     */
    @ApiOperation(value = "删除角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("roleMsg:Remove")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String, Object> params) {
        int id = Integer.parseInt(params.get("roleId").toString());
        SysRole sysRole = sysRoleService.selectById(id);
        if (sysRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysRole.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysRole.setStatus(2);
        sysRoleService.deleteRole(sysRole);
        systemService.clearAuthorizationInfoByRoleId(id);
        return Result.success();
    }

    /**
     * 角色列表
     *
     * @param params 分页参数
     * @return
     */
    @ApiOperation(value = "角色列表", httpMethod = "POST", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequiresPermissions("roleMsg:Query")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody Map<String, Object> params) {
        /*  PageInfo pageInfo = sysRoleService.selectPage(page, rows);*/
        PageInfo pageInfo = sysRoleService.listRole(Integer.valueOf((String) params.get("page")), Integer.valueOf((String) params.get("rows")), (String) params.get("roleName"));
        return Result.success(pageInfo);
    }
}