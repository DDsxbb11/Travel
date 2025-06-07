package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionInfo;
import com.travel.web.admin.dto.attraction.AttractionDTO;
import com.travel.web.admin.dto.attraction.AttractionInfoDTO;
import com.travel.web.admin.service.AttractionInfoService;
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
@RequestMapping("/attraction")
@Slf4j
@Tag(name = "旅游景点")
public class AttractionController {

    @Autowired
    private AttractionInfoService attractionInfoService;

    @PostMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页获取景点信息")
    public Result<IPage<AttractionInfo>> getAttractionInfoPage(@PathVariable("pageNum") Long pageNum,
                                                               @PathVariable("pageSize") Long pageSize,
                                                               @RequestBody(required = false) AttractionDTO dto){
        IPage<AttractionInfo> page = new Page<>(pageNum, pageSize);
        return Result.ok(attractionInfoService.getAttractionInfoPage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除景点信息")
    public Result deleteAttractionById(@PathVariable("id") Long id){
        attractionInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新景点信息")
    public Result saveOrUpdateAttraction(@RequestBody AttractionInfoDTO dto){
        attractionInfoService.saveOrUpdateAttraction(dto);
        return Result.ok();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id获取景点信息")
    public Result<AttractionInfoDTO> getAttractionInfoById(@PathVariable("id") Long id){
        return Result.ok(attractionInfoService.getAttractionInfoById(id));
    }

}
