package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionOrderInfo;
import com.travel.web.front.service.AttractionOrderInfoService;
import com.travel.web.front.vo.attraction.AttractionOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO: 景点订单表
 * @date: 2025-04-11 13:50
 */

@RestController
@RequestMapping("/system/order/attraction")
@Slf4j
@Tag(name = "景点订单")
public class AttractionOrderController {

    @Autowired
    private AttractionOrderInfoService attractionOrderInfoService;

    @PostMapping
    @Operation(summary = "创建景点订单")
    @LoginCheck
    public Result createAttractionOrder(@RequestHeader("Token") String token,
                                        @RequestBody AttractionOrderInfo attractionOrderInfo){
        attractionOrderInfoService.createAttractionOrder(token,attractionOrderInfo);
        return Result.ok();
    }

    @GetMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询景点订单")
    @LoginCheck
    public Result<IPage<AttractionOrderVo>> pageQuery(@RequestHeader("Token") String token,
                                                      @PathVariable("pageNum") Integer pageNum,
                                                      @PathVariable("pageSize") Integer pageSize){
        IPage<AttractionOrderVo> page = new Page<>(pageNum,pageSize);
        IPage<AttractionOrderVo> result = attractionOrderInfoService.pageQuery(token,page);
        return Result.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除景点订单")
    @LoginCheck
    public Result deleteAttractionOrder(@RequestHeader("Token") String token,
                                        @PathVariable("id") Long id) {
        attractionOrderInfoService.deleteAttractionOrder(token, id);
        return Result.ok();
    }

}
