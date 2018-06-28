package com.hunt.system.security.ip;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.hunt.system.exception.ForbiddenIpException;
import com.hunt.tools.IPHelper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.hunt.service.SystemService;
import com.hunt.util.ResponseCode;

import javax.servlet.http.HttpServletRequest;

/**
 * ip拦截前置拦截器
 */
@Aspect
@Component
@Order(1) //order值越小越先执行
public class ForbiddenIpAOP {

    private static Logger log = LoggerFactory.getLogger(ForbiddenIpAOP.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Before("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void forbiddenIp() throws ForbiddenIpException {
        if ("true".equals(systemService.selectDataItemByKey("ip_forbidden", 3))) {
            Boolean ip_forbidden = redisTemplate.hasKey("ip_intercepter");
            if (!ip_forbidden && systemService.selectIPForbiddenStatus()) {
                systemService.openIpIntercept();
            }
            log.debug("open ip intercepter : {}", true);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String ipAddress = IPHelper.getIpAddress(request);
            /*  String remoteAddr = request.getRemoteAddr();*/
            if (systemService.isForbiddenIp(ipAddress)) {
                log.error("this {}  ip is  forbidden ", ipAddress);
                throw new ForbiddenIpException(ResponseCode.forbidden_ip.getMsg());
            }
        }
    }
}
