package com.travel.web.admin.service.impl;

import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import com.travel.common.utilis.UploadImageToMinioUtility;
import com.travel.web.admin.service.FileService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-31 14:25
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
