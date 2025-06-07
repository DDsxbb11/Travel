package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.AttractionTheme;
import com.travel.model.pojo.RoomInfo;
import com.travel.web.admin.dto.hotel.RoomDTO;
import com.travel.web.admin.dto.theme.ThemeDTO;
import com.travel.web.admin.service.AttractionThemeService;
import com.travel.web.admin.service.RoomInfoService;
import com.travel.web.admin.vo.hotel.RoomDetailVo;
import com.travel.web.admin.vo.hotel.RoomVo;
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
@RequestMapping("/room")
@Slf4j
@Tag(name = "房间管理")
public class RoomController {

    @Autowired
    private RoomInfoService roomInfoService;

    @PostMapping("")
    @Operation(summary = "分页获取酒店信息")
    public Result<IPage<RoomVo>> getAttractionThemePage(@RequestBody RoomDTO dto){
        IPage<RoomVo> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        return Result.ok(roomInfoService.getRoomInfoPage(page, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除酒店信息")
    public Result deleteRoomInfoById(@PathVariable("id") Long id){
        roomInfoService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或更新酒店信息")
    public Result saveOrUpdateRoomInfo(@RequestBody RoomDetailVo dto){
        roomInfoService.saveOrUpdateRoomInfo(dto);
        return Result.ok();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "根据id查询酒店信息")
    public Result<RoomDetailVo> getRoomInfoById(@PathVariable("id") Long id){
        return Result.ok(roomInfoService.getRoomInfoById(id));
    }

}
