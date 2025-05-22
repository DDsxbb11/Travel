package com.travel.web.front.controller;

import com.travel.common.result.Result;
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
@Tag(name = "文件上传")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> upload(@RequestParam("file") MultipartFile file){
        String result=fileService.upload(file);
        return Result.ok(result);
    }
}
