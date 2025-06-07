package com.travel.web.admin.vo.location;

import lombok.Data;

import java.util.List;

/**
 * @author: JYX
 * @description: TODO: 地理信息
 * @date: 2025-03-16 14:07
 */
@Data
public class OptionVo {
    private String value;
    private String label;
    private List<OptionVo> children;
}
