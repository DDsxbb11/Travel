package com.travel.web.admin.vo.hotel;

import com.travel.model.pojo.RoomInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-01 15:17
 */
@Data
public class RoomVo extends RoomInfo {
    private String hotelName;
    private String province;
    private String city;
    private String district;
    private String address;
}
