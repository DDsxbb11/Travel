package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.model.pojo.StrategyOrderInfo;
import com.travel.web.front.vo.strategy.StrategyOrderVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 15101
* @description 针对表【strategy_order_info(攻略订单信息)】的数据库操作Mapper
* @createDate 2025-04-06 10:07:36
* @Entity com.travel.model.pojo.StrategyOrderInfo
*/
public interface StrategyOrderInfoMapper extends BaseMapper<StrategyOrderInfo> {

    IPage<StrategyOrderVo> pageQuery(IPage<StrategyOrderVo> page, @Param("userId") Long userId);
}




