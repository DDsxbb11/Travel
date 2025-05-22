package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionTheme;
import com.travel.web.front.dto.attraction.AttractionDTO;
import com.travel.web.front.service.AttractionThemeService;
import com.travel.web.front.vo.attraction.AttractionDetailVo;
import com.travel.web.front.service.AttractionInfoService;
import com.travel.web.front.vo.attraction.AttractionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 旅游景点
 * @date: 2025-03-14 19:57
 */

@RestController
@RequestMapping("/attraction")
@Slf4j
@Tag(name = "旅游景点")
public class AttractionController {

    @Autowired
    private AttractionInfoService attractionInfoService;
    @Autowired
    private AttractionThemeService themeService;


    @PostMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页获取景点信息")
    public Result<IPage<AttractionVo>> getAttractionInfoPage(@PathVariable("pageNum") Long pageNum,
                                              @PathVariable("pageSize") Long pageSize,
                                              @RequestBody(required = false) AttractionDTO attractionDTO){
        IPage<AttractionVo> page = new Page<>(pageNum, pageSize);
        return Result.ok(attractionInfoService.getAttractionInfoPage(page, attractionDTO));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "获取景点详情")
    public Result<AttractionDetailVo> getAttractionDetail(@RequestHeader(name = "Token",required = false) String token,
                                                          @PathVariable("id") Long id){
        AttractionDetailVo result=attractionInfoService.getAttractionDetail(token,id);
        return Result.ok(result);
    }

    @GetMapping("/theme")
    @Operation(summary = "查询景点主题")
    public Result<List<AttractionTheme>> getAttractionTheme() {
        List<AttractionTheme> result=themeService.getAttractionTheme();
        return Result.ok(result);
    }
}
