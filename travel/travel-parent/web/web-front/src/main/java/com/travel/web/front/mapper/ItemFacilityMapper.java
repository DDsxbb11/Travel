package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.model.pojo.FacilityInfo;
import com.travel.model.pojo.ItemFacility;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15101
* @description 针对表【item_facility(实体设施对照表)】的数据库操作Mapper
* @createDate 2025-03-15 22:16:56
* @Entity com.travel.model.pojo.ItemFacility
*/
public interface ItemFacilityMapper extends BaseMapper<ItemFacility> {

    List<FacilityInfo> getItemFacility(@Param("id") Long id, @Param("type") String type);
}




