package com.travel.web.front.controller;

import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.UserInfo;
import com.travel.web.front.dto.login.LoginDto;
import com.travel.web.front.dto.user.ChangePasswordDTO;
import com.travel.web.front.service.SmsService;
import com.travel.web.front.service.UserInfoService;
import com.travel.web.front.vo.login.CaptchaVo;
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
@RequestMapping("user")
@Slf4j

@Tag(name = "用户")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(@RequestBody LoginDto loginDto) {
        String token = userInfoService.login(loginDto);
        return Result.ok(token);
    }

    @GetMapping
    @Operation(summary = "获取用户信息")
    @LoginCheck
    public Result<UserInfo> getUserInfo(@RequestHeader("Token") String token) {
        UserInfo result=userInfoService.getUserInfo(token);
        return Result.ok(result);
    }

    @GetMapping("/captcha")
    @Operation(summary = "获取图片验证码")
    public Result<CaptchaVo> getCaptcha() {
        return Result.ok(userInfoService.getCaptcha());
    }

    @PostMapping("/changeUserInfo")
    @Operation(summary = "更新用户信息")
    @LoginCheck
    public Result updateUserInfo(@RequestHeader("Token") String token,
                                 @RequestBody UserInfo userInfo) {
        userInfoService.updateUserInfo(token,userInfo);
        return Result.ok();
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
        userInfoService.logout(token);
        return Result.ok();
    }

    @PostMapping("/changePassword")
    @Operation(summary = "修改密码")
    @LoginCheck
    public Result changePassword(@RequestHeader("Token") String token, @RequestBody ChangePasswordDTO dto) {
        userInfoService.changePassword(token,dto);
        return Result.ok();
    }
}
