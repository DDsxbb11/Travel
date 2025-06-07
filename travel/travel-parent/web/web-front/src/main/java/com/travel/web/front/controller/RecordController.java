package com.travel.web.front.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.RecordInfo;
import com.travel.web.front.dto.record.RecordDTO;
import com.travel.web.front.dto.record.RecordSavaDTO;
import com.travel.web.front.service.RecordInfoService;
import com.travel.web.front.vo.record.RecordDetailVo;
import com.travel.web.front.vo.record.RecordVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: JYX
 * @description: TODO:
 * @date: 2025-03-17 0:23
 */

@RestController
@RequestMapping("/record")
@Slf4j
@Tag(name = "日记模块", description = "日记模块")
public class RecordController {

    @Autowired
    private RecordInfoService recordInfoService;

    @PostMapping("/{current}/{size}")
    @Operation(summary = "分页查询日记")
    public Result<IPage<RecordVo>> getPage(@PathVariable("current") Integer current,
                                             @PathVariable("size") Integer size,
                                             @RequestHeader(name = "Token",required = false) String token,
                                             @RequestBody(required = false) RecordDTO recordDTO) {
        IPage<RecordVo> page=new Page<>(current,size);
        IPage<RecordVo> result=recordInfoService.getPage(page,recordDTO,token);
        return Result.ok(result);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "查询日记详情")
    public Result<RecordDetailVo> getRecordDetailById(@PathVariable("id") Long id,
                                                      @RequestHeader(name = "Token",required = false) String token) {
        RecordDetailVo result=recordInfoService.getRecordDetailById(id,token);
        return Result.ok(result);
    }

    @PostMapping("/save")
    @Operation(summary = "保存日记")
    public Result saveRecord(@RequestBody RecordSavaDTO dto,
                       @RequestHeader String token){
        recordInfoService.saveRecord(dto,token);
        return Result.ok();
    }

}
