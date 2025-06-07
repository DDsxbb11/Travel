package com.travel.web.admin.vo.strategy;

import com.travel.model.pojo.RouteInfo;
import com.travel.model.pojo.StrategyFeeInfo;
import com.travel.model.pojo.StrategyInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-05 13:28
 */
@Data
public class StrategyVo extends StrategyInfo {
    private List<RouteInfo> routeList;
    private List<StrategyFeeInfo> feeList;
    private List<Long> labelList;
}
