package com.travel.web.front.vo.attraction;

import com.travel.model.pojo.AttractionOrderInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO: 景点订单
 * @date: 2025-05-03 21:55
 */
@Data
public class AttractionOrderVo extends AttractionOrderInfo {

    private String name;

    private String theme;

    private String province;

    private String city;

    private String address;
}
