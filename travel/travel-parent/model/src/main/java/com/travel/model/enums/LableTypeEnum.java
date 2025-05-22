package com.travel.model.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 标签类型枚举
 * @date: 2025-03-16 18:07
 */

@Getter
public enum LableTypeEnum {
    HOTEL("hotel","酒店"),
    ROOM("room","房间");

    private String type;
    private String desc;

    LableTypeEnum(String type,String desc){
        this.type = type;
        this.desc = desc;
    }
}
