package com.hunt.controller;

import com.hunt.model.entity.*;
import com.hunt.service.SysLoginRecordsService;
import com.hunt.service.SysLoginlogService;
import com.hunt.system.security.geetest.GeetestConfig;
import com.hunt.system.security.geetest.GeetestLib;
import com.hunt.model.dto.LoginInfo;
import com.hunt.model.dto.PageInfo;
import com.hunt.service.SysUserService;
import com.hunt.service.SystemService;
import com.hunt.tools.CaptchaImgCreater;
import com.hunt.tools.Constant;
import com.hunt.tools.IPHelper;
import com.hunt.tools.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.hunt.util.ResponseCode;
import com.hunt.util.Result;
import com.hunt.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统功能模块
 */
@Api(value = "系统功能模块")
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SysLoginlogService sysLoginlogService;
    @Autowired
    private SysLoginRecordsService sysLoginRecordsService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 引导页
     *
     * @return
     */
    @ApiOperation(value = "跳转至引导页", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String index() {
        return "system/index";
    }

    /**
     * 登录
     * loginName 登录名
     * password  密码
     * platforms 终端类型
     *
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json")
    public Result login(@RequestBody Map<String, Object> params,
                        HttpServletRequest request) throws Exception {
       /* //极限验证二次服务验证
        if (!verifyCaptcha(request)) {
            return Result.instance(ResponseCode.verify_captcha_error.getCode(), ResponseCode.verify_captcha_error.getMsg());
        }*/
        String loginName = MD5Utils.md5LoginName((String) params.get("loginName"));
        String password = (String) params.get("password");
        String platforms = (String) params.get("platforms");
        SysUser user = sysUserService.selectByLoginName(loginName);
        SysLoginlog sysLoginlog = new SysLoginlog();
        String ipAddress = IPHelper.getIpAddress(request);
        sysLoginlog.setLoginip(ipAddress);
        sysLoginlog.setAccount(loginName);
        sysLoginlog.setAddtime(new Date());
        sysLoginlog.setLogintime(new Date());
        Integer platform = Integer.parseInt(platforms);
        if (user == null) {
            sysLoginlog.setErrormsg(ResponseCode.unknown_account.getMsg());
            sysLoginlog.setIssuccess("0");
            sysLoginlog.setLogtype("登录");
            sysLoginlogService.insert(sysLoginlog);
            return Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMsg());
        }
        if (user.getStatus() == 0) {
            sysLoginlog.setErrormsg(ResponseCode.forbidden_account.getMsg());
            sysLoginlog.setIssuccess("0");
            sysLoginlog.setLogtype("登录");
            sysLoginlogService.insert(sysLoginlog);
            return Result.instance(ResponseCode.forbidden_account.getCode(), ResponseCode.forbidden_account.getMsg());
        }
        if (user.getExpiryTime() != null) {
            if (user.getExpiryTime().getTime() <= System.currentTimeMillis() - 86400000) {
                sysLoginlog.setErrormsg(ResponseCode.account_expiration.getMsg());
                sysLoginlog.setIssuccess("0");
                sysLoginlog.setLogtype("登录");
                sysLoginlogService.insert(sysLoginlog);
                return Result.instance(ResponseCode.account_expiration.getCode(), ResponseCode.account_expiration.getMsg());
            }
        }
        SysLoginrecordsParams sys_params = new SysLoginrecordsParams();
        sys_params.clear();
        sys_params.createCriteria().andAccountEqualTo(loginName);
        List<SysLoginrecords> sysLoginrecords = sysLoginRecordsService.selectByExample(sys_params);

        if (sysLoginrecords.size() == 0) {
            SysLoginrecords record = new SysLoginrecords();
            record.setAccount(loginName);
            record.setAddtime(new Date());
            record.setLastloginip(ipAddress);
            record.setLogintime(new Date());
            record.setLastlogintime(new Date());
            record.setLoginip(ipAddress);
            record.setLogincount(1L);
            sysLoginRecordsService.insert(record);

        } else {
            SysLoginrecords record = sysLoginrecords.get(0);
            long timeMillis = System.currentTimeMillis();
            long lastlogintimeMillis = record.getLastlogintime().getTime();
            int a = (int) ((timeMillis - lastlogintimeMillis) / (1000 * 3600 * 24));
            if (a > 90) {
                sysLoginlog.setErrormsg(ResponseCode.dormant_account.getMsg());
                sysLoginlog.setIssuccess("0");
                sysLoginlog.setLogtype("登录");
                sysLoginlogService.insert(sysLoginlog);
                return Result.instance(ResponseCode.dormant_account.getCode(), ResponseCode.dormant_account.getMsg());
            }
            record.setAccount(loginName);
            record.setLastloginip(ipAddress);
            record.setLastlogintime(new Date());
            record.setLogincount(sysLoginrecords.get(0).getLogincount() + 1);
            record.setUpdatetime(new Date());
            sysLoginRecordsService.updateByExample(record, sys_params);
        }
        sysLoginlog.setErrormsg("登录成功");
        sysLoginlog.setIssuccess("1");
        sysLoginlog.setLogtype("登录");
        sysLoginlogService.insert(sysLoginlog);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(loginName, password));
        LoginInfo loginInfo = sysUserService.login(user, subject.getSession().getId(), platform);
        subject.getSession().setAttribute("loginInfo", loginInfo);
        String session = subject.getSession().getId().toString();
        System.out.print("00000----===" + session.toString());
        log.debug("登录成功");
        return Result.success(loginInfo);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Result logout(@RequestBody Map<String, Object> params, HttpServletRequest request) {

        SysLoginlog sysLoginlog = new SysLoginlog();
        String ipAddress = IPHelper.getIpAddress(request);
        sysLoginlog.setLoginip(ipAddress);
        Object principal = SecurityUtils.getSubject().getPrincipal();
        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
        String loginName = (String) principal;
        sysLoginlog.setAccount(loginName);
        sysLoginlog.setAddtime(new Date());
        sysLoginlog.setLogintime(new Date());
        sysLoginlog.setIssuccess("1");
        sysLoginlog.setLogtype("注销");
        sysLoginlog.setErrormsg("注销成功");
        redisTemplate.opsForValue().getOperations().delete(sessionId);
        redisTemplate.opsForValue().getOperations().delete(sessionId + "_Permission");
        redisTemplate.opsForValue().getOperations().delete("shiro-cache-" + loginName);
        redisTemplate.opsForValue().getOperations().delete("longinCount" + loginName);
        SecurityUtils.getSubject().logout();
        sysLoginlogService.insert(sysLoginlog);
        return Result.success();
    }

    @RequestMapping(value = "/captchImg", method = RequestMethod.GET)
    public void getCaptchImg(HttpServletRequest request, HttpServletResponse response) {
        CaptchaImgCreater captchaImgCreater = new CaptchaImgCreater(response, request);
        try {
            captchaImgCreater.createRandImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 极限验证
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "极限验证", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public String captcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());
        int gtServerStatus = gtSdk.preProcess();
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        return gtSdk.getResponseStr();
    }

    /**
     * 用户状态
     *
     * @return
     */
    @ApiOperation(value = "跳转至用户状态模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("user:loginStatu:list")
    @RequestMapping(value = "online", method = RequestMethod.GET)
    public String online() {
        return "system/online";
    }

    /**
     * 在线用户列表
     *
     * @param page 起始页码
     * @param rows 分页大小
     * @return
     */
    @ApiOperation(value = "在线用户列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("user:loginStatu:list")
    @ResponseBody
    @RequestMapping(value = "online/list", method = RequestMethod.GET)
    public PageInfo onlineList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "30") int rows) {
        PageInfo pageInfo = systemService.selectLogStatus(page, rows);
        return pageInfo;
    }

    /**
     * 强制用户下线
     *
     * @param userIds 用户ids
     * @return
     */
    @ApiOperation(value = "强制用户下线", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:loginout")
    @ResponseBody
    @RequestMapping(value = "forceLogout", method = RequestMethod.GET)
    public Result forceLogout(@RequestParam String userIds) {
        System.out.println("userIds = [" + userIds + "]");
        String[] ids = userIds.split(",");
        for (String id : ids) {
            systemService.forceLogout(Integer.valueOf(id));
        }
        return Result.success();
    }

    /**
     * 日志页面
     *
     * @return
     */
    @ApiOperation(value = "跳转至日志页面", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("log:list")
    @RequestMapping(value = "log")
    public String log() {
        return "system/log";
    }

    /**
     * 查询日志列表
     *
     * @param params 起始页码
     * @return
     */
    @ApiOperation(value = "查询日志列表", httpMethod = "POST", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("systemLogMsg:Query")
    @ResponseBody
    @RequestMapping(value = "log/list", method = RequestMethod.POST)
    public Result logList(@RequestBody Map<String, Object> params) {
        Result results = null;
        try {
            Integer page = Integer.valueOf((String) params.get("page"));
            Integer rows = Integer.valueOf((String) params.get("rows"));
            String method = (String) params.get("method");
            String url = (String) params.get("url");
            String param = (String) params.get("param");
            String result = (String) params.get("result");
            String sort = "id";
            String order = "desc";
            results = Result.success(systemService.selectLog(page, rows, StringUtil.camelToUnderline(sort), order, method, url, param, result));
        } catch (Exception e) {
            e.printStackTrace();
            results = Result.error();
        }
        return results;
    }

    /**
     * 查询登录日志列表
     *
     * @param params 起始页码
     * @return
     */
    @ApiOperation(value = "查询日志列表", httpMethod = "POST", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("systemLoginLogMsg:Query")
    @ResponseBody
    @RequestMapping(value = "loginLog/list", method = RequestMethod.POST)
    public Result loginLogList(@RequestBody Map<String, Object> params) {
        Result results = null;
        try {
            Integer page = Integer.valueOf((String) params.get("page"));
            Integer rows = Integer.valueOf((String) params.get("rows"));
            String account = (String) params.get("account");
            String sort = "id";
            String order = "desc";
            SysLoginlogParams sysLoginlogParams = new SysLoginlogParams();
            sysLoginlogParams.clear();
            if (account != null) {
                sysLoginlogParams.createCriteria().andAccountLike("%" + account + "%");
            }
            results = Result.success(sysLoginlogService.selectByExample(sysLoginlogParams, page, rows));
        } catch (Exception e) {
            e.printStackTrace();
            results = Result.error();
        }
        return results;
    }

    /**
     * 新增字典组
     *
     * @param name        名称
     * @param description 描述
     * @return
     */
    @ApiOperation(value = "新增字典组", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:group:insert")
    @ResponseBody
    @RequestMapping(value = "dataGroup/insert", method = RequestMethod.POST)
    public Result dataGroupInsert(@RequestParam String name,
                                  @RequestParam String description) {
        boolean isExistName = systemService.isExistDataGroupName(name);
        if (isExistName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysDataGroup sysDataGroup = new SysDataGroup();
        sysDataGroup.setName(name);
        sysDataGroup.setDescription(description);
        sysDataGroup.setParentId(0);
        sysDataGroup.setIsFinal(2);
        Integer id = systemService.insertSysDataGroup(sysDataGroup);
        return Result.success(id);
    }

    /**
     * 字典组列表
     *
     * @return
     */
    @ApiOperation(value = "字典组列表", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:group:list")
    @ResponseBody
    @RequestMapping(value = "dataGroup/list", method = RequestMethod.GET)
    public List<SysDataGroup> dataGroupList() {
        List<SysDataGroup> list = systemService.selectDataGroupList();
        return list;
    }

    @ApiOperation(value = "跳转至字典页面", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String data() {
        return "system/data";
    }

    /**
     * 新增数据字典
     *
     * @param key         key
     * @param value       value
     * @param description 描述
     * @param groupId     字典组
     * @return
     */
    @ApiOperation(value = "新增数据字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:insert")
    @ResponseBody
    @RequestMapping(value = "data/insert", method = RequestMethod.POST)
    public Result dataInsert(@RequestParam String key,
                             @RequestParam String value,
                             @RequestParam String description,
                             @RequestParam Integer groupId) {
        boolean isExistName = systemService.isExistDataItemKeyName(key, groupId);
        if (isExistName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysDataItem sysDataItem = new SysDataItem();
        sysDataItem.setKeyName(key);
        sysDataItem.setKeyValue(value);
        sysDataItem.setSysDataGroupId(groupId);
        sysDataItem.setDescription(description);
        Integer id = systemService.insertSysDataItem(sysDataItem);
        return Result.success(id);
    }


    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除字典", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:delete")
    @ResponseBody
    @RequestMapping(value = "data/delete", method = RequestMethod.GET)
    public Result dataDelete(@RequestParam Integer id) {
        systemService.deleteDataItemById(id);
        return Result.success();
    }

    /**
     * 更新字典
     *
     * @param id          id
     * @param key         key
     * @param value       value
     * @param description 描述
     * @param groupId     字典组
     * @return
     */
    @ApiOperation(value = "更新字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:update")
    @ResponseBody
    @RequestMapping(value = "data/update", method = RequestMethod.POST)
    public Result dataUpdate(@RequestParam Integer id,
                             @RequestParam String key,
                             @RequestParam String value,
                             @RequestParam String description,
                             @RequestParam Integer groupId) {
        SysDataItem sysDataItem = systemService.selectDataItemById(id);
        if (sysDataItem == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysDataItem.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        boolean isExistDataItemNameExcludeId = systemService.isExistDataItemNameExcludeId(id, key, groupId);
        if (isExistDataItemNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        sysDataItem.setKeyName(key);
        sysDataItem.setKeyValue(value);
        sysDataItem.setSysDataGroupId(groupId);
        sysDataItem.setDescription(description);
        systemService.updateDateItem(sysDataItem);

        return Result.success();
    }

    /**
     * 查询字典详情
     *
     * @return
     */
    @ApiOperation(value = "查询字典详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:select")
    @ResponseBody
    @RequestMapping(value = "data/select", method = RequestMethod.GET)
    public Result dataSelect() {
        return Result.success();
    }

    /**
     * 字典列表
     *
     * @param page 起始页码
     * @param rows 分页大小
     * @return
     */
    @ApiOperation(value = "字典列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("data:list")
    @ResponseBody
    @RequestMapping(value = "data/list", method = RequestMethod.GET)
    public PageInfo dataList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "30") int rows) {
        PageInfo pageInfo = systemService.selectDataItemPage(page, rows);
        return pageInfo;
    }

    /**
     * ip页面跳转
     *
     * @return
     */
    @ApiOperation(value = "跳转至ip模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("ip:list")
    @RequestMapping(value = "ip", method = RequestMethod.GET)
    public String ip() {
        return "system/ip";
    }

    /**
     * 插入ip
     *
     * @param params,//参数
     * @return
     */
    @ApiOperation(value = "插入ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("iPAccessControlMsg:Add")
    @ResponseBody
    @RequestMapping(value = "ip/insert", method = RequestMethod.POST)
    public Result ipInsert(@RequestBody Map<String, Object> params) throws ParseException {
        String ip = (String) params.get("ip");
        String expireTime = (String) params.get("expireTime");
        String description = (String) params.get("description");
        boolean isExistIp = systemService.isExistIp(ip);
        if (isExistIp) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysIpForbidden sysIpForbidden = new SysIpForbidden();
        sysIpForbidden.setIp(ip);
        sysIpForbidden.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        sysIpForbidden.setCreateTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        Integer userId = loginInfo.getId();
        sysIpForbidden.setCreateBy(userId);
        sysIpForbidden.setDescription(description);
        Integer id = systemService.insertIp(sysIpForbidden);
        return Result.success(id);
    }

    /**
     * 删除ip
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "删除ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("iPAccessControlMsg:Remove")
    @ResponseBody
    @RequestMapping(value = "ip/delete", method = RequestMethod.POST)
    public Result ipDelete(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        systemService.deleteIp(id);
        return Result.success();
    }

    /**
     * 更新ip
     *
     * @param params,//参数
     * @return
     */
    @ApiOperation(value = "更新ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("iPAccessControlMsg:Edit")
    @ResponseBody
    @RequestMapping(value = "ip/update", method = RequestMethod.POST)
    public Result ipUpdate(@RequestBody Map<String, Object> params) throws ParseException {
        Integer id = (Integer) params.get("id");
        String ip = (String) params.get("ip");
        String expireTime = (String) params.get("expireTime");
        String description = (String) params.get("description");
        boolean isExistIpExcludeId = systemService.isExistIpExcludeId(ip, id);
        if (isExistIpExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysIpForbidden sysIpForbidden = new SysIpForbidden();
        sysIpForbidden.setId(id);
        sysIpForbidden.setIp(ip);
        sysIpForbidden.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        sysIpForbidden.setDescription(description);
        sysIpForbidden.setUpdateTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        Integer userId = loginInfo.getId();
        sysIpForbidden.setUpdateBy(userId);
        systemService.updateIp(sysIpForbidden);
        return Result.success();
    }

    /**
     * 查询ip列表
     *
     * @param params 分页参数
     * @return
     */
    @ApiOperation(value = "查询ip列表", httpMethod = "POST", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("iPAccessControlMsg:Query")
    @ResponseBody
    @RequestMapping(value = "ip/list", method = RequestMethod.POST)
    public Result ipSelect(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            int page = (Integer) params.get("page");
            int rows = (Integer) params.get("rows");
            result = Result.success(systemService.selectIp(page, rows));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器错误！");
        }
        return result;
    }

    @ApiOperation(value = "ip拦截开关", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("iPAccessControlMsg:IpSwitch")
    @RequestMapping(value = "ip/intercept", method = RequestMethod.POST)
    public Result intercept(@RequestBody Map<String, Object> params) {
        boolean open = (boolean) params.get("open");
        //启用
        if (open == true) {
            systemService.openIpIntercept();
        }
        //禁用
        if (open == false) {
            systemService.closeIpIntercept();
        }
        return Result.success();
    }

    @ApiOperation(value = "ip拦截开关状态", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "ip/intercept/status")
    public Result interceptStatus() {
        boolean ip_forbidden = systemService.selectIPForbiddenStatus();
        return Result.success(ip_forbidden);
    }

}
