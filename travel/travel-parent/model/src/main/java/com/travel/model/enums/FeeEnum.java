package com.travel.model.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 是否支付
 * @date: 2025-03-15 17:29
 */

@Getter
public enum FeeEnum {
    ALL(0,"全部"),
    PAID(1,"收费"),
    UNPAID(2,"免费");

    private Integer code;
    private String desc;
    FeeEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
