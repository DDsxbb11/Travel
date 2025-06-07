package com.travel.web.front.dto.order;

import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO: 取消订单
 * @date: 2025-05-07 23:06
 */
@Data
public class OrderCancelDTO {
    private Long id;
    private String reason;
}
