package com.travel.web.admin.dto.order;

import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 13:06
 */
@Data
public class OrderDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String phone;
    private String itemName;
}
