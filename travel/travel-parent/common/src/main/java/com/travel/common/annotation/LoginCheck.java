package com.travel.common.annotation;

import java.lang.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO: 登录校验注解
 * @date: 2025-05-01 17:19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
}
