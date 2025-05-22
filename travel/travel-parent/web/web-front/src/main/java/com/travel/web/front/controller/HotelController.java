package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.web.front.dto.hotel.HotelDTO;
import com.travel.web.front.service.HotelInfoService;
import com.travel.web.front.vo.hotel.HotelDetailVo;
import com.travel.web.front.vo.hotel.HotelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: JYX
 * @description: TODO:
 * @date: 2025-03-15 23:01
 */

@RestController
@RequestMapping("/hotel")
@Slf4j
@Tag(name = "酒店模块")
public class HotelController {

    @Autowired
    private HotelInfoService hotelInfoService;

    @PostMapping("/{current}/{size}")
    @Operation(summary = "分页查询酒店信息")
    public Result<IPage<HotelVo>> getPage(@PathVariable("current") Long current,
                                       @PathVariable("size") Long size,
                                       @RequestBody(required = false) HotelDTO hotelDTO) {
        System.out.println("hotelDTO = " + hotelDTO);
        IPage<HotelVo> page = new Page<>(current,size);
        IPage<HotelVo> result=hotelInfoService.getPage(page,hotelDTO);
        return Result.ok(result);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id查询酒店信息")
    public Result<HotelDetailVo> getHotelById(@PathVariable("id") Long id,
                                              @RequestHeader(value = "Token",required = false) String token) {
        HotelDetailVo result = hotelInfoService.getHotelById(id,token);
        return Result.ok(result);
    }
}
