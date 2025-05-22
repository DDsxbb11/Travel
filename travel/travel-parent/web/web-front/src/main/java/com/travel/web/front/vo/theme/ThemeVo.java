package com.travel.web.front.vo.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: JYX
 * @description: TODO: 主题返回类型
 * @date: 2025-03-15 16:57
 */
@Data
public class ThemeVo {

    @Schema(description = "主题id")
    private Long id;

    @Schema(description = "主题名称")
    private String name;
}
