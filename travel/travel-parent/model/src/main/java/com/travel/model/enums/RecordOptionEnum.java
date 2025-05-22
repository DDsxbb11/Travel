package com.travel.model.enums;

import lombok.Getter;

/**
 * @author: JYX
 * @description: TODO: 日记选择
 * @date: 2025-03-17 0:27
 */

@Getter
public enum RecordOptionEnum {

    ALL("all","全部"),
    MY("my","我的"),
    NEW("new","最新"),
    HOT("hot","最热");

    private String code;
    private String desc;

    RecordOptionEnum(String code,String desc){
        this.code=code;
        this.desc=desc;
    }

}
