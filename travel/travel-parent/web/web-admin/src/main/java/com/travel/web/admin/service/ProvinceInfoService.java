package com.travel.web.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.ProvinceInfo;
import com.travel.web.admin.vo.location.OptionVo;

import java.util.List;

/**
* @author 15101
* @description 针对表【province_info】的数据库操作Service
* @createDate 2025-03-14 19:44:06
*/
public interface ProvinceInfoService extends IService<ProvinceInfo> {

    List<OptionVo> getProvinceAndCityCascade();

    List<OptionVo> getProvinceAndCityAndDistrictCascade();
}
