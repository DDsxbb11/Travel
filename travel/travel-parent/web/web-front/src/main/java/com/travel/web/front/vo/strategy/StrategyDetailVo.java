package com.travel.web.front.vo.strategy;

import cn.hutool.core.net.Ipv4Util;
import com.travel.model.pojo.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 攻略详情
 * @date: 2025-03-16 17:43
 */

@Data
public class StrategyDetailVo extends StrategyInfo {

    @Schema(description = "标签列表")
    private List<String> lableList;

    @Schema(description = "路线列表")
    private List<RouteInfo> routeList;

    @Schema(description = "费用说明")
    private List<StrategyFeeInfo> feeInfoList;

    @Schema(description = "是否收藏")
    private Boolean isCollect;
}
