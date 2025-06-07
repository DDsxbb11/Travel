package com.travel.web.admin.dto.login;

import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-04-19 12:51
 */

@Data
public class LoginDto {
    private String phone;

    private String password;

    private String captcha;

    private String captchaKey;

    private String smsCode;
}
