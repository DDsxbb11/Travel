package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.web.admin.dto.StatusDTO;
import com.travel.web.admin.dto.tourist.TouristDTO;

/**
* @author 15101
* @description 针对表【system_user】的数据库操作Service
* @createDate 2025-05-31 17:58:58
*/
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUser> getSystemUserInfoList(IPage<SystemUser> page, TouristDTO dto);

    void updateSystemUserStatus(StatusDTO dto);

    void updatePassword(SystemUser info);
}
