package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.HotelInfo;
import com.travel.web.front.dto.hotel.HotelDTO;
import com.travel.web.front.vo.hotel.HotelVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【hotel_info(酒店信息)】的数据库操作Mapper
* @createDate 2025-03-15 22:16:56
* @Entity com.travel.model.pojo.HotelInfo
*/
public interface HotelInfoMapper extends BaseMapper<HotelInfo> {

    IPage<HotelVo> getPage(IPage<HotelVo> page, @Param("queryVo") HotelDTO queryVo);
}




