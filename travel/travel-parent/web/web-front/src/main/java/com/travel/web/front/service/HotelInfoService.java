package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.HotelInfo;
import com.travel.web.front.dto.hotel.HotelDTO;
import com.travel.web.front.vo.hotel.HotelDetailVo;
import com.travel.web.front.vo.hotel.HotelVo;

/**
* @author 15101
* @description 针对表【hotel_info(酒店信息)】的数据库操作Service
* @createDate 2025-03-15 22:16:56
*/
public interface HotelInfoService extends IService<HotelInfo> {

    /**
     * 分页查询酒店信息
     */
    IPage<HotelVo> getPage(IPage<HotelVo> page, HotelDTO hotelDTO);

    /**
     * 根据id查询酒店信息
     */
    HotelDetailVo getHotelById(Long id,String token);
}
