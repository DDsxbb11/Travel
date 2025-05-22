package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.BrowsingHistoryInfo;
import com.travel.web.front.service.BrowsingHistoryInfoService;
import com.travel.web.front.vo.attraction.AttractionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO: 浏览历史
 * @date: 2025-04-18 0:46
 */
@RestController
@RequestMapping("/system/history")
@Slf4j
@Tag(name = "浏览历史")
public class BrowsingHistoryController {

    @Autowired
    private BrowsingHistoryInfoService browsingHistoryInfoService;

    @GetMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询浏览历史")
    @LoginCheck
    public Result<IPage<BrowsingHistoryInfo>> getBrowsingHistory(@PathVariable("pageNum") Integer pageNum,
                                                                 @PathVariable("pageSize") Integer pageSize,
                                                                 @RequestHeader("Token") String token) {
        IPage<BrowsingHistoryInfo> result = browsingHistoryInfoService.getPage(pageNum, pageSize,token);
        return Result.ok(result);
    }

    @PostMapping
    @Operation(summary = "添加浏览历史")
    @LoginCheck
    public Result addBrowsingHistory(@RequestHeader("Token") String token,
                                     @RequestBody BrowsingHistoryInfo browsingHistoryInfo) {
        browsingHistoryInfoService.addBrowsingHistory(token,browsingHistoryInfo);
        return Result.ok();
    }
}
