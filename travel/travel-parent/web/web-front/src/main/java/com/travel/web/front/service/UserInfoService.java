package com.travel.web.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.UserInfo;
import com.travel.web.front.dto.login.LoginDto;
import com.travel.web.front.dto.user.ChangePasswordDTO;
import com.travel.web.front.vo.login.CaptchaVo;

/**
* @author 15101
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2025-03-18 11:00:12
*/
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getUserInfo(String token);

    CaptchaVo getCaptcha();

    String login(LoginDto loginDto);

    void updateUserInfo(String token, UserInfo userInfo);

    void logout(String token);

    void changePassword(String token, ChangePasswordDTO dto);
}
