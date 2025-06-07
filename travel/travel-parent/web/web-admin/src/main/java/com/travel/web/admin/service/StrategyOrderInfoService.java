package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.StrategyOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.vo.order.StrategyOrderVo;

/**
* @author 15101
* @description 针对表【strategy_order_info(攻略订单信息)】的数据库操作Service
* @createDate 2025-04-06 10:07:36
*/
public interface StrategyOrderInfoService extends IService<StrategyOrderInfo> {

    IPage<StrategyOrderVo> getStrategyOrderInfoList(IPage<StrategyOrderVo> page, OrderDTO dto);
}
