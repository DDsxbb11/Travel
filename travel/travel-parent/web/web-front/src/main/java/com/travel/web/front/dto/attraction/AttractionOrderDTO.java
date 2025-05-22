package com.travel.web.front.dto.attraction;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Amoretto
 * @description: TODO: 景点订单信息
 * @date: 2025-04-11 14:16
 */

@Data
public class AttractionOrderDTO {

    private Integer adultNumber;

    private Integer childrenNumber;

    private BigDecimal totalPrice;

    private String date;

    private Integer type;
}
