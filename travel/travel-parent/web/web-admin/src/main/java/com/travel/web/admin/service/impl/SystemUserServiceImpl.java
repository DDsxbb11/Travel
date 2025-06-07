package com.travel.web.admin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.model.pojo.SystemUser;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.StatusDTO;
import com.travel.web.admin.dto.tourist.TouristDTO;
import com.travel.web.admin.mapper.SystemUserMapper;
import com.travel.web.admin.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【system_user】的数据库操作Service实现
* @createDate 2025-05-31 17:58:58
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService {

    @Override
    public IPage<SystemUser> getSystemUserInfoList(IPage<SystemUser> page, TouristDTO dto) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(dto.getUsername()!=null && StringUtils.hasText(dto.getUsername()),SystemUser::getUsername,dto.getUsername())
                .like(dto.getPhone()!=null && StringUtils.hasText(dto.getPhone()),SystemUser::getPhone,dto.getPhone());
        return this.page(page,queryWrapper);
    }

    @Override
    public void updateSystemUserStatus(StatusDTO dto) {
        if (dto==null || dto.getStatus()==null || dto.getId()==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<SystemUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUser::getId,dto.getId())
                .set(SystemUser::getStatus,dto.getStatus());
        this.update(updateWrapper);
    }

    @Override
    public void updatePassword(SystemUser info) {
        if (info==null || info.getPhone()==null || info.getPassword()==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<SystemUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUser::getPhone,info.getPhone())
                .set(SystemUser::getPassword, MD5.create().digestHex(info.getPassword()));
        this.update(updateWrapper);
    }
}




