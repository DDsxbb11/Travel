package com.travel.web.admin;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: JYX
 * @description: TODO: ${description}
 * @date: 2025-03-14 19:05
 */

@SpringBootApplication
@ComponentScan("com.travel")
@MapperScan("com.travel.web.admin.mapper")
public class WebAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class,args);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //添加版本号插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防止全表更新和删除拦截器
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }
}