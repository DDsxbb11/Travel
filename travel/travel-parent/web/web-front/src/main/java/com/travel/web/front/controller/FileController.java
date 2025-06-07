package com.travel.web.front.controller;

import com.travel.common.exce.GlobalException;
import com.travel.common.result.Result;
import com.travel.common.result.ResultCodeEnum;
import com.travel.web.front.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Amoretto
 * @description: TODO: 文件上传
 * @date: 2025-04-25 21:06
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        if (file==null){
            throw new GlobalException(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.ok(fileService.upload(file));
    }
}

