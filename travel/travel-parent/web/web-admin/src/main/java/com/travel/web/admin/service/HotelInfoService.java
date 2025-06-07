package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.HotelInfo;
import com.travel.web.admin.dto.hotel.HotelDTO;
import com.travel.web.admin.vo.hotel.HotelVo;

/**
* @author 15101
* @description 针对表【hotel_info(酒店信息)】的数据库操作Service
* @createDate 2025-03-15 22:16:56
*/
public interface HotelInfoService extends IService<HotelInfo> {

    IPage<HotelInfo> getHotelInfoPage(IPage<HotelInfo> page, HotelDTO dto);

    HotelVo getHotelInfoById(Long id);

    void saveOrUpdateHotelInfo(HotelVo hotelVo);
}
