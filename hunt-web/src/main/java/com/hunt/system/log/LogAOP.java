package com.hunt.system.log;

import com.hunt.model.dto.LoginInfo;
import com.hunt.model.entity.SysLog;
import com.hunt.tools.IPHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.hunt.service.SystemService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: ouyangan
 * @Date : 2016/10/15
 */
@Aspect
@Component
@Order(2)
public class LogAOP {
    private static final Logger logger = LoggerFactory.getLogger(LogAOP.class);

    @Autowired
    private SystemService systemService;

    @Around("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public Object recordLog(ProceedingJoinPoint p) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object o = null;
        Long t1 = System.currentTimeMillis();
        try {
            o = p.proceed();
        } catch (Exception e) {//这里建议将异常向上层抛让异常处理器来进行捕捉
            if (e instanceof UnknownAccountException) {
                throw new UnknownAccountException(e);
            } else if (e instanceof IncorrectCredentialsException) {
                throw new IncorrectCredentialsException(e);
            } else if (e instanceof UnauthorizedException) {
                throw new UnauthorizedException(e);
            } else {
                throw new Exception(e);
            }
        }
        SysLog log = new SysLog();

        Long t2 = System.currentTimeMillis();

        if (o.toString().length() < 5000) {
            log.setResult(o.toString());
        } else {
            log.setResult("data is too long");
        }
        Integer a = (t2.intValue() - t1.intValue());
        log.setDuration(a);
        log.setMethod(p.getTarget().getClass().getName() + "." + p.getSignature().getName());
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : request.getParameterMap().keySet()) {
            stringBuilder.append(s);
            stringBuilder.append(" = ");
            stringBuilder.append(request.getParameterMap().get(s)[0]);
            stringBuilder.append(" | ");
        }
        log.setParam(stringBuilder.toString());
        log.setIp(IPHelper.getIpAddress(request));
        log.setUrl(request.getRequestURL().toString());
        log.setUserAgent(request.getHeader("user-agent"));
        log.setCreateTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        if (loginInfo != null) {
            Integer userId = loginInfo.getId();
            log.setCreateBy(userId);
            systemService.insertSysControllerLog(log);
        }


        logger.info("request contentType:{}", request.getHeader("Accept"));
        logger.info("request param : {}", log.getParam());
        logger.info("reuest method : {}", request.getMethod());
        logger.info("request url : {}", log.getUrl());
        return o;
    }

}
