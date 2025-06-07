package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.UserInfo;
import com.travel.web.admin.dto.StatusDTO;
import com.travel.web.admin.dto.order.OrderDTO;
import com.travel.web.admin.dto.tourist.TouristDTO;
import com.travel.web.admin.service.UserInfoService;
import com.travel.web.admin.vo.order.AttractionOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 18:22
 */
@RestController
@RequestMapping("/tourist")
@Slf4j
public class TouristController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping()
    @Operation(summary = "分页查询游客信息")
    public Result<IPage<UserInfo>> getUserInfoList(@RequestBody TouristDTO dto){
        IPage<UserInfo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<UserInfo> result=userInfoService.getUserInfoList(page,dto);
        return Result.ok(result);
    }

    @PostMapping("/updateStatus")
    @Operation(summary = "修改游客状态")
    public Result updateTouristStatus(@RequestBody StatusDTO dto){
        userInfoService.updateTouristStatus(dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除游客")
    public Result deleteTouristById(@PathVariable("id") Long id){
        userInfoService.removeById(id);
        return Result.ok();
    }
}
