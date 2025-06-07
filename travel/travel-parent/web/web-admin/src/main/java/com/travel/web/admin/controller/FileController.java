package com.travel.web.admin.controller;

import com.travel.common.exce.GlobalException;
import com.travel.common.result.Result;
import com.travel.common.result.ResultCodeEnum;
import com.travel.web.admin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-31 14:24
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
