package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.enums.FacilityTypeEnum;
import com.travel.model.pojo.RoomInfo;
import com.travel.web.admin.dto.hotel.RoomDTO;
import com.travel.web.admin.mapper.RoomInfoMapper;
import com.travel.web.admin.service.ItemFacilityService;
import com.travel.web.admin.service.RoomInfoService;
import com.travel.web.admin.vo.hotel.RoomDetailVo;
import com.travel.web.admin.vo.hotel.RoomVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 15101
* @description 针对表【room_info(房间信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
    implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ItemFacilityService itemFacilityService;

    @Override
    public IPage<RoomVo> getRoomInfoPage(IPage<RoomVo> page, RoomDTO dto) {
        return roomInfoMapper.getRoomInfoPage(page,dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateRoomInfo(RoomDetailVo vo) {
        RoomInfo info = new RoomInfo();
        BeanUtils.copyProperties(vo,info);
        this.saveOrUpdate(info);
        itemFacilityService.saveOrUpdateFacility(info.getId(),vo.getId(),vo.getRoomFacilityList(),"room",FacilityTypeEnum.ROOM_FACILITY.getType());
        itemFacilityService.saveOrUpdateFacility(info.getId(),vo.getId(),vo.getRoomBathroomFacilityList(),"room",FacilityTypeEnum.ROOM_BATHROOM_FACILITY.getType());
        itemFacilityService.saveOrUpdateFacility(info.getId(),vo.getId(),vo.getRoomFoodFacilityList(),"room",FacilityTypeEnum.ROOM_FOOD_FACILITY.getType());
    }

    @Override
    public RoomDetailVo getRoomInfoById(Long id) {
        RoomDetailVo vo = new RoomDetailVo();
        RoomInfo info = this.getById(id);
        BeanUtils.copyProperties(info,vo);
        List <Long> roomFactilityList=itemFacilityService.getFacilityIdListByTypeAndItemId(id,"room" ,FacilityTypeEnum.ROOM_FACILITY.getType());
        List <Long> roomBathroomFactilityList=itemFacilityService.getFacilityIdListByTypeAndItemId(id, "room",FacilityTypeEnum.ROOM_BATHROOM_FACILITY.getType());
        List <Long> roomFoodFactilityList=itemFacilityService.getFacilityIdListByTypeAndItemId(id,"room", FacilityTypeEnum.ROOM_FOOD_FACILITY.getType());
        vo.setRoomFacilityList(roomFactilityList);
        vo.setRoomBathroomFacilityList(roomBathroomFactilityList);
        vo.setRoomFoodFacilityList(roomFoodFactilityList);
        return vo;
    }
}




