package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.StrategyOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.mapper.StrategyOrderInfoMapper;
import com.travel.web.admin.service.StrategyOrderInfoService;
import com.travel.web.admin.vo.order.StrategyOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【strategy_order_info(攻略订单信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class StrategyOrderInfoServiceImpl extends ServiceImpl<StrategyOrderInfoMapper, StrategyOrderInfo>
    implements StrategyOrderInfoService {

    @Autowired
    private StrategyOrderInfoMapper strategyOrderInfoMapper;

    @Override
    public IPage<StrategyOrderVo> getStrategyOrderInfoList(IPage<StrategyOrderVo> page, OrderDTO dto) {
        return strategyOrderInfoMapper.getStrategyOrderInfoList(page,dto);
    }
}




