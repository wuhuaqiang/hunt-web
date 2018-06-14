package com.hunt.model.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * @Auther: whq
 * @Date: 2018/6/1 13:29
 * @Description:
 */
public class JSONEntity extends JSONObject {
    private Set<String> roleList;
    private Set<String> menuList;
    private Set<String> permissionList;
    private Set<Integer> permissionIds;
    private List<JSONObject> picList;
    private List<JSONObject> menus;
    private List<JSONObject> users;
    private List<JSONObject> permissions;
}
