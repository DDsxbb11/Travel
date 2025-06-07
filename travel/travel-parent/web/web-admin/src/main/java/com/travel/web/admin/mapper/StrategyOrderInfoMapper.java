package com.travel.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.StrategyOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.vo.order.StrategyOrderVo;

/**
* @author 15101
* @description 针对表【strategy_order_info(攻略订单信息)】的数据库操作Mapper
* @createDate 2025-05-27 18:34:04
* @Entity com.travel.model.pojo.StrategyOrderInfo
*/
public interface StrategyOrderInfoMapper extends BaseMapper<StrategyOrderInfo> {

    IPage<StrategyOrderVo> getStrategyOrderInfoList(IPage<StrategyOrderVo> page, OrderDTO dto);
}




