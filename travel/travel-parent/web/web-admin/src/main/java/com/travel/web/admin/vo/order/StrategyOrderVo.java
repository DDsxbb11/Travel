package com.travel.web.admin.vo.order;

import com.travel.model.pojo.StrategyOrderInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 18:05
 */
@Data
public class StrategyOrderVo extends StrategyOrderInfo {

    private String strategyName;

    private String address;

    private String username;

    private String phone;
}
