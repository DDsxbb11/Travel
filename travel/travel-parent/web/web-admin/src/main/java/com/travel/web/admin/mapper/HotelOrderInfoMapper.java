package com.travel.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.HotelOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.vo.order.HotelOrderVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Mapper
* @createDate 2025-05-27 18:34:04
* @Entity com.travel.model.pojo.HotelOrderInfo
*/
public interface HotelOrderInfoMapper extends BaseMapper<HotelOrderInfo> {

    IPage<HotelOrderVo> getHotelOrderInfoList(IPage<HotelOrderVo> page, @Param("dto") OrderDTO dto);
}




