package com.travel.web.admin.controller;

import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.SystemUser;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.login.ChangePasswordDTO;
import com.travel.web.admin.dto.login.LoginDto;
import com.travel.web.admin.service.LoginService;
import com.travel.web.admin.service.SmsService;
import com.travel.web.admin.service.UserInfoService;
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
@RequestMapping("user")
@Slf4j

@Tag(name = "用户")
public class UserController {

    @Autowired
    private LoginService  loginService;


    @GetMapping()
    @Operation(summary = "获取用户信息")
    @LoginCheck
    public Result<SystemUser> getUserInfo(@RequestHeader("Token") String token) {
        SystemUser result=loginService.getUserInfo(token);
        return Result.ok(result);
    }

    @PostMapping("/changeUserInfo")
    @Operation(summary = "更新用户信息")
    @LoginCheck
    public Result updateUserInfo(@RequestHeader("Token") String token,
                                 @RequestBody SystemUser userInfo) {
        loginService.updateUserInfo(token,userInfo);
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
