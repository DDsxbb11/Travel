package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.vo.order.AttractionOrderVo;
import com.travel.web.admin.vo.order.HotelOrderVo;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Service
* @createDate 2025-04-06 10:07:36
*/
public interface HotelOrderInfoService extends IService<HotelOrderInfo> {

    IPage<HotelOrderVo> getHotelOrderInfoList(IPage<HotelOrderVo> page, OrderDTO dto);
}
