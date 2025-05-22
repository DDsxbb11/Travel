package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.model.enums.FeeEnum;
import com.travel.model.enums.ItemEnum;
import com.travel.model.enums.RouteTypeEnum;
import com.travel.model.pojo.*;
import com.travel.web.front.dto.strategy.StrategyDTO;
import com.travel.web.front.mapper.*;
import com.travel.web.front.service.CollectionsInfoService;
import com.travel.web.front.service.StrategyInfoService;
import com.travel.web.front.vo.strategy.StrategyDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
* @author 15101
* @description 针对表【strategy_info(攻略信息)】的数据库操作Service实现
* @createDate 2025-03-16 17:11:18
*/
@Service
public class StrategyInfoServiceImpl extends ServiceImpl<StrategyInfoMapper, StrategyInfo>
    implements StrategyInfoService {

    @Autowired
    private StrategyInfoMapper strategyInfoMapper;
    @Autowired
    private StrategyLableMapper strategyLableMapper;
    @Autowired
    private RouteInfoMapper routeInfoMapper;
    @Autowired
    private StrategyFeeInfoMapper  strategyFeeInfoMapper;
    @Autowired
    private CollectionsInfoService collectionsInfoService;

    /**
     * 分页获取攻略
     */
    @Override
    public IPage<StrategyInfo> getPage(IPage<StrategyInfo> page, StrategyDTO strategyDTO) {
        LambdaQueryWrapper<StrategyInfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(StrategyInfo::getProvince, strategyDTO.getProvince())
//                .gt(Objects.equals(strategyDTO.getFeeType(), FeeEnum.PAID.getCode()),StrategyInfo::getPrice,0)
//                .eq(Objects.equals(strategyDTO.getFeeType(), FeeEnum.UNPAID.getCode()),StrategyInfo::getPrice,0)
//                .like(StringUtils.hasText(strategyDTO.getKeyword()),StrategyInfo::getName,strategyDTO.getKeyword());
        return strategyInfoMapper.selectPage(page, queryWrapper);
    }


    /**
     * 根据id查询攻略详情
     */
    @Override
    public StrategyDetailVo getDetailById(Long id,String token) {
        StrategyDetailVo vo = new StrategyDetailVo();
        StrategyInfo strategyInfo = strategyInfoMapper.selectById(id);
        if (strategyInfo== null) {
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
        BeanUtils.copyProperties(strategyInfo,vo);
        //获取攻略的标签
        List<String> lableList = strategyLableMapper.getLableList(id);
        vo.setLableList(lableList);

        //路线列表
        LambdaQueryWrapper<RouteInfo> routeInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        routeInfoLambdaQueryWrapper.eq(RouteInfo::getItemId,id)
                .eq(RouteInfo::getType, RouteTypeEnum.STRATEGY.getCode())
                .orderByDesc(RouteInfo::getSort);
        List<RouteInfo> routeList = routeInfoMapper.selectList(routeInfoLambdaQueryWrapper);
        vo.setRouteList(routeList);
        //费用说明列表
        LambdaQueryWrapper<StrategyFeeInfo> strategyFeeInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        strategyFeeInfoLambdaQueryWrapper.eq(StrategyFeeInfo::getStrategyId,id)
                .orderByDesc(StrategyFeeInfo::getSort);
        List<StrategyFeeInfo> strategyFeeInfoList = strategyFeeInfoMapper.selectList(strategyFeeInfoLambdaQueryWrapper);
        vo.setFeeInfoList(strategyFeeInfoList);
        //是否收藏
        if (StringUtils.hasText(token)) {
            vo.setIsCollect(collectionsInfoService.isCollect(token, id, ItemEnum.Strategy.getLabel()));
        }
        return vo;
    }
}




