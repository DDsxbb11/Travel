package com.travel.model.pojo.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 实体枚举
 * @date: 2025-03-15 17:02
 */
@Getter
public enum ItemEnum {
    ATTRACTION("attraction", "景点"),
    HOTEL("hotel", "酒店"),
    Strategy("strategy", "攻略"),
    RECORD("record", "旅行日记");

    private String label;
    private String desc;

    private ItemEnum(String label, String desc) {
        this.label = label;
        this.desc = desc;
    }
}
