package com.travel.web.admin.service;

import com.travel.model.pojo.SystemUser;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.login.ChangePasswordDTO;
import com.travel.web.admin.dto.login.LoginDto;
import com.travel.web.admin.vo.login.CaptchaVo;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-05 17:36
 */

public interface LoginService {

    SystemUser getUserInfo(String token);

    CaptchaVo getCaptcha();

    String login(LoginDto loginDto);

    void updateUserInfo(String token, SystemUser systemUser);

    void logout(String token);

    void changePassword(String token, ChangePasswordDTO dto);
}
