package com.travel.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.mapper.AttractionOrderInfoMapper;
import com.travel.web.admin.service.AttractionOrderInfoService;
import com.travel.web.admin.vo.order.AttractionOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 15101
* @description 针对表【attraction_order_info(景点订单信息)】的数据库操作Service实现
* @createDate 2025-04-14 19:25:36
*/
@Service
public class AttractionOrderInfoServiceImpl extends ServiceImpl<AttractionOrderInfoMapper, AttractionOrderInfo>
    implements AttractionOrderInfoService {

    @Autowired
    private AttractionOrderInfoMapper attractionOrderInfoMapper;

    @Override
    public IPage<AttractionOrderVo> getAttractionOrderInfoList(IPage<AttractionOrderVo> page, OrderDTO dto) {
        return attractionOrderInfoMapper.getOrderByPage(page, dto);
    }
}




