package com.travel.web.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.model.pojo.StrategyLable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15101
* @description 针对表【strategy_lable(攻略标签)】的数据库操作Mapper
* @createDate 2025-03-16 17:11:18
* @Entity com.travel.model.pojo.StrategyLable
*/
public interface StrategyLableMapper extends BaseMapper<StrategyLable> {

    List<String> getLableList(@Param("strategyId") Long strategyId);
}




