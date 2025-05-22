package com.travel.common.aspect;

import com.travel.common.annotation.LoginCheck;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: 那谁，等我娶你
 * @description: TODO: 登录注解类
 * @date: 2024-08-19 17:22
 */

@Aspect
@Component
public class LoginCheckAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Around("execution(* com.travel..controller..*(..)) && @annotation(loginCheck)")
    public Object process(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        //获取request对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra=(ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        //获取token
        String token = request.getHeader("Token");
        if (!StringUtils.hasText(token)){
            throw new GlobalException(ResultCodeEnum.FRONT_LOGIN_AUTH);
        }
        //判断token是否过期
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        //执行方法
        return joinPoint.proceed();
    }
}
