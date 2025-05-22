package com.travel.web.front.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.travel.common.constant.RedisConstant;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.CodeUtility;
import com.travel.web.front.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author: Amoretto
 * @description: TODO: 发送短信
 * @date: 2025-05-06 22:58
 */

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private Client client;
//    @Autowired
//    private RedisTemplate redisTemplate;
    @Autowired
    private CodeUtility codeUtility;

    @Override
    public void sendSms(String phone) {
//        if (!StringUtils.hasText(phone)){
//            throw new GlobalException(ResultCodeEnum.FRONT_LOGIN_PHONE_EMPTY);
//        }
//        //生成验证码
//        String code = codeUtility.getCode(6);
//        String key=RedisConstant.FRONT_LOGIN_SMS_PREFIX+phone;
//        //判断是否发送过短信
//        Boolean isSend = redisTemplate.hasKey(key);
//        if(Boolean.TRUE.equals(isSend)){
//            //发送过短信
//            if (redisTemplate.getExpire(key,TimeUnit.SECONDS)<RedisConstant.FRONT_LOGIN_CODE_RESEND_TIME_SEC){
//                //发送过短信，并且距离上次发送时间小于60秒
//                throw new GlobalException(ResultCodeEnum.FRONT_SEND_SMS_TOO_OFTEN);
//            }
//        }
//        try {
//            //发送短信
//            SendSmsRequest smsRequest = new SendSmsRequest();
//            smsRequest.setPhoneNumbers(phone);
//            smsRequest.setSignName("阿里云短信测试");
//            smsRequest.setTemplateCode("SMS_154950909");
//            smsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
//            client.sendSms(smsRequest);
//            //将验证码存入redis
//            redisTemplate.opsForValue().set(key,code, 5, TimeUnit.MINUTES);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
