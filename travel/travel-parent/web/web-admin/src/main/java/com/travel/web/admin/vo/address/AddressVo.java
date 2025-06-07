package com.travel.web.admin.vo.address;

import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO: 地理信息
 * @date: 2025-04-06 10:15
 */
@Data
public class AddressVo {
    private Long id;
    private String name;
    private List<AddressVo> childrenList;
}
