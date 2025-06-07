package com.travel.web.admin.controller;

import com.travel.common.result.Result;
import com.travel.model.pojo.*;
import com.travel.web.admin.service.*;
import com.travel.web.admin.vo.location.OptionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO: 过滤条件
 * @date: 2025-05-28 12:44
 */
@RestController
@RequestMapping("/option")
@Slf4j
@Tag(name = "过滤条件", description = "过滤条件")
public class OptionController {

    @Autowired
    private AttractionThemeService attractionThemeService;
    @Autowired
    private ProvinceInfoService provinceInfoService;
    @Autowired
    private CityInfoService cityInfoService;
    @Autowired
    private DistrictInfoService districtInfoService;
    @Autowired
    private LableInfoService lableInfoService;

    @GetMapping("/theme")
    @Operation(summary = "获取主题列表")
    public Result<List<AttractionTheme>> getThemeList() {
        return Result.ok(attractionThemeService.list());
    }

    @GetMapping("/province")
    @Operation(summary = "获取省份列表")
    public Result<List<ProvinceInfo>> getProvinceList() {
        return Result.ok(provinceInfoService.list());
    }

    @GetMapping("/city/{id}")
    @Operation(summary = "获取城市列表")
    public Result<List<CityInfo>> getCityListByProvinceId(@PathVariable("id") Long id) {
        List<CityInfo> result= cityInfoService.getCityListByProvinceId(id);
        return Result.ok(result);
    }

    @GetMapping("/district/{id}")
    @Operation(summary = "获取区县列表")
    public Result<List<DistrictInfo>> getDistrictByCityId(@PathVariable("id") Long id) {
        List<DistrictInfo> result= districtInfoService.getDistrictByCityId(id);
        return Result.ok(result);
    }

    @GetMapping("getProvinceAndCityCascade")
    @Operation(summary = "获取省市联级")
    public Result<List<OptionVo>> getProvinceAndCityCascade() {
        List<OptionVo> result=provinceInfoService.getProvinceAndCityCascade();
        return Result.ok(result);
    }

    @GetMapping("getProvinceAndCityAndDistrictCascade")
    @Operation(summary = "获取省市县联级")
    public Result<List<OptionVo>> getProvinceAndCityAndDistrictCascade() {
        List<OptionVo> result=provinceInfoService.getProvinceAndCityAndDistrictCascade();
        return Result.ok(result);
    }

    @GetMapping("/getAllStrategyLable")
    @Operation(summary = "获取攻略标签")
    public Result<List<LableInfo>> getAllStrategyLable() {
        return Result.ok(lableInfoService.list());
    }
}
