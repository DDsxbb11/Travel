package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.mapper.HotelOrderInfoMapper;
import com.travel.web.admin.service.HotelOrderInfoService;
import com.travel.web.admin.vo.order.AttractionOrderVo;
import com.travel.web.admin.vo.order.HotelOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class HotelOrderInfoServiceImpl extends ServiceImpl<HotelOrderInfoMapper, HotelOrderInfo>
    implements HotelOrderInfoService {

    @Autowired
    private HotelOrderInfoMapper hotelOrderInfoMapper;

    @Override
    public IPage<HotelOrderVo> getHotelOrderInfoList(IPage<HotelOrderVo> page, OrderDTO dto) {
        return hotelOrderInfoMapper.getHotelOrderInfoList(page,dto);
    }
}




