package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.FacilityInfo;
import com.travel.web.admin.dto.facility.FacilityDTO;
import com.travel.web.admin.mapper.FacilityInfoMapper;
import com.travel.web.admin.service.FacilityInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 15101
* @description 针对表【facility_info(设施信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class FacilityInfoServiceImpl extends ServiceImpl<FacilityInfoMapper, FacilityInfo>
    implements FacilityInfoService {

    @Override
    public IPage<FacilityInfo> getFacilityInfoPage(IPage<FacilityInfo> page, FacilityDTO dto) {
        LambdaQueryWrapper<FacilityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dto.getType() != null && StringUtils.hasText(dto.getType()), FacilityInfo::getType, dto.getType())
                .like(dto.getName() != null && StringUtils.hasText(dto.getName()), FacilityInfo::getName, dto.getName())
                .orderByDesc(FacilityInfo::getCreateTime);
        return this.page(page,queryWrapper);
    }

    @Override
    public List<FacilityInfo> getFacilityByType(FacilityDTO dto) {
        LambdaQueryWrapper<FacilityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dto.getType() != null && StringUtils.hasText(dto.getType()), FacilityInfo::getType, dto.getType())
                .orderByDesc(FacilityInfo::getCreateTime);
        return this.list(queryWrapper);
    }
}




