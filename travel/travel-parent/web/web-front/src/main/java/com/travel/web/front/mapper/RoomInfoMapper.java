package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.model.pojo.RoomInfo;
import com.travel.web.front.vo.hotel.RoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15101
* @description 针对表【room_info(房间信息)】的数据库操作Mapper
* @createDate 2025-03-15 22:16:56
* @Entity com.travel.model.pojo.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    List<RoomVo> selectRoomList(@Param("hotelId") Long hotelId);
}




