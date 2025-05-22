package com.travel.common.utilis;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author: JYX
 * @description: TODO: 获取短信验证码工具类
 * @date: 2024-11-10 15:46
 */
@Component
public class CodeUtility {
    public String getCode(Integer length){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(new Random().nextInt(10));
        }
        return builder.toString();
    }
}
