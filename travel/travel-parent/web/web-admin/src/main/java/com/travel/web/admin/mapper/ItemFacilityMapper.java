package com.travel.web.admin.mapper;

import com.travel.model.pojo.ItemFacility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15101
* @description 针对表【item_facility(实体设施对照表)】的数据库操作Mapper
* @createDate 2025-05-27 18:34:04
* @Entity com.travel.model.pojo.ItemFacility
*/
public interface ItemFacilityMapper extends BaseMapper<ItemFacility> {

    List<Long> getFacilityIdListByTypeAndItemId(@Param("id") Long id,@Param("type") String type,@Param("facilityType") String facilityType);
}




