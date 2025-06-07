package com.travel.model.pojo.enums;

import lombok.Getter;

/**
 * @author: Amoretto
 * @description: TODO: 订单状态
 * @date: 2025-05-07 23:16
 */

@Getter
public enum OrderStateEnum {

    PAID(0,"已支付"),
    CANCEL(1,"已退款"),
    FINISHED(2,"已使用"),
    REFUND(3,"已过期"),
    PROCESSING(4,"处理中");

    private Integer code;
    private String desc;
    OrderStateEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
