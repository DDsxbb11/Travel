package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.web.front.service.HotelOrderInfoService;
import com.travel.web.front.vo.hotel.HotelOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO:酒店订单
 * @date: 2025-05-01 23:56
 */
@RestController
@RequestMapping("system/order/hotel")
public class HotelOrderController {

    @Autowired
    private HotelOrderInfoService hotelOrderInfoService;

    @PostMapping
    @Operation(summary = "创建订单")
    @LoginCheck
    public Result createOrder(@RequestHeader("Token") String token,
                              @RequestBody HotelOrderInfo info) {
        hotelOrderInfoService.createOrder(token, info);
        return Result.ok();
    }

    @GetMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询订单")
    @LoginCheck
    public Result<IPage<HotelOrderVo>> pageQuery(@RequestHeader("Token") String token,
                                                 @PathVariable("pageNum") Integer pageNum,
                                                 @PathVariable("pageSize") Integer pageSize) {
        IPage<HotelOrderVo> page = new Page<>(pageNum, pageSize);
        IPage<HotelOrderVo> result = hotelOrderInfoService.pageQuery(token, page);
        return Result.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单")
    @LoginCheck
    public Result deleteHotelOrder(@RequestHeader("Token") String token, @PathVariable("id") Long id) {
        hotelOrderInfoService.deleteHotelOrder(token, id);
        return Result.ok();
    }
}
