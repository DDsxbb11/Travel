package com.travel.web.front.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-04-02 16:53
 */

@RestController
@RequestMapping("/queryOption")
@Slf4j
@Tag(name = "查询选项控制器", description = "提供查询选项相关的接口")
public class QueryOptionController {
}
