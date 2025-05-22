package com.travel.common.exce;

import com.travel.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author: JYX
 * @description: TODO: 全局异常处理
 * @date: 2024-11-02 12:59
 */

@Data
public class GlobalException extends RuntimeException {

    //异常状态码
    private Integer code;
    private String message;
    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public GlobalException(String message, Integer code) {
        super(message);
        this.code = code;
        this.message=message;
    }

    /**
     * 根据响应结果枚举对象创建异常对象
     * @param resultCodeEnum
     */
    public GlobalException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "LeaseException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}