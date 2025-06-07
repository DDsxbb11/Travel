package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.JwtUtil;
import com.travel.model.pojo.enums.OrderStateEnum;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.mapper.HotelOrderInfoMapper;
import com.travel.web.front.service.HotelOrderInfoService;
import com.travel.web.front.vo.hotel.HotelOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【hotel_order_info(房间订单信息)】的数据库操作Service实现
* @createDate 2025-04-06 10:07:36
*/
@Service
public class HotelOrderInfoServiceImpl extends ServiceImpl<HotelOrderInfoMapper, HotelOrderInfo>
    implements HotelOrderInfoService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HotelOrderInfoMapper hotelOrderInfoMapper;

    @Override
    public void createOrder(String token, HotelOrderInfo info) {
        if (jwtUtil.isExpiration(token)) {
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        info.setUserId(jwtUtil.getUserId(token));
        boolean save = this.save(info);
        if (!save) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public IPage<HotelOrderVo> pageQuery(String token, IPage<HotelOrderVo> page) {
        if (jwtUtil.isExpiration(token)){
            throw new GlobalException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        return hotelOrderInfoMapper.pageQuery(page, jwtUtil.getUserId(token));
    }

    @Override
    public void deleteHotelOrder(String token, Long id) {
        boolean remove = this.removeById(id);
        if (!remove) {
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public void cancelHotelOrder(String token, OrderCancelDTO dto) {
        if(dto==null||dto.getId()==null || dto.getReason() ==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        LambdaUpdateWrapper<HotelOrderInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HotelOrderInfo::getId, dto.getId())
                .eq(HotelOrderInfo::getUserId, jwtUtil.getUserId(token))
                .set(HotelOrderInfo::getState, OrderStateEnum.PROCESSING.getCode())
                .set(HotelOrderInfo::getNote, dto.getReason());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new GlobalException(ResultCodeEnum.ORDER_CANCEL_ERROR);
        }
    }
}




