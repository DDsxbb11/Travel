package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.FacilityInfo;
import com.travel.web.admin.dto.facility.FacilityDTO;

import java.util.List;

/**
* @author 15101
* @description 针对表【facility_info(设施信息)】的数据库操作Service
* @createDate 2025-03-15 22:16:56
*/
public interface FacilityInfoService extends IService<FacilityInfo> {

    IPage<FacilityInfo> getFacilityInfoPage(IPage<FacilityInfo> page, FacilityDTO dto);

    List<FacilityInfo> getFacilityByType(FacilityDTO dto);
}
