package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.front.dto.strategy.StrategyDTO;
import com.travel.web.front.service.StrategyInfoService;
import com.travel.web.front.vo.strategy.StrategyDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: JYX
 * @description: TODO: 攻略
 * @date: 2025-03-16 17:14
 */

@RestController
@RequestMapping("/strategy")
@Slf4j
@Tag(name = "攻略模块")
public class StrategyController {

    @Autowired
    private StrategyInfoService strategyInfoService;

    @PostMapping("/{current}/{size}")
    @Operation(summary = "分页查询攻略")
    public Result<IPage<StrategyInfo>> getPage(@PathVariable("current") Integer current,
                                @PathVariable("size") Integer size,
                                @RequestBody(required = false) StrategyDTO strategyDTO) {
        IPage<StrategyInfo> page=new Page<>(current,size);
        IPage<StrategyInfo> result= strategyInfoService.getPage(page,strategyDTO);
        return Result.ok(result);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id查询攻略详情")
    public Result<StrategyDetailVo> getDetailById(@PathVariable("id") Long id,
                                                  @RequestHeader(name = "Token",required = false) String token) {
        StrategyDetailVo result= strategyInfoService.getDetailById(id,token);
        return Result.ok(result);
    }
}
