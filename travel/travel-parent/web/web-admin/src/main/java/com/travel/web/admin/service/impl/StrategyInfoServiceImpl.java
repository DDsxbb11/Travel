package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.enums.RouteTypeEnum;
import com.travel.model.pojo.RouteInfo;
import com.travel.model.pojo.StrategyFeeInfo;
import com.travel.model.pojo.StrategyInfo;
import com.travel.model.pojo.StrategyLable;
import com.travel.web.admin.dto.strategy.StrategyDTO;
import com.travel.web.admin.mapper.StrategyInfoMapper;
import com.travel.web.admin.service.*;
import com.travel.web.admin.vo.strategy.StrategyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 15101
* @description 针对表【strategy_info(攻略信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:37
*/
@Service
public class StrategyInfoServiceImpl extends ServiceImpl<StrategyInfoMapper, StrategyInfo>
    implements StrategyInfoService {

    @Autowired
    private StrategyFeeInfoService strategyFeeInfoService;
    @Autowired
    private RouteInfoService routeInfoService;
    @Autowired
    private StrategyLableService strategyLableService;

    @Override
    public IPage<StrategyInfo> getStrategyInfoByPage(IPage<StrategyInfo> page, StrategyDTO dto) {
        LambdaQueryWrapper<StrategyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dto.getProvince()!=null && StringUtils.hasText(dto.getProvince()), StrategyInfo::getProvince, dto.getProvince()).
                like(dto.getName() != null && StringUtils.hasText(dto.getName()), StrategyInfo::getName, dto.getName());
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateStrategy(StrategyVo vo) {
        this.saveOrUpdate(vo);
        //报错费用说明
        vo.getFeeList().forEach(item->{
            item.setStrategyId(vo.getId());
            strategyFeeInfoService.saveOrUpdate(item);
        });
        //记录
        vo.getRouteList().forEach(item->{
            item.setItemId(vo.getId());
            item.setType(RouteTypeEnum.STRATEGY.getCode());
            routeInfoService.saveOrUpdate(item);
        });
        //添加标签
        List<Long> lableIds = strategyLableService.
                list(new LambdaQueryWrapper<StrategyLable>().
                        eq(StrategyLable::getStrategyId, vo.getId())).
                stream().
                map(StrategyLable::getLableId).toList();
        StrategyLable strategyLable = new StrategyLable();
        strategyLable.setStrategyId(vo.getId());
        vo.getLabelList().forEach(item->{
            if(!lableIds.contains(item)){
                //新增
                strategyLable.setLableId(item);
                strategyLableService.save(strategyLable);
            }
        });
        lableIds.forEach(item->{
            //删除
            if(!vo.getLabelList().contains(item)){
                strategyLableService.remove(new LambdaQueryWrapper<StrategyLable>().
                        eq(StrategyLable::getLableId, item).
                        eq(StrategyLable::getStrategyId, vo.getId()));
            }
        });
    }

    @Override
    public StrategyVo getStrategyById(Long id) {
        StrategyVo vo = new StrategyVo();
        StrategyInfo info = this.getById(id);
        BeanUtils.copyProperties(info,vo);
        //查费用说明
        vo.setFeeList(strategyFeeInfoService.list(
                new LambdaQueryWrapper<StrategyFeeInfo>().
                        eq(StrategyFeeInfo::getStrategyId, id).
                        orderByDesc(StrategyFeeInfo::getSort)));
        //查路线
        vo.setRouteList(routeInfoService.list(new LambdaQueryWrapper<RouteInfo>().
                eq(RouteInfo::getItemId, id).
                eq(RouteInfo::getType, RouteTypeEnum.STRATEGY.getCode()).
                orderByDesc(RouteInfo::getSort)));
        //查标签
        vo.setLabelList(strategyLableService.
                list(new LambdaQueryWrapper<StrategyLable>().
                        eq(StrategyLable::getStrategyId, id)).
                stream().
                map(StrategyLable::getLableId).toList());
        return vo;
    }
}




