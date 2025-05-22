package com.travel.web.front.vo.hotel;

import com.travel.model.pojo.HotelOrderInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO: 酒店订单
 * @date: 2025-04-18 0:14
 */

@Data
public class HotelOrderVo extends HotelOrderInfo {

    private Long hotelId;

    private String hotelName;

    private String roomName;

    private String province;

    private String city;

    private String district;

    private String address;

}
