package com.travel.model.pojo.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 记录类型
 * @date: 2025-03-17 0:20
 */
@Getter
public enum RouteTypeEnum {
    STRATEGY("strategy", "攻略"),
    RECORD("record", "日记");

    private String code;
    private String desc;

    RouteTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
