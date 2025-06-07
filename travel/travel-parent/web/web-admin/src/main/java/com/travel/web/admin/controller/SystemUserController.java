package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.SystemUser;
import com.travel.web.admin.dto.StatusDTO;
import com.travel.web.admin.dto.tourist.TouristDTO;
import com.travel.web.admin.service.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-31 18:02
 */
@RestController
@RequestMapping("/systemUser")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping()
    @Operation(summary = "分页查询系统用户信息")
    public Result<IPage<SystemUser>> getSystemUserInfoList(@RequestBody TouristDTO dto){
        IPage<SystemUser> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<SystemUser> result=systemUserService.getSystemUserInfoList(page,dto);
        return Result.ok(result);
    }

    @PostMapping("/updateStatus")
    @Operation(summary = "修改系统用户状态")
    public Result updateSystemUserStatus(@RequestBody StatusDTO dto){
        systemUserService.updateSystemUserStatus(dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "系统用户")
    public Result deleteTouristById(@PathVariable("id") Long id){
        systemUserService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/updatePassword")
    @Operation(summary = "修改系统用户密码")
    public Result updatePassword(@RequestBody SystemUser info){
        systemUserService.updatePassword(info);
        return Result.ok();
    }
}
