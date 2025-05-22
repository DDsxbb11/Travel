package com.travel.web.front.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.constant.RedisConstant;
import com.travel.common.constant.RegisterUserConstant;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.UserInfo;
import com.travel.web.front.dto.login.LoginDto;
import com.travel.web.front.dto.user.ChangePasswordDTO;
import com.travel.web.front.mapper.UserInfoMapper;
import com.travel.web.front.service.UserInfoService;
import com.travel.web.front.vo.login.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.checkerframework.checker.guieffect.qual.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
* @author 15101
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2025-03-18 11:00:12
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Override
    public UserInfo getUserInfo(String token) {
        UserInfo info = userInfoMapper.selectById(jwtUtil.getUserId(token));
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
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginDto.getPhone());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
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
    public void updateUserInfo(String token, UserInfo userInfo) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        userInfo.setId(jwtUtil.getUserId(token));
        //更新密码
        if (StringUtils.hasText(userInfo.getPassword())) {
            userInfo.setPassword(MD5.create().digestHex(userInfo.getPassword()));
        }
        int row = userInfoMapper.updateById(userInfo);
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
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId, jwtUtil.getUserId(token))
                .eq(UserInfo::getPassword,MD5.create().digestHex(dto.getCurrentPassword()))
                .set(UserInfo::getPassword, MD5.create().digestHex(dto.getNewPassword()));
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new GlobalException(ResultCodeEnum.FRONT_PASSWORD_ERROR);
        }

    }

    private long registerUser(String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.create().digestHex(RegisterUserConstant.PASSWORD));
        userInfo.setAvatarUrl(RegisterUserConstant.AVATAR_URL);
        userInfo.setStatus(RegisterUserConstant.STATUS);
        int row = userInfoMapper.insert(userInfo);
        if (row == 0) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
        return userInfo.getId();
    }
}




