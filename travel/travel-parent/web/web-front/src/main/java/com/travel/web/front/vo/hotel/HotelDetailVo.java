package com.travel.web.front.vo.hotel;

import com.travel.model.pojo.FacilityInfo;
import com.travel.model.pojo.HotelInfo;
import com.travel.model.pojo.PolicyInfo;
import com.travel.model.pojo.RoomInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 酒店详情
 * @date: 2025-03-16 11:53
 */

@Data
public class HotelDetailVo  extends HotelInfo{

    @Schema(description = "房间列表")
    private List<RoomVo> roomList;

    @Schema(description = "政策列表")
    private List<PolicyInfo> policyList;

    @Schema(description = "酒店设施")
    private List<FacilityInfo> facilityList;

    @Schema(description = "收藏ID")
    private boolean collect;
}
