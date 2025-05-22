package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.front.vo.hotel.HotelOrderVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Mapper
* @createDate 2025-04-06 10:07:36
* @Entity com.travel.model.pojo.HotelOrderInfo
*/
public interface HotelOrderInfoMapper extends BaseMapper<HotelOrderInfo> {

    IPage<HotelOrderVo> pageQuery(IPage<HotelOrderVo> page,@Param("userId") Long userId);
}




