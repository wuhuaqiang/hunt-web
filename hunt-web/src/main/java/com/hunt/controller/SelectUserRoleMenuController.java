package com.hunt.controller;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.dto.SysUserRoleMenuDto;
import com.hunt.model.dto.SystemUserRoleDto;
import com.hunt.model.entity.SysRole;
import com.hunt.model.entity.SysUser;
import com.hunt.service.SelectUserRoleMenuService;
import com.hunt.service.SysPermissionService;
import com.hunt.service.SysRoleService;
import com.hunt.service.SysUserService;
import com.hunt.util.ResponseCode;
import com.hunt.util.Result;
import com.hunt.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cwx
 * @Date: 2018/5/30 16:31
 * @Description:
 */
@Api(value = "用户菜单")
@Controller
@RequestMapping("selectuserrolemenu")
public class SelectUserRoleMenuController extends BaseController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SelectUserRoleMenuService selectUserRoleMenuService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 获取用户的菜单信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取用户的菜单信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/selectUserRoleMenuList", method = RequestMethod.POST)
    public Result selectUserRoleMenu(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            String id = (String) params.get("id");
            long ids = Long.parseLong(id);
            List<SysUserRoleMenuDto> list = selectUserRoleMenuService.selectUserRoleMenu(ids);
            result = Result.success(list);
        } catch (Exception e) {
            result = Result.error();
        }
        return result;
    }

    /**
     * 获取用户的权限信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户的权限信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/queryUserRoleList", method = RequestMethod.POST)
    public Result queryUserRoleList(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            String id = (String) params.get("id");
            long ids = Long.parseLong(id);
            List<SystemUserRoleDto> list = selectUserRoleMenuService.queryUserRoleList(ids);
            result = Result.success(list);
        } catch (Exception e) {
            result = Result.error();
        }
        return result;
    }

    /**
     * 获取所以的权限信息
     *
     * @return
     */
    @ApiOperation(value = "获取所以的权限信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/selectRoleAll", method = RequestMethod.POST)
    public Result selectRoleAll() {
        Result result = null;
        try {
            List<SysRole> list = selectUserRoleMenuService.selectRoleAll();
            result = Result.success(list);
        } catch (Exception e) {
            result = Result.error();
        }
        return result;
    }

    /**
     * 用户添加
     *
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequiresPermissions("userMsg:Add")
    @RequestMapping(value = "/userInsert", method = RequestMethod.POST, produces = "application/json")
    public Result userInsert(@RequestBody SysUser sysUser) {
        Result result = null;
        try {
            boolean isExistLoginName = sysUserService.isExistLoginName(sysUser.getLoginName());
            if (isExistLoginName) {
                return Result.error(ResponseCode.name_already_exist.getMsg());
            }
            if ((!StringUtils.hasText(sysUser.getPassword())) && sysUser.getPassword().length() < 6) {
                return Result.error("请设置密码长度大于等于6");
            }
            selectUserRoleMenuService.userInsert(sysUser);
            result = Result.success();
        } catch (Exception e) {
            result = Result.error();
        }
        return result;
    }

    /**
     * 查询用户列表
     *
     * @param params 查询参数
     * @return
     */
    @ApiOperation(value = "查询用户列表", httpMethod = "POST", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequiresPermissions("userMsg:Query")
    @RequestMapping(value = "listUser", method = RequestMethod.POST)
    public Result listUser(@RequestBody Map<String, Object> params) {
        int page = Integer.valueOf((String) params.get("page"));
        int rows = Integer.valueOf((String) params.get("rows"));
        String sort = "zhName";
        String order = "asc";
        String loginName = (String) params.get("loginName");
        String zhName = (String) params.get("zhName");
        String email = (String) params.get("email");
        String phone = (String) params.get("phone");
        String address = (String) params.get("address");
        PageInfo pageInfo = sysUserService.selectPage(page, rows, StringUtil.camelToUnderline(sort), order, loginName, zhName, email, phone, address);
        return Result.success(pageInfo);
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
    @RequestMapping(value = "/listRole", method = RequestMethod.POST)
    public Result listRole(@RequestBody Map<String, Object> params) {
        /*  PageInfo pageInfo = sysRoleService.selectPage(page, rows);*/
        PageInfo pageInfo = sysRoleService.listRole(Integer.valueOf((String) params.get("page")), Integer.valueOf((String) params.get("rows")), (String) params.get("roleName"));
        return Result.success(pageInfo);
    }

    /**
     * 查询权限列表
     *
     * @param
     * @return result
     */
    @ApiOperation(value = "查询权限列表", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    /*@RequiresPermissions("permissionMsg:Query")*/
    @RequestMapping(value = "listAllPermission", method = RequestMethod.POST)
    public Result listAllPermission() {
        List list = sysPermissionService.listAllPermission();
        return Result.success(list);
    }

    /**
     * 根据用户id进行修改信息
     *
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据用户id进行修改信息", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("userMsg:Edit")
    @RequestMapping(value = "updataUserId", method = RequestMethod.POST)
    public Result updataUserId(@RequestBody SysUser sysUser) {
        if (sysUser != null) {
            sysUserService.updateUser(sysUser);
            return Result.success();
        } else {
            return Result.error("用户修改失败");
        }
    }

}
