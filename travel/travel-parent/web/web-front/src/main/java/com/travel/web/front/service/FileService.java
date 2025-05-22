package com.travel.web.front.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-04-25 21:09
 */

public interface FileService {
    String upload(MultipartFile file);
}
