package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.FacilityInfo;
import com.travel.web.admin.dto.facility.FacilityDTO;
import com.travel.web.admin.dto.theme.ThemeDTO;
import com.travel.web.admin.service.AttractionThemeService;
import com.travel.web.admin.service.FacilityInfoService;
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
@RequestMapping("/facility")
@Slf4j
@Tag(name = "景点主题管理")
public class FacilityController {

    @Autowired
    private FacilityInfoService facilityInfoService;

    @PostMapping("")
    @Operation(summary = "分页获取配套设施")
    public Result<IPage<FacilityInfo>> getFacilityInfoPage(@RequestBody FacilityDTO dto){
        IPage<FacilityInfo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return Result.ok(facilityInfoService.getFacilityInfoPage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除配套设施")
    public Result deleteFacilityInfoById(@PathVariable("id") Long id){
        facilityInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新酒店信息")
    public Result saveOrUpdateFacilityInfo(@RequestBody FacilityInfo dto){
        facilityInfoService.saveOrUpdate(dto);
        return Result.ok();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id获取配套设施")
    public Result<FacilityInfo> getFacilityInfoById(@PathVariable("id") Long id){
        return Result.ok(facilityInfoService.getById(id));
    }

    @PostMapping("/getFacilityByType")
    @Operation(summary = "根据类型获取配套设施")
    public Result<List<FacilityInfo>> getFacilityByType(@RequestBody FacilityDTO dto){
        return Result.ok(facilityInfoService.getFacilityByType(dto));
    }

}
