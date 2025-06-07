package com.travel.web.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.CityInfo;

import java.util.List;

/**
* @author 15101
* @description 针对表【city_info】的数据库操作Service
* @createDate 2025-03-14 19:44:06
*/
public interface CityInfoService extends IService<CityInfo> {

    List<CityInfo> getCityListByProvinceId(Long id);
}
