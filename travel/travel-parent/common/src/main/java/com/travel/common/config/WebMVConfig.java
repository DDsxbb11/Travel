package com.travel.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Amoretto
 * @description: TODO: mvc配置类——解决跨越问题
 * @date: 2025-04-02 16:46
 */

@Configuration
public class WebMVConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("http://localhost:9528") // 明确指定允许的来源
                // 跨域允许时间
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否允许证书
                .allowedHeaders("*");
    }
}
