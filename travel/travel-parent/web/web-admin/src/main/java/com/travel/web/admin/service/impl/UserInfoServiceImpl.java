package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.StatusDTO;
import com.travel.web.admin.dto.tourist.TouristDTO;
import com.travel.web.admin.mapper.UserInfoMapper;
import com.travel.web.admin.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    @Override
    public IPage<UserInfo> getUserInfoList(IPage<UserInfo> page, TouristDTO dto) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(dto.getUsername()!=null && StringUtils.hasText(dto.getUsername()),UserInfo::getUsername,dto.getUsername())
                .like(dto.getPhone()!=null && StringUtils.hasText(dto.getPhone()),UserInfo::getPhone,dto.getPhone());
        return this.page(page,queryWrapper);
    }

    @Override
    public void updateTouristStatus(StatusDTO dto) {
        if (dto==null || dto.getStatus()==null || dto.getId()==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,dto.getId())
                .set(UserInfo::getStatus,dto.getStatus());
        this.update(updateWrapper);
    }
}




