package com.travel.web.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.vo.attraction.AttractionOrderVo;

/**
* @author 15101
* @description 针对表【attraction_order_info(景点订单信息)】的数据库操作Service
* @createDate 2025-04-06 10:07:36
*/
public interface AttractionOrderInfoService extends IService<AttractionOrderInfo> {

    void createAttractionOrder(String token, AttractionOrderInfo attractionOrderInfo);

    IPage<AttractionOrderVo> pageQuery(String token, IPage<AttractionOrderVo> page);

    void deleteAttractionOrder(String token, Long id);

    void cancelAttractionOrder(String token, OrderCancelDTO dto);
}
