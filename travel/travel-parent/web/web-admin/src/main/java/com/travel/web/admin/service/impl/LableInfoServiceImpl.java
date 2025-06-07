package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.LableInfo;
import com.travel.web.admin.dto.strategy.LableDTO;
import com.travel.web.admin.mapper.LableInfoMapper;
import com.travel.web.admin.service.LableInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【lable_info(攻略标签)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class LableInfoServiceImpl extends ServiceImpl<LableInfoMapper, LableInfo>
    implements LableInfoService {

    @Override
    public IPage<LableInfo> getLableInfoPage(IPage<LableInfo> page, LableDTO dto) {
        LambdaQueryWrapper<LableInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(dto.getName()!=null && StringUtils.hasText(dto.getName()),LableInfo::getName,dto.getName())
                .orderByDesc(LableInfo::getCreateTime);
        return this.page(page,queryWrapper);
    }
}




