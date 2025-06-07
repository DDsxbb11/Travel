package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionTheme;
import com.travel.web.admin.dto.theme.ThemeDTO;
import com.travel.web.admin.service.AttractionThemeService;
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
@RequestMapping("/theme")
@Slf4j
@Tag(name = "景点主题管理")
public class ThemeController {

    @Autowired
    private AttractionThemeService attractionThemeService;

    @PostMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页获取酒店信息")
    public Result<IPage<AttractionTheme>> getAttractionThemePage(@PathVariable("pageNum") Long pageNum,
                                                           @PathVariable("pageSize") Long pageSize,
                                                           @RequestBody(required = false) ThemeDTO dto){
        IPage<AttractionTheme> page = new Page<>(pageNum, pageSize);
        return Result.ok(attractionThemeService.getAttractionThemePage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除酒店信息")
    public Result deleteAttractionThemeById(@PathVariable("id") Long id){
        attractionThemeService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新酒店信息")
    public Result saveOrUpdateAttractionTheme(@RequestBody AttractionTheme dto){
        attractionThemeService.saveOrUpdate(dto);
        return Result.ok();
    }

}
