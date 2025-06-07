package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.HotelOrderInfo;
import com.travel.model.pojo.StrategyOrderInfo;
import com.travel.web.front.dto.order.OrderCancelDTO;
import com.travel.web.front.service.HotelOrderInfoService;
import com.travel.web.front.service.StrategyOrderInfoService;
import com.travel.web.front.vo.attraction.AttractionVo;
import com.travel.web.front.vo.strategy.StrategyOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

/**
 * @author: Amoretto
 * @description: TODO:攻略订单
 * @date: 2025-05-01 23:56
 */
@RestController
@RequestMapping("system/order/strategy")
public class StrategyOrderController {

    @Autowired
    private StrategyOrderInfoService strategyOrderInfoService;

    @PostMapping
    @Operation(summary = "创建订单")
    @LoginCheck
    public Result createOrder(@RequestHeader("Token") String token,
                              @RequestBody StrategyOrderInfo info) {
        strategyOrderInfoService.createOrder(token, info);
        return Result.ok();
    }

    @GetMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询订单")
    @LoginCheck
    public Result<IPage<StrategyOrderVo>> pageQuery(@RequestHeader("Token") String token,
                                                    @PathVariable("pageNum") Integer pageNum,
                                                    @PathVariable("pageSize") Integer pageSize) {
        IPage<StrategyOrderVo> page = new Page<>(pageNum, pageSize);
        IPage<StrategyOrderVo> result = strategyOrderInfoService.pageQuery(token, page);
        return Result.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单")
    @LoginCheck
    public Result deleteOrder(@RequestHeader("Token") String token,
                              @PathVariable("id") Long id) {
        strategyOrderInfoService.deleteOrder(token, id);
        return Result.ok();
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消订单")
    @LoginCheck
    public Result cancelStrategyOrder(@RequestHeader("Token") String token,
                                      @RequestBody OrderCancelDTO dto) {
        strategyOrderInfoService.cancelStrategyOrder(token, dto);
        return Result.ok();
    }
}
