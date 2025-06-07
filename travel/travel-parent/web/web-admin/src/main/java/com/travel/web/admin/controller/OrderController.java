package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.service.AttractionOrderInfoService;
import com.travel.web.admin.service.HotelOrderInfoService;
import com.travel.web.admin.service.StrategyOrderInfoService;
import com.travel.web.admin.vo.order.AttractionOrderVo;
import com.travel.web.admin.vo.order.HotelOrderVo;
import com.travel.web.admin.vo.order.StrategyOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO: 账单
 * @date: 2025-05-30 13:03
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private AttractionOrderInfoService attractionOrderInfoService;
    @Autowired
    private HotelOrderInfoService hotelOrderInfoService;
    @Autowired
    private StrategyOrderInfoService strategyOrderInfoService;

    @PostMapping("/attraction")
    @Operation(summary = "分页查询景点账单")
    public Result<IPage<AttractionOrderVo>> getAttractionOrderInfoList(@RequestBody OrderDTO dto){
        IPage<AttractionOrderVo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<AttractionOrderVo> result=attractionOrderInfoService.getAttractionOrderInfoList(page,dto);
        return Result.ok(result);
    }

    @DeleteMapping("/attraction/{id}")
    @Operation(summary = "分页查询景点账单")
    public Result deleteAttractionOrder(@PathVariable("id") Long id){
        attractionOrderInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/hotel")
    @Operation(summary = "分页查询酒店账单")
    public Result<IPage<HotelOrderVo>> getHotelOrderInfoList(@RequestBody OrderDTO dto){
        IPage<HotelOrderVo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<HotelOrderVo> result=hotelOrderInfoService.getHotelOrderInfoList(page,dto);
        return Result.ok(result);
    }

    @DeleteMapping("/hotel/{id}")
    @Operation(summary = "分页查询景点账单")
    public Result deleteHotelOrder(@PathVariable("id") Long id){
        hotelOrderInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/strategy")
    @Operation(summary = "分页查询酒店账单")
    public Result<IPage<StrategyOrderVo>> getStrategyOrderInfoList(@RequestBody OrderDTO dto){
        IPage<StrategyOrderVo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<StrategyOrderVo> result=strategyOrderInfoService.getStrategyOrderInfoList(page,dto);
        return Result.ok(result);
    }

    @DeleteMapping("/strategy/{id}")
    @Operation(summary = "分页查询景点账单")
    public Result deleteStrategyOrder(@PathVariable("id") Long id){
        strategyOrderInfoService.removeById(id);
        return Result.ok();
    }
}
