package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.front.dto.strategy.StrategyDTO;
import com.travel.web.front.vo.strategy.StrategyDetailVo;

/**
* @author 15101
* @description 针对表【strategy_info(攻略信息)】的数据库操作Service
* @createDate 2025-03-16 17:11:18
*/
public interface StrategyInfoService extends IService<StrategyInfo> {

    /**
     * 分页查询攻略
     */
    IPage<StrategyInfo> getPage(IPage<StrategyInfo> page, StrategyDTO strategyDTO);

    /**
     * 根据id查询攻略详情
     */
    StrategyDetailVo getDetailById(Long id,String token);
}
