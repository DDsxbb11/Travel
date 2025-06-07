package com.travel.web.front.service.impl;

import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.UploadImageToMinioUtility;
import com.travel.web.front.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Amoretto
 * @description: TODO: 文件上传
 * @date: 2025-04-25 21:09
 */

@Service
public class FileServiceImpl implements FileService {

    private static final String FILE_PATH="travel";

    @Autowired
    private UploadImageToMinioUtility minioUtility;

    @Override
    public String upload(MultipartFile file) {
        try {
            return minioUtility.upload(file,FILE_PATH);
        } catch (Exception e) {
            throw new GlobalException(ResultCodeEnum.FILE_ERROR);
        }
    }
}
