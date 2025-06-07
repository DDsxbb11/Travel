package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.admin.dto.strategy.StrategyDTO;
import com.travel.web.admin.service.StrategyInfoService;
import com.travel.web.admin.vo.strategy.StrategyVo;
import io.swagger.v3.oas.annotations.Operation;
import jdk.jfr.consumer.RecordedMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 18:26
 */
@RestController
@RequestMapping("/strategy")
@Slf4j
public class StrategyController {

    @Autowired
    private StrategyInfoService strategyInfoService;

    @PostMapping("/getStrategyByPage")
    public Result<IPage<StrategyInfo>> getStrategyInfoByPage(@RequestBody StrategyDTO dto){
        IPage<StrategyInfo> page=new Page<>(dto.getPageNum(),dto.getPageSize());
        return Result.ok(strategyInfoService.getStrategyInfoByPage(page,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除攻略")
    public Result deleteStrategyById(@PathVariable("id") Long id){
        strategyInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdateStrategy(@RequestBody StrategyVo vo){
        strategyInfoService.saveOrUpdateStrategy(vo);
        return Result.ok();
    }

    @GetMapping("/detail/{id}")
    public Result<StrategyVo> getStrategyById(@PathVariable("id") Long id){
        return Result.ok(strategyInfoService.getStrategyById(id));
    }
}
