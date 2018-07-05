package com.hunt.system.security.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: whq
 * @Date: 2018/7/4 11:39
 * @Description: 验证器，增加了登录次数校验功能
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger log = LoggerFactory.getLogger(RetryLimitCredentialsMatcher.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if (!"LOCK".equals(opsForValue.get("longinLock" + username))) {
            String longinCount = opsForValue.get("longinCount" + username);
            if (longinCount == null) {
                opsForValue.set("longinCount" + username, "1");
            } else {
                opsForValue.set("longinCount" + username, Integer.parseInt(longinCount) + 1 + "");
            }
            //计数大于5时，设置用户被锁定一小时
            if (Integer.parseInt(opsForValue.get("longinCount" + username)) >= 5) {
                opsForValue.set("longinLock" + username, "LOCK");
                redisTemplate.expire("longinLock" + username, 1, TimeUnit.HOURS);
            }
        }
        if ("LOCK".equals(opsForValue.get("longinLock" + username))) {
            throw new ExcessiveAttemptsException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //清空登录计数
            opsForValue.set("longinCount" + username, "0");
        }
        return matches;
    }
}

