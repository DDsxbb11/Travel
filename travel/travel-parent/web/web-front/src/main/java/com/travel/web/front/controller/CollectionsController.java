package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.common.annotation.LoginCheck;
import com.travel.common.result.Result;
import com.travel.model.pojo.BrowsingHistoryInfo;
import com.travel.model.pojo.CollectionsInfo;
import com.travel.web.front.service.CollectionsInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Amoretto
 * @description:  我的收藏
 * @date: 2025-04-18 15:55
 */
@RestController
@RequestMapping("/system/collections")
@Slf4j
@Tag(name = "我的收藏")
public class CollectionsController {

    @Autowired
    private CollectionsInfoService collectionsInfoService;

    @GetMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询收藏历史")
    @LoginCheck
    public Result<IPage<CollectionsInfo>> getCollectionsPage(@RequestHeader("Token") String token,
                                                             @PathVariable("pageNum") Integer pageNum,
                                                             @PathVariable("pageSize") Integer pageSize) {
        IPage<CollectionsInfo> result = collectionsInfoService.getCollectionsPage(token,pageNum, pageSize);
        return Result.ok(result);
    }

    @PostMapping
    @Operation(summary = "添加收藏")
    @LoginCheck
    public Result<Long> addCollections(@RequestHeader("Token") String token,
                                         @RequestBody CollectionsInfo collectionsInfo) {
        Long id=collectionsInfoService.addCollections(token,collectionsInfo);
        return Result.ok(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "取消收藏")
    @LoginCheck
    public Result deleteCollections(@RequestHeader("Token") String token,
                                    @PathVariable("id") Long id) {
        collectionsInfoService.deleteCollections(token,id);
        return Result.ok();
    }
}
