package com.travel.web.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.ProvinceInfo;
import com.travel.web.front.vo.location.OptionVo;
import com.travel.web.front.vo.province.ProvinceVo;

import java.util.List;

/**
* @author 15101
* @description 针对表【province_info】的数据库操作Service
* @createDate 2025-03-14 19:44:06
*/
public interface ProvinceInfoService extends IService<ProvinceInfo> {

    List<ProvinceVo> getProvinceList();

    /**
     * 获取地区信息
     */
    List<OptionVo> getLocationOptions();
}
