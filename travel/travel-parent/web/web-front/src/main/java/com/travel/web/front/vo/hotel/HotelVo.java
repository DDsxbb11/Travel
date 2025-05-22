package com.travel.web.front.vo.hotel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: JYX
 * @description: TODO:
 * @date: 2025-03-15 23:40
 */
@Data
public class HotelVo {

    private Long id;

    private String name;

    private String address;

    private String imgUrl;

    private String level;

    private BigDecimal rating;

    private String minPrice;
}
