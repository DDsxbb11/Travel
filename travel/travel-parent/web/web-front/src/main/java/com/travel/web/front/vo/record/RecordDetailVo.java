package com.travel.web.front.vo.record;

import com.travel.model.pojo.RecordInfo;
import com.travel.model.pojo.RouteInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 日记详情
 * @date: 2025-03-17 0:45
 */

@Data
public class RecordDetailVo extends RecordInfo {
    @Schema(description = "作者名称")
    private String authorName;

    @Schema(description = "作者头像")
    private String authorAvatar;

    @Schema(description = "记录")
    private List<RouteInfo> routeList;

    @Schema(description = "是否收藏")
    private Boolean isCollect;
}
