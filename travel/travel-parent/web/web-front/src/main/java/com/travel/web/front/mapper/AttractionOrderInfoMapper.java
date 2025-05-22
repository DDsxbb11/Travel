package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.front.vo.attraction.AttractionOrderVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【attraction_order_info(景点订单信息)】的数据库操作Mapper
* @createDate 2025-04-06 10:07:35
* @Entity com.travel.model.pojo.AttractionOrderInfo
*/
public interface AttractionOrderInfoMapper extends BaseMapper<AttractionOrderInfo> {

    IPage<AttractionOrderVo> pageQuery(IPage<AttractionOrderVo> page, @Param("userId") Long userId);
}




