package com.travel.web.front.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Amoretto
 * @description: TODO: 图片验证码
 * @date: 2025-04-19 12:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVo {

    @Schema(description="验证码图片信息")
    private String image;

    @Schema(description="验证码key")
    private String key;
}
