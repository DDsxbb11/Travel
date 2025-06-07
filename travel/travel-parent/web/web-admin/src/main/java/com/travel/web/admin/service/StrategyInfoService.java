package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.admin.dto.strategy.StrategyDTO;
import com.travel.web.admin.vo.strategy.StrategyVo;

/**
* @author 15101
* @description 针对表【strategy_info(攻略信息)】的数据库操作Service
* @createDate 2025-03-16 17:11:18
*/
public interface StrategyInfoService extends IService<StrategyInfo> {

    IPage<StrategyInfo> getStrategyInfoByPage(IPage<StrategyInfo> page, StrategyDTO dto);

    void saveOrUpdateStrategy(StrategyVo vo);

    StrategyVo getStrategyById(Long id);
}
