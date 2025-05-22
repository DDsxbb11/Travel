package com.travel.model.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 图片
 * @date: 2025-03-15 17:57
 */

@Getter
public enum PictureTypeEnum {
    HEADER("header","景点首图"),
    SIMPLE("simple","景点普通图");

    private String code;
    private String name;

    private PictureTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }
}
