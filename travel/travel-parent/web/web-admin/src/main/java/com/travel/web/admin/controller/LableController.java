package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.LableInfo;
import com.travel.web.admin.dto.strategy.LableDTO;
import com.travel.web.admin.dto.theme.ThemeDTO;
import com.travel.web.admin.service.AttractionThemeService;
import com.travel.web.admin.service.LableInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: JYX
 * @description: TODO: 旅游景点
 * @date: 2025-03-14 19:57
 */

@RestController
@RequestMapping("/lable")
@Slf4j
@Tag(name = "攻略标签")
public class LableController {

    @Autowired
    private LableInfoService lableInfoService;

    @PostMapping("")
    @Operation(summary = "分页获取酒店信息")
    public Result<IPage<LableInfo>> getLableInfoPage(@RequestBody LableDTO dto){
        IPage<LableInfo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return Result.ok(lableInfoService.getLableInfoPage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除酒店信息")
    public Result deleteLableInfoById(@PathVariable("id") Long id){
        lableInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新酒店信息")
    public Result saveOrUpdateLableInfo(@RequestBody LableInfo dto){
        lableInfoService.saveOrUpdate(dto);
        return Result.ok();
    }

}
