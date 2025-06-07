package com.travel.web.admin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.travel.common.constant.RedisConstant;
import com.travel.common.constant.RegisterUserConstant;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.SystemUser;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.login.ChangePasswordDTO;
import com.travel.web.admin.dto.login.LoginDto;
import com.travel.web.admin.mapper.SystemUserMapper;
import com.travel.web.admin.mapper.UserInfoMapper;
import com.travel.web.admin.service.LoginService;
import com.travel.web.admin.service.SystemUserService;
import com.travel.web.admin.service.UserInfoService;
import com.travel.web.admin.vo.login.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-05 17:37
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Override
    public SystemUser getUserInfo(String token) {
        SystemUser info = systemUserMapper.selectById(jwtUtil.getUserId(token));
        info.setPassword(null);
        return info;
    }

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String code = specCaptcha.text().toLowerCase();
        String key = RedisConstant.FRONT_LOGIN_PREFIX + UUID.randomUUID();
        String image = specCaptcha.toBase64();
        //redisTemplate.opsForValue().set(key, code, RedisConstant.FRONT_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(image, key);
    }

    @Override
    public String login(LoginDto loginDto) {
        if (!StringUtils.hasText(loginDto.getPhone()) ||
                !StringUtils.hasText(loginDto.getCaptcha()) ||
                !StringUtils.hasText(loginDto.getCaptchaKey())) {
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
        //验证码校验
//        String captchaCode = (String) redisTemplate.opsForValue().get(loginDto.getCaptchaKey());
//        if (!loginDto.getCaptcha().equals(captchaCode)) {
//            throw new GlobalException(ResultCodeEnum.FRONT_LOGIN_CODE_EXPIRED);
//        }
        //根据电话号码获取用户信息
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getPhone, loginDto.getPhone());
        SystemUser userInfo = systemUserMapper.selectOne(queryWrapper);
        if (userInfo == null) {
            //注册新用户
            Long userId = registerUser(loginDto.getPhone());
            return jwtUtil.createToken(userId);
        }
        //用户禁用
        if (userInfo.getStatus() == 0) {
            throw new GlobalException(ResultCodeEnum.FRONT_ACCOUNT_DISABLED_ERROR);
        }
        if (StringUtils.hasText(loginDto.getPassword())) {
            //密码登录
            if (! MD5.create().digestHex(loginDto.getPassword()).equals(userInfo.getPassword())){
                throw new GlobalException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
            }
            return jwtUtil.createToken(userInfo.getId());
        }
        if (StringUtils.hasText(loginDto.getSmsCode())){
            //短信登录
//            String smsCode = (String) redisTemplate.opsForValue().get(RedisConstant.FRONT_LOGIN_SMS_PREFIX + loginDto.getPhone());
//            if (!loginDto.getSmsCode().equals(smsCode)) {
//                throw new GlobalException(ResultCodeEnum.FRONT_LOGIN_CODE_ERROR);
//            }
            return jwtUtil.createToken(userInfo.getId());
        }
        throw new GlobalException(ResultCodeEnum.DATA_ERROR);
    }

    @Override
    public void updateUserInfo(String token, SystemUser userInfo) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        userInfo.setId(jwtUtil.getUserId(token));
        //更新密码
        if (StringUtils.hasText(userInfo.getPassword())) {
            userInfo.setPassword(MD5.create().digestHex(userInfo.getPassword()));
        }
        int row = systemUserMapper.updateById(userInfo);
        if (row == 0) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public void logout(String token) {
        jwtUtil.tokenExpiration(token);
    }

    @Override
    public void changePassword(String token, ChangePasswordDTO dto) {
        LambdaUpdateWrapper<SystemUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUser::getId, jwtUtil.getUserId(token))
                .eq(SystemUser::getPassword,MD5.create().digestHex(dto.getCurrentPassword()))
                .set(SystemUser::getPassword, MD5.create().digestHex(dto.getNewPassword()));
        boolean update = systemUserService.update(updateWrapper);
        if (!update) {
            throw new GlobalException(ResultCodeEnum.FRONT_PASSWORD_ERROR);
        }

    }

    private long registerUser(String phone) {
        SystemUser userInfo = new SystemUser();
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.create().digestHex(RegisterUserConstant.PASSWORD));
        userInfo.setHeader(RegisterUserConstant.AVATAR_URL);
        userInfo.setStatus(RegisterUserConstant.STATUS);
        int row = systemUserMapper.insert(userInfo);
        if (row == 0) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
        return userInfo.getId();
    }
}
