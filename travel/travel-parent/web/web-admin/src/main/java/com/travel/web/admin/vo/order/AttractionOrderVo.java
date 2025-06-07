package com.travel.web.admin.vo.order;

import com.travel.model.pojo.AttractionOrderInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 13:15
 */
@Data
public class AttractionOrderVo extends AttractionOrderInfo {
    private String username;
    private String phone;
    private String attractionName;
    private String address;
}
