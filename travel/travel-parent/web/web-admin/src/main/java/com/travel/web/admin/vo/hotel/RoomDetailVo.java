package com.travel.web.admin.vo.hotel;

import com.travel.model.pojo.RecordInfo;
import com.travel.model.pojo.RoomInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-01 17:59
 */
@Data
public class RoomDetailVo extends RoomInfo {
    private List<Long> roomFacilityList;
    private List<Long> roomBathroomFacilityList;
    private List<Long> roomFoodFacilityList;
}
