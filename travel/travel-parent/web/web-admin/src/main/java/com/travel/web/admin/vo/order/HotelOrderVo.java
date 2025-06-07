package com.travel.web.admin.vo.order;

import com.travel.model.pojo.HotelOrderInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 14:39
 */
@Data
public class HotelOrderVo extends HotelOrderInfo {
    private String hotelName;
    private String roomName;
    private String username;
    private String phone;
    private String address;
}
