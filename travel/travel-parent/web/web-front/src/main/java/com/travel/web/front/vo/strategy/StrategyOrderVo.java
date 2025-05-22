package com.travel.web.front.vo.strategy;

import com.travel.model.pojo.StrategyOrderInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO: 旅游攻略订单
 * @date: 2025-04-18 0:29
 */

@Data
public class StrategyOrderVo extends StrategyOrderInfo {

    @Schema(description = "攻略id")
    private Long strategyId;

    @Schema(description = "攻略名称")
    private String name;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "出发地址")
    private String address;

}
