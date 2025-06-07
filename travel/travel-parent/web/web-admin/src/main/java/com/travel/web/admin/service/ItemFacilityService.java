package com.travel.web.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.ItemFacility;

import java.util.List;

/**
* @author 15101
* @description 针对表【item_facility(实体设施对照表)】的数据库操作Service
* @createDate 2025-03-15 22:16:56
*/
public interface ItemFacilityService extends IService<ItemFacility> {

    void saveOrUpdateFacility(Long infoId, Long voId, List<Long> facilityList, String type,String facilityType);

    List<Long> getFacilityIdListByTypeAndItemId(Long id, String type,String facilityType);
}
