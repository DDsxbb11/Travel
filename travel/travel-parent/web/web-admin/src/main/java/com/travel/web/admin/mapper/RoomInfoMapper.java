package com.travel.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.RoomInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.web.admin.dto.hotel.RoomDTO;
import com.travel.web.admin.vo.hotel.RoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15101
* @description 针对表【room_info(房间信息)】的数据库操作Mapper
* @createDate 2025-05-27 18:34:04
* @Entity com.travel.model.pojo.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomVo> getRoomInfoPage(IPage<RoomVo> page, @Param("dto") RoomDTO dto);

}




