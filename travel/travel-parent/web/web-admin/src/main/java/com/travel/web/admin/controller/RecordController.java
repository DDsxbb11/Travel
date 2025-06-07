package com.travel.web.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.result.Result;
import com.travel.model.pojo.StrategyInfo;
import com.travel.web.admin.dto.record.RecordDTO;
import com.travel.web.admin.dto.strategy.StrategyDTO;
import com.travel.web.admin.service.RecordInfoService;
import com.travel.web.admin.vo.record.RecordVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-30 19:17
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordInfoService recordInfoService;

    @PostMapping("/getRecordByPage")
    public Result<IPage<RecordVo>> getRecordInfoByPage(@RequestBody RecordDTO dto){
        IPage<RecordVo> page=new Page<>(dto.getPageNum(),dto.getPageSize());
        return Result.ok(recordInfoService.getRecordInfoByPage(page,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除景点信息")
    public Result deleteRecordById(@PathVariable("id") Long id){
        recordInfoService.removeById(id);
        return Result.ok();
    }
}
