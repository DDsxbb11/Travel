package com.travel.common.constant;

/**
 * @author: JYX
 * @description: TODO: redis常量
 * @date: 2024-11-02 12:30
 */

public class RedisConstant {
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;
    public static final String FRONT_LOGIN_PREFIX = "front:login:";
    public static final Integer FRONT_LOGIN_CAPTCHA_TTL_SEC = 60;
    public static final String FRONT_LOGIN_SMS_PREFIX= "front:sms:";
    public static final Integer FRONT_LOGIN_SMS_TTL_SEC = 60;
    public static final Integer FRONT_LOGIN_CODE_RESEND_TIME_SEC = 60;
    public static final Integer FRONT_LOGIN_CODE_TTL_SEC = 60 * 10;
    public static final String FRONT_ROOM_PREFIX = "front:room:";
    public static final Integer FRONT_ROOM_TTL_SEC=60*60*24*10;
}
