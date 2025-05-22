package com.travel.common.config;

/**
 * @author: JYX
 * @description: TODO: 短信服务配置类
 * @date: 2024-11-10 15:03
 */

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
public class SampleConfig {

    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     * @return Client
     *
     * @throws Exception
     */
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;


    @Bean
    public Client client(){
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        Config config = new Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(accessKeyId)
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(accessKeySecret)
                // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                .setEndpoint(endpoint);
        try {
            return new Client(config);
        }catch (Exception e){
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
    }

}

