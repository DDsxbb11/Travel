package com.travel.web.front.dto.hotel;

import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 酒店查询
 * @date: 2025-03-15 22:50
 */

@Data
public class HotelDTO {

    private String keyword;

    private String province;

    private String city;

    private String district;

    private String inTime;

    private String outTime;

    private List<String> levelList;

    private String minPrice;

    private String maxPrice;
}
