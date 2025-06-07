package com.travel.model.pojo.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 设施类型
 * @date: 2025-03-16 12:30
 */
@Getter
public enum FacilityTypeEnum {
    HOTEL_FACILITY("hotel_facility", "酒店设施"),
    ROOM_FACILITY("room_facility","房间设施"),
    ROOM_BATHROOM_FACILITY("bathroom_facility","房间浴室设施"),
    ROOM_FOOD_FACILITY("food_facility","房间浴室设施");

    private String type;
    private String desc;

    FacilityTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
