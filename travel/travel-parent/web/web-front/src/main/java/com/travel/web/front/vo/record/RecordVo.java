package com.travel.web.front.vo.record;

import com.travel.model.pojo.RecordInfo;
import lombok.Data;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-04-22 14:22
 */

@Data
public class RecordVo {
    private Long id;
    private String name;
    private String imgUrl;
    private String createTime;
    private String likeCount;
    private String desc;
}
