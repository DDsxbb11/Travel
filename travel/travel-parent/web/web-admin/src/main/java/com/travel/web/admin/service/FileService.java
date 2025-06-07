package com.travel.web.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-05-31 14:24
 */

public interface FileService {
    String upload(MultipartFile file);
}
