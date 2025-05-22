package com.travel.web.front.vo.attraction;

import com.travel.model.pojo.AttractionImg;
import com.travel.model.pojo.AttractionInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 订单详情
 * @date: 2025-03-15 17:47
 */
@Data
public class AttractionDetailVo extends AttractionInfo {

    @Schema(description = "景点图片")
    private List<AttractionImg> pictureList;

    @Schema(description = "收藏ID")
    private Boolean isCollect;
}
