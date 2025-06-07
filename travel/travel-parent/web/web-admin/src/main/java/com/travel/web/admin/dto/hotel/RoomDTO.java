package com.travel.web.admin.dto.hotel;

import com.travel.web.admin.dto.BasePageDTO;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-01 15:08
 */
@Data
public class RoomDTO extends BasePageDTO {
    private String roomName;
    private String hotelName;
    private String province;
    private String city;
    private String district;
}
