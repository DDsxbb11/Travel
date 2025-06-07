package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.enums.OrderStateEnum;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.mapper.AttractionOrderInfoMapper;
import com.travel.web.front.service.AttractionOrderInfoService;
import com.travel.web.front.vo.attraction.AttractionOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 15101
 * @description 针对表【attraction_order_info(景点订单信息)】的数据库操作Service实现
 * @createDate 2025-04-06 10:07:35
 */
@Service
public class AttractionOrderInfoServiceImpl extends ServiceImpl<AttractionOrderInfoMapper, AttractionOrderInfo>
        implements AttractionOrderInfoService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AttractionOrderInfoMapper attractionOrderInfoMapper;

    @Override
    public void createAttractionOrder(String token, AttractionOrderInfo attractionOrderInfo) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        attractionOrderInfo.setUserId(jwtUtil.getUserId(token));
        boolean save = this.save(attractionOrderInfo);
        if (!save) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public IPage<AttractionOrderVo> pageQuery(String token, IPage<AttractionOrderVo> page) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        return attractionOrderInfoMapper.pageQuery(page, jwtUtil.getUserId(token));
    }

    @Override
    public void deleteAttractionOrder(String token, Long id) {
        boolean remove = this.removeById(id);
        if (!remove) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public void cancelAttractionOrder(String token, OrderCancelDTO dto) {
        if(dto==null||dto.getId()==null || dto.getReason() ==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<AttractionOrderInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AttractionOrderInfo::getId, dto.getId())
                .eq(AttractionOrderInfo::getUserId, jwtUtil.getUserId(token))
                .set(AttractionOrderInfo::getState, OrderStateEnum.PROCESSING.getCode())
                .set(AttractionOrderInfo::getNote, dto.getReason());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new GlobalException(ResultCodeEnum.ORDER_CANCEL_ERROR);
        }
    }
}




