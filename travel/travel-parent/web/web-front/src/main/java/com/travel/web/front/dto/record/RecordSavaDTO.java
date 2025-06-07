package com.travel.web.front.dto.record;

import com.travel.model.pojo.RecordInfo;
import com.travel.model.pojo.RouteInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-05 21:12
 */
@Data
public class RecordSavaDTO extends RecordInfo {
    private List<RouteInfo> routeList;
}
