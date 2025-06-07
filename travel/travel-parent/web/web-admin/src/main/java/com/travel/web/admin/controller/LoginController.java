package com.travel.web.admin.controller;

import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.login.ChangePasswordDTO;
import com.travel.web.admin.dto.login.LoginDto;
import com.travel.web.admin.service.LoginService;
import com.travel.web.admin.service.SmsService;
import com.travel.web.admin.vo.login.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO: 用户
 * @date: 2025-04-18 16:11
 */
@RestController
@RequestMapping("login")
@Slf4j

@Tag(name = "用户")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SmsService smsService;

    @PostMapping()
    @Operation(summary = "用户登录")
    public Result<String> login(@RequestBody LoginDto loginDto) {
        String token = loginService.login(loginDto);
        return Result.ok(token);
    }


    @GetMapping("/captcha")
    @Operation(summary = "获取图片验证码")
    public Result<CaptchaVo> getCaptcha() {
        return Result.ok(loginService.getCaptcha());
    }


    @GetMapping("/sms")
    @Operation(summary = "发送短信验证码")
    public Result sendSms(@RequestParam("phone") String phone) {
        smsService.sendSms(phone);
        return Result.ok();
    }

    @PostMapping("logout")
    @Operation(summary = "用户登出")
    public Result logout(@RequestHeader("Token") String token) {
        loginService.logout(token);
        return Result.ok();
    }

    @PostMapping("/changePassword")
    @Operation(summary = "修改密码")
    @LoginCheck
    public Result changePassword(@RequestHeader("Token") String token, @RequestBody ChangePasswordDTO dto) {
        loginService.changePassword(token,dto);
        return Result.ok();
    }
}
