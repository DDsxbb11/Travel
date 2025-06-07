package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.RoomInfo;
import com.travel.web.admin.dto.hotel.RoomDTO;
import com.travel.web.admin.vo.hotel.RoomDetailVo;
import com.travel.web.admin.vo.hotel.RoomVo;

/**
* @author 15101
* @description 针对表【room_info(房间信息)】的数据库操作Service
* @createDate 2025-03-15 22:16:56
*/
public interface RoomInfoService extends IService<RoomInfo> {

    IPage<RoomVo> getRoomInfoPage(IPage<RoomVo> page, RoomDTO dto);

    void saveOrUpdateRoomInfo(RoomDetailVo dto);

    RoomDetailVo getRoomInfoById(Long id);
}
