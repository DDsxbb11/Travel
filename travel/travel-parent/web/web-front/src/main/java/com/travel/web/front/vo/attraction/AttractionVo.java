package com.travel.web.front.vo.attraction;

import lombok.Data;

/**
 * @author: JYX
 * @description: TODO: 分页获取实体
 * @date: 2025-03-14 20:02
 */

@Data
public class AttractionVo {

    private Long id;

    private String name;

    private String theme;

    private String city;

    private String address;

    private Integer isFee;

    private String province;

    private String description;

    private String imgUrl;
}
