package com.travel.web.front.controller;

import com.travel.common.result.Result;
import com.travel.web.front.service.ProvinceInfoService;
import com.travel.web.front.vo.location.OptionVo;
import com.travel.web.front.vo.province.ProvinceVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 省份
 * @date: 2025-03-14 23:35
 */
@RestController
@RequestMapping("/address")
@Slf4j
@Tag(name = "省份信息")
public class AddressController {

    @Autowired
    private ProvinceInfoService provinceInfoService;

    @GetMapping("/province")
    @Operation(summary = "获取所有省份")
    public Result<List<ProvinceVo>> getProvinceList(){
        return Result.ok(provinceInfoService.getProvinceList());
    }

    @GetMapping("/options")
    @Operation(summary = "获取地区信息")
    public Result<List<OptionVo>> getLocationOptions(){
        List<OptionVo> result=provinceInfoService.getLocationOptions();
        return Result.ok(result);
    }

}
