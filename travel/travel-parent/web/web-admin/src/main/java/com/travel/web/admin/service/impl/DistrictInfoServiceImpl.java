package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.CityInfo;
import com.travel.model.pojo.DistrictInfo;
import com.travel.web.admin.mapper.DistrictInfoMapper;
import com.travel.web.admin.service.DistrictInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15101
* @description 针对表【district_info】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class DistrictInfoServiceImpl extends ServiceImpl<DistrictInfoMapper, DistrictInfo>
    implements DistrictInfoService {

    @Override
    public List<DistrictInfo> getDistrictByCityId(Long id) {
        LambdaQueryWrapper<DistrictInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DistrictInfo::getCityId,id);
        return this.list(queryWrapper);
    }
}




