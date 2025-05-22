package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.mapper.UserInfoMapper;
import com.travel.model.pojo.UserInfo;
import com.travel.model.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




