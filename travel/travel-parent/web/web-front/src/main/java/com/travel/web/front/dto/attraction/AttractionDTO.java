package com.travel.web.front.dto.attraction;

import lombok.Data;

/**
 * @author: JYX
 * @description: TODO:
 * @date: 2025-03-14 20:06
 */

@Data
public class AttractionDTO {

    private String keyword;

    private String theme;

    private String province;

    private Integer isFee;
}
