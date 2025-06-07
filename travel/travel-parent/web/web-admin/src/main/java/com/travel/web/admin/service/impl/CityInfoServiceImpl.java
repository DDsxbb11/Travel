package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.CityInfo;
import com.travel.web.admin.mapper.CityInfoMapper;
import com.travel.web.admin.service.CityInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15101
* @description 针对表【city_info】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
    implements CityInfoService {


    @Override
    public List<CityInfo> getCityListByProvinceId(Long id) {
        LambdaQueryWrapper<CityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CityInfo::getProvinceId,id);
        return this.list(queryWrapper);
    }
}




