package com.travel.web.front.vo.hotel;

import com.travel.model.pojo.FacilityInfo;
import com.travel.model.pojo.RoomInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: JYX
 * @description: TODO: 房间详情
 * @date: 2025-03-16 11:54
 */
@Data
public class RoomVo extends RoomInfo {
    @Schema(description = "房间设施")
    private List<FacilityInfo> roomFacilityList;

    @Schema(description = "浴室设施")
    private List<FacilityInfo> bathroomFacilityList;

    @Schema(description = "早餐类型")
    private List<FacilityInfo> foodFacilityList;
}
