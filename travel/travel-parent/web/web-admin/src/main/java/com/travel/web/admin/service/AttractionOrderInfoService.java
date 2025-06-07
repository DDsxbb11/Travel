package com.travel.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.vo.order.AttractionOrderVo;

/**
* @author 15101
* @description 针对表【attraction_order_info(景点订单信息)】的数据库操作Service
* @createDate 2025-04-06 10:07:36
*/
public interface AttractionOrderInfoService extends IService<AttractionOrderInfo> {

    IPage<AttractionOrderVo> getAttractionOrderInfoList(IPage<AttractionOrderVo> page, OrderDTO dto);
}
