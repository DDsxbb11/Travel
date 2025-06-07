package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.enums.OrderStateEnum;
import com.travel.model.pojo.StrategyOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.mapper.StrategyOrderInfoMapper;
import com.travel.web.front.service.StrategyOrderInfoService;
import com.travel.web.front.vo.strategy.StrategyOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【strategy_order_info(攻略订单信息)】的数据库操作Service实现
* @createDate 2025-04-06 10:07:36
*/
@Service
public class StrategyOrderInfoServiceImpl extends ServiceImpl<StrategyOrderInfoMapper, StrategyOrderInfo>
    implements StrategyOrderInfoService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StrategyOrderInfoMapper strategyOrderInfoMapper;

    @Override
    public void createOrder(String token, StrategyOrderInfo info) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        info.setUserId(jwtUtil.getUserId(token));
        boolean save = this.save(info);
        if (!save){
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public IPage<StrategyOrderVo> pageQuery(String token, IPage<StrategyOrderVo> page) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        return strategyOrderInfoMapper.pageQuery(page,jwtUtil.getUserId(token));
    }

    @Override
    public void deleteOrder(String token, Long id) {
        boolean remove = this.removeById(id);
        if (!remove){
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public void cancelStrategyOrder(String token, OrderCancelDTO dto) {
        if(dto==null||dto.getId()==null || dto.getReason() ==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<StrategyOrderInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StrategyOrderInfo::getId, dto.getId())
                .eq(StrategyOrderInfo::getUserId, jwtUtil.getUserId(token))
                .set(StrategyOrderInfo::getState, OrderStateEnum.PROCESSING.getCode())
                .set(StrategyOrderInfo::getNote, dto.getReason());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new GlobalException(ResultCodeEnum.ORDER_CANCEL_ERROR);
        }
    }
}




