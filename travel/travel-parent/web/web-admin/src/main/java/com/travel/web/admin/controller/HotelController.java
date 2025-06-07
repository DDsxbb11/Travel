package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.HotelInfo;
import com.travel.model.pojo.PolicyInfo;
import com.travel.web.admin.dto.hotel.HotelDTO;
import com.travel.web.admin.service.HotelInfoService;
import com.travel.web.admin.service.PolicyInfoService;
import com.travel.web.admin.vo.hotel.HotelVo;
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
@RequestMapping("/hotel")
@Slf4j
@Tag(name = "旅游景点")
public class HotelController {

    @Autowired
    private HotelInfoService hotelInfoService;
    @Autowired
    private PolicyInfoService policyInfoService;

    @PostMapping("/{pageNum}/{pageSize}")
    @Operation(summary = "分页获取酒店信息")
    public Result<IPage<HotelInfo>> getHotelInfoPage(@PathVariable("pageNum") Long pageNum,
                                                     @PathVariable("pageSize") Long pageSize,
                                                     @RequestBody(required = false) HotelDTO dto){
        IPage<HotelInfo> page = new Page<>(pageNum, pageSize);
        return Result.ok(hotelInfoService.getHotelInfoPage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除酒店信息")
    public Result deleteHotelInfoById(@PathVariable("id") Long id){
        hotelInfoService.removeById(id);
        return Result.ok();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id获取酒店信息")
    public Result<HotelVo> getHotelInfoById(@PathVariable("id") Long id){
        return Result.ok(hotelInfoService.getHotelInfoById(id));
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新酒店信息")
    public Result saveOrUpdateHotelInfo(@RequestBody HotelVo hotelVo){
        hotelInfoService.saveOrUpdateHotelInfo(hotelVo);
        return Result.ok();
    }

    @PostMapping("savePolicy")
    @Operation(summary = "保存或更新酒店政策信息")
    public Result saveOrUpdateHotelPolicy(@RequestBody PolicyInfo info){
        policyInfoService.save(info);
        return Result.ok();
    }

    @DeleteMapping("/policy/{id}")
    @Operation(summary = "删除酒店政策信息")
    public Result deleteHotelPolicyById(@PathVariable("id") Long id){
        policyInfoService.removeById(id);
        return Result.ok();
    }
}
