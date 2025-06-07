package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.vo.hotel.HotelOrderVo;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Service
* @createDate 2025-04-06 10:07:36
*/
public interface HotelOrderInfoService extends IService<HotelOrderInfo> {

    void createOrder(String token, HotelOrderInfo info);

    IPage<HotelOrderVo> pageQuery(String token, IPage<HotelOrderVo> page);

    void deleteHotelOrder(String token, Long id);

    void cancelHotelOrder(String token, OrderCancelDTO dto);
}
