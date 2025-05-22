package com.travel.web.admin.vo;

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

    private String country;

    private String province;

    private String imgUrl;
}
