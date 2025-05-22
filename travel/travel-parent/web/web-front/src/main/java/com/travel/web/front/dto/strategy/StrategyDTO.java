package com.travel.web.front.dto.strategy;

import lombok.Data;

/**
 * @author: JYX
 * @description: TODO: 攻略查询搜索条件
 * @date: 2025-03-16 17:21
 */

@Data
public class StrategyDTO {

    private String keyword;

    private String province;

    private Integer feeType;
}
