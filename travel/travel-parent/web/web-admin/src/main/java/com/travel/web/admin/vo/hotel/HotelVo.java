package com.travel.web.admin.vo.hotel;

import com.travel.model.pojo.HotelInfo;
import com.travel.model.pojo.PolicyInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-06-01 17:22
 */
@Data
public class HotelVo extends HotelInfo {
    private List<Long> failityList;
    private List<PolicyInfo> policyInfoList;
}
